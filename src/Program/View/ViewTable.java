
package Program.View;
import Program.Controller.ControllerTable;
import java.sql.SQLException;
import Program.Model.Table;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;


public class ViewTable extends javax.swing.JFrame {

  
  // MÉTODO PARA CARGAR LAS MESAS DESDE LA BD A LA TABLA
    private void cargarMesas() {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0); // Limpiar tabla existente
        
        try {
            ControllerTable controller = new ControllerTable();
            List<Table> mesas = controller.listarMesas(); // Obtiene todas las mesas
            
            for (Table mesa : mesas) {
                model.addRow(new Object[]{ mesa.getId(), mesa.getName() }); // Añade filas
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar mesas: " + ex.getMessage());
        }
    }
    
    public ViewTable() {
    initComponents();
    cargarMesas();  
}

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        btnCreate = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        btnDelete = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnBack = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Name"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 340, 240));

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel1.setText("Name:");

        btnCreate.setText("CREATE");
        btnCreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCreate, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCreate))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 340, 70));

        btnDelete.setText("DELETE");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnEdit.setText("EDIT");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btnBack.setText("BACK");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnDelete, javax.swing.GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE)
                    .addComponent(btnEdit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnBack, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(41, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(btnEdit)
                .addGap(18, 18, 18)
                .addComponent(btnDelete)
                .addGap(69, 69, 69)
                .addComponent(btnBack)
                .addContainerGap(104, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 10, 160, 290));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateActionPerformed
          String nombreMesa = txtName.getText().trim();
    
        if (nombreMesa.isEmpty()) {
            JOptionPane.showMessageDialog(this, "⚠️ El nombre no puede estar vacío");
            return;
        }
    
        try {
            ControllerTable controller = new ControllerTable();
            if (controller.crearMesa(nombreMesa)) {
                JOptionPane.showMessageDialog(this, "✅ Mesa creada exitosamente");
                txtName.setText(""); // Limpia el campo
                cargarMesas(); // Actualiza la tabla con los nuevos datos
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "❌ Error al crear mesa: " + ex.getMessage());
        }
    }//GEN-LAST:event_btnCreateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
         int fila = jTable1.getSelectedRow();
    if (fila < 0) {
        JOptionPane.showMessageDialog(this, "⚠️ Selecciona una mesa");
        return;
    }
    
     // ======== AÑADE ESTO ======== //
    int id = (int) jTable1.getValueAt(fila, 0);
    int confirm = JOptionPane.showConfirmDialog(
        this, 
        "¿Eliminar la mesa ID: " + id + "?", 
        "Confirmar Eliminación", 
        JOptionPane.YES_NO_OPTION
    );
    if (confirm != JOptionPane.YES_OPTION) return;
    // ============================ //
    
   
    
    try {
        if (new ControllerTable().eliminarMesa(id)) {
            cargarMesas(); // Refresca la tabla
            JOptionPane.showMessageDialog(this, "✅ Mesa eliminada");
        }
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this, "❌ Error: " + ex.getMessage());
    }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        int fila = jTable1.getSelectedRow();
    if (fila < 0) {
        JOptionPane.showMessageDialog(this, "⚠️ Selecciona una mesa");
        return;
    }
    
    int id = (int) jTable1.getValueAt(fila, 0);
    String nombreActual = (String) jTable1.getValueAt(fila, 1);
    
    String nuevoNombre = JOptionPane.showInputDialog(this, "Nuevo nombre:", nombreActual);
    if (nuevoNombre == null || nuevoNombre.trim().isEmpty()) return;
    
    try {
        if (new ControllerTable().actualizarMesa(id, nuevoNombre.trim())) {
            cargarMesas();
            JOptionPane.showMessageDialog(this, "✅ Mesa actualizada");
        }
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this, "❌ Error: " + ex.getMessage());
    }
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnBackActionPerformed

 
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnCreate;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField txtName;
    // End of variables declaration//GEN-END:variables
}
