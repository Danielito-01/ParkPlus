/*
    ParkPlus - esquema completo para Microsoft SQL Server
    Archivo recomendado para el repositorio: database/ParkPlus_schema.sql

    OBJETIVO
    - Crear la base de datos ParkPlus si todavía no existe.
    - Recrear las tablas del proyecto vacías.
    - Crear llaves, restricciones, índices y triggers de integridad.

    ADVERTENCIA
    - Este script es DESTRUCTIVO para las tablas de ParkPlus.
    - Si las tablas ya existen, se eliminan junto con sus datos y se crean de nuevo.
    - No contiene usuarios, contraseñas ni rutas físicas de archivos MDF/LDF.

    ORDEN RECOMENDADO DESPUÉS DE EJECUTARLO
    1. Cargar areas.txt.
    2. Cargar spots_corregidos.txt.
*/

USE [master];
GO

IF DB_ID(N'ParkPlus') IS NULL
BEGIN
    EXEC (N'CREATE DATABASE [ParkPlus]');
END;
GO

ALTER DATABASE [ParkPlus] SET RECOVERY SIMPLE;
GO

USE [ParkPlus];
GO

SET ANSI_NULLS ON;
SET QUOTED_IDENTIFIER ON;
SET NOCOUNT ON;
SET XACT_ABORT ON;
GO

