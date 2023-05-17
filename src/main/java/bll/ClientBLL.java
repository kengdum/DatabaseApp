package bll;

import dao.ClientDAO;
import model.Client;

import java.util.List;
import java.util.NoSuchElementException;

public class ClientBLL {

    private ClientDAO clientDAO;

    public ClientBLL() {
        clientDAO = new ClientDAO();
    }

    public Client findClientById(int id) {
        Client cl = clientDAO.findById(id);
        if(cl == null) {
            throw  new NoSuchElementException("The client with id = " + id + "was not found!");
        }
        return cl;
    }
    public List<Client> getAllClients() {
        return clientDAO.findAll();
    }
    public void createClient(Client client) {
        clientDAO.insert(client);
    }
    public void updateClient(Client client) {
        clientDAO.update(client);
    }
    public void deleteClient(Client client) {
        if(client == null) {
            throw new IllegalArgumentException("Client cannot be null");
        }
        int clientId = client.getId();
        clientDAO.deleteById(clientId);
    }

}
