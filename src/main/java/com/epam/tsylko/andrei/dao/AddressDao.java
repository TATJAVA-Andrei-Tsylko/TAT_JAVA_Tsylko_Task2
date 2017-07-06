package com.epam.tsylko.andrei.dao;


import com.epam.tsylko.andrei.entities.Address;
import com.epam.tsylko.andrei.dao.exceptions.DAOException;

public interface AddressDao {

    void addAddressToUser(Address address) throws DAOException;

    void updateUsersAddress(Address address)throws DAOException;

    Address getAddress(int addressId)throws DAOException;

}
