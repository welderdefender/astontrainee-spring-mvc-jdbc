package ru.example.astonjdbc.dao;

import org.springframework.stereotype.Component;
import ru.example.astonjdbc.model.UsersFlights;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserFlightsDAO {
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "iamroot";

    private static Connection connection;

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<UsersFlights> getAll() {
        List<UsersFlights> usersFlightsList = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String SQL = "SELECT * FROM users_flights UF " +
                    "JOIN users U ON U.user_id = UF.user_id";
            ResultSet resultSet = statement.executeQuery(SQL);

            while (resultSet.next()) {
                UsersFlights usersFlights = new UsersFlights();
                usersFlights.setUserFlightsId(resultSet.getInt("users_flights_id"));
                usersFlights.setUsername(resultSet.getString("name"));
                usersFlights.setFlightId(resultSet.getInt("flight_id"));

                usersFlightsList.add(usersFlights);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return usersFlightsList;
    }

    public UsersFlights findUserFlights(int id) {
        UsersFlights usersFlights = null;
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT * FROM(SELECT * FROM users_flights UF JOIN users U ON U" +
                            ".user_id = UF.user_id) AS ASD " +
                            "WHERE users_flights_id=?");


            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            usersFlights = new UsersFlights();
            usersFlights.setUserFlightsId(resultSet.getInt("users_flights_id"));
            usersFlights.setUserId(resultSet.getInt("user_id"));
            usersFlights.setUsername(resultSet.getString("name"));
            usersFlights.setFlightId(resultSet.getInt("flight_id"));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return usersFlights;
    }
}