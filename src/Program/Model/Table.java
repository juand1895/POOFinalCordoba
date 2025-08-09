package Program.Model;

public class Table {
    private int id;
    private String name;  // "name" en vez de "nombre"

    // Constructores (adaptados)
    public Table() {
        this.name = "";  // Valor por defecto
    }

    public Table(String name) {
        this.name = name;
    }

    // Constructor para datos de BD
    public Table(int id, String name) {
        this.id = id;
        this.name = name;
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

    // toString() útil para comboboxes o listas
    @Override
    public String toString() {
        return name;  // Mostrará solo el nombre en JComboBox/JList
    }
}