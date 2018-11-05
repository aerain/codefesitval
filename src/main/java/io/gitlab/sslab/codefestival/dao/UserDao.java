package io.gitlab.sslab.codefestival.dao;

import io.gitlab.sslab.codefestival.service.User;

import java.sql.*;

public class UserDao {
    public User get(String id) throws ClassNotFoundException, SQLException {
        Class.forName("org.mariadb.jdbc.Driver");
        Connection connection = DriverManager.getConnection(
                "jdbc:mariadb://172.18.102.128/programming_contest", "contest", "sslab08295860");

        PreparedStatement preparedStatement = connection.prepareStatement(
                "select * from users where id = ?");
        preparedStatement.setString(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();

        User user = new User();
        user.setId(resultSet.getString("id"));
        user.setPassword(resultSet.getString("password"));
        user.setName(resultSet.getString("name"));
        user.setScoreGet(resultSet.getInt("score_get"));
        user.setScoreBool(resultSet.getBoolean("score_bool"));

        resultSet.close();
        preparedStatement.close();
        connection.close();

        return user;
    }
}
