package Ventanas;

import Clases.Ticket;
import DAO.ReporteDAO;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class IngresoDia extends javax.swing.JDialog {
    
    public IngresoDia(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblIngresos = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("Ingresos del dia");

        tblIngresos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {
                "ID",
                "PLACA",
                "CARNET",
                "TIPO USUARIO",
                "TIPO VEHÍCULO",
                "SPOT",
                "AREA",
                "INGRESO",
                "SALIDA",
                "TARIFA",
                "MONTO",
                "MÉTODO",
                "ESTADO"
            }
        ));
        jScrollPane1.setViewportView(tblIngresos);

        jButton1.setText("Exportar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Generar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 852, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2)
                        .addGap(44, 44, 44)
                        .addComponent(jButton1)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jButton2)
                    .addComponent(jButton1))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        ReporteDAO dao = new ReporteDAO();
        List<Ticket> lista = dao.obtenerTicketsIniciadosHoy();
        cargarTablaCierre(lista);    
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
       ReporteDAO dao = new ReporteDAO();
       dao.exportarTablaExcel(tblIngresos);
    }//GEN-LAST:event_jButton1ActionPerformed

private void cargarTablaCierre(List<Ticket> lista) {

    DefaultTableModel modelo = (DefaultTableModel) tblIngresos.getModel();
    modelo.setRowCount(0); // limpiar tabla

    for (Ticket t : lista) {
        modelo.addRow(new Object[]{
            t.getId(),                      
            t.getPlacaVehiculo(),           
            t.getCarnetUsuario(),           
            t.getTipoUsuario(),             
            t.getTipoVehiculo(),            
            t.getCodigoSpot(),              
            t.getCodigoArea(),              
            t.getFechaHoraIngreso(),        
            t.getFechaHoraSalida(),         
            t.getTarifaAplicada(),          
            "Q" + t.getMonto(),             
            t.getMetodoPago(),
            t.getEstado()
        });
    }
}    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblIngresos;
    // End of variables declaration//GEN-END:variables
}
