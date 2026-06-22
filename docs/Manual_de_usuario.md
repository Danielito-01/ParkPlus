# 📖 Manual de Usuario – ParkPlus

El sistema **ParkPlus** permite la gestión integral de parqueos para **docentes, estudiantes e invitados**, controlando el acceso, la asignación de espacios y la administración de tickets y pagos.

---

## 1. Menú Principal
La barra superior contiene los módulos principales:

- **Menu** → Configuración de usuarios y vehículos.  
- **Información** → Consulta de áreas, spots y datos registrados.  
- **Reportes** → Histórico, cierre diario e ingresos.  
- **Parquear** → Registro de entradas de vehículos.  
- **Salida** → Procesar salidas y pagos.

---

## 2. Registro de Usuarios
En **Menu > Nuevo Usuario**:

- Campos requeridos:
  - Carnet
  - Teléfono
  - Nombre y Apellido
  - Tipo de usuario (Docente / Estudiante)
  - Carrera y semestre (para estudiantes)

- Botones:
  - **Agregar** → añade el usuario.
  - **Guardar** → confirma los datos.
  - **Limpiar** → borra el formulario.

---

## 3. Registro de Vehículos
En la sección **Vehículos a agregar**:

- Campos:
  - Placa
  - Color
  - Tipo de vehículo (Moto / Carro)
  - Rol (Propietario / Tercero)

- Validaciones:
  - Si la placa ya existe para el usuario:  
    *Error: La placa MOT005 ya tiene un rol para este usuario.*
  - Si el vehículo ya tiene propietario:  
    *Este vehículo ya tiene un propietario registrado.*

- Botones:
  - **Agregar** → añade el vehículo.
  - **Guardar** → confirma el registro.
  - **Confirmar Guardado** → ventana de confirmación antes de guardar.

---

## 4. Parquear
En el módulo **Parquear**:

1. Seleccione el usuario (Docente, Estudiante o Invitado).  
2. El sistema muestra las áreas y spots disponibles:
   - **Motos** → ABCM33, ABCM34, etc.  
   - **Docentes (Carros)** → ABCD21, ABCD22, etc.  
   - **Estudiantes (Carros)** → ABCE1, ABCE6, etc.  
3. Elija un spot libre (verde).  
4. Se genera un **ticket** con número único y estado **PENDIENTE** si  elegio tarifa variable, **ACTIVO** si escogio tarifa plana.  

---

## 5. Salida
En el módulo **Salida**:

- Ingrese el número de ticket.  
- El sistema muestra:
  - Carnet
  - Placa
  - Tipo de vehículo
  - Hora de ingreso y salida
  - Tarifa aplicada
  - Estado del ticket

- Validaciones:
  - Si el usuario ya pagó tarifa plana:  
    *¿Desea cerrar el ticket y liberar el spot?*  
  - Al confirmar, el spot se libera y el ticket pasa a **COMPLETADO**.

---

## 6. Pagos
- **Métodos de pago**: Efectivo y Tarjeta.  
- **Tipos de tarifa**:
  - **Variable** → calculada según tiempo de uso.  
  - **Plana** → monto fijo (ej. Q15.0).  

- Campos mostrados:
  - Monto a pagar
  - Método de pago seleccionado
  - Estado del ticket (Pendiente / Activo / Finalizado)

---

## 7. Reportes
En el módulo **Reportes**:

- **Cierre del día** → listado de transacciones finalizadas.  
- **Histórico** → consulta de tickets en un rango de fechas.  
- **Ingresos diarios** → resumen de pagos y estados.  
- **Áreas y Spots** → visualización de espacios ocupados/libres.  
- **Usuarios y Vehículos** → asociación carnet–placa–rol.

---

## 8. Mensajes y Validaciones
- **Confirmar Guardado** → “¿Está seguro de guardar los datos?”  
- **Error de placa duplicada** → “La placa ya tiene un rol para este usuario.”  
- **Ticket cerrado** → “Salida procesada correctamente. Spot liberado.”  
- **Pago pendiente** → “Método de pago: PENDIENTE.”  

---

## 9. Flujo resumido
1. Registrar usuario.  
2. Registrar vehículo.  
3. Seleccionar spot libre y parquear.  
4. Generar ticket de entrada.  
5. Procesar salida → calcular tarifa → pagar.  
6. Liberar spot → ticket finalizado.  

---

## 👨‍💻 Autor
**Daniel** – Proyecto final de gestión de parqueo.  

