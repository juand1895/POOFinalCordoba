package Program.DAO;

import Program.Model.Table;
import Program.Model.ConexionDB;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAOTable {
    private Connection connection;

    public DAOTable() {
        try {
            this.connection = ConexionDB.Conectar(); // Usa tu clase de conexión
        } catch (SQLException e) {
            System.err.println("Error al conectar: " + e.getMessage());
        }
    }

    // 1. Guardar mesa (Create)
    public boolean save(Table table) throws SQLException {
        String sql = "INSERT INTO `tables` (name) VALUES (?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, table.getName());
            stmt.executeUpdate();
            
            // Obtener el ID generado
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                table.setId(rs.getInt(1));
            }
            return true;
        }
    }

    // 2. Listar todas las mesas (Read)
    public List<Table> getAll() throws SQLException {
        List<Table> tables = new ArrayList<>();
        String sql = "SELECT * FROM `tables`";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Table table = new Table(
                    rs.getInt("id"),
                    rs.getString("name")
                );
                tables.add(table);
            }
        }
        return tables;
    }

    // 3. Actualizar mesa (Update)
    public boolean update(int id, String newName) throws SQLException {
        String sql = "UPDATE `tables` SET name = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, newName);
            stmt.setInt(2, id);
            return stmt.executeUpdate() > 0;
        }
    }

    // 4. Eliminar mesa (Delete)
    public boolean delete(int id) throws SQLException {
        if (hasActiveOrders(id)) {
            throw new SQLException("No se puede eliminar: tiene pedidos activos");
        }
        
        String sql = "DELETE FROM `tables` WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    // Helper: Verificar pedidos activos (para futuras integraciones)
    private boolean hasActiveOrders(int tableId) throws SQLException {
        // Implementar luego si añades pedidos
        return false;
    }

    // Cerrar conexión
    public void close() {
        try {
            if (connection != null) connection.close();
        } catch (SQLException e) {
            System.err.println("Error al cerrar conexión: " + e.getMessage());
        }
    }
}
