package bll;

import dao.PurchaseDAO;
import model.Bill;
import model.Product;
import model.Purchase;
import start.ReflectionExample;

import java.sql.Ref;
import java.util.List;
import java.util.NoSuchElementException;

public class PurchaseBLL {
    private PurchaseDAO orderDAO;

    public PurchaseBLL() {
        orderDAO = new PurchaseDAO();
    }
    public Purchase findPurchaseById(int id) {
        Purchase or = orderDAO.findById(id);
        if(or == null) {
            throw  new NoSuchElementException("The order with id = " + id + "was not found!");
        }
        return or;
    }
    public List<Purchase> getAllPurchases() {
        return orderDAO.findAll();
    }
    public void createPurchase(Purchase purchase) {
        int productId = purchase.getProductid();
        ProductBLL productBLL = new ProductBLL();
        Product product = productBLL.findProductById(productId);
        String productname = product.getName();
        if (product != null && product.getQuantity() - purchase.getAmount() > 0) {
            orderDAO.insert(purchase);

            product.setQuantity(product.getQuantity() - purchase.getAmount());
            productBLL.updateProduct(product);
            Bill bill = new Bill(productname, purchase.getAmount(), purchase.getClientid());
            bill.insert();
        } else {
            throw new IllegalArgumentException("Product with ID " + productId + " is not available");
        }
    }

    public void updatePurchase(Purchase purchase) {
        orderDAO.update(purchase);
    }

    public void deletePurchase(Purchase purchase) {
        if (purchase == null) {
            throw new IllegalArgumentException("Purchase cannot be null.");
        }
        int purchaseId = purchase.getId();
        ProductBLL productBLL = new ProductBLL();
        Product product = productBLL.findProductById(purchase.getProductid());
        product.setQuantity(product.getQuantity() + purchase.getAmount());
        productBLL.updateProduct(product);
        System.out.println(purchaseId);
        orderDAO.deleteById(purchaseId);
    }
}
