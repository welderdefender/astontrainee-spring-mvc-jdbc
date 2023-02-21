package ru.example.astonjdbc.dao;

import org.springframework.stereotype.Component;
import ru.example.astonjdbc.model.Flight;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class FlightDAO {
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

    public List<Flight> getAllFlights() {
        List<Flight> flightList = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String SQL = "SELECT * FROM flights F " +
                    "JOIN company C ON C.company_id = F.flight_id " +
                    "JOIN airport_codes AC on AC.airport_code_id = F.flight_id";
            ResultSet resultSet = statement.executeQuery(SQL);

            while (resultSet.next()) {
                Flight flight = new Flight();
                flight.setFlightId(resultSet.getInt("flight_id"));
                flight.setCompany(resultSet.getInt("company"));
                flight.setCompanyName(resultSet.getString("name"));
                flight.setAirportCode((resultSet.getInt("airport_code")));
                flight.setAirportCodeName(resultSet.getString("airport_code_name"));
                flight.setFlightDate(resultSet.getString("flight_date"));
                flight.setFlightFrom(resultSet.getString("flight_from"));
                flight.setFlightTo(resultSet.getString("flight_to"));

                flightList.add(flight);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return flightList;
    }

    public Flight findFlight(int id) {
        Flight flight = null;
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT * FROM flights WHERE flight_id=?");

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            flight = new Flight();
            flight.setFlightId(resultSet.getInt("flight_id"));
            flight.setCompany(resultSet.getInt("company"));
            flight.setAirportCode(resultSet.getInt("airport_code"));
            flight.setFlightDate(resultSet.getString("flight_date"));
            flight.setFlightFrom(resultSet.getString("flight_from"));
            flight.setFlightTo(resultSet.getString("flight_to"));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return flight;
    }

    public void save(Flight flight) {

        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("INSERT INTO flights VALUES(default, ?, ?, ?, ?, ?)");
            preparedStatement.setInt(1, flight.getCompany());
            preparedStatement.setInt(2, flight.getAirportCode());
            preparedStatement.setString(3, flight.getFlightDate());
            preparedStatement.setString(4, flight.getFlightFrom());
            preparedStatement.setString(5, flight.getFlightTo());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(int id, Flight updatedFlight) {
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(
                            "UPDATE flights SET flight_date=?, flight_from=?, flight_to=? " +
                                    "WHERE flight_id=?");
            preparedStatement.setString(1, updatedFlight.getFlightDate());
            preparedStatement.setString(2, updatedFlight.getFlightFrom());
            preparedStatement.setString(3, updatedFlight.getFlightTo());
            preparedStatement.setInt(4, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(int id) {
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("DELETE FROM flights WHERE flight_id=?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}