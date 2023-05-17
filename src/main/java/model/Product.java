package model;

public class Product {
    private int id;
    private int quantity;

    private String name;

    public Product(int id, int quantity, String  name) {
        this.id = id;
        this.quantity = quantity;
        this.name = name;
    }
    public Product(int quantity, String name) {
        this.quantity = quantity;
        this.name = name;
    }
    public Product() {

    }
    public int getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
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
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
