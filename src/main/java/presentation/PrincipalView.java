package presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PrincipalView extends JFrame {
    private JButton clientButton;
    private JButton productButton;
    private JButton purchaseButton;

    public PrincipalView() {
        setTitle("Main Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);

        clientButton = new JButton("Client");
        productButton = new JButton("Product");
        purchaseButton = new JButton("Purchase");

        // Set the layout for the frame
        setLayout(new FlowLayout());

        // Add buttons to the frame
        add(clientButton);
        add(productButton);
        add(purchaseButton);

        // Add action listeners to the buttons
        clientButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openClientFrame();
            }
        });
        productButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openProductFrame();
            }
        });

        purchaseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openPurchaseFrame();
            }
        });
    }



    // Method to open the client frame
    private void openClientFrame() {
        // Create and display the client frame
        ClientView clientView = new ClientView();
        clientView.setVisible(true);
        setVisible(false); // Hide the principal frame
    }

    // Method to open the product frame
    private void openProductFrame() {
        // Create and display the product frame
        ProductView productView = new ProductView();
        productView.setVisible(true);
        setVisible(false); // Hide the principal frame
    }

    // Method to open the purchase frame
    private void openPurchaseFrame() {
        // Create and display the purchase frame
        PurchaseView purchaseView = new PurchaseView();
        purchaseView.setVisible(true);
        setVisible(false); // Hide the principal frame
    }

}
