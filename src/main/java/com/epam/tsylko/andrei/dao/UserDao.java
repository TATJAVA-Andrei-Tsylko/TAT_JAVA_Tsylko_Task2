package com.epam.tsylko.andrei.dao;

import com.epam.tsylko.andrei.entities.Role;
import com.epam.tsylko.andrei.entities.User;
import com.epam.tsylko.andrei.dao.exceptions.DAOException;

import java.util.List;

public interface UserDao {

    void registration(User user)throws DAOException;

    void signIn(User user)throws DAOException;

    void editUser(User user)throws DAOException;

    User getUser(int userId)throws DAOException;

    List<User> getAllUsers()throws DAOException;

    void changeUserBlockStatus(int userId,boolean blockStatus)throws DAOException;

    void changeRole(int userId, Role role)throws DAOException;


}
