package com.epam.tsylko.andrei.dao.impl;


import com.epam.tsylko.andrei.dao.UserDao;
import com.epam.tsylko.andrei.dao.pool.ConnectionPool;
import com.epam.tsylko.andrei.entities.Address;
import com.epam.tsylko.andrei.entities.Role;
import com.epam.tsylko.andrei.entities.User;
import com.epam.tsylko.andrei.dao.exceptions.DAOException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {

    private static Logger logger = Logger.getLogger(UserDaoImpl.class);
    private static final String ADD_USER = "INSERT INTO `library`.`user` (`login`, `password`, `userName`, `userSurname`, `birthday`, `address`, `email`, `phone`) " +
            "VALUES (?, ?, ?, ?, ?,?, ?, ?);";

    private static final String EDIT_USER = "UPDATE `library`.`user` " +
            "SET  `password`=?, `userName`=?, `userSurname`=?, `birthday`=?, `email`=?, `phone`=? WHERE `id`=?;";

    private static final String USER_BLOCK_STATUS = "UPDATE `library`.`user` SET `enabled`=? WHERE `id`= ?;";

    private static final String SET_ROLE = "UPDATE `library`.`user` SET `role`= ? WHERE `id`=?;";
    private static final String GET_USER = "SELECT * FROM library.user where id =?";
    private static final String GET_USERS = "SELECT * FROM library.user";


    @Override
    public void registration(User user) throws DAOException {
        logger.debug("add user");
        ConnectionPool connectionPool = null;
        Connection connection = null;
        PreparedStatement ps = null;
        try {
//            TODO change connection pool in dao layer
            connectionPool = new ConnectionPool();
            connection = connectionPool.getConnection();

            ps = connection.prepareStatement(ADD_USER);
            ps = getUserData(user, ps);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Cannot add user to DB", e);
        } finally {
            if (connectionPool != null) {
                connectionPool.closeConnection(connection, ps);
            }
        }
    }

    //TODO complete methods
    @Override
    public void signIn(User user) {

    }

    @Override
    public List<User> getAllUsers() throws DAOException {
        logger.debug("User.getUser()");
        ConnectionPool connectionPool = null;
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        List<User> users = new ArrayList<>();
        try {
//            TODO change connection pool
            connectionPool = new ConnectionPool();
            connection = connectionPool.getConnection();
            ps = connection.prepareStatement(GET_USERS);
            resultSet = ps.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user = setUsersParams(user, resultSet);
                users.add(user);
            }
        } catch (SQLException e) {
            throw new DAOException("Operation failed in database. Cannot retrieve users", e);
        } finally {
            if (connectionPool != null) {
                connectionPool.closeConnection(connection, ps, resultSet);
            }
        }
        return users;
    }

    @Override
    public void editUser(User user) throws DAOException {
        logger.debug("edit user");
        ConnectionPool connectionPool = null;
        Connection connection = null;
        PreparedStatement ps = null;
        try {
//            TODO change connection pool in dao layer
            connectionPool = new ConnectionPool();
            connection = connectionPool.getConnection();
            ps = connection.prepareStatement(EDIT_USER);
//TODO CHECK this place.
            ps.setString(1, user.getPassword());
            ps.setString(2, user.getUserName());
            ps.setString(3, user.getUserSurname());
            ps.setDate(4, user.getBirthday());
            ps.setString(5, user.getEmail());
            ps.setString(6, user.getPhone());
            ps.setInt(7, user.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Cannot update user info in db", e);
        } finally {
            if (connectionPool != null) {
                connectionPool.closeConnection(connection, ps);
            }
        }
    }

    @Override
    public User getUser(int userId) throws DAOException {
        logger.debug("User.getUser()");
        ConnectionPool connectionPool = null;
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        List<User> users = new ArrayList<>();
        try {
//            TODO change connection pool
            connectionPool = new ConnectionPool();
            connection = connectionPool.getConnection();
            ps = connection.prepareStatement(GET_USER);
            ps.setInt(1, userId);
            resultSet = ps.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user = setUsersParams(user, resultSet);
                users.add(user);
            }
        } catch (SQLException e) {
            throw new DAOException("Operation failed in database. Cannot retrieve user", e);
        } finally {
            if (connectionPool != null) {
                connectionPool.closeConnection(connection, ps, resultSet);
            }
        }
        if (users == null || users.size() == 0) {
            throw new DAOException("Nothing receive from db");
        }
        if (users.size() > 1) {
            throw new DAOException("Receive more than one record");
        }
        return users.get(0);
    }


    private User setUsersParams(User user, ResultSet rs) throws SQLException {
        logger.debug("user.setUsersParams()");
        user.setId(rs.getInt(1));
        user.setLogin(rs.getString(2));
        user.setPassword(rs.getString(3));
        user.setEnabled(rs.getBoolean(4));
        user.setUserName(rs.getString(5));
        user.setUserSurname(rs.getString(6));
        user.setBirthday(rs.getDate(7));
        user.setAddress(new Address(rs.getInt(8)));
        user.setEmail(rs.getString(9));
        user.setPhone(rs.getString(10));
        user.setRole(Role.getByName(rs.getString(11)));
        logger.debug("user.setUsersParams()-> success");
        return user;
    }


    private PreparedStatement getUserData(User user, PreparedStatement ps) throws SQLException {
        ps.setString(1, user.getLogin());
        ps.setString(2, user.getPassword());
        ps.setString(3, user.getUserName());
        ps.setString(4, user.getUserSurname());
        ps.setDate(5, user.getBirthday());
        ps.setInt(6, user.getAddress().getId());
        ps.setString(7, user.getEmail());
        ps.setString(8, user.getPhone());
        return ps;
    }


    @Override
    public void changeUserBlockStatus(int userId, boolean blockStatus) throws DAOException {
        logger.debug("unblock user");
        ConnectionPool connectionPool = null;
        Connection connection = null;
        PreparedStatement ps = null;
        try {
//            TODO change connection pool in dao layer
            connectionPool = new ConnectionPool();
            connection = connectionPool.getConnection();
            ps = connection.prepareStatement(USER_BLOCK_STATUS);

            ps.setBoolean(1, blockStatus);
            ps.setInt(2, userId);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Cannot unblock user and set false to field enabled", e);
        } finally {
            if (connectionPool != null) {
                connectionPool.closeConnection(connection, ps);
            }
        }
    }

    @Override
    public void changeRole(int userId, Role role) throws DAOException {
        logger.debug("change role");
        ConnectionPool connectionPool = null;
        Connection connection = null;
        PreparedStatement ps = null;
        try {
//            TODO change connection pool in dao layer
            connectionPool = new ConnectionPool();
            connection = connectionPool.getConnection();
            ps = connection.prepareStatement(SET_ROLE);
            logger.debug("Role in method changeRole is " + role.getRole());
            ps.setString(1, role.getRole());
            ps.setInt(2, userId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Cannot change role", e);
        } finally {
            if (connectionPool != null) {
                connectionPool.closeConnection(connection, ps);
            }
        }
    }


}
