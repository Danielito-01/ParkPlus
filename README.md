# ParkPlus - Sistema de Gestión del Parqueo UMG

## 1. Descripción del Proyecto
ParkPlus es un sistema desarrollado en **Java con Programación Orientada a Objetos (POO)** para automatizar la gestión, control de acceso y cobro del parqueo de la UMG, Mazatenango. El sistema maneja tarifas **Plana** y **Variable**, y soporta diferentes tipos de usuarios (Catedráticos, Estudiantes, Invitados) y vehículos (Carro, Moto).

---

## 2. Requisitos Previos

Asegúrate de tener instalado y configurado lo siguiente:

* **Java Development Kit (JDK):** Versión 11 o superior.
* **Sistema de Gestión de Base de Datos:** Microsoft SQL Server Express.
* **Librerías / Dependencias (JARS):**
    * **JDBC (SQL Server):** El conector necesario para la base de datos.
    * **jCalendar:** Utilizado para la selección de fechas en la UI.
    * **Apache POI (ooxml, ooxml-lite, xmlbeans):** Utilizado para la **generación de reportes** en formato MS Excel (`.xlsx`).

---

## 3. Instalación y Configuración de la Base de Datos

El sistema utiliza la base de datos `SQL Server Express` para la persistencia de datos.

### 3.1. Crear la Base de Datos
1.  Abre tu herramienta de gestión de SQL Server (por ejemplo, SSMS).
2.  Crea una base de datos con el nombre: `[INSERTAR NOMBRE DE TU DB AQUÍ, e.g., ParkPlusDB]`.

### 3.2. Ejecutar el Script de Creación de Tablas
1.  En el SSMS, selecciona la base de datos que acabas de crear.
2.  Ejecuta el script SQL llamado **`schema.sql`** (incluido en este repositorio).

### 3.3. Carga Inicial de Datos (ETL)
El sistema utiliza archivos CSV para la carga inicial de catálogos e históricos.
1.  Ubica las plantillas CSV: `areas.txt`, `spots.txt`, `vehiculos.txt`, `usuarios.txt`,  `uv.txt`,  `historico.txt` (si aplica).
2.  **Carga:** La aplicación debe iniciar el proceso de carga de estos archivos al usar una opción de menú **"Cargar Datos"**.

### 3.4. Configuración de Conexión
1.  Abre el archivo de configuración de la conexión a la base de datos.
    * *Ruta del archivo:* `[INSERTAR RUTA DEL ARCHIVO DE CONFIGURACIÓN, e.g., src/main/resources/dbconfig.properties]`
2.  Asegúrate de que los parámetros de conexión sean correctos (URL, Usuario, Contraseña).

---

## 4. Compilación y Ejecución

1.  Abre el proyecto en tu IDE (NetBeans, IntelliJ, etc.).
2.  Verifica que todas las librerías mencionadas en el punto 2 estén correctamente enlazadas al *Build Path* del proyecto.
3.  Limpia y construye el proyecto.
4.  Ejecuta la clase principal.
    * *Clase Principal:* `[INSERTAR NOMBRE DE LA CLASE MAIN, e.g., com.umg.parkplus.MainApp]`
