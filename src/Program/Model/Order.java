package Program.Model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


public class Order {
    private int id;
    private Table table;
    private Timestamp openTime;
    private Timestamp closeTime;
    private List<ItemOrder> items;
    private double subtotal;
    private double total;
    private String status; // "open" o "closed"

    // Constructores
    public Order() {
        this.items = new ArrayList<>();
        this.openTime = new Timestamp(System.currentTimeMillis());
        this.status = "open";
    }

    public Order(Table table) {
        this();
        this.table = table;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public Timestamp getOpenTime() {
        return openTime;
    }

    public void setOpenTime(Timestamp openTime) {
        this.openTime = openTime;
    }

    public Timestamp getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(Timestamp closeTime) {
        this.closeTime = closeTime;
    }

    public List<ItemOrder> getItems() {
        return items;
    }

    public void setItems(List<ItemOrder> items) {
        this.items = items != null ? items : new ArrayList<>();
        calculateSubtotalAndTotal();
    }

    public double getSubtotal() {
        return subtotal;
    }

    public double getTotal() {
        return total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Métodos clave
    public void addItem(ItemOrder item) {
        this.items.add(item);
        calculateSubtotalAndTotal();
    }

    public void removeItem(ItemOrder Item) {
        this.items.remove(Item);
        calculateSubtotalAndTotal();
    }

    private void calculateSubtotalAndTotal() {
        this.subtotal = items.stream().mapToDouble(ItemOrder::getSubtotal).sum();
        this.total = subtotal; // (Opcional: agregar descuentos después)
    }

    // Método auxiliar para imprimir detalles (útil para debug)
    @Override
    public String toString() {
        return "Order [id=" + id + ", table=" + table.getId() + ", status=" + status + ", total=" + total + "]";
    }
}