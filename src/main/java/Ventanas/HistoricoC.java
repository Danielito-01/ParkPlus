package Ventanas;

import Clases.Ticket;
import Gestiones.Gestion;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class HistoricoC extends javax.swing.JDialog {
    Gestion g = new Gestion();

    public HistoricoC(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCargaH = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("Cargar Historico");

        tblCargaH.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {
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
        jScrollPane1.setViewportView(tblCargaH);

        jButton1.setText("Abrir");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Guardar");
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
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 270, Short.MAX_VALUE)
                        .addComponent(jButton1)
                        .addGap(181, 181, 181)
                        .addComponent(jButton2))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
       File archivo = g.cargarArchivo(this);
       g.leerArchivoTickets(archivo, this);
       cargarHistoricoEnTabla(g.getTickets());
    }//GEN-LAST:event_jButton1ActionPerformed

   
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        int respuesta = JOptionPane.showConfirmDialog(
            null,
            "Esta seguro de guardar los datos?",
            "Confirmar Guardado", 
            JOptionPane.YES_NO_OPTION 
        );

        if (respuesta == JOptionPane.YES_OPTION) {
            ArrayList<Ticket> listaTickets = g.getTickets();
            g.insertarTicketsEnBD(listaTickets);
            JOptionPane.showMessageDialog(this, "Se guardo correctamente");
            return;
        } else if (respuesta == JOptionPane.NO_OPTION) {
            return;  
        } else if (respuesta == JOptionPane.CLOSED_OPTION) {
            return;
        }  

    }//GEN-LAST:event_jButton2ActionPerformed

private void cargarHistoricoEnTabla(List<Ticket> lista) {

    DefaultTableModel model = (DefaultTableModel) tblCargaH.getModel();
    model.setRowCount(0);

    for (Ticket t : lista) {
        model.addRow(new Object[]{                    
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
    private javax.swing.JTable tblCargaH;
    // End of variables declaration//GEN-END:variables
}
