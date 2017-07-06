package com.epam.tsylko.andrei.dao.factory;


import com.epam.tsylko.andrei.dao.AddressDao;
import com.epam.tsylko.andrei.dao.BookDao;
import com.epam.tsylko.andrei.dao.OrdersRepositoryDao;
import com.epam.tsylko.andrei.dao.UserDao;
import com.epam.tsylko.andrei.dao.impl.AddressDaoImpl;
import com.epam.tsylko.andrei.dao.impl.BookDaoImpl;
import com.epam.tsylko.andrei.dao.impl.OrdersRepositoryDaoImpl;
import com.epam.tsylko.andrei.dao.impl.UserDaoImpl;

public final class DAOFactory {
    private static final DAOFactory instance = new DAOFactory();
    private final BookDao mysqlBookImpl = new BookDaoImpl();
    private final UserDao mysqlUserImpl = new UserDaoImpl();
    private final AddressDao mysqlAddressDao = new AddressDaoImpl();
    private final OrdersRepositoryDao mysqlOrdersRepositoryDao = new OrdersRepositoryDaoImpl();

    private DAOFactory(){}

    public static DAOFactory getInstance(){
        return instance;
    }

    public BookDao getMysqlBookImpl() {
        return mysqlBookImpl;
    }

    public UserDao getMysqlUserImpl() {
        return mysqlUserImpl;
    }

    public AddressDao getMysqlAddressDao() {
        return mysqlAddressDao;
    }

    public OrdersRepositoryDao getMysqlOrdersRepositoryDao() {
        return mysqlOrdersRepositoryDao;
    }
}