BEGIN TRY
    BEGIN TRANSACTION;

    /* =========================================================
       1. LIMPIEZA DE OBJETOS EXISTENTES
       ========================================================= */
    DROP TABLE IF EXISTS dbo.ticket;
    DROP TABLE IF EXISTS dbo.usuario_vehiculo;
    DROP TABLE IF EXISTS dbo.spot;
    DROP TABLE IF EXISTS dbo.vehiculo;
    DROP TABLE IF EXISTS dbo.usuario;
    DROP TABLE IF EXISTS dbo.area;

    /* =========================================================
       2. ÁREAS DEL PARQUEO
       ========================================================= */
    CREATE TABLE dbo.area
    (
        id              INT IDENTITY(1,1) NOT NULL,
        codigo          VARCHAR(20) NOT NULL,
        nombre          VARCHAR(20) NOT NULL,
        capacidad       INT NOT NULL,
        tipodevehiculo  VARCHAR(10) NOT NULL,

        CONSTRAINT PK_area
            PRIMARY KEY CLUSTERED (id),

        CONSTRAINT UQ_area_codigo
            UNIQUE (codigo),

        CONSTRAINT UQ_area_nombre
            UNIQUE (nombre),

        CONSTRAINT CK_area_codigo_no_vacio
            CHECK (LEN(LTRIM(RTRIM(codigo))) > 0),

        CONSTRAINT CK_area_nombre_no_vacio
            CHECK (LEN(LTRIM(RTRIM(nombre))) > 0),

        CONSTRAINT CK_area_capacidad
            CHECK (capacidad > 0),

        CONSTRAINT CK_area_nombre
            CHECK (UPPER(LTRIM(RTRIM(nombre))) IN
                   ('ESTUDIANTES', 'DOCENTES', 'MOTOS')),

        CONSTRAINT CK_area_tipo
            CHECK
            (
                (
                    UPPER(LTRIM(RTRIM(nombre))) IN
                    ('ESTUDIANTES', 'DOCENTES')
                    AND UPPER(LTRIM(RTRIM(tipodevehiculo))) = 'CARRO'
                )
                OR
                (
                    UPPER(LTRIM(RTRIM(nombre))) = 'MOTOS'
                    AND UPPER(LTRIM(RTRIM(tipodevehiculo))) = 'MOTO'
                )
            )
    );

    /* =========================================================
       3. USUARIOS
       - El carnet no puede repetirse.
       ========================================================= */
    CREATE TABLE dbo.usuario
    (
        id           INT IDENTITY(1,1) NOT NULL,
        carnet       VARCHAR(20) NOT NULL,
        telefono     VARCHAR(20) NULL,
        nombre       VARCHAR(100) NULL,
        apellido     VARCHAR(100) NULL,
        tipoUsuario  VARCHAR(20) NULL,
        carrera      VARCHAR(100) NULL,
        semestre     VARCHAR(20) NULL,

        CONSTRAINT PK_usuario
            PRIMARY KEY CLUSTERED (id),

        CONSTRAINT UQ_usuario_carnet
            UNIQUE (carnet),

        CONSTRAINT CK_usuario_carnet_no_vacio
            CHECK (LEN(LTRIM(RTRIM(carnet))) > 0),

        CONSTRAINT CK_usuario_tipo_no_vacio
            CHECK
            (
                tipoUsuario IS NULL
                OR LEN(LTRIM(RTRIM(tipoUsuario))) > 0
            )
    );

    /* =========================================================
       4. VEHÍCULOS
       - La placa identifica de forma única al vehículo.
       ========================================================= */
    CREATE TABLE dbo.vehiculo
    (
        id      INT IDENTITY(1,1) NOT NULL,
        placa   VARCHAR(20) NOT NULL,
        color   VARCHAR(50) NULL,
        tipo    VARCHAR(20) NOT NULL,

        CONSTRAINT PK_vehiculo
            PRIMARY KEY CLUSTERED (id),

        CONSTRAINT UQ_vehiculo_placa
            UNIQUE (placa),

        CONSTRAINT CK_vehiculo_placa_no_vacia
            CHECK (LEN(LTRIM(RTRIM(placa))) > 0),

        CONSTRAINT CK_vehiculo_tipo
            CHECK (UPPER(LTRIM(RTRIM(tipo))) IN ('CARRO', 'MOTO'))
    );

    /* =========================================================
       5. SPOTS
       - Código único.
       - Área obligatoria y existente.
       - El trigger posterior valida tipo y capacidad.
       ========================================================= */
    CREATE TABLE dbo.spot
    (
        id              INT IDENTITY(1,1) NOT NULL,
        codigo          VARCHAR(20) NOT NULL,
        codigodearea    VARCHAR(20) NOT NULL,
        tipodevehiculo  VARCHAR(10) NOT NULL,
        estado          BIT NOT NULL
            CONSTRAINT DF_spot_estado DEFAULT (0),

        CONSTRAINT PK_spot
            PRIMARY KEY CLUSTERED (id),

        CONSTRAINT UQ_spot_codigo
            UNIQUE (codigo),

        CONSTRAINT CK_spot_codigo_no_vacio
            CHECK (LEN(LTRIM(RTRIM(codigo))) > 0),

        CONSTRAINT CK_spot_tipodevehiculo
            CHECK
            (
                UPPER(LTRIM(RTRIM(tipodevehiculo)))
                IN ('CARRO', 'MOTO')
            ),

        CONSTRAINT FK_spot_area
            FOREIGN KEY (codigodearea)
            REFERENCES dbo.area (codigo)
    );

    CREATE INDEX IX_spot_codigodearea
        ON dbo.spot (codigodearea);

    /* =========================================================
       6. ASOCIACIÓN USUARIO - VEHÍCULO
       - No se puede repetir la misma pareja usuario/vehículo.
       - Solo se aceptan los roles Propietario o Tercero.
       - Al borrar usuario o vehículo se elimina la asociación.
       - Un trigger limita a un propietario por vehículo.
       ========================================================= */
    CREATE TABLE dbo.usuario_vehiculo
    (
        idUsuario   INT NOT NULL,
        idVehiculo  INT NOT NULL,
        rol         VARCHAR(20) NOT NULL,

        CONSTRAINT PK_usuario_vehiculo
            PRIMARY KEY CLUSTERED (idUsuario, idVehiculo),

        CONSTRAINT CK_usuario_vehiculo_rol
            CHECK
            (
                UPPER(LTRIM(RTRIM(rol)))
                IN ('PROPIETARIO', 'TERCERO')
            ),

        CONSTRAINT FK_usuario_vehiculo_usuario
            FOREIGN KEY (idUsuario)
            REFERENCES dbo.usuario (id)
            ON UPDATE CASCADE
            ON DELETE CASCADE,

        CONSTRAINT FK_usuario_vehiculo_vehiculo
            FOREIGN KEY (idVehiculo)
            REFERENCES dbo.vehiculo (id)
            ON UPDATE CASCADE
            ON DELETE CASCADE
    );

    CREATE INDEX IX_usuario_vehiculo_idVehiculo
        ON dbo.usuario_vehiculo (idVehiculo);

    /* =========================================================
       7. TICKETS
       - Mantiene los nombres de columnas utilizados por Java.
       - DECIMAL se usa para dinero en lugar de FLOAT.
       ========================================================= */
    CREATE TABLE dbo.ticket
    (
        id                 INT IDENTITY(1,1) NOT NULL,
        placaVehiculo      VARCHAR(20) NOT NULL,
        carnetUsuario      VARCHAR(20) NOT NULL,
        tipoUsuario        VARCHAR(20) NOT NULL,
        tipoVehiculo       VARCHAR(20) NOT NULL,
        codigoSpot         VARCHAR(20) NOT NULL,
        codigoArea         VARCHAR(20) NOT NULL,
        fechaHoraIngreso   DATETIME NOT NULL
            CONSTRAINT DF_ticket_fechaHoraIngreso DEFAULT (SYSDATETIME()),
        fechaHoraSalida    DATETIME NULL,
        tarifaAplicada     VARCHAR(30) NOT NULL,
        monto              DECIMAL(10,2) NULL
            CONSTRAINT DF_ticket_monto DEFAULT (0),
        metodoPago         VARCHAR(20) NOT NULL,
        estado             VARCHAR(20) NOT NULL,

        CONSTRAINT PK_ticket
            PRIMARY KEY CLUSTERED (id),

        CONSTRAINT CK_ticket_placa_no_vacia
            CHECK (LEN(LTRIM(RTRIM(placaVehiculo))) > 0),

        CONSTRAINT CK_ticket_carnet_no_vacio
            CHECK (LEN(LTRIM(RTRIM(carnetUsuario))) > 0),

        CONSTRAINT CK_ticket_tipoVehiculo
            CHECK
            (
                UPPER(LTRIM(RTRIM(tipoVehiculo)))
                IN ('CARRO', 'MOTO')
            ),

        CONSTRAINT CK_ticket_monto
            CHECK (monto IS NULL OR monto >= 0),

        CONSTRAINT CK_ticket_fechas
            CHECK
            (
                fechaHoraSalida IS NULL
                OR fechaHoraSalida >= fechaHoraIngreso
            )
    );

    CREATE INDEX IX_ticket_carnetUsuario
        ON dbo.ticket (carnetUsuario);

    CREATE INDEX IX_ticket_fechaHoraIngreso
        ON dbo.ticket (fechaHoraIngreso);

    /* Un vehículo no puede tener dos tickets abiertos simultáneos. */
    CREATE UNIQUE INDEX UX_ticket_placa_activa
        ON dbo.ticket (placaVehiculo)
        WHERE fechaHoraSalida IS NULL;

    /* Un spot no puede tener dos tickets abiertos simultáneos. */
    CREATE UNIQUE INDEX UX_ticket_spot_activo
        ON dbo.ticket (codigoSpot)
        WHERE fechaHoraSalida IS NULL;

    COMMIT TRANSACTION;
