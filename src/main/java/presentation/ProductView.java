package presentation;

import bll.ProductBLL;
import model.Client;
import model.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.util.List;

public class ProductView extends JFrame {

    private JComboBox<String> crudComboBox;
    private JLabel idLabel;
    private JLabel nameLabel;
    private JLabel quantityLabel;
    private JTextField idTextField;
    private JTextField nameTextField;
    private JTextField quantityTextField;

    private JButton actionButton;
    private JButton backButton;

    private JTable productTable;
    private DefaultTableModel tableModel;

    public ProductView() {
        setTitle("Product View");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(4, 2, 10, 10));

        crudComboBox = new JComboBox<>(new String[]{"Create", "Update", "Delete"});
        idLabel = new JLabel("ID:");
        nameLabel = new JLabel("Name:");
        quantityLabel = new JLabel("Quantity:");

        idTextField = new JTextField();
        nameTextField = new JTextField();
        quantityTextField = new JTextField();

        inputPanel.add(crudComboBox);
        inputPanel.add(new JLabel()); // Empty label for spacing
        inputPanel.add(idLabel);
        inputPanel.add(idTextField);
        inputPanel.add(nameLabel);
        inputPanel.add(nameTextField);
        inputPanel.add(quantityLabel);
        inputPanel.add(quantityTextField);

        JPanel buttonPanel = new JPanel();
        actionButton = new JButton("Perform Action");
        backButton = new JButton("Back");
        buttonPanel.add(actionButton);
        buttonPanel.add(backButton);

        String[] columnNames = getColumnNames(Client.class);

        tableModel = new DefaultTableModel(columnNames, 0);
        productTable = new JTable(tableModel);

        JScrollPane tableScrollPane = new JScrollPane(productTable);

        add(inputPanel, BorderLayout.NORTH);
        add(tableScrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        displayAllProducts();

        // Add action listener to the back button
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                PrincipalView principalView = new PrincipalView();
                principalView.setVisible(true);
            }
        });
        actionButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedAction = (String) crudComboBox.getSelectedItem();
                switch (selectedAction) {
                    case "Create":
                        createProduct();
                        displayAllProducts();
                        break;
                    case "Update":
                        updateProduct();
                        displayAllProducts();
                        break;
                    case "Delete":
                        deleteProduct();
                        displayAllProducts();
                        break;
                }
            }
        });

        setVisible(true);
    }

    private void createProduct() {
        try {
            int id = Integer.parseInt(idTextField.getText());
            String name = nameTextField.getText();
            int quantity = Integer.parseInt(quantityTextField.getText());

            // Create a new product object
            Product product = new Product(id, quantity, name);

            // Call the createProduct method in the ProductBLL
            ProductBLL productBLL = new ProductBLL();
            productBLL.createProduct(product);
            clearInputFields();

            JOptionPane.showMessageDialog(this, "Product created successfully.");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input format. Please enter numeric values for ID and Quantity.");
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void deleteProduct() {
        try {
            int id = Integer.parseInt(idTextField.getText());
            ProductBLL productBLL = new ProductBLL();
            Product product = productBLL.findProductById(id);
            productBLL.deleteProduct(product);
            clearInputFields();
            JOptionPane.showMessageDialog(this, "Product deleted successfully.");
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }
    private void updateProduct() {
        try {
            int id = Integer.parseInt(idTextField.getText());

            ProductBLL productBLL = new ProductBLL();
            Product product = productBLL.findProductById(id);

            String name = nameTextField.getText();
            if (!name.equals("")) {
                product.setName(name);
            }
            String quantityText = quantityTextField.getText();
            if (!quantityText.equals("")) {
                int quantity = Integer.parseInt(quantityText);
                product.setQuantity(quantity);
            }

            productBLL.updateProduct(product);
            clearInputFields();
            JOptionPane.showMessageDialog(this, "Product updated successfully.");

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void clearInputFields() {
        idTextField.setText("");
        nameTextField.setText("");
        quantityTextField.setText("");
    }

    private void displayAllProducts() {
        tableModel.setRowCount(0);

        ProductBLL productBLL = new ProductBLL();
        List<Product> products = productBLL.getAllProducts();
        displayAllObjects(products);
    }

    private <T> void displayAllObjects(List<T> objects) {
        DefaultTableModel model = (DefaultTableModel) productTable.getModel();
        model.setRowCount(0);

        for (T object : objects) {
            Class<?> objectClass = object.getClass();
            Field[] fields = objectClass.getDeclaredFields();

            Object[] rowData = new Object[fields.length];
            for (int i = 0; i < fields.length; i++) {
                try {
                    fields[i].setAccessible(true);
                    Object value = fields[i].get(object);
                    rowData[i] = value;
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

            model.addRow(rowData);
        }
    }
    private String[] getColumnNames(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        String[] columnNames = new String[fields.length];

        for (int i = 0; i < fields.length; i++) {
            columnNames[i] = fields[i].getName();
        }
        return columnNames;
    }

}

