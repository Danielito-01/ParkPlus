# ParkPlus

Sistema de escritorio para la gestión de parqueos, desarrollado en Java Swing y conectado a Microsoft SQL Server.

ParkPlus permite administrar usuarios, vehículos, asociaciones entre usuarios y vehículos, áreas, spots, entradas, salidas, tarifas, pagos y reportes.

## Funcionalidades principales

* Registro de estudiantes y docentes.
* Registro de vehículos tipo carro y moto.
* Asociación de vehículos como `PROPIETARIO` o `TERCERO`.
* Control de placas y carnets únicos.
* Administración de áreas y spots.
* Validación de la capacidad máxima de cada área.
* Asignación visual de spots disponibles.
* Registro de entradas para usuarios e invitados.
* Manejo de tarifas planas y variables.
* Cálculo y registro de pagos.
* Liberación automática del spot al procesar la salida.
* Reportes de cierre e histórico.
* Exportación de reportes a Excel.

## Tecnologías utilizadas

* Java 24.
* Java Swing.
* Apache Maven.
* JDBC.
* Microsoft SQL Server.
* SQL Server Management Studio.
* Apache POI.
* JCalendar.
* Apache NetBeans, recomendado.

## Requisitos previos

Antes de ejecutar el proyecto, instale:

* JDK 24.
* Apache Maven.
* Microsoft SQL Server.
* SQL Server Management Studio.
* Git.
* Apache NetBeans o cualquier IDE compatible con Maven.

También debe comprobar que SQL Server:

* Permita autenticación de SQL Server.
* Tenga habilitado el protocolo TCP/IP.
* Esté disponible en `localhost:1433`.

## Instalación

### 1. Clonar el repositorio

```bash
git clone https://github.com/Danielito-01/ParkPlus.git
cd ParkPlus
```

### 2. Crear la base de datos

1. Abra SQL Server Management Studio.
2. Conéctese con un usuario administrador.
3. Abra el archivo:

```text
docs/ParkPlus_schema.sql
```

4. Ejecute el script completo.

El script crea o reconstruye la base de datos `ParkPlus`, junto con sus tablas, relaciones, restricciones, índices y triggers.

> [!WARNING]
> El script puede eliminar y volver a crear las tablas de `ParkPlus`. No debe ejecutarse si existen datos que necesite conservar.

### 3. Crear un login para la aplicación

Conéctese a SQL Server con una cuenta administradora y ejecute el siguiente script. Cambie el nombre del login y la contraseña por valores locales propios.

```sql
USE [master];
GO

IF NOT EXISTS (
    SELECT 1
    FROM sys.server_principals
    WHERE name = N'parkplus_app'
)
BEGIN
    CREATE LOGIN [parkplus_app]
    WITH PASSWORD = 'CAMBIE_ESTA_CONTRASENA_LOCAL';
END;
GO

USE [ParkPlus];
GO

IF NOT EXISTS (
    SELECT 1
    FROM sys.database_principals
    WHERE name = N'parkplus_app'
)
BEGIN
    CREATE USER [parkplus_app]
    FOR LOGIN [parkplus_app]
    WITH DEFAULT_SCHEMA = [dbo];
END;
GO

ALTER ROLE [db_datareader] ADD MEMBER [parkplus_app];
ALTER ROLE [db_datawriter] ADD MEMBER [parkplus_app];
GO
```

No publique la contraseña utilizada.

### 4. Configurar las variables de entorno

La aplicación obtiene las credenciales mediante estas variables:

```text
PARKPLUS_DB_USER
PARKPLUS_DB_PASSWORD
```

#### Windows con CMD

```bat
setx PARKPLUS_DB_USER "parkplus_app"
setx PARKPLUS_DB_PASSWORD "SU_CONTRASENA_LOCAL"
```

#### Windows con PowerShell

