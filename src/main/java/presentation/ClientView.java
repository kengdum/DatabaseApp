package presentation;

import bll.ClientBLL;
import model.Client;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Vector;

public class ClientView extends JFrame {
    private JComboBox<String> crudComboBox;
    private JLabel idLabel;
    private JLabel nameLabel;
    private JLabel addressLabel;
    private JLabel emailLabel;
    private JLabel ageLabel;

    private JTextField idTextField;
    private JTextField nameTextField;
    private JTextField addressTextField;
    private JTextField emailTextField;
    private JTextField ageTextField;

    private JButton actionButton;
    private JButton backButton;

    private JTable clientTable;
    private DefaultTableModel tableModel;

    public ClientView() {
        setTitle("Client View");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(6, 2, 10, 10));

        crudComboBox = new JComboBox<>(new String[]{"Create", "Update", "Delete"});
        idLabel = new JLabel("ID:");
        nameLabel = new JLabel("Name:");
        addressLabel = new JLabel("Address:");
        emailLabel = new JLabel("Email:");
        ageLabel = new JLabel("Age:");

        idTextField = new JTextField();
        nameTextField = new JTextField();
        addressTextField = new JTextField();
        emailTextField = new JTextField();
        ageTextField = new JTextField();

        inputPanel.add(crudComboBox);
        inputPanel.add(new JLabel()); // Empty label for spacing
        inputPanel.add(idLabel);
        inputPanel.add(idTextField);
        inputPanel.add(nameLabel);
        inputPanel.add(nameTextField);
        inputPanel.add(addressLabel);
        inputPanel.add(addressTextField);
        inputPanel.add(emailLabel);
        inputPanel.add(emailTextField);
        inputPanel.add(ageLabel);
        inputPanel.add(ageTextField);

        JPanel buttonPanel = new JPanel();
        actionButton = new JButton("Perform Action");
        backButton = new JButton("Back");
        buttonPanel.add(actionButton);
        buttonPanel.add(backButton);

        // Create the table model
        tableModel = new DefaultTableModel(new String[]{"ID", "Name", "Address", "Email", "Age"}, 0);
        clientTable = new JTable(tableModel);

        JScrollPane tableScrollPane = new JScrollPane(clientTable);

        add(inputPanel, BorderLayout.NORTH);
        add(tableScrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        displayAllClients();

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
                        createClient();
                        displayAllClients();
                        break;
                    case "Update":
                        updateClient();
                        displayAllClients();
                        break;
                    case "Delete":
                        deleteClient();
                        displayAllClients();
                        // Implement delete logic
                        break;
                }
            }
        });

        setVisible(true);
    }

    private void createClient() {
        try {
            int id = Integer.parseInt(idTextField.getText());
            String name = nameTextField.getText();
            String address = addressTextField.getText();
            String email = emailTextField.getText();
            int age = Integer.parseInt(ageTextField.getText());

            // Create a new client object
            Client client = new Client(id, name, address, email, age);

            // Call the createClient method in the ClientBLL
            ClientBLL clientBLL = new ClientBLL();
            clientBLL.createClient(client);
            clearInputFields();

            JOptionPane.showMessageDialog(this, "Client created successfully.");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input format. Please enter numeric values for ID and Age.");
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }
    private void deleteClient() {
        try {
            int id = Integer.parseInt(idTextField.getText());
            ClientBLL clientBLL = new ClientBLL();
            Client client = clientBLL.findClientById(id);
            System.out.println(client);
            clientBLL.deleteClient(client);
            clearInputFields();
            JOptionPane.showMessageDialog(this, "Client deleted successfully.");
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }
    private void updateClient() {
        try {
            int id = Integer.parseInt(idTextField.getText());

            ClientBLL clientBLL = new ClientBLL();
            Client client = clientBLL.findClientById(id);

            String name = nameTextField.getText();
            if (!name.equals("")) {
                client.setName(name);
            }
            String address = addressTextField.getText();
            if (!address.equals("")) {
                client.setAddress(address);
            }
            String email = emailTextField.getText();
            if (!email.equals("")) {
                client.setEmail(email);
            }
            String ageText = ageTextField.getText();
            if (!ageText.equals("")) {
                int age = Integer.parseInt(ageText);
                client.setAge(age);
            }

            clientBLL.updateClient(client);
            clearInputFields();
            JOptionPane.showMessageDialog(this, "Client updated successfully.");

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }
    private void clearInputFields() {
        idTextField.setText("");
        nameTextField.setText("");
        addressTextField.setText("");
        emailTextField.setText("");
        ageTextField.setText("");
    }

    private void displayAllClients() {
        tableModel.setRowCount(0);

        ClientBLL clientBLL = new ClientBLL();
        List<Client> clients = clientBLL.getAllClients();
        displayAllObjects(clients);

    }
    private <T> void displayAllObjects(List<T> objects) {
        DefaultTableModel model = (DefaultTableModel) clientTable.getModel();
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