package com.epam.tsylko.andrei.service.impl;

import com.epam.tsylko.andrei.dao.OrdersRepositoryDao;
import com.epam.tsylko.andrei.dao.exceptions.DAOException;
import com.epam.tsylko.andrei.dao.factory.DAOFactory;
import com.epam.tsylko.andrei.entities.Book;
import com.epam.tsylko.andrei.entities.OrdersRepository;
import com.epam.tsylko.andrei.entities.User;
import com.epam.tsylko.andrei.service.OrdersService;
import com.epam.tsylko.andrei.service.exception.ServiceException;
import org.apache.log4j.Logger;

import java.util.List;

//TODO add checker
public class OrdersServiceImpl implements OrdersService {
    private static Logger logger = Logger.getLogger(OrdersServiceImpl.class);
    private DAOFactory daoFactory = DAOFactory.getInstance();

    @Override
    public void reserveBook(Book book, User user) throws ServiceException {
        logger.debug("OrdersServiceImpl.reserveBook");
        OrdersRepositoryDao ordersRepositoryDao = daoFactory.getMysqlOrdersRepositoryDao();

        try {
            ordersRepositoryDao.reserveBookByUser(book, user);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<OrdersRepository> getAllBooksBookedOrTakenByUser(User user) throws ServiceException {
        OrdersRepositoryDao ordersRepositoryDao = daoFactory.getMysqlOrdersRepositoryDao();
        List<OrdersRepository> ordersRepositoryList = null;
        try {
            ordersRepositoryList = ordersRepositoryDao.getAllBooksBookedOrTakenByUser(user);
            return ordersRepositoryList;
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

    }

    @Override
    public void cancelBookReservation(OrdersRepository ordersRepository) throws ServiceException {
        logger.debug("OrdersServiceImpl.cancelBookReservation");
        OrdersRepositoryDao ordersRepositoryDao = daoFactory.getMysqlOrdersRepositoryDao();

        try {
            ordersRepositoryDao.cancelBookReservation(ordersRepository);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void setBookIsTakenAwayByUser(int ordersRepositoryId) throws ServiceException {
        logger.debug("OrdersServiceImpl.setBookIsTakenAwayByUser");
        OrdersRepositoryDao ordersRepositoryDao = daoFactory.getMysqlOrdersRepositoryDao();

        try {
            ordersRepositoryDao.setBookIsTakenAwayByUser(ordersRepositoryId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void setBookIsReturnedByUser(OrdersRepository ordersRepository) throws ServiceException {
        logger.debug("OrdersServiceImpl.setBookIsReturnedByUser");
        OrdersRepositoryDao ordersRepositoryDao = daoFactory.getMysqlOrdersRepositoryDao();

        try {
            ordersRepositoryDao.setBookIsReturnedByUser(ordersRepository);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
