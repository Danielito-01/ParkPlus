# Manual de Usuario - ParkPlus

## 1. Objetivo
Este manual proporciona una guía paso a paso para el personal de la UMG sobre cómo utilizar el sistema ParkPlus para registrar entradas, procesar salidas, gestionar usuarios y generar reportes del parqueo.

---

## 2. Módulos Principales del Sistema

Al iniciar la aplicación, encontrarás los siguientes módulos principales en la interfaz gráfica (UI):

| Módulo | Función |
| :--- | :--- |
| **Parquear (Entrada/Salida)** | Registro de ingreso de vehículos y procesamiento de la salida y cobro. |
| **Registros** | Registro\Carga de usuarios, vehículos y areas o spots. |
| **Reportes** | Generación de informes históricos (utilizando **Apache POI** para formato Excel). |
| **Información (Ocupación)** | Vista en tiempo real de los *spots* libres/ocupados en cada área. |

---

## 3. Operación Diaria

### 3.1. Registro de Ingreso de un Vehículo (Check-in)

1.  Navega al módulo **Parquear**.
2.  Ubícate en **Entrada**.
3.  **Identificación del Usuario:** Ingresa el **Carnet** (para registrados) o marca **Invitado** (para visitantes).
4.  **Vehículo:** Selecciona la **Placa** del vehículo.
5.  **Procesar:** Haz clic en el botón **Entrada**.  Debe asignar **Spot** (El sistema automáticamente detecta el áreas disponibles para el usuario/vehículo a ingresar).

### 3.2. Proceso de Salida de un Vehículo (Check-out)

1.  En el módulo **Parquear** y en **Salida**.
2.  Ingresa el numero de **Ticket**.
3.  Haz clic en el botón **Salida**.
4.  **Cobro:** El sistema calculará el monto (si es tarifa Variable) o solo confirmará el cierre (si es tarifa Plana)  (Si confirma la salida el spot es liberado y el ticket completado),
5.  **Confirmación:** El *spot* es liberado.

---

## 3. Módulo de Reportes

1.  Navega al módulo **Reportes**.
2.  **Filtros:** Utiliza el **jCalendar** para seleccionar el rango de fechas para el reporte.
3.  **Tipos de Reporte:** Elige entre Reporte de **Ingreso total del día** o **Histórica**.
4.  **Generación:** Haz clic en **Exportar**. El sistema generará el archivo de salida en formato **Excel (`.xlsx`)** usando Apache POI, y lo guardará en la ruta por defecto `[COMPLETAR RUTA DE SALIDA]`.
