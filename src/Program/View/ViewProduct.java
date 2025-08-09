
package Program.View;
import Program.Controller.ControllerProduct;
import java.sql.SQLException;
import Program.Model.Product;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
 import javax.swing.JPanel;
 import javax.swing.JLabel;
 import javax.swing.JTextField;
 import javax.swing.JCheckBox;
 import java.awt.GridLayout;


public class ViewProduct extends javax.swing.JFrame {

   private void loadProducts() {
    DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
    model.setRowCount(0); // Clear existing table
    
    try {
        ControllerProduct controller = new ControllerProduct();
        List<Product> products = controller.getAllProducts(); // Obtiene todos los productos
        
        for (Product product : products) {
            model.addRow(new Object[]{
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getCost(),
                product.isCustom()// Custom en inglés
            });
        }
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this, "Error loading products: " + ex.getMessage());
    }
}
    public ViewProduct() {
        initComponents();
        
        // Configurar la columna "Custom" como checkbox
    jTable2.getColumn("Custom").setCellRenderer(jTable2.getDefaultRenderer(Boolean.class));
    jTable2.getColumn("Custom").setCellEditor(jTable2.getDefaultEditor(Boolean.class));
     
    
    loadProducts(); // Cargar datos
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        txtName = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        txtDescription = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        spinnerCost = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        spinnerPrice = new javax.swing.JLabel();
        btnSave = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        chkCustom = new javax.swing.JLabel();
        btnEdit = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        checkBoxCustom = new javax.swing.JCheckBox();
        btnBack = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        txtName.setText("Name:");

        txtDescription.setText("Description:");

        spinnerCost.setText("Cost:");

        spinnerPrice.setText("Price:");

        btnSave.setText("SAVE");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnDelete.setText("DELETE");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        chkCustom.setText("Custom:");

        btnEdit.setText("EDIT");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Description", "Price", "Cost", "Custom"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Double.class, java.lang.Boolean.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jTable2);

        btnBack.setText("BACK");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDescription)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(spinnerCost, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(spinnerPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(chkCustom, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(36, 36, 36)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(checkBoxCustom, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnSave, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnDelete, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
                            .addComponent(btnEdit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnBack, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(35, 35, 35)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 508, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(364, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtName))
                        .addGap(10, 10, 10)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtDescription)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(spinnerPrice)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(4, 4, 4)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(spinnerCost)
                            .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(chkCustom)
                            .addComponent(checkBoxCustom))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                        .addComponent(btnSave)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnEdit)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnDelete)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBack)
                        .addGap(12, 12, 12))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
         // Validaciones
    if (jTextField1.getText().trim().isEmpty()) {
        JOptionPane.showMessageDialog(this, "⚠️ Name is required");
        return;
    }

    try {
        // Obtener valores numéricos (con validación)
        double price = Double.parseDouble(jTextField3.getText());
        double cost = Double.parseDouble(jTextField4.getText());

        if (price <= cost) {
            JOptionPane.showMessageDialog(this, "❌ Price must be greater than cost");
            return;
        }

        // Obtener el estado del checkbox (¡clave!)
        boolean isCustom = checkBoxCustom.isSelected();

        // Crear el producto CON el valor de custom
        Product newProduct = new Product(
            0, // ID autogenerado
            jTextField1.getText().trim(),    // Nombre
            jTextField2.getText().trim(),    // Descripción
            price,                          // Precio
            cost,                           // Costo
            isCustom                        // Custom (true/false)
        );

        // Guardar en BD
        ControllerProduct controller = new ControllerProduct();
        if (controller.createProduct(newProduct)) {
            JOptionPane.showMessageDialog(this, "✅ Product saved successfully");
            loadProducts();  // Actualizar tabla
            clearFields();   // Limpiar formulario
        }
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "⚠️ Price and Cost must be numbers");
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this, "❌ Database error: " + ex.getMessage());
    }
    
   
}

