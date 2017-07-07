package com.epam.tsylko.andrei.dao.impl;


import com.epam.tsylko.andrei.dao.BookDao;
import com.epam.tsylko.andrei.dao.pool.ConnectionPool;
import com.epam.tsylko.andrei.dao.pool.ConnectionPoolException;
import com.epam.tsylko.andrei.entities.Book;
import com.epam.tsylko.andrei.dao.exceptions.DAOException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDaoImpl implements BookDao {
    private static Logger logger = Logger.getLogger(BookDao.class);

    private static final String ADD_BOOK = "INSERT INTO `library`.`book` (`booksName`, `authorName`,`authorSurname`, `publisher`, `cityPublisher`, `yearPublished`, `ISBN`,`printRun`, `paperBack`) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?,?);";
    private static final String UPD_BOOK = "UPDATE `library`.`book` SET `booksName`=?,`authorName`=?, `authorSurname`=?, `publisher`=?, `cityPublisher`=?, `yearPublished`=?, " +
            "`ISBN`=?, `printRun`=?, `paperBack`=?  WHERE `id`=?;";
    private static final String BOOK_IS_NOT_AVAILABLE = "UPDATE `library`.`book` SET `isValidBook`=? WHERE `id`=?;";
    private static final String GET_BOOK = "SELECT * FROM library.book where id = ?;";
    private static final String GET_BOOKS = "SELECT * FROM library.book;";
    private static final String FREE_BOOK="UPDATE `library`.`book` SET `isFree`=? WHERE `id`=?;";
    private static final String FREE_SORTED_BOOKS = "SELECT * FROM library.book where library.book.isFree = 1 ORDER by library.book.yearPublished = ?;";

    @Override
    public void addBook(Book book) throws DAOException {
        logger.debug("add book");
        ConnectionPool connectionPool = null;
        Connection connection = null;
        PreparedStatement ps = null;
        try {
//            TODO change connection pool in dao layer
            connectionPool = new ConnectionPool();
            connection = connectionPool.getConnection();
            ps = connection.prepareStatement(ADD_BOOK);
            ps = getBookData(book, ps);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Cannot add book to DB", e);
        } finally {
            if (connectionPool != null) {
                connectionPool.closeConnection(connection, ps);
            }
        }

    }

    @Override
    public Book getBook(int bookId) throws DAOException {
        logger.debug("BookDaoImpl.getBook()");
        ConnectionPool connectionPool = null;
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        List<Book> books = new ArrayList<>();
        try {
//            TODO change connection pool
            connectionPool = new ConnectionPool();
            connection = connectionPool.getConnection();
            ps = connection.prepareStatement(GET_BOOK);
            ps.setInt(1, bookId);
            resultSet = ps.executeQuery();
            while (resultSet.next()) {
                Book book = new Book();
                book = setBooksParams(book, resultSet);
                books.add(book);
            }
        } catch (SQLException e) {
            throw new DAOException("Operation failed in database. Cannot retrieve books", e);
        } finally {
            if (connectionPool != null) {
                connectionPool.closeConnection(connection, ps, resultSet);
            }
        }
        if (books == null || books.size() == 0) {
            throw new DAOException("Nothing receive from db");
        }
        if (books.size() > 1) {
            throw new DAOException("Receive more than one record");
        }
        return books.get(0);
    }

    @Override
    public List<Book> getBooks() throws DAOException {
        logger.debug("BookDaoImpl.getAllBooks()");
        ConnectionPool connectionPool = null;
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        List<Book> books = new ArrayList<>();
        try {
            //            TODO change connection pool
            connectionPool = new ConnectionPool();
            connection = connectionPool.getConnection();
            ps = connection.prepareStatement(GET_BOOKS);
            resultSet = ps.executeQuery();
            while (resultSet.next()) {
                Book book = new Book();
                book = setBooksParams(book, resultSet);
                books.add(book);
            }
        } catch (ConnectionPoolException e) {
            throw new DAOException("can't get connection in database", e);
        } catch (SQLException e) {
            throw new DAOException("Operation failed in database. Cannot retrieve books", e);
        } finally {
            if (connectionPool != null) {
                connectionPool.closeConnection(connection, ps, resultSet);
            }
        }
        logger.debug("BookDaoImpl.getAllBooks() - success");

        return books;
    }

    private Book setBooksParams(Book book, ResultSet rs) throws SQLException {
        logger.debug("books.setBooksParams()");
        book.setId(rs.getInt(1));
        book.setBooksName(rs.getString(2));
        book.setAuthorName(rs.getString(3));
        book.setAuthorSurname(rs.getString(4));
        book.setPublisher(rs.getString(5));
        book.setCityPublisher(rs.getString(6));
        book.setYearPublished(rs.getDate(7));
        book.setISBN(rs.getInt(8));
        book.setPrintRun(rs.getInt(9));
        book.setPaperBack(rs.getInt(10));
        book.setValidBook(rs.getBoolean(11));
        book.setFree(rs.getBoolean(12));
        logger.debug("books.setBooksParams()-> success");
        return book;
    }

    @Override
    public void editBook(Book book) throws DAOException {
        logger.debug("edit book");
        ConnectionPool connectionPool = null;
        Connection connection = null;
        PreparedStatement ps = null;
        try {
//            TODO change connection pool in dao layer
            connectionPool = new ConnectionPool();
            connection = connectionPool.getConnection();
            ps = connection.prepareStatement(UPD_BOOK);
            ps = getBookData(book, ps);
            ps.setInt(10, book.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Cannot edit book in db", e);
        } finally {
            if (connectionPool != null) {
                connectionPool.closeConnection(connection, ps);
            }
        }

    }

    private PreparedStatement getBookData(Book book, PreparedStatement ps) throws SQLException {
        ps.setString(1, book.getBooksName());
        ps.setString(2, book.getAuthorName());
        ps.setString(3, book.getAuthorSurname());
        ps.setString(4, book.getPublisher());
        ps.setString(5, book.getCityPublisher());
        ps.setDate(6, book.getYearPublished());
        ps.setInt(7, book.getISBN());
        ps.setInt(8, book.getPrintRun());
        ps.setInt(9, book.getPaperBack());
        return ps;
    }



    @Override
    public void makeBookUnAvailable(int bookId, boolean booksAvailabilityStatus) throws DAOException {
        logger.debug("BookDaoImpl.makeBookUnAvailable");
        ConnectionPool connectionPool = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
//            TODO change connection pool in dao layer
            connectionPool = new ConnectionPool();
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement(BOOK_IS_NOT_AVAILABLE);
            preparedStatement.setBoolean(1, booksAvailabilityStatus);
            preparedStatement.setInt(2, bookId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Cannot change isValidBook state", e);
        } finally {
            if (connectionPool != null) {
                connectionPool.closeConnection(connection, preparedStatement);
            }
        }
    }

    @Override
    public void makeBookNotFree(int bookId, boolean booksFreeStatus,Connection connection) throws DAOException {
        logger.debug("BookDaoImpl.makeBookNotFree");


        PreparedStatement preparedStatement = null;
        try {
//            TODO change connection pool in dao layer

            preparedStatement = connection.prepareStatement(FREE_BOOK);
            preparedStatement.setBoolean(1, booksFreeStatus);
            preparedStatement.setInt(2, bookId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Cannot change isFree state", e);
        }
    }

    @Override
    public List<Book> sortFreeBooksByDate(String sortOrder) throws DAOException {
        logger.debug("BookDaoImpl.sortFreeBooksByDate()");
        ConnectionPool connectionPool = null;
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        List<Book> books = new ArrayList<>();
        try {
            //            TODO change connection pool
            connectionPool = new ConnectionPool();
            connection = connectionPool.getConnection();
            ps = connection.prepareStatement(FREE_SORTED_BOOKS);
            ps.setString(1, sortOrder);
            resultSet = ps.executeQuery();
            while (resultSet.next()) {
                Book book = new Book();
                book = setBooksParams(book, resultSet);
                books.add(book);
            }
        } catch (ConnectionPoolException e) {
            throw new DAOException("can't get connection in database", e);
        } catch (SQLException e) {
            throw new DAOException("Operation failed in database. Cannot retrieve books", e);
        } finally {
            if (connectionPool != null) {
                connectionPool.closeConnection(connection, ps, resultSet);
            }
        }
        logger.debug("BookDaoImpl.getAllBooks() - success");

        return books;
    }

    @Override
    public List<Book> sortAllBooksByDate() {
        return null;
    }
}
