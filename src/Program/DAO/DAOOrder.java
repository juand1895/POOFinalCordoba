package Program.DAO;

import Program.Model.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DAOOrder {
    private Connection connection;
    private static final Logger logger = Logger.getLogger(DAOOrder.class.getName());

    public DAOOrder() throws SQLException {
        this.connection = ConexionDB.Conectar(); // Usa tu conexión
    }

    // Crea un nuevo pedido (sin descuentos)
    public void create(Order order) throws SQLException {
       String sqlOrder = "INSERT INTO orders (table_id, open_time, status, subtotal, total) "
                    + "VALUES (?, ?, ?, ?, ?)";
    
    try {
        connection.setAutoCommit(false); // Iniciar transacción
        
        // 1. Guardar la orden principal
        try (PreparedStatement stmt = connection.prepareStatement(sqlOrder, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, order.getTable().getId());
            stmt.setTimestamp(2, order.getOpenTime());
            stmt.setString(3, order.getStatus());
            stmt.setDouble(4, order.getSubtotal());
            stmt.setDouble(5, order.getTotal());

            if (stmt.executeUpdate() > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        order.setId(rs.getInt(1));
                    }
                }
            }
        }
        
        // 2. Guardar todos los ítems
        if (order.getId() > 0) { // Si se guardó la orden principal
            saveOrderItems(order);
        }
        
        connection.commit(); // Confirmar transacción
        
    } catch (SQLException e) {
        connection.rollback(); // Revertir en caso de error
        throw e;
    } finally {
        connection.setAutoCommit(true);
    }
    }
  
    
    
    
   private void saveOrderItems(Order order) throws SQLException {
   if (order == null || order.getItems() == null || order.getItems().isEmpty()) {
        return; // No hay items para guardar
    }

    String sql = "INSERT INTO order_items (order_id, product_id, quantity, unit_price) VALUES (?, ?, ?, ?)";
    
    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
        for (ItemOrder item : order.getItems()) {
            // Validación básica
            if (item == null || item.getProduct() == null) continue;
            
            stmt.setInt(1, order.getId());
            stmt.setInt(2, item.getProduct().getId());
            stmt.setInt(3, item.getQuantity());
            stmt.setDouble(4, item.getUnitPrice());
            stmt.addBatch();
        }
        stmt.executeBatch();
    }
}
    
    
    public List<Order> getAllActiveOrders() throws SQLException {
    List<Order> orders = new ArrayList<>();
    String sql = "SELECT o.id, o.open_time, o.table_id, t.name as table_name " +
                 "FROM orders o JOIN tables t ON o.table_id = t.id " +
                 "WHERE o.status = 'open'";

    try (PreparedStatement stmt = connection.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {
        
        while (rs.next()) {
            Order order = new Order();
            order.setId(rs.getInt("id"));
            order.setOpenTime(rs.getTimestamp("open_time"));
            
            Table table = new Table();
            table.setId(rs.getInt("table_id"));
            table.setName(rs.getString("table_name"));
            order.setTable(table);
            
            orders.add(order);
        }
    }
    return orders;
}

    // Obtiene pedido activo de una mesa
    public Order getActiveByTable(Table table) throws SQLException {
        String sql = "SELECT id, open_time FROM orders WHERE table_id = ? AND status = 'open'";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, table.getId());
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Order order = new Order();
                order.setId(rs.getInt("id"));
                order.setTable(table);
                order.setOpenTime(rs.getTimestamp("open_time"));
                order.setItems(getItemsByOrder(order.getId())); // Usa ItemOrder
                return order;
            }
            return null;
        }
    }

    // Obtiene todos los ítems de un pedido (ahora con ItemOrder)
    public List<ItemOrder> getItemsByOrder(int orderId) throws SQLException {
        List<ItemOrder> items = new ArrayList<>();
        String sql = "SELECT oi.id, oi.product_id, oi.quantity, oi.unit_price, "
                   + "p.name as product_name, p.description "
                   + "FROM order_items oi JOIN products p ON oi.product_id = p.id "
                   + "WHERE oi.order_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("product_id"));
                product.setName(rs.getString("product_name"));
                product.setDescription(rs.getString("description"));
                
                ItemOrder item = new ItemOrder(
                    product,
                    rs.getInt("quantity"),
                    rs.getDouble("unit_price")
                );
                item.setId(rs.getInt("id"));
                items.add(item);
            }
        }
        return items;
    }

    // Agrega un ítem al pedido (con ItemOrder)
    public void addItem(Order order, ItemOrder item) throws SQLException {
        String sql = "INSERT INTO order_items (order_id, product_id, quantity, unit_price) "
                   + "VALUES (?, ?, ?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, order.getId());
            stmt.setInt(2, item.getProduct().getId());
            stmt.setInt(3, item.getQuantity());
            stmt.setDouble(4, item.getUnitPrice());
            stmt.executeUpdate();
        }
    }

    // Cierra un pedido
    public void close(Order order) throws SQLException {
        String sql = "UPDATE orders SET status = 'closed', close_time = CURRENT_TIMESTAMP "
                   + "WHERE id = ? AND status = 'open'";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, order.getId());
            if (stmt.executeUpdate() == 0) {
                throw new SQLException("No se pudo cerrar el pedido");
            }
        }
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al cerrar conexión", e);
        }
    }
}