private void clearFields() {
    txtName.setText("");
    txtDescription.setText("");
    spinnerPrice.setText("");
    spinnerCost.setText("");
    //chkCustom.setText(false);
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        int selectedRow = jTable2.getSelectedRow();
    
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "⚠️ Select a product to delete");
        return;
    }
    
    // Obtener el ID del producto seleccionado
    int id = (int) jTable2.getValueAt(selectedRow, 0); // Columna 0 = ID
    
    // Confirmación antes de eliminar
    int confirm = JOptionPane.showConfirmDialog(
        this, 
        "Delete product ID: " + id + "?", 
        "Confirm Delete", 
        JOptionPane.YES_NO_OPTION
    );
    
    if (confirm != JOptionPane.YES_OPTION) return;
    
    // Eliminar de la BD
    try {
        ControllerProduct controller = new ControllerProduct();
        if (controller.deleteProduct(id)) {
            JOptionPane.showMessageDialog(this, "✅ Product deleted");
            loadProducts(); // Actualizar tabla
        }
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this, "❌ Error: " + ex.getMessage());
    }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
  
         int selectedRow = jTable2.getSelectedRow();
    
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "⚠️ Select a product to edit");
        return;
    }
    
    // 1. Obtener datos del producto seleccionado
    int id = (int) jTable2.getValueAt(selectedRow, 0);
    String name = (String) jTable2.getValueAt(selectedRow, 1);
    String description = (String) jTable2.getValueAt(selectedRow, 2);
    double price = (double) jTable2.getValueAt(selectedRow, 3);
    double cost = (double) jTable2.getValueAt(selectedRow, 4);
    boolean custom = (boolean) jTable2.getValueAt(selectedRow, 5);
    
    // 2. Crear panel de edición (CORREGIDO)
    javax.swing.JPanel editPanel = new javax.swing.JPanel();
    editPanel.setLayout(new java.awt.GridLayout(0, 1));
    
    // Campo Nombre (CORREGIDO)
    editPanel.add(new javax.swing.JLabel("Name:"));
    javax.swing.JTextField nameField = new javax.swing.JTextField(name);
    editPanel.add(nameField);
    
    // Campo Descripción (CORREGIDO)
    editPanel.add(new javax.swing.JLabel("Description:"));
    javax.swing.JTextField descField = new javax.swing.JTextField(description);
    editPanel.add(descField);
    
    // Campo Precio (CORREGIDO)
    editPanel.add(new javax.swing.JLabel("Price:"));
    javax.swing.JTextField priceField = new javax.swing.JTextField(String.valueOf(price));
    editPanel.add(priceField);
    
    // Campo Costo (CORREGIDO)
    editPanel.add(new javax.swing.JLabel("Cost:"));
    javax.swing.JTextField costField = new javax.swing.JTextField(String.valueOf(cost));
    editPanel.add(costField);
    
    // Checkbox Custom (CORREGIDO)
    editPanel.add(new javax.swing.JLabel("Custom:"));
    javax.swing.JCheckBox customCheckbox = new javax.swing.JCheckBox("", custom);
    editPanel.add(customCheckbox);
    
    // 3. Mostrar diálogo (CORREGIDO)
    int result = javax.swing.JOptionPane.showConfirmDialog(
        this, 
        editPanel, 
        "Edit Product", 
        javax.swing.JOptionPane.OK_CANCEL_OPTION,
        javax.swing.JOptionPane.PLAIN_MESSAGE
    );
    
    // 4. Procesar actualización (CORREGIDO)
    if (result == javax.swing.JOptionPane.OK_OPTION) {
        try {
            if (nameField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "⚠️ Name is required");
                return;
            }
            
            Product updatedProduct = new Product(
                id,
                nameField.getText().trim(),
                descField.getText().trim(),
                Double.parseDouble(priceField.getText()),
                Double.parseDouble(costField.getText()),
                customCheckbox.isSelected()
            );
            
            if (new ControllerProduct().updateProduct(updatedProduct)) {
                JOptionPane.showMessageDialog(this, "✅ Product updated");
                loadProducts();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "⚠️ Price/Cost must be numbers");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "❌ Database error: " + ex.getMessage());
        }
    }
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnBackActionPerformed

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnSave;
    private javax.swing.JCheckBox checkBoxCustom;
    private javax.swing.JLabel chkCustom;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JLabel spinnerCost;
    private javax.swing.JLabel spinnerPrice;
    private javax.swing.JLabel txtDescription;
    private javax.swing.JLabel txtName;
    // End of variables declaration//GEN-END:variables
}