```powershell
[Environment]::SetEnvironmentVariable(
    "PARKPLUS_DB_USER",
    "parkplus_app",
    "User"
)

[Environment]::SetEnvironmentVariable(
    "PARKPLUS_DB_PASSWORD",
    "SU_CONTRASENA_LOCAL",
    "User"
)
```

#### Linux o macOS

```bash
export PARKPLUS_DB_USER="parkplus_app"
export PARKPLUS_DB_PASSWORD="SU_CONTRASENA_LOCAL"
```

Después de crear las variables, cierre y vuelva a abrir NetBeans, la terminal o el IDE para que pueda leerlas.

La conexión predeterminada utiliza:

```text
Servidor: localhost
Puerto: 1433
Base de datos: ParkPlus
```

Si SQL Server se encuentra en otro servidor, instancia o puerto, actualice la constante `URL` en:

```text
src/main/java/DAO/Conexion.java
```

### 5. Cargar áreas y spots iniciales

Ejecute ParkPlus y abra el módulo de carga de archivos.

Cargue en este orden:

1. `docs/.csv/areas.txt`
2. `docs/.csv/spots.txt`

Las áreas deben cargarse primero porque cada spot necesita estar asociado con un área existente.

La configuración inicial es:

| Código | Área        | Capacidad | Vehículo |
| ------ | ----------- | --------: | -------- |
| `ABCE` | Estudiantes |        20 | Carro    |
| `ABCD` | Docentes    |        12 | Carro    |
| `ABCM` | Motos       |        30 | Moto     |

En total se cargan 62 spots:

* 20 spots para estudiantes.
* 12 spots para docentes.
* 30 spots para motos.

La carpeta `docs/.csv` también contiene archivos de ejemplo para usuarios, vehículos, asociaciones e histórico. Utilícelos únicamente desde el módulo correspondiente y respetando las dependencias entre los datos.

## Compilación

Desde la raíz del proyecto ejecute:

```bash
mvn clean package
```

También puede utilizar NetBeans:

1. Abra NetBeans.
2. Seleccione **File > Open Project**.
3. Abra la carpeta `ParkPlus`.
4. Espere a que Maven descargue las dependencias.
5. Ejecute la clase principal:

```text
com.mycompany.parkplus.ParkPlus
```

## Uso del sistema

### 1. Registrar usuarios y vehículos

Abra:

```text
Menú > Nuevo Usuario
```

Ingrese:

* Carnet.
* Nombre.
* Apellido.
* Teléfono.
* Tipo de usuario: `ESTUDIANTE` o `DOCENTE`.

Para cada vehículo ingrese:

* Placa.
* Color.
* Tipo: `CARRO` o `MOTO`.
* Rol: `PROPIETARIO` o `TERCERO`.

Presione **Agregar** para incluir el vehículo en la lista temporal y luego **Guardar** para registrar toda la información.

### 2. Consultar áreas y spots

Abra:

```text
Información > Áreas y Spots
```

Desde este módulo puede consultar:

* Código del área.
* Nombre.
* Capacidad.
* Tipo de vehículo permitido.
* Spots asignados.
* Estado libre u ocupado.

### 3. Registrar una entrada

Abra la pestaña **Parquear**.

Para un usuario registrado:

1. Deje desmarcada la opción **Invitado**.
2. Ingrese el carnet.
3. Seleccione la placa.
4. Presione **Entrada**.
5. Seleccione un spot libre compatible.
6. Seleccione la tarifa.
7. Confirme la operación.

Para un invitado:

1. Marque la opción **Invitado**.
2. Ingrese los datos solicitados.
3. Seleccione el tipo de vehículo.
4. Presione **Entrada**.
5. Seleccione un spot compatible.
6. Confirme la tarifa y la operación.

### 4. Procesar una salida

Abra la pestaña **Salida**.

1. Ingrese el número de ticket o la placa.
2. Presione **Salida**.
3. Revise el monto calculado.
4. Seleccione el método de pago.
5. Confirme la operación.

Al finalizar, el ticket se cierra y el spot queda libre.

### 5. Consultar reportes

