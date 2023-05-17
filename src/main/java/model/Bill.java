package model;

import connection.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public record Bill(String productName, int amount, int clientId) {
    private String createInsertQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO Bill (productname, amount, clientid) VALUES (?, ?, ?)");
        return sb.toString();
    }

    private void setInsertValues(PreparedStatement statement) throws SQLException {
        statement.setString(1, productName);
        statement.setInt(2, amount);
        statement.setInt(3, clientId);
    }
    public void insert() {
        try (Connection connection = ConnectionFactory.getConnection()) {
            String query = createInsertQuery();
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                setInsertValues(statement);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}

