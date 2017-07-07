package com.epam.tsylko.andrei.service.impl;


import com.epam.tsylko.andrei.dao.UserDao;
import com.epam.tsylko.andrei.dao.exceptions.DAOException;
import com.epam.tsylko.andrei.dao.factory.DAOFactory;
import com.epam.tsylko.andrei.entities.Role;
import com.epam.tsylko.andrei.entities.User;
import com.epam.tsylko.andrei.service.ClientService;
import com.epam.tsylko.andrei.service.exception.ServiceException;
import com.epam.tsylko.andrei.service.util.Util;
import com.epam.tsylko.andrei.service.util.exception.UtilException;
import org.apache.log4j.Logger;

import java.util.List;

//TODO add checkers
public class ClientServiceImpl implements ClientService {
    private static Logger logger = Logger.getLogger(ClientServiceImpl.class);
    private DAOFactory daoFactory = DAOFactory.getInstance();

    @Override
    public void registration(User user) throws ServiceException {
        logger.debug("ClientServiceImpl.registration");
        UserDao userDao = daoFactory.getMysqlUserImpl();

        try {
            checkRegistrationParams(user);
            Util.checkEmail(user);
            user.setPassword(Util.getHashForPassword(user.getPassword()));
            logger.debug("registrtion user " + user.toString());
            checkLogin(user);

            userDao.registration(user);

        } catch (DAOException e) {
            throw new ServiceException(e);
        } catch (UtilException e) {
            throw new ServiceException("Check inputed params in registration form", e);
        }
    }

    @Override
    public void signIn(User user) throws ServiceException {
        logger.debug("ClientServiceImpl.signIn");
        UserDao userDao = daoFactory.getMysqlUserImpl();

        try {
            User userFromDB=userDao.getUser(user);
            logger.debug("User from DB " + userFromDB.toString());
            Util.checkHash(userFromDB.getPassword(),user.getPassword());
        } catch (DAOException e) {
            throw new ServiceException(e);
        } catch (UtilException e) {
            throw new ServiceException("Incorrect password " + user.getPassword());
        }
    }

    @Override
    public void editUser(User user) throws ServiceException {
        logger.debug("ClientServiceImpl.editUser");
        UserDao userDao = daoFactory.getMysqlUserImpl();

        try {

            Util.checkEmail(user);
            Util.isEmptyString(user.getPassword());
            user.setPassword(Util.getHashForPassword(user.getPassword()));

            userDao.editUser(user);
        } catch (DAOException e) {
            throw new ServiceException(e);
        } catch (UtilException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<User> getAllUsers() throws ServiceException {
        logger.debug("ClientServiceImpl.getAllUsers");
        UserDao userDao = daoFactory.getMysqlUserImpl();
        List<User> users = null;
        try {
            users = userDao.getAllUsers();
            return users;
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public User getUser(int userId) throws ServiceException {
        logger.debug("ClientServiceImpl.getUser");
        UserDao userDao = daoFactory.getMysqlUserImpl();
        User user = null;
        try {
            user = userDao.getUser(userId);
            return user;
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public User getUser(User user) throws ServiceException {
        logger.debug("ClientServiceImpl.getUser");
        UserDao userDao = daoFactory.getMysqlUserImpl();
        User userFromRequest = null;
        try {
            userFromRequest = userDao.getUser(user);
            return userFromRequest;
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean checkUserRole(int userId, Role superAdmin) throws ServiceException {
        logger.debug("ClientServiceImpl.checkUserRole");
        UserDao userDao = daoFactory.getMysqlUserImpl();
        User user = null;
        try {
            user = userDao.getUser(userId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        logger.debug("checkUserRole:" + user.toString());
        return (user.getRole().equals(superAdmin)) && user.isEnabled() != false;
    }

    @Override
    public boolean checkUserRole(int userId, Role superAdmin, Role admin) throws ServiceException {
        logger.debug("ClientServiceImpl.checkUserRole");
        UserDao userDao = daoFactory.getMysqlUserImpl();
        User user = null;
        try {
            user = userDao.getUser(userId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        logger.debug("checkUserRole:" + user.toString());
        return (user.getRole().equals(superAdmin) || user.getRole().equals(admin)) && user.isEnabled() != false;
    }

    @Override
    public boolean checkUserRole(int userId, Role superAdmin, Role admin, Role userRole) throws ServiceException {
        logger.debug("ClientServiceImpl.checkUserRole");
        UserDao userDao = daoFactory.getMysqlUserImpl();
        User user = null;
        try {
            user = userDao.getUser(userId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        logger.debug("checkUserRole:" + user.toString());
        return (user.getRole().equals(superAdmin) || user.getRole().equals(admin) || user.getRole().equals(userRole)) && user.isEnabled() != false;
    }

    @Override
    public void changeUserBlockStatus(int userId, boolean blockStatus) throws ServiceException {
        logger.debug("ClientServiceImpl.changeUserBlockStatus");
        UserDao userDao = daoFactory.getMysqlUserImpl();

        try {
            userDao.changeUserBlockStatus(userId, blockStatus);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void changeRole(int userId, Role role) throws ServiceException {
        logger.debug("ClientServiceImpl.changeRole");
        UserDao userDao = daoFactory.getMysqlUserImpl();

        try {
            userDao.changeRole(userId, role);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    private void checkRegistrationParams(User user) throws UtilException {
        logger.debug("ClientServiceImpl.checkRegistrationParams");
        Util.isNull(user.getLogin(), user.getPassword());
        Util.isEmptyString(user.getLogin(), user.getPassword());

    }

    private void checkLogin(User user) throws ServiceException {
        logger.debug("ClientServiceImpl.checkLogin");
        user.toString();
        User checkedUser = null;
        try {
            checkedUser = getUser(user);
        } catch (ServiceException e) {
            logger.info("Login " + user.getLogin() + " exist.");
        }
        if (checkedUser != null) {
            throw new ServiceException("Login exist");
        }
    }
}
