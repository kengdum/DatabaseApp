package model;

public class Purchase {
    private int id;
    private int clientid;

    private int productid;
    private int amount;

    public Purchase(int id, int clientid, int productid, int amount) {
        this.id = id;
        this.clientid = clientid;
        this.productid = productid;
        this.amount = amount;
    }
    public Purchase(int clientid, int productid, int amount) {
        this.clientid = clientid;
        this.productid = productid;
        this.amount = amount;
    }
    public Purchase() {

    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getClientid() {
        return this.clientid;
    }
    public void setClientid(int clientid) {
        this.clientid = clientid;
    }
    public int getProductid() {
        return  this.productid;
    }
    public void setProductid(int productid) {
        this.productid = productid;
    }
    public void setAmount(int amount) {
        this.amount = amount;
    }
    public int getAmount() {
        return this.amount;
    }

}
