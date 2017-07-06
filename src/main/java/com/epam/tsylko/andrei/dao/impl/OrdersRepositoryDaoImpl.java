package com.epam.tsylko.andrei.dao.impl;


import com.epam.tsylko.andrei.dao.BookDao;
import com.epam.tsylko.andrei.dao.OrdersRepositoryDao;
import com.epam.tsylko.andrei.dao.factory.DAOFactory;
import com.epam.tsylko.andrei.dao.pool.ConnectionPool;
import com.epam.tsylko.andrei.entities.Book;
import com.epam.tsylko.andrei.entities.OrdersRepository;
import com.epam.tsylko.andrei.entities.User;
import com.epam.tsylko.andrei.dao.exceptions.DAOException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrdersRepositoryDaoImpl implements OrdersRepositoryDao {

    private static Logger logger = Logger.getLogger(OrdersRepositoryDao.class);
    //    TODO add automat data
    private static final String RESERVED_BOOK = "INSERT INTO `library`.`oredersrepository` (`bookId`, `userId`) VALUES (?,?);";
    private static final String ALL_USERS_BOOKS = "SELECT * FROM library.oredersrepository where userId = ?;";
    private static final String REPEALED_BOOK = "UPDATE `library`.`oredersrepository` SET `isBooked`='0' WHERE `id`=?;";
    private static final String USER_TOOK_BOOK = "UPDATE `library`.`oredersrepository` SET `isTakenAway`='1' WHERE `id`=?;";
    private static final String USER_RETURNED_BOOK = "UPDATE `library`.`oredersrepository` SET `isReturned`='1' WHERE `id`=?;\n";

    @Override
    public void reserveBookByUser(Book book, User user) throws DAOException {
        logger.debug("reserve book by user");
        ConnectionPool connectionPool = null;
        Connection connection = null;
        PreparedStatement ps = null;
        try {
//            TODO change connection pool in dao layer
            connectionPool = new ConnectionPool();
            connection = connectionPool.getConnection();
            ps = connection.prepareStatement(RESERVED_BOOK);
            ps.setInt(1, book.getId());
            ps.setInt(2, user.getId());

            connection.setAutoCommit(false);
            ps.executeUpdate();
            BookDao repository = DAOFactory.getInstance().getMysqlBookImpl();
            repository.makeBookNotFree(book.getId(), false, connection);
            connection.commit();


        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                logger.error("Exception during rollback operation", e);
            }
            throw new DAOException("Cannot add reserved book to DB", e);
        } finally {
            if (connectionPool != null) {
                connectionPool.closeConnection(connection, ps);
            }
        }
    }

    @Override
    public List<OrdersRepository> getAllBooksBookedOrTakenByUser(User user) throws DAOException {
        logger.debug("getAllBooksBookedOrTakenByUser");
        ConnectionPool connectionPool = null;
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        List<OrdersRepository> ordersRepositories = new ArrayList<>();
        try {
//            TODO change connection pool
            connectionPool = new ConnectionPool();
            connection = connectionPool.getConnection();
            ps = connection.prepareStatement(ALL_USERS_BOOKS);
            ps.setInt(1, user.getId());
            resultSet = ps.executeQuery();
            while (resultSet.next()) {
                OrdersRepository ordersRepository = new OrdersRepository();
                ordersRepository.setId(resultSet.getInt(1));
                ordersRepository.setBook(new Book(resultSet.getInt(2)));
                ordersRepository.setUser(new User(resultSet.getInt(3)));
                ordersRepository.setDateOfIssuance(resultSet.getDate(4));
                ordersRepository.setDateOfTheReturn(resultSet.getDate(5));
                ordersRepository.setBooked(resultSet.getBoolean(6));
                ordersRepository.setTakenAway(resultSet.getBoolean(7));
                ordersRepository.setReturned(resultSet.getBoolean(8));
                ordersRepositories.add(ordersRepository);
            }
        } catch (SQLException e) {
            throw new DAOException("Operation failed in database. Cannot retrieve information from OrdersRepository", e);
        } finally {
            if (connectionPool != null) {
                connectionPool.closeConnection(connection, ps, resultSet);
            }
        }
        if (ordersRepositories == null || ordersRepositories.size() == 0) {
            throw new DAOException("Nothing receive from db");
        }
        return ordersRepositories;

    }

    @Override
    public void cancelBookReservation(OrdersRepository ordersRepository) throws DAOException {
        logger.debug("cancel book reservation");
        ConnectionPool connectionPool = null;
        Connection connection = null;
        PreparedStatement ps = null;
        try {
//
//          TODO change connection pool in dao layer
            connectionPool = new ConnectionPool();
            connection = connectionPool.getConnection();
            connection.setAutoCommit(false);
            ps = connection.prepareStatement(REPEALED_BOOK);
            ps.setInt(1, ordersRepository.getId());
            ps.executeUpdate();
            BookDao repository = DAOFactory.getInstance().getMysqlBookImpl();
            repository.makeBookNotFree(ordersRepository.getBook().getId(), true, connection);
            connection.commit();

        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                logger.error("Exception during rollback operation", e);
            }
            throw new DAOException("Book's reservation wasn't cancel", e);
        } finally {
            if (connectionPool != null) {
                connectionPool.closeConnection(connection, ps);
            }
        }
    }

    @Override
    public void setBookIsTakenAwayByUser(int ordersRepositoryId) throws DAOException {
        logger.debug("book was taken by user");
        ConnectionPool connectionPool = null;
        Connection connection = null;
        PreparedStatement ps = null;
        try {
//
//          TODO change connection pool in dao layer
            connectionPool = new ConnectionPool();
            connection = connectionPool.getConnection();

            ps = connection.prepareStatement(USER_TOOK_BOOK);
            ps.setInt(1, ordersRepositoryId);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Cannot change isTakenAway status", e);
        } finally {
            if (connectionPool != null) {
                connectionPool.closeConnection(connection, ps);
            }
        }
    }

    @Override
    public void setBookIsReturnedByUser(OrdersRepository ordersRepository) throws DAOException {
        logger.debug("book was returned by user");
        ConnectionPool connectionPool = null;
        Connection connection = null;
        PreparedStatement ps = null;
        try {
//
//          TODO change connection pool in dao layer
            connectionPool = new ConnectionPool();
            connection = connectionPool.getConnection();

            connection.setAutoCommit(false);
            ps = connection.prepareStatement(USER_RETURNED_BOOK);
            ps.setInt(1, ordersRepository.getId());
            ps.executeUpdate();

            BookDao repository = DAOFactory.getInstance().getMysqlBookImpl();
            repository.makeBookNotFree(ordersRepository.getBook().getId(),true,connection);
            connection.commit();

        } catch (SQLException e) {
            try{
                connection.rollback();
            } catch (SQLException e1) {
                logger.error("Exception during rollback operation",e);
            }
            throw new DAOException("Cannot change isReturned status", e);
        } finally {
            if (connectionPool != null) {
                connectionPool.closeConnection(connection, ps);
            }
        }
    }
}
