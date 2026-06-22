# 📚 ParkPlus: Guía de Instrucciones Operacionales

Este documento proporciona las instrucciones paso a paso para operar el sistema de gestión de parqueos **ParkPlus**, basadas en los flujos de trabajo presentados en la documentación del proyecto.

## 🚀 I. Instrucciones de Instalación y Configuración (Setup)

_(**NOTA IMPORTANTE:** Los comandos y requisitos técnicos específicos dependen del entorno de desarrollo. Los siguientes puntos deben ser completados por el desarrollador del sistema.)_

### 1. Requisitos Previos

-   Asegúrese de tener instalado [Base de Datos Requerida, ej. SQL Server].
    
-   Asegúrese de tener el entorno de ejecución [Lenguaje de Programación, ej. Java, Python] configurado.
    

### 2. Puesta en Marcha (Placeholder)

1.  Clonar el repositorio: `git clone https://aws.amazon.com/es/what-is/repo/`
    
2.  Instalar dependencias: `[Comando de instalación]`
    
3.  Configurar la base de datos: `[Pasos para conexión]`
    
4.  Iniciar el servicio: `[Comando de inicio]`
    

----------

## 🚦 II. Instrucciones Operacionales (Uso del Sistema)

### 1. Gestión de Datos Maestros (Menú)

El módulo **Menu** permite configurar las bases de datos iniciales del sistema.

#### **Instrucción 1.1: Registrar Nuevos Usuarios (Menu > Nuevo Usuario)**

1.  Navegue a **Menu** y seleccione **Nuevo Usuario**.
    
2.  Complete la información personal: **Carnet** (obligatorio, ej. `C0001011`), **Nombre**, **Apellido**, **Teléfono**.
    
3.  Seleccione el **Tipo** de usuario: **DOCENTE** o **ESTUDIANTE**.
    
4.  En la sección **Vehículos a agregar**, ingrese los datos del vehículo:
    
    -   **Placa** (ej. `MOT005`), **Color**, **Tipo** (**Moto** o **Carro**).
        
    -   Defina el **Rol** del vehículo para ese usuario (**PROPIETARIO** o **TERCERO**).
        
5.  Haga clic en **Agregar** para incluir el vehículo en la lista temporal.
    
6.  Haga clic en **Guardar** para finalizar el registro del usuario y sus vehículos.
    

#### **Instrucción 1.2: Consultar Áreas y Spots (Informacion > Áreas y Spots)**

1.  Navegue a **Informacion** y seleccione **Áreas y Spots**.
    
2.  Utilice las pestañas para verificar las **Áreas** configuradas (ej. `ABCM` para **MOTOS**) y los **Spots** individuales disponibles (ej. `ABCM33`).
   
   **1.1 y 1.2** puede omitirlos si carga los archivos txt ya incluidos. 

### 2. Proceso de Ingreso y Parqueo (Parquear)

Este es el flujo principal para dar **Entrada** a un vehículo.

#### **Instrucción 2.1: Registrar la Entrada de un Usuario Registrado**

1.  Acceda a la pestaña **Parquear**.
    
2.  Asegúrese de que la casilla **Invitado** esté **desmarcada**.
    
3.  Ingrese el **Carnet** del usuario.
    
4.  Seleccione la **Placa** del vehículo que ingresa.
    
5.  Haga clic en el botón **Entrada**.
    
6.  **Asignación de Spot:**
    
    -   Visualice el mapa del parqueo.
        
    -   Seleccione un Spot **Libre** (Generalmente indicado en color **Verde**) compatible con el tipo de vehículo (Carro/Moto).
        
7.  **Selección de Tarifa:** Se le solicitará elegir la tarifa aplicable: **Tarifa Variable** o **Tarifa Plana**.
    
8.  El sistema generará el **Ticket No** (ej. **29**) con **Estado: PENDIENTE** y **Monto: Q0.0**.
    

#### **Instrucción 2.2: Registrar la Entrada de un Invitado**

1.  Acceda a la pestaña **Parquear** y **marque** la casilla **Invitado**.
    
2.  Ingrese el **Nombre** del invitado y la **Placa** del vehículo.
    
3.  Siga los pasos 5 a 8 de la Instrucción 2.1. (Nota: Los invitados suelen tener **Tarifa Plana** que puede ser pagada de inmediato, dejando el **Estado: ACTIVO** y **Monto: Q15.0**).
    

### 3. Proceso de Egreso y Pago (Salida)

Este es el flujo principal para dar **Salida** a un vehículo y cerrar el ticket.

#### **Instrucción 3.1: Procesar Salida y Pago (Tarifa Variable)**

1.  Acceda a la pestaña **Salida**.
    
2.  Ingrese el número de **Ticket** (ej. **29**) o la **Placa** del vehículo.
    
3.  Haga clic en el botón **Salida**.
    
4.  El sistema calculará el **Monto a pagar** (ej. Q15.0).
    
5.  Seleccione el **Método de Pago** (**EFECTIVO** o **TARJETA**).
    
6.  Confirme el pago. El sistema mostrará un mensaje de **Salida Procesada** y el ticket pasará a **Estado: FINALIZADO**.
    

#### **Instrucción 3.2: Procesar Salida (Tarifa Plana Pagada Previamente)**

1.  Acceda a la pestaña **Salida** e ingrese el **Ticket No** (ej. **30**).
    
2.  Haga clic en **Salida**.
    
3.  El sistema preguntará: **"El usuario ya pagó la tarifa plana. ¿Desea cerrar el ticket y liberar el spot?"**
    
4.  Confirme la operación para liberar el espacio.
    

### 4. Generación de Reportes (Reportes)

Los reportes son esenciales para la auditoría y control financiero.

#### **Instrucción 4.1: Generar el Reporte de Cierre del Día**

1.  Navegue a **Reportes** y seleccione la opción **Cierre**.
    
2.  Seleccione la fecha deseada.
    
3.  El sistema generará el informe detallado de las transacciones finalizadas, incluyendo: **PLACA**, **TARIFA**, **MONTO**, y **MÉTODO** de pago utilizado.
    

#### **Instrucción 4.2: Consultar el Histórico de Transacciones**

1.  Navegue a **Reportes** y seleccione la opción **Histórico**.
    
2.  Defina el rango de fechas de consulta utilizando los campos **DESDE** y **HASTA**.
    
3.  Haga clic en **Consultar**.