END TRY
BEGIN CATCH
    IF @@TRANCOUNT > 0
        ROLLBACK TRANSACTION;

    THROW;
END CATCH;
GO

/* =============================================================
   8. TRIGGER: VALIDAR SPOT, ÁREA Y CAPACIDAD
   ============================================================= */
CREATE OR ALTER TRIGGER dbo.trg_spot_validar_area_capacidad
ON dbo.spot
AFTER INSERT, UPDATE
AS
BEGIN
    SET NOCOUNT ON;
    SET XACT_ABORT ON;

    /* Bloquea las áreas afectadas mientras se valida la capacidad. */
    DECLARE @areasBloqueadas BIGINT;

    SELECT @areasBloqueadas = COUNT_BIG(*)
    FROM dbo.area AS a WITH (UPDLOCK, HOLDLOCK)
    INNER JOIN
    (
        SELECT DISTINCT codigodearea
        FROM inserted
    ) AS afectadas
        ON afectadas.codigodearea = a.codigo;

    IF EXISTS
    (
        SELECT 1
        FROM inserted AS i
        INNER JOIN dbo.area AS a
            ON a.codigo = i.codigodearea
        WHERE UPPER(LTRIM(RTRIM(i.tipodevehiculo)))
              <> UPPER(LTRIM(RTRIM(a.tipodevehiculo)))
    )
    BEGIN
        THROW 50001,
            'El tipo de vehículo del spot no coincide con el tipo permitido por el área.',
            1;
    END;

    IF EXISTS
    (
        SELECT 1
        FROM
        (
            SELECT DISTINCT codigodearea
            FROM inserted
        ) AS afectadas
        INNER JOIN dbo.area AS a
            ON a.codigo = afectadas.codigodearea
        CROSS APPLY
        (
            SELECT COUNT_BIG(*) AS totalSpots
            FROM dbo.spot AS s WITH (HOLDLOCK)
            WHERE s.codigodearea = afectadas.codigodearea
        ) AS cantidades
        WHERE cantidades.totalSpots > a.capacidad
    )
    BEGIN
        THROW 50002,
            'No se puede guardar el spot: el área alcanzó su capacidad máxima.',
            1;
    END;
END;
GO

/* =============================================================
   9. TRIGGER: PROTEGER CAMBIOS EN ÁREAS
   ============================================================= */
