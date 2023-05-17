package start;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import bll.ClientBLL;
import bll.ProductBLL;
import bll.PurchaseBLL;
import model.Client;
import model.Product;
import model.Purchase;
import presentation.PrincipalView;

/**
 * @Author: Technical University of Cluj-Napoca, Romania Distributed Systems
 *          Research Laboratory, http://dsrl.coned.utcluj.ro/
 * @Since: Apr 03, 2017
 */
public class Start {
    protected static final Logger LOGGER = Logger.getLogger(Start.class.getName());

    public static void main(String[] args) throws SQLException {


        PrincipalView principalView = new PrincipalView();
        principalView.setVisible(true);

    }

}
