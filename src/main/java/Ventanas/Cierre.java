package Ventanas;

import Clases.Ticket;
import DAO.ReporteDAO;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Cierre extends javax.swing.JDialog {
    
    public Cierre(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCierre = new javax.swing.JTable();
        jdcFecha = new com.toedter.calendar.JDateChooser();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("Cierre del dia");

        jButton1.setText("Generar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        tblCierre.setModel(new javax.swing.table.DefaultTableModel(
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
                "MÉTODO"
            }
        ));
        jScrollPane1.setViewportView(tblCierre);

        jButton2.setText("Exportar");
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 866, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1)
                        .addGap(198, 198, 198)
                        .addComponent(jdcFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton1)
                        .addComponent(jButton2))
                    .addComponent(jdcFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 347, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
     if (jdcFecha.getDate() == null) {
        JOptionPane.showMessageDialog(this, "Seleccione una fecha.");
        return;
        }

        LocalDate fecha = jdcFecha.getDate().toInstant()
                .atZone(ZoneId.systemDefault()).toLocalDate();

        ReporteDAO dao = new ReporteDAO();
        List<Ticket> lista = dao.obtenerTicketsCerradosPorFecha(fecha);
        cargarTablaCierre(lista);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
         ReporteDAO dao = new ReporteDAO();
        dao.exportarTablaExcel(tblCierre);
    }//GEN-LAST:event_jButton2ActionPerformed

private void cargarTablaCierre(List<Ticket> lista) {

    DefaultTableModel modelo = (DefaultTableModel) tblCierre.getModel();
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
        });
    }
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private com.toedter.calendar.JDateChooser jdcFecha;
    private javax.swing.JTable tblCierre;
    // End of variables declaration//GEN-END:variables
}
