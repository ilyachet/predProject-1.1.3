package jm.task.core.jdbc.dao;

import com.mysql.cj.Session;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private Statement statement;
    private String SQL;

    public UserDaoJDBCImpl() {

    }
    public void createUsersTable() {
        this.SQL = "CREATE TABLE IF NOT EXISTS `users`" +
                "(`id` INT NOT NULL AUTO_INCREMENT," +
                "`name` VARCHAR(45) NULL," +
                "`lastName` VARCHAR(45) NULL," +
                "`age` INT(3) NULL," +
                "PRIMARY KEY (`id`));";
        try {
            this.statement = Util.getConnection().createStatement();
            this.statement.executeUpdate(SQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        this.SQL = "drop table IF exists users;";
        try {
            this.statement = Util.getConnection().createStatement();
            this.statement.executeUpdate(SQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        StringBuilder stringBuilder = new StringBuilder("INSERT INTO users (name, lastName, age) values (");
        stringBuilder.append("'"+ name + "', '" + lastName + "', " + age + ")");
        this.SQL = stringBuilder.toString();
        try {
            this.statement = Util.getConnection().createStatement();
            this.statement.executeUpdate(SQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        this.SQL = "DELETE from users where id = " + id;
        try {
            this.statement = Util.getConnection().createStatement();
            this.statement.executeUpdate(SQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        this.SQL = "SELECT name, lastName, age FROM users";
        List<User> list = new LinkedList<>();
        try {
            this.statement = Util.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(SQL);
            while (resultSet.next()) {
                list.add(new User(resultSet.getString("name"), resultSet.getString("lastName"), (byte) resultSet.getInt("age")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public void cleanUsersTable() {
        SQL = "DELETE FROM users";
        try {
            this.statement = Util.getConnection().createStatement();
            this.statement.executeUpdate(SQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