CREATE OR ALTER TRIGGER dbo.trg_area_validar_cambios
ON dbo.area
AFTER UPDATE
AS
BEGIN
    SET NOCOUNT ON;
    SET XACT_ABORT ON;

    IF EXISTS
    (
        SELECT 1
        FROM inserted AS i
        CROSS APPLY
        (
            SELECT COUNT_BIG(*) AS totalSpots
            FROM dbo.spot AS s WITH (UPDLOCK, HOLDLOCK)
            WHERE s.codigodearea = i.codigo
        ) AS cantidades
        WHERE cantidades.totalSpots > i.capacidad
    )
    BEGIN
        THROW 50003,
            'No se puede reducir la capacidad: el área ya tiene más spots registrados.',
            1;
    END;

    IF EXISTS
    (
        SELECT 1
        FROM inserted AS i
        INNER JOIN dbo.spot AS s
            ON s.codigodearea = i.codigo
        WHERE UPPER(LTRIM(RTRIM(s.tipodevehiculo)))
              <> UPPER(LTRIM(RTRIM(i.tipodevehiculo)))
    )
    BEGIN
        THROW 50004,
            'No se puede cambiar el tipo del área porque existen spots incompatibles.',
            1;
    END;
END;
GO

/* =============================================================
   10. TRIGGER: UN SOLO PROPIETARIO POR VEHÍCULO
   ============================================================= */
CREATE OR ALTER TRIGGER dbo.trg_usuario_vehiculo_un_propietario
ON dbo.usuario_vehiculo
AFTER INSERT, UPDATE
AS
BEGIN
    SET NOCOUNT ON;
    SET XACT_ABORT ON;

    DECLARE @vehiculosBloqueados BIGINT;

    SELECT @vehiculosBloqueados = COUNT_BIG(*)
    FROM dbo.usuario_vehiculo AS uv WITH (UPDLOCK, HOLDLOCK)
    WHERE uv.idVehiculo IN
    (
        SELECT DISTINCT idVehiculo
        FROM inserted
    );

    IF EXISTS
    (
        SELECT uv.idVehiculo
        FROM dbo.usuario_vehiculo AS uv
        WHERE uv.idVehiculo IN
        (
            SELECT DISTINCT idVehiculo
            FROM inserted
        )
          AND UPPER(LTRIM(RTRIM(uv.rol))) = 'PROPIETARIO'
        GROUP BY uv.idVehiculo
        HAVING COUNT_BIG(*) > 1
    )
    BEGIN
        THROW 50005,
            'Un vehículo solamente puede tener un usuario con rol Propietario.',
            1;
    END;
END;
GO

/* =============================================================
   11. VERIFICACIÓN FINAL
   ============================================================= */
SELECT
    t.name AS tabla,
    SUM(p.rows) AS filas
FROM sys.tables AS t
INNER JOIN sys.partitions AS p
    ON p.object_id = t.object_id
   AND p.index_id IN (0, 1)
WHERE t.schema_id = SCHEMA_ID('dbo')
  AND t.name IN
  (
      'area',
      'spot',
      'usuario',
      'vehiculo',
      'usuario_vehiculo',
      'ticket'
  )
GROUP BY t.name
ORDER BY t.name;
GO

SELECT
    tr.name AS trigger_nombre,
    OBJECT_NAME(tr.parent_id) AS tabla,
    tr.is_disabled
FROM sys.triggers AS tr
WHERE tr.name IN
(
    'trg_spot_validar_area_capacidad',
    'trg_area_validar_cambios',
    'trg_usuario_vehiculo_un_propietario'
)
ORDER BY tr.name;
GO

PRINT 'Esquema de ParkPlus creado correctamente.';
PRINT 'Cargue primero areas.txt y después spots_corregidos.txt.';
GO

/*
    CONFIGURACIÓN OPCIONAL DEL USUARIO DE LA APLICACIÓN
    ---------------------------------------------------
    No se incluye una contraseña real dentro del repositorio.

    Ejecute manualmente, como administrador, cambiando la contraseña:

    USE [master];
    GO
    CREATE LOGIN [danipark]
        WITH PASSWORD = 'CAMBIAR_POR_UNA_CONTRASENA_SEGURA';
    GO

    USE [ParkPlus];
    GO
    CREATE USER [danipark] FOR LOGIN [danipark]
        WITH DEFAULT_SCHEMA = [dbo];
    GO
    ALTER ROLE [db_datareader] ADD MEMBER [danipark];
    ALTER ROLE [db_datawriter] ADD MEMBER [danipark];
    GO
*/
