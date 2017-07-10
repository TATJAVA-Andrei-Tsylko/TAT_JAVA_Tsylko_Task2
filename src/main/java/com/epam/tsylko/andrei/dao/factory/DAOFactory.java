package com.epam.tsylko.andrei.dao.factory;


import com.epam.tsylko.andrei.dao.*;
import com.epam.tsylko.andrei.dao.impl.*;

public final class DAOFactory {
    private static final DAOFactory instance = new DAOFactory();
    private final BookDao mysqlBookImpl = new BookDaoImpl();
    private final TransactionDao mysqlTransactionImpl = new TransactionDaoImpl();
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

    public TransactionDao getMysqlTransactionImpl() {
        return mysqlTransactionImpl;
    }

    public OrdersRepositoryDao getMysqlOrdersRepositoryDao() {
        return mysqlOrdersRepositoryDao;
    }
}
