package Program.DAO;

import Program.Model.Product;
import Program.Model.ConexionDB;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAOProduct {
    private Connection connection;

    public DAOProduct() throws SQLException {
        this.connection = ConexionDB.Conectar();
    }

    // CREATE - Guardar producto
    public boolean save(Product product) throws SQLException {
        String sql = "INSERT INTO products (name, description, price, cost, custom) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, product.getName());
            stmt.setString(2, product.getDescription());
            stmt.setDouble(3, product.getPrice());
            stmt.setDouble(4, product.getCost());
            stmt.setBoolean(5, product.isCustom());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        product.setId(rs.getInt(1));
                    }
                }
                return true;
            }
            return false;
        }
    }

    // READ - Listar todos los productos
    public List<Product> getAll() throws SQLException {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Product product = new Product(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getDouble("price"),
                    rs.getDouble("cost"),
                    rs.getBoolean("custom")
                );
                products.add(product);
            }
        }
        return products;
    }

    // UPDATE - Actualizar producto
    public boolean update(Product product) throws SQLException {
        String sql = "UPDATE products SET name = ?, description = ?, price = ?, cost = ?, custom = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, product.getName());
            stmt.setString(2, product.getDescription());
            stmt.setDouble(3, product.getPrice());
            stmt.setDouble(4, product.getCost());
            stmt.setBoolean(5, product.isCustom());
            stmt.setInt(6, product.getId());
            
            return stmt.executeUpdate() > 0;
        }
    }

    // DELETE - Eliminar producto
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM products WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    // Verificar si producto existe
    public boolean exists(String name) throws SQLException {
        String sql = "SELECT COUNT(*) FROM products WHERE name = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, name);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    // Cerrar conexión
    public void close() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}