En el módulo **Reportes** puede:

* Generar el cierre de una fecha.
* Consultar transacciones dentro de un rango.
* Revisar placa, tarifa, monto y método de pago.
* Exportar información a Excel.

## Reglas principales de la base de datos

La base de datos protege las siguientes reglas:

* Cada carnet identifica a un único usuario.
* Cada placa identifica a un único vehículo.
* Una asociación usuario-vehículo no puede repetirse.
* Un vehículo solo puede tener un propietario.
* Un spot debe pertenecer a un área existente.
* El tipo de vehículo del spot debe coincidir con el tipo permitido por el área.
* La cantidad de spots no puede superar la capacidad del área.
* La capacidad de un área no puede reducirse por debajo de sus spots existentes.
* Una placa no puede tener dos tickets abiertos al mismo tiempo.
* Un spot no puede tener dos tickets abiertos al mismo tiempo.
* Un monto no puede ser negativo.
* La fecha de salida no puede ser anterior a la fecha de entrada.

## Estructura principal del repositorio

```text
ParkPlus/
├── docs/
│   ├── .csv/
│   │   ├── areas.txt
│   │   ├── spots.txt
│   │   ├── usuarios.txt
│   │   ├── vehiculos.txt
│   │   ├── relacion uv.txt
│   │   └── historico.txt
│   ├── Manual_tecnico.md
│   ├── Manual_de_usuario.md
│   └── ParkPlus_schema.sql
├── src/
│   └── main/
├── .gitignore
├── pom.xml
└── README.md
```

## Documentación

* [Manual técnico](docs/Manual_tecnico.md)
* [Manual de usuario](docs/Manual_de_usuario.md)
* [Script de base de datos](docs/ParkPlus_schema.sql)

## Seguridad

No publique en el repositorio:

* Contraseñas.
* Variables de entorno.
* Archivos `.env`.
* Respaldos con información real.
* Credenciales de SQL Server.
* Archivos locales del IDE.

Las credenciales se configuran localmente mediante:

```text
PARKPLUS_DB_USER
PARKPLUS_DB_PASSWORD
```

Ejemplo recomendado para `.gitignore`:

```gitignore
# Maven
target/
*.class

# Credenciales y configuración local
.env
.env.*
*.properties.local

# NetBeans
nbproject/private/
build/
dist/
nbbuild/
nbdist/

# IntelliJ IDEA
.idea/
*.iml

# Visual Studio Code
.vscode/

# Sistema operativo
.DS_Store
Thumbs.db

# Logs
*.log
```

## Solución de problemas

### Falta configurar una variable de entorno

Si aparece:

```text
Falta configurar la variable de entorno: PARKPLUS_DB_USER
```

o:

```text
Falta configurar la variable de entorno: PARKPLUS_DB_PASSWORD
```

cree la variable faltante y reinicie el IDE.

### Error de inicio de sesión

Compruebe:

* Que el login exista en SQL Server.
* Que la contraseña de la variable coincida.
* Que el usuario tenga permisos sobre `ParkPlus`.
* Que SQL Server permita autenticación mediante login y contraseña.

### Error de conexión con `localhost:1433`

Compruebe:

* Que el servicio de SQL Server esté iniciado.
* Que TCP/IP esté habilitado.
* Que SQL Server utilice el puerto 1433.
* Que el firewall permita la conexión local.
* Que la base `ParkPlus` exista.

### Error al cargar spots

Compruebe:

* Que las áreas se hayan cargado primero.
* Que los códigos de área coincidan.
* Que el tipo de vehículo del spot coincida con el área.
* Que la cantidad de spots no supere la capacidad del área.

## Autor

**Daniel Cuyuch**

Repositorio: https://github.com/Danielito-01/ParkPlus

## Estado del proyecto

Proyecto académico en desarrollo.

## Licencia

Actualmente el repositorio no incluye una licencia de distribución. Antes de reutilizar o redistribuir el proyecto, consulte al autor.
