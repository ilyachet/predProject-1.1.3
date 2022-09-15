package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private String SQL;
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        this.SQL = "CREATE TABLE IF NOT EXISTS `users`" +
                "(`id` INT NOT NULL AUTO_INCREMENT," +
                "`name` VARCHAR(45) NULL," +
                "`lastName` VARCHAR(45) NULL," +
                "`age` INT(3) NULL," +
                "PRIMARY KEY (`id`));";
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createSQLQuery(SQL).addEntity(User.class);
        query.executeUpdate();
        transaction.commit();
        session.close();
    }

    @Override
    public void dropUsersTable() {
        this.SQL = "DROP TABLE IF EXISTS users";
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createSQLQuery(SQL).addEntity(User.class);
        query.executeUpdate();

        transaction.commit();
        session.close();

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        session.save(new User(name, lastName, age));
        transaction.commit();
        session.close();
    }

    @Override
    public void removeUserById(long id) {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        session.createQuery("delete User where id = " + id).executeUpdate();

        transaction.commit();
        session.close();
    }

    @Override
    public List<User> getAllUsers() {

        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        List<User> list = session.createQuery("from User")
                .getResultList();

        transaction.commit();
        session.close();
        return list;
    }

    @Override
    public void cleanUsersTable() {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        session.createQuery("delete User").executeUpdate();

        transaction.commit();
        session.close();

    }
}
