package Program.Controller;

import Program.DAO.DAOProduct;
import Program.Model.Product;
import java.sql.SQLException;
import java.util.List;

public class ControllerProduct {
    private final DAOProduct daoProduct;

    public ControllerProduct() throws SQLException {
        this.daoProduct = new DAOProduct();
    }

    // Crear producto
    public boolean createProduct(Product product) throws SQLException {
        if (daoProduct.exists(product.getName())) {
            return false; // Producto ya existe
        }
        return daoProduct.save(product);
    }

    // Obtener todos los productos
    public List<Product> getAllProducts() throws SQLException {
        return daoProduct.getAll();
    }

    // Actualizar producto
    public boolean updateProduct(Product product) throws SQLException {
        return daoProduct.update(product);
    }

    // Eliminar producto
    public boolean deleteProduct(int id) throws SQLException {
        return daoProduct.delete(id);
    }

    // Verificar existencia
    public boolean productExists(String name) throws SQLException {
        return daoProduct.exists(name);
    }

    // Cerrar conexión
    public void close() throws SQLException {
        daoProduct.close();
    }
}