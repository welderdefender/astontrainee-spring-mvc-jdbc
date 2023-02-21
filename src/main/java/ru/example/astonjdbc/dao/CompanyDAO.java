package ru.example.astonjdbc.dao;

import org.springframework.stereotype.Component;
import ru.example.astonjdbc.model.Company;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class CompanyDAO {
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

    public List<Company> getAllCompanies() {
        List<Company> companyList = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String SQL = "SELECT * FROM company";
            ResultSet resultSet = statement.executeQuery(SQL);

            while (resultSet.next()) {
                Company company = new Company();
                company.setCompanyId(resultSet.getInt("company_id"));
                company.setName(resultSet.getString("name"));

                companyList.add(company);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return companyList;
    }

    public Company findCompany(int id) {
        Company company = null;
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT * FROM company WHERE company_id=?");

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            company = new Company();
            company.setCompanyId(resultSet.getInt("company_id"));
            company.setName(resultSet.getString("name"));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return company;
    }

    public void save(Company company) {

        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("INSERT INTO company VALUES(default, ?)");
            preparedStatement.setString(1, company.getName());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(int id, Company updatedCompany) {
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(
                            "UPDATE company SET name=? WHERE company_id=?");
            preparedStatement.setString(1, updatedCompany.getName());
            preparedStatement.setInt(2, id);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(int id) {
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("DELETE FROM company WHERE company_id=?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}