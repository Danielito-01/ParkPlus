USE [master];
GO

IF DB_ID(N'ParkPlus') IS NULL
BEGIN
    EXEC(N'CREATE DATABASE [ParkPlus]');
END;
GO

ALTER DATABASE [ParkPlus] SET AUTO_CLOSE OFF;
GO

ALTER DATABASE [ParkPlus] SET AUTO_SHRINK OFF;
GO

ALTER DATABASE [ParkPlus] SET AUTO_UPDATE_STATISTICS ON;
GO

ALTER DATABASE [ParkPlus] SET RECOVERY SIMPLE;
GO

ALTER DATABASE [ParkPlus] SET PAGE_VERIFY CHECKSUM;
GO

ALTER DATABASE [ParkPlus] SET MULTI_USER;
GO

ALTER DATABASE [ParkPlus] SET READ_WRITE;
GO

USE [ParkPlus];
GO

SET ANSI_NULLS ON;
GO

SET QUOTED_IDENTIFIER ON;
GO


/* =========================================================
   TABLA: area
   ========================================================= */

CREATE TABLE [dbo].[area]
(
    [id]             INT IDENTITY(1,1) NOT NULL,
    [codigo]         VARCHAR(20) NOT NULL,
    [nombre]         VARCHAR(20) NOT NULL,
    [capacidad]      INT NOT NULL,
    [tipodevehiculo] VARCHAR(10) NOT NULL,

    CONSTRAINT [PK_area]
        PRIMARY KEY CLUSTERED ([id] ASC),

    CONSTRAINT [UQ_area_codigo]
        UNIQUE ([codigo]),

    CONSTRAINT [CK_area_capacidad]
        CHECK ([capacidad] > 0),

    CONSTRAINT [CK_area_nombre]
        CHECK (
            [nombre] IN (
                'MOTOS',
                'DOCENTES',
                'ESTUDIANTES'
            )
        ),

    CONSTRAINT [CK_area_tipo]
        CHECK
        (
            (
                [nombre] IN ('DOCENTES', 'ESTUDIANTES')
                AND [tipodevehiculo] = 'CARRO'
            )
            OR
            (
                [nombre] = 'MOTOS'
                AND [tipodevehiculo] = 'MOTO'
            )
        )
);
GO


/* =========================================================
   TABLA: spot
   ========================================================= */

CREATE TABLE [dbo].[spot]
(
    [id]             INT IDENTITY(1,1) NOT NULL,
    [codigo]         VARCHAR(20) NOT NULL,
    [codigodearea]   VARCHAR(20) NOT NULL,
    [tipodevehiculo] VARCHAR(10) NOT NULL,

    [estado] BIT NOT NULL
        CONSTRAINT [DF_spot_estado]
        DEFAULT (0),

    CONSTRAINT [PK_spot]
        PRIMARY KEY CLUSTERED ([id] ASC),

    CONSTRAINT [UQ_spot_codigo]
        UNIQUE ([codigo]),

    CONSTRAINT [CK_spot_tipodevehiculo]
        CHECK (
            [tipodevehiculo] IN ('MOTO', 'CARRO')
        ),

    CONSTRAINT [FK_spot_area]
        FOREIGN KEY ([codigodearea])
        REFERENCES [dbo].[area] ([codigo])
);
GO


/* =========================================================
   TABLA: usuario
   ========================================================= */

CREATE TABLE [dbo].[usuario]
(
    [id]          INT IDENTITY(1,1) NOT NULL,
    [carnet]      VARCHAR(20) NOT NULL,
    [telefono]    VARCHAR(20) NULL,
    [nombre]      VARCHAR(100) NULL,
    [apellido]    VARCHAR(100) NULL,
    [tipoUsuario] VARCHAR(20) NULL,
    [carrera]     VARCHAR(100) NULL,
    [semestre]    VARCHAR(20) NULL,

    CONSTRAINT [PK_usuario]
        PRIMARY KEY CLUSTERED ([id] ASC),

    CONSTRAINT [UQ_usuario_carnet]
        UNIQUE ([carnet])
);
GO


/* =========================================================
   TABLA: vehiculo
   ========================================================= */

CREATE TABLE [dbo].[vehiculo]
(
    [id]    INT IDENTITY(1,1) NOT NULL,
    [placa] VARCHAR(20) NOT NULL,
    [color] VARCHAR(50) NULL,
    [tipo]  VARCHAR(20) NULL,

    CONSTRAINT [PK_vehiculo]
        PRIMARY KEY CLUSTERED ([id] ASC),

    CONSTRAINT [UQ_vehiculo_placa]
        UNIQUE ([placa])
);
GO


/* =========================================================
   TABLA: usuario_vehiculo
   ========================================================= */

CREATE TABLE [dbo].[usuario_vehiculo]
(
    [idUsuario]  INT NOT NULL,
    [idVehiculo] INT NOT NULL,
    [rol]        VARCHAR(20) NULL,

    CONSTRAINT [PK_usuario_vehiculo]
        PRIMARY KEY CLUSTERED
        (
            [idUsuario] ASC,
            [idVehiculo] ASC
        ),

    CONSTRAINT [CK_usuario_vehiculo_rol]
        CHECK (
            [rol] IN ('Tercero', 'Propietario')
        ),

    CONSTRAINT [FK_usuario_vehiculo_usuario]
        FOREIGN KEY ([idUsuario])
        REFERENCES [dbo].[usuario] ([id])
        ON UPDATE CASCADE
        ON DELETE CASCADE,

    CONSTRAINT [FK_usuario_vehiculo_vehiculo]
        FOREIGN KEY ([idVehiculo])
        REFERENCES [dbo].[vehiculo] ([id])
        ON UPDATE CASCADE
        ON DELETE CASCADE
);
GO


/* =========================================================
   TABLA: ticket
   ========================================================= */

CREATE TABLE [dbo].[ticket]
(
    [id]               INT IDENTITY(1,1) NOT NULL,
    [placaVehiculo]    VARCHAR(20) NOT NULL,
    [carnetUsuario]    VARCHAR(20) NOT NULL,
    [tipoUsuario]      VARCHAR(20) NOT NULL,
    [tipoVehiculo]     VARCHAR(20) NOT NULL,
    [codigoSpot]       VARCHAR(20) NOT NULL,
    [codigoArea]       VARCHAR(20) NOT NULL,

    [fechaHoraIngreso] DATETIME NOT NULL
        CONSTRAINT [DF_ticket_fechaHoraIngreso]
        DEFAULT (SYSDATETIME()),

    [fechaHoraSalida] DATETIME NULL,

    [tarifaAplicada] VARCHAR(30) NOT NULL,

    [monto] DECIMAL(10,2) NULL
        CONSTRAINT [DF_ticket_monto]
        DEFAULT (0),

    [metodoPago] VARCHAR(20) NOT NULL,
    [estado]     VARCHAR(20) NOT NULL,

    CONSTRAINT [PK_ticket]
        PRIMARY KEY CLUSTERED ([id] ASC)
);
GO


PRINT N'La base de datos ParkPlus y sus tablas fueron creadas correctamente.';
GO