package com.epam.tsylko.andrei.service.impl;

import com.epam.tsylko.andrei.dao.BookDao;
import com.epam.tsylko.andrei.dao.exceptions.DAOException;
import com.epam.tsylko.andrei.dao.factory.DAOFactory;
import com.epam.tsylko.andrei.entities.Book;
import com.epam.tsylko.andrei.service.LibraryService;
import com.epam.tsylko.andrei.service.exception.ServiceException;
import com.epam.tsylko.andrei.service.util.Util;
import com.epam.tsylko.andrei.service.util.exception.UtilException;
import org.apache.log4j.Logger;

import java.util.List;


public class LibraryServiceImpl implements LibraryService {
    private static Logger logger = Logger.getLogger(ResidenceServiceImpl.class);
    private DAOFactory daoFactory = DAOFactory.getInstance();

    @Override
    public void addNewBook(Book book) throws ServiceException {
        logger.debug("LibraryServiceImpl.addNewBook()");
        BookDao bookDao = daoFactory.getMysqlBookImpl();
        try {
            if (checkInputtedBookData(book)) {
                bookDao.addBook(book);
            }
        } catch (UtilException e) {
            throw new ServiceException(e);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void addEditedBook(Book book) throws ServiceException {
        logger.debug("LibraryServiceImpl.addEditedBook()");
        BookDao bookDao = daoFactory.getMysqlBookImpl();
        try {
            if (checkInputtedBookData(book)) {
                bookDao.editBook(book);
            }
        } catch (UtilException e) {
            throw new ServiceException(e);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Book> retrieveAllBooksFromLibrary() throws ServiceException {
        logger.debug("LibraryServiceImpl.retrieveAllBooksFromLibrary()");
        BookDao bookDao = daoFactory.getMysqlBookImpl();
        List<Book> books = null;
        try {
            books = bookDao.getBooks();
            return books;
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Book getBookFromTheLibrary(int bookId) throws ServiceException {
        logger.debug("LibraryServiceImpl.getBookFromTheLibrary()");
        BookDao bookDao = daoFactory.getMysqlBookImpl();
        Book book = null;
        try {
            book = bookDao.getBook(bookId);
            return book;
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
//TODO service layer
    @Override
    public List<Book> sortFreeBooksByDate() throws ServiceException {
        return null;
    }

    @Override
    public List<Book> sortAllBooksByDate() throws ServiceException {
        return null;
    }

    @Override
    public void makeBookUnAvailable(int bookId, boolean booksAvailabilityStatus) throws ServiceException {
        logger.debug("LibraryServiceImpl.makeBookUnAvailable()");
        BookDao bookDao = daoFactory.getMysqlBookImpl();

        try {
            bookDao.makeBookUnAvailable(bookId, booksAvailabilityStatus);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    private boolean checkInputtedBookData(Book book) throws UtilException {
        Util.isNull(book.getBooksName(), book.getAuthorSurname());
        Util.isEmptyString(book.getBooksName(), book.getAuthorSurname());
        Util.isNumberPositive(book.getISBN(), book.getPaperBack(), book.getPrintRun());
        Util.checkISBN(book.getISBN());
        return true;
    }
}