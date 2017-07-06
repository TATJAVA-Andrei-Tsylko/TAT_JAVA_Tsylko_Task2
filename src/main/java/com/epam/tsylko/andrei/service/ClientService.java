package com.epam.tsylko.andrei.service;


import com.epam.tsylko.andrei.entities.Role;
import com.epam.tsylko.andrei.entities.User;
import com.epam.tsylko.andrei.service.exception.ServiceException;

import java.util.List;

public interface ClientService {

    void registration(User user)throws ServiceException;
//TODO add signIn
    void signIn(User user)throws ServiceException;

    void editUser(User user)throws ServiceException;

    List<User> getAllUsers()throws ServiceException;

    User getUser(int userId)throws ServiceException;

    boolean checkUserRole(int userId, Role superAdmin)throws ServiceException;
    boolean checkUserRole(int userId, Role superAdmin,Role admin)throws ServiceException;
    boolean checkUserRole(int userId, Role superAdmin,Role admin,Role user)throws ServiceException;

    void changeUserBlockStatus(int userId,boolean blockStatus)throws ServiceException;

    void changeRole(int userId, Role role)throws ServiceException;
}
