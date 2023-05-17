package bll;

import dao.ProductDAO;
import model.Product;

import java.util.List;
import java.util.NoSuchElementException;

public class ProductBLL {
    private ProductDAO productDAO;

    public ProductBLL() {
        productDAO = new ProductDAO();
    }
    public Product findProductById(int id) {
        Product pr = productDAO.findById(id);
        if(pr == null) {
            throw new NoSuchElementException("The product with the id =" + id + "was not found" );

        }
        return pr;
    }
    public List<Product> getAllProducts() {
        return productDAO.findAll();
    }
    public void createProduct(Product product) {
        productDAO.insert(product);
    }
    public void updateProduct(Product product) {
        productDAO.update(product);
    }
    public void deleteProduct(Product product) {
        if(product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        int productId = product.getId();
        productDAO.deleteById(productId);
    }
}
