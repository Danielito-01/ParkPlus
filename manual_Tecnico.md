# Manual Técnico del Sistema ParkPlus

## 1. Introducción

El presente manual técnico describe la arquitectura, funcionamiento interno, diseño de clases, estructura de base de datos, algoritmos y lógica empresarial del sistema **ParkPlus**. Está orientado a desarrolladores, personal de soporte y responsables de mantenimiento.

----------

## 2. Arquitectura General

ParkPlus utiliza una arquitectura multicapa:

-   **Capa de Modelo (Clases/)**: Entidades principales del dominio.
    
-   **Capa DAO (Acceso a Datos)**: Conexión y consultas a SQL Server.
    
-   **Capa de Gestión (Logica/Negocio)**: Procesamiento, validación, carga masiva y operaciones centrales.
    
-   **Capa de Presentación**: Interfaz Swing.
    

El sistema permite:

-   Registro de usuarios y vehículos
    
-   Asignación usuario–vehículo
    
-   Control de tickets de parqueo
    
-   Cálculo de tarifas dinámicas
    
-   Generación de reportes
    

----------

## 3. Modelo de Clases (Clases/)

### 3.1 Usuario

```java
class Usuario {
    int id;
    String carnet;
    String telefono;
    String nombre;
    String apellido;
    String tipousuario;
}

```

### 3.2 Estudiante

Extiende Usuario y agrega:

-   carrera
    
-   semestre
    

### 3.3 Docente

Extensión directa de Usuario.

### 3.4 Vehículo

```java
class Vehiculo {
    int id;
    String placa;
    String color;
    String tipoVehiculo;
    String rol;
}

```

Subclases: **Moto**, **Carro**.

### 3.5 Área

Representa una zona dentro del parqueo.

-   código
    
-   nombre
    
-   capacidad
    
-   tipoVehiculo
    

### 3.6 Spot

Espacios individuales dentro de un área.

### 3.7 Ticket

Estructura de entrada y salida del vehículo.

Campos principales:

-   placaVehiculo
    
-   carnetUsuario
    
-   tipoVehiculo
    
-   fechaHoraIngreso
    
-   fechaHoraSalida
    
-   tarifaAplicada
    
-   monto
    
-   estado
    

### 3.8 Tarifas

**ConfigTarifas** carga valores desde `app.properties`.  
**TarifaCalculator** aplica lógica según:

-   PLANA
    
-   HORA
    
-   FRACCION
    
-   AUTO
    

----------

## 4. Acceso a Datos (DAO/)

### 4.1 Conexion

Se conecta mediante JDBC a SQL Server.

### 4.2 UsuarioDAO

Maneja:

-   Insertar usuario
    
-   Verificar existencia por carnet
    
-   Obtener usuario por carnet
    
-   Obtener placas asociadas
    

### 4.3 VehiculoDAO

Funciones:

-   Insertar vehículos
    
-   Verificar existencia por placa
    
-   Validar si está parqueado
    

### 4.4 UsuarioVehiculoDAO

Gestiona:

-   Relación usuario–vehículo
    
-   Validación de propietario único
    

### 4.5 TicketDAO

Control de tickets:

-   Crear ticket
    
-   Cerrar ticket
    
-   Liberar spot
    

### 4.6 ReporteDAO

Permite generar reportes de tickets:

-   Por fecha
    
-   Por rango
    
-   Exportación a Excel (con Apache POI)
    

----------

## 5. Lógica del Sistema (Gestiones/)

### 5.1 Carga de Usuarios

-   Validación de formato
    
-   Verificación de duplicados
    
-   Almacenamiento temporal en listas
    

### 5.2 Carga de Vehículos

-   Validación de placas duplicadas
    
-   Creación de objetos Moto/Carro
    

### 5.3 Asociación Usuario–Vehículo

-   Verificación de existencia
    
-   Validación de rol Propietario
    

### 5.4 Tickets

**Entrada**:

-   Verifica si está dentro
    
-   Asigna spot
    
-   Genera ticket
    

**Salida**:

-   Calcula monto
    
-   Libera spot
    
-   Cambia estado a COMPLETADO
    

----------

## 6. Estructura de Base de Datos

Tablas:

-   usuario
    
-   vehiculo
    
-   usuario_vehiculo
    
-   area
    
-   spot
    
-   ticket
    

Incluye claves foráneas según:

-   usuario_vehiculo → usuario, vehiculo
    
-   ticket → spot, usuario, vehiculo
    

----------

## 7. Lógica de Tarifas

Parámetros:

```
tarifa.modo
	PLANA | HORA | FRACCION | AUTO

fraccion.minutos
	ej. 15

valor.tarifa
	ej. 5

```

Ejemplo de cálculo HORA:

```java
double horas = Math.ceil(minutos / 60.0);
return horas * valorFraccion;

```

----------

## 8. Exportación a Excel

El sistema usa **Apache POI** para generar archivos `.xlsx`.

Pasos:

1.  Crear libro `Workbook wb = new XSSFWorkbook()`
    
2.  Crear hoja
    
3.  Insertar encabezados
    
4.  Insertar filas
    
5.  Guardar archivo
    

----------

## 9. Flujo General

1.  Usuario ingresa vehículo
    
2.  Verificación en BD
    
3.  Asignación de spot
    
4.  Emisión de ticket
    
5.  Cálculo de tarifa
    
6.  Salida y pago
    
7.  Reporte del día
    

----------

## 10. Dependencias

-   Java 17+
    
-   JDBC SQL Server
    
-   Apache POI
    
-   Swing
    
-   SQL Server 2019+
    

----------

