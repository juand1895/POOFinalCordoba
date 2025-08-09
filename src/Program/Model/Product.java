package Program.Model;

public class Product {
    private int id;
    private String name;
    private String description;
    private double price;
    private double cost;
    private boolean custom; // "elaboracion" -> "custom" (producto personalizado)

    // Constructores
    public Product() {
        this.name = "";
        this.description = "";
        this.price = 0.0;
        this.cost = 0.0;
        this.custom = false;
    }

    // Para creación manual (sin ID)
    public Product(String name, String description, double price, double cost, boolean custom) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.cost = cost;
        this.custom = custom;
    }

    // Para BD (con ID)
    public Product(int id, String name, String description, double price, double cost, boolean custom) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.cost = cost;
        this.custom = custom;
    }

    // Para casos donde solo necesitas el ID (ej: búsquedas)
    public Product(int id) {
        this.id = id;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public boolean isCustom() {
        return custom;
    }

    public void setCustom(boolean custom) {
        this.custom = custom;
    }

    // Representación en String (útil para comboboxes o logs)
    @Override
    public String toString() {
        return name + " - " + description + " (Price: $" + price + ")";
    }
}