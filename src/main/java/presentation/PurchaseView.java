package presentation;

import bll.PurchaseBLL;
import model.Purchase;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.util.List;

public class PurchaseView extends JFrame {
    private JComboBox<String> crudComboBox;
    private JLabel idLabel;
    private JLabel clientIdLabel;
    private JLabel productIdLabel;
    private JLabel amountLabel;

    private JTextField idTextField;
    private JTextField clientIdTextField;
    private JTextField productIdTextField;
    private JTextField amountTextField;

    private JButton actionButton;
    private JButton backButton;

    private JTable purchaseTable;
    private DefaultTableModel tableModel;

    public PurchaseView() {
        setTitle("Purchase View");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(5, 2, 10, 10));

        crudComboBox = new JComboBox<>(new String[]{"Create", "Update", "Delete"});
        idLabel = new JLabel("ID:");
        clientIdLabel = new JLabel("Client ID:");
        productIdLabel = new JLabel("Product ID:");
        amountLabel = new JLabel("Amount:");

        idTextField = new JTextField();
        clientIdTextField = new JTextField();
        productIdTextField = new JTextField();
        amountTextField = new JTextField();

        inputPanel.add(crudComboBox);
        inputPanel.add(new JLabel()); // Empty label for spacing
        inputPanel.add(idLabel);
        inputPanel.add(idTextField);
        inputPanel.add(clientIdLabel);
        inputPanel.add(clientIdTextField);
        inputPanel.add(productIdLabel);
        inputPanel.add(productIdTextField);
        inputPanel.add(amountLabel);
        inputPanel.add(amountTextField);

        JPanel buttonPanel = new JPanel();
        actionButton = new JButton("Perform Action");
        backButton = new JButton("Back");
        buttonPanel.add(actionButton);
        buttonPanel.add(backButton);

        // Create the table model
        tableModel = new DefaultTableModel(new String[]{"ID", "Client ID", "Product ID", "Amount"}, 0);
        purchaseTable = new JTable(tableModel);

        JScrollPane tableScrollPane = new JScrollPane(purchaseTable);

        add(inputPanel, BorderLayout.NORTH);
        add(tableScrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        displayAllPurchases();

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
                        createPurchase();
                        displayAllPurchases();
                        break;
                    case "Update":
                        updatePurchase();
                        displayAllPurchases();
                        break;
                    case "Delete":
                        deletePurchase();
                        displayAllPurchases();
                        break;
                }
            }
        });

        setVisible(true);
    }

    private void createPurchase() {
        try {
            int id = Integer.parseInt(idTextField.getText());
            int clientId = Integer.parseInt(clientIdTextField.getText());
            int productId = Integer.parseInt(productIdTextField.getText());
            int amount = Integer.parseInt(amountTextField.getText());

            // Create a new purchase object
            Purchase purchase = new Purchase(id, clientId, productId, amount);

            // Call the createPurchase method in the PurchaseBLL
            PurchaseBLL purchaseBLL = new PurchaseBLL();
            purchaseBLL.createPurchase(purchase);
            clearInputFields();
            JOptionPane.showMessageDialog(this, "Purchase created successfully.");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input format. Please enter numeric values for ID, Client ID, Product ID, and Amount.");
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void deletePurchase() {
        try {
            int id = Integer.parseInt(idTextField.getText());
            PurchaseBLL purchaseBLL = new PurchaseBLL();
            Purchase purchase = purchaseBLL.findPurchaseById(id);
            purchaseBLL.deletePurchase(purchase);
            clearInputFields();
            JOptionPane.showMessageDialog(this, "Purchase deleted successfully.");
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void updatePurchase() {
        try {
            int id = Integer.parseInt(idTextField.getText());
            PurchaseBLL purchaseBLL = new PurchaseBLL();
            Purchase purchase = purchaseBLL.findPurchaseById(id);

            String clientIdText = clientIdTextField.getText();
            if (!clientIdText.equals("")) {
                int clientId = Integer.parseInt(clientIdText);
                purchase.setClientid(clientId);
            }

            String productIdText = productIdTextField.getText();
            if (!productIdText.equals("")) {
                int productId = Integer.parseInt(productIdText);
                purchase.setProductid(productId);
            }

            String amountText = amountTextField.getText();
            if (!amountText.equals("")) {
                int amount = Integer.parseInt(amountText);
                purchase.setAmount(amount);
            }

            purchaseBLL.updatePurchase(purchase);
            clearInputFields();
            JOptionPane.showMessageDialog(this, "Purchase updated successfully.");

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void clearInputFields() {
        idTextField.setText("");
        clientIdTextField.setText("");
        productIdTextField.setText("");
        amountTextField.setText("");
    }

    private void displayAllPurchases() {
        tableModel.setRowCount(0);

        PurchaseBLL purchaseBLL = new PurchaseBLL();
        List<Purchase> purchases = purchaseBLL.getAllPurchases();
        displayAllObjects(purchases);
    }

    private <T> void displayAllObjects(List<T> objects) {
        DefaultTableModel model = (DefaultTableModel) purchaseTable.getModel();
        model.setRowCount(0);

        for (Object object : objects) {
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
}

