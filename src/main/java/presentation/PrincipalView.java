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

        setLayout(new FlowLayout());

        add(clientButton);
        add(productButton);
        add(purchaseButton);

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



    private void openClientFrame() {
        ClientView clientView = new ClientView();
        clientView.setVisible(true);
        setVisible(false);
    }

    private void openProductFrame() {
        ProductView productView = new ProductView();
        productView.setVisible(true);
        setVisible(false);
    }

    private void openPurchaseFrame() {
        PurchaseView purchaseView = new PurchaseView();
        purchaseView.setVisible(true);
        setVisible(false);
    }

}
