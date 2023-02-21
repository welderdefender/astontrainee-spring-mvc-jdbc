package ru.example.astonjdbc.dao;

import org.springframework.stereotype.Component;
import ru.example.astonjdbc.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserDAO {
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

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String SQL = "SELECT * FROM users";
            ResultSet resultSet = statement.executeQuery(SQL);

            while (resultSet.next()) {
                User user = new User();
                user.setUserId(resultSet.getInt("user_id"));
                user.setName(resultSet.getString("name"));
                user.setAge(resultSet.getInt("age"));

                userList.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userList;
    }

    public User findUser(int id) {
        User user = null;
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT * FROM users WHERE user_id=?");

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            user = new User();
            user.setUserId(resultSet.getInt("user_id"));
            user.setName(resultSet.getString("name"));
            user.setAge(resultSet.getInt("age"));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    public void save(User user) {
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("INSERT INTO users VALUES(default, ?, ?)");
            preparedStatement.setString(1, user.getName());
            preparedStatement.setInt(2, user.getAge());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(int id, User updatedUser) {
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(
                            "UPDATE users SET name=?, age=? WHERE user_id=?");
            preparedStatement.setString(1, updatedUser.getName());
            preparedStatement.setInt(2, updatedUser.getAge());
            preparedStatement.setInt(3, id);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(int id) {
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("DELETE FROM users WHERE user_id=?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}