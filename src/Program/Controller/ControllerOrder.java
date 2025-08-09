package Program.Controller;

import Program.DAO.DAOOrder;
import Program.Model.Order;
import Program.Model.ItemOrder;
import Program.Model.Table;
import java.sql.SQLException;
import java.util.List;

public class ControllerOrder {
    private final DAOOrder daoOrder;

    public ControllerOrder() throws SQLException {
        this.daoOrder = new DAOOrder();
    }

    // Crea un nuevo pedido
    public void createOrder(Order order) throws SQLException {
        daoOrder.create(order);
    }

    // Obtiene todos los pedidos activos
    public List<Order> getAllActiveOrders() throws SQLException {
        return daoOrder.getAllActiveOrders();
    }

    // Obtiene los ítems de un pedido
    public List<ItemOrder> getItemsByOrder(int orderId) throws SQLException {
        return daoOrder.getItemsByOrder(orderId);
    }

    // Agrega un ítem al pedido
    public void addItem(Order order, ItemOrder item) throws SQLException {
        daoOrder.addItem(order, item);
    }

    // Elimina un pedido (y sus ítems automáticamente por ON DELETE CASCADE)
    public void deleteOrder(Order order) throws SQLException {
        daoOrder.close(order); // Cierra primero (o implementa deleteOrder en DAO si prefieres)
    }

    // Obtiene mesa por ID (¿Necesitas esto? Podría estar en ControllerTable)
    public Table getTableById(int tableId) throws SQLException {
        // Implementa esto en DAOTable o déjalo si DAOOrder ya lo tiene
        return null;
    }

    // Obtiene pedido activo de una mesa
    public Order getActiveOrderByTable(Table table) throws SQLException {
        return daoOrder.getActiveByTable(table);
    }

    // Cierra un pedido
    public void closeOrder(Order order) throws SQLException {
        daoOrder.close(order);
    }

    // Método opcional para devolver productos al stock (si lo necesitas)
    public void returnProductsToStock(int orderId) throws SQLException {
        // Implementación similar a tu amigo, pero con ItemOrder
    }
}