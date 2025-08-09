
package Program.View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;


import Program.Controller.ControllerOrder;
import Program.Controller.ControllerProduct;
import Program.Controller.ControllerTable;
import Program.Model.Order;
import Program.Model.ItemOrder;
import Program.Model.Product;
import Program.Model.Table;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

// SQL y utilidades
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.text.SimpleDateFormat;


public class ViewOrder extends javax.swing.JFrame {
    
    
  public ViewOrder() {
        initComponents();
        
        
            try {
        loadTablesFromDatabase();
        loadProductsFromDatabase();
        //setupOrderItemsTable();
       // refreshOrdersTable();
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, 
            "Error al inicializar: " + e.getMessage(),
            "Error",
            JOptionPane.ERROR_MESSAGE);
    }
            
                  
    }
  
  
  
  
 
    
   private void loadTablesFromDatabase() {
    try {
        // 1. Obtener las mesas de la base de datos
        ControllerTable controller = new ControllerTable();
        List<Table> tables = controller.listarMesas();
        
        // 2. Crear el modelo del ComboBox
        DefaultComboBoxModel<Table> model = new DefaultComboBoxModel<>();
        
        // 3. Llenar el modelo con las mesas
        for (Table table : tables) {
            model.addElement(table);
        }
        
        // 4. Asignar el modelo al ComboBox existente (no crear uno nuevo)
        cbTables.setModel(model);
        
        // 5. Configurar cómo se muestran las mesas
        cbTables.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, 
                int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Table) {
                    Table table = (Table) value;
                    setText(table.getName()); // Mostrar el nombre de la mesa
                }
                return this;
            }
        });
        
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, 
            "Error al cargar mesas: " + e.getMessage(), 
            "Error", 
            JOptionPane.ERROR_MESSAGE);
    }
}
   
   
   
   private void loadProductsFromDatabase() {
    try {
        // 1. Crear instancia del controlador (CORRECTO)
        ControllerProduct controller = new ControllerProduct();
        
        // 2. Llamar al método de instancia (como está implementado)
        List<Product> products = controller.getAllProducts();
        
        // 3. Configurar el modelo del ComboBox
        DefaultComboBoxModel<Product> model = new DefaultComboBoxModel<>();
        
        // 4. Llenar el modelo
        for (Product product : products) {
            model.addElement(product);
        }
        
        // 5. Asignar modelo al ComboBox
        cbProducts.setModel(model);
        
        // 6. Configurar renderer
        cbProducts.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, 
                int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Product) {
                    Product p = (Product) value;
                    setText(p.getName() + " ($" + p.getPrice() + ")");
                }
                return this;
            }
        });
        
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, 
            "Error al cargar productos: " + e.getMessage(),
            "Error de Base de Datos",
            JOptionPane.ERROR_MESSAGE);
    }
}
   
   
   
   
 private void saveOrderToDatabase() {
    if (tblOrderItems.getRowCount() == 0) {
        JOptionPane.showMessageDialog(this, "Agregue productos primero", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    try {
        Order newOrder = new Order((Table) cbTables.getSelectedItem());
        
        DefaultTableModel model = (DefaultTableModel) tblOrderItems.getModel();
        
        for (int i = 0; i < model.getRowCount(); i++) {
            // Convertimos los Strings de vuelta a números
            int productId = Integer.parseInt(model.getValueAt(i, 0).toString());
            int quantity = Integer.parseInt(model.getValueAt(i, 2).toString());
            double unitPrice = Double.parseDouble(model.getValueAt(i, 3).toString());
            
            Product p = new Product();
            p.setId(productId);
            
            ItemOrder item = new ItemOrder(p, quantity, unitPrice);
            newOrder.addItem(item);
        }
        
        ControllerOrder controller = new ControllerOrder();
        controller.createOrder(newOrder);
        
        JOptionPane.showMessageDialog(this, "Pedido guardado!", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        
        // Limpiar después de guardar
        model.setRowCount(0);
        txtTotal.setText("0.00");
        
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this,
            "Error al guardar: " + e.getMessage(),
            "Error",
            JOptionPane.ERROR_MESSAGE);
    }
}
 
 
 
 private void addProductToOrder() {
    try {
        Product selectedProduct = (Product) cbProducts.getSelectedItem();
        int quantity = Integer.parseInt(txtQuantity.getText());
        
        // Convertimos todos los números a String para evitar errores de formato
        DefaultTableModel model = (DefaultTableModel) tblOrderItems.getModel();
        model.addRow(new Object[]{
            String.valueOf(selectedProduct.getId()),    // ID como texto
            selectedProduct.getName(),                 // Nombre como texto
            String.valueOf(quantity),                  // Cantidad como texto
            String.format("%.2f", selectedProduct.getPrice()), // Precio formateado
            String.format("%.2f", quantity * selectedProduct.getPrice()) // Subtotal
        });
        
        // Actualizar total (asegúrate que updateTotal() trabaje con Strings)
        updateTotal();
        
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this,
            "Error al agregar producto: " + e.getMessage(),
            "Error",
            JOptionPane.ERROR_MESSAGE);
    }
}

private void updateTotal() {
    double total = 0.0;
    DefaultTableModel model = (DefaultTableModel) tblOrderItems.getModel();
    
    for (int i = 0; i < model.getRowCount(); i++) {
        try {
            // Parseamos el String a double
            total += Double.parseDouble(model.getValueAt(i, 4).toString());
        } catch (NumberFormatException e) {
            // Ignoramos filas con formato inválido
        }
    }
    
    txtTotal.setText(String.format("%.2f", total));
}
  
    
    
  

private void resetForm() {
    ((DefaultTableModel) tblOrderItems.getModel()).setRowCount(0);
    txtQuantity.setText("");
    txtTotal.setText("0.00");
    cbTables.setSelectedIndex(-1);
}

private void refreshOrdersTable() {
    try {
        // 1. Crear instancia del controlador
        ControllerOrder controller = new ControllerOrder();
        
        // 2. Obtener órdenes activas
        List<Order> orders = controller.getAllActiveOrders();
        
        // 3. Obtener modelo de la tabla
        DefaultTableModel model = (DefaultTableModel) tblOrders.getModel();
        model.setRowCount(0); // Limpiar tabla
        
        // 4. Llenar la tabla con los datos
        for (Order order : orders) {
            model.addRow(new Object[]{
                order.getId(),
                order.getTable().getId(),  // Accedemos al ID a través del objeto Table
                formatDateTime(order.getOpenTime()),
                order.getCloseTime() != null ? formatDateTime(order.getCloseTime()) : "",
                order.getStatus(),
                String.format("%.2f", order.getSubtotal()),
                String.format("%.2f", order.getTotal())
            });
        }
        
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, 
            "Error al cargar órdenes: " + e.getMessage(),
            "Error de Base de Datos",
            JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
}

// Método auxiliar para formatear fechas (si no lo tienes)
private String formatDateTime(Timestamp timestamp) {
    if (timestamp == null) return "";
    return new SimpleDateFormat("dd/MM/yyyy HH:mm").format(timestamp);
}




private void createNewOrder() {
    // Limpiar la tabla de items
    ((DefaultTableModel) tblOrderItems.getModel()).setRowCount(0);
    // Resetear campos
    txtQuantity.setText("");
    txtTotal.setText("0.00");
    cbTables.setSelectedIndex(-1);
    cbProducts.setSelectedIndex(0);
}
private void setupOrderItemsTable() {
    String[] columns = {"product_id", "Product", "Quantity", "Unit Price", "Subtotal"};
    DefaultTableModel model = new DefaultTableModel(columns, 0) {
        @Override
        public Class<?> getColumnClass(int columnIndex) {
            return columnIndex == 0 ? Integer.class : 
                   columnIndex == 2 ? Integer.class : 
                   columnIndex >= 3 ? Double.class : 
                   Object.class;
        }
    };
    tblOrderItems.setModel(model);
    tblOrderItems.removeColumn(tblOrderItems.getColumnModel().getColumn(0)); // Ocultar product_id
}


   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblOrders = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        btnCreateOrder = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cbTables = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        cbProducts = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        txtQuantity = new javax.swing.JTextField();
        btnAddProduct = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtTotal = new javax.swing.JTextField();
        btnSave = new javax.swing.JButton();
        btnBack = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblOrderItems = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tblOrders.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "table_id", "open_time", "close_time", "status", "subtotal", "total"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.String.class, java.lang.Double.class, java.lang.Double.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblOrders);

        btnCreateOrder.setText("CREATE ORDER");
        btnCreateOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreateOrderActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(btnCreateOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(btnCreateOrder)
                .addContainerGap(32, Short.MAX_VALUE))
        );

        jLabel1.setText("Tables:");

        jLabel2.setText("Products:");

        jLabel3.setText("cantidad:");

        btnAddProduct.setText("ADD PRODUCT");
        btnAddProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddProductActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbTables, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 63, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtQuantity, javax.swing.GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE)
                            .addComponent(cbProducts, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(56, 56, 56))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(btnAddProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbTables, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cbProducts, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addComponent(btnAddProduct)
                .addContainerGap(123, Short.MAX_VALUE))
        );

        jLabel4.setText("TOTAL:");

        btnSave.setText("SAVE:");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnBack.setText("BACK:");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(67, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSave)
                    .addComponent(btnBack))
                .addGap(14, 14, 14))
        );

        tblOrderItems.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id ", "order_id", "product_id", "quantity", "unit_price"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Double.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tblOrderItems);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(48, 48, 48))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(61, 61, 61))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(19, 19, 19))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(33, 33, 33))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
                                               
        this.dispose();
    
    }//GEN-LAST:event_btnBackActionPerformed

    private void btnCreateOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateOrderActionPerformed
         // Limpiar formulario para nueva orden
    resetForm();
    JOptionPane.showMessageDialog(this, 
        "Nueva orden preparada", 
        "Nueva Orden", 
        JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_btnCreateOrderActionPerformed

    private void btnAddProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddProductActionPerformed
     // 1. Validar mesa seleccionada
    if (cbTables.getSelectedItem() == null) {
        JOptionPane.showMessageDialog(this,
            "Seleccione una mesa primero",
            "Error",
            JOptionPane.ERROR_MESSAGE);
        return;
    }
    
    // 2. Validar cantidad
    try {
        int quantity = Integer.parseInt(txtQuantity.getText());
        if (quantity <= 0) throw new NumberFormatException();
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this,
            "Ingrese una cantidad válida (número mayor a 0)",
            "Error",
            JOptionPane.ERROR_MESSAGE);
        return;
    }
    
    // 3. Validar producto seleccionado
    if (cbProducts.getSelectedItem() == null) {
        JOptionPane.showMessageDialog(this,
            "Seleccione un producto",
            "Error",
            JOptionPane.ERROR_MESSAGE);
        return;
    }
    
    // 4. Agregar a la tabla
    addProductToOrder();
    }//GEN-LAST:event_btnAddProductActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        // Guardar la orden en la base de datos
         saveOrderToDatabase();
    }//GEN-LAST:event_btnSaveActionPerformed

 
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddProduct;
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnCreateOrder;
    private javax.swing.JButton btnSave;
    private javax.swing.JComboBox<Product> cbProducts;
    private javax.swing.JComboBox<Table> cbTables;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblOrderItems;
    private javax.swing.JTable tblOrders;
    private javax.swing.JTextField txtQuantity;
    private javax.swing.JTextField txtTotal;
    // End of variables declaration//GEN-END:variables
}
