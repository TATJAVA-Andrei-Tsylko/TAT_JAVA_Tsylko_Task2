package com.epam.tsylko.andrei.dao;


import com.epam.tsylko.andrei.entities.Book;
import com.epam.tsylko.andrei.dao.exceptions.DAOException;

import java.sql.Connection;
import java.util.List;

public interface BookDao {

    void addBook(Book book) throws DAOException;

    Book getBook(int bookId) throws DAOException;

    List<Book> getBooks() throws DAOException;

    void editBook(Book book) throws DAOException;

    void makeBookUnAvailable(int bookId, boolean booksAvailabilityStatus) throws DAOException;

    void makeBookNotFree(int bookId, boolean booksFreeStatus,Connection connection) throws DAOException;

    List<Book> sortFreeBooksByDate() throws DAOException;

    List<Book> sortAllBooksByDate() throws DAOException;

}
