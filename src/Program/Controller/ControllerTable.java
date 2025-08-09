package Program.Controller;

import Program.DAO.DAOTable;
import Program.Model.Table;
import java.sql.SQLException;
import java.util.List;

public class ControllerTable {
    private final DAOTable daoTables;

    public ControllerTable() {
        this.daoTables = new DAOTable();
    }

    // --- CRUD ---
    // 1. CREAR (C)
    public boolean crearMesa(String nombre) throws SQLException {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("❌ El nombre no puede estar vacío");
        }
        Table mesa = new Table(nombre);
        return daoTables.save(mesa);
    }

    // 2. LEER (R) - Todas las mesas
    public List<Table> listarMesas() throws SQLException {
        return daoTables.getAll();
    }

    // 3. LEER (R) - Mesa por ID
    public Table buscarMesaPorId(int id) throws SQLException {
        return daoTables.getAll().stream()
                .filter(m -> m.getId() == id)
                .findFirst()
                .orElseThrow(() -> new SQLException("❌ Mesa no encontrada"));
    }

    // 4. ACTUALIZAR (U)
    public boolean actualizarMesa(int id, String nuevoNombre) throws SQLException {
        if (nuevoNombre == null || nuevoNombre.trim().isEmpty()) {
            throw new IllegalArgumentException("❌ Nombre inválido");
        }
        return daoTables.update(id, nuevoNombre);
    }

    // 5. ELIMINAR (D)
    public boolean eliminarMesa(int id) throws SQLException {
        return daoTables.delete(id);
    }

    // Cerrar conexión
    public void cerrar() {
        daoTables.close();
    }
}
