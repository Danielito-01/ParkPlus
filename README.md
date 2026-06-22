## I. Instalación y configuración

### 1. Requisitos previos

Para ejecutar ParkPlus necesita:

* Java Development Kit 24.
* Apache Maven.
* Microsoft SQL Server.
* SQL Server Management Studio.
* Apache NetBeans, recomendado.
* Git.

### 2. Clonar el repositorio

Ejecute:

```bash
git clone https://github.com/Danielito-01/ParkPlus.git
```

Ingrese a la carpeta del proyecto:

```bash
cd ParkPlus
```

### 3. Configurar la base de datos

1. Abra Microsoft SQL Server Management Studio.
2. Inicie sesión con un usuario administrador.
3. Abra el archivo:

```text
docs/ParkPlus.sql
```

4. Ejecute el script completo.

El script crea o reconstruye la base de datos `ParkPlus` con sus tablas, relaciones, restricciones y triggers.

> Advertencia: el script puede borrar las tablas y los datos existentes de `ParkPlus`. No debe ejecutarse si existen datos que necesite conservar.

### 4. Configurar la conexión

La aplicación se conecta a SQL Server mediante JDBC utilizando:

```text
Servidor: localhost
Puerto: 1433
Base de datos: ParkPlus
```

Las credenciales de SQL Server deben configurarse mediante variables de entorno:

```text
PARKPLUS_DB_USER
PARKPLUS_DB_PASSWORD
```

No publique contraseñas dentro del repositorio.

### 5. Cargar áreas y spots

Después de crear la base de datos, ejecute ParkPlus y abra el módulo para cargar archivos.

Cargue los archivos en este orden:

1. `areas.txt`
2. `spots.txt`

Las áreas deben cargarse primero porque cada spot necesita estar asociado a un área existente.

La configuración inicial contiene:

| Área        | Capacidad | Vehículo |
| ----------- | --------: | -------- |
| Estudiantes |        20 | Carro    |
| Docentes    |        12 | Carro    |
| Motos       |        30 | Moto     |

En total se crean 62 spots.

### 6. Compilar el proyecto

Desde la carpeta principal del proyecto ejecute:

```bash
mvn clean package
```

También puede utilizar NetBeans:

1. Abra Apache NetBeans.
2. Seleccione **File > Open Project**.
3. Abra la carpeta `ParkPlus`.
4. Espere a que Maven descargue las dependencias.
5. Ejecute la clase principal:

```text
com.mycompany.parkplus.ParkPlus
```