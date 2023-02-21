package ru.example.astonjdbc.dao;

import org.springframework.stereotype.Component;
import ru.example.astonjdbc.model.AirportCodes;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class AirportCodesDAO {
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

    public List<AirportCodes> getAllCodes() {
        List<AirportCodes> codesList = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String SQL = "SELECT * FROM airport_codes";
            ResultSet resultSet = statement.executeQuery(SQL);

            while (resultSet.next()) {
                AirportCodes airportCodes = new AirportCodes();
                airportCodes.setAirportCodeId(resultSet.getInt("airport_code_id"));
                airportCodes.setAirportCodeName(resultSet.getString("airport_code_name"));

                codesList.add(airportCodes);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return codesList;
    }

    public AirportCodes findAirportCode(int id) {
        AirportCodes airportCodes = null;
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT * FROM airport_codes WHERE airport_code_id=?");

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            airportCodes = new AirportCodes();
            airportCodes.setAirportCodeId(resultSet.getInt("airport_code_id"));
            airportCodes.setAirportCodeName(resultSet.getString("airport_code_name"));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return airportCodes;
    }

    public void save(AirportCodes airportCodes) {
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("INSERT INTO airport_codes VALUES(default, ?)");
            preparedStatement.setString(1, airportCodes.getAirportCodeName());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(int id, AirportCodes updatedAirportCodes) {
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(
                            "UPDATE airport_codes SET airport_code_name=? WHERE airport_code_id=?");
            preparedStatement.setString(1, updatedAirportCodes.getAirportCodeName());
            preparedStatement.setInt(2, id);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(int id) {
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("DELETE FROM airport_codes WHERE airport_code_id=?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}