package com.epam.tsylko.andrei.service;


import com.epam.tsylko.andrei.entities.Address;
import com.epam.tsylko.andrei.service.exception.ServiceException;

public interface ResidenceService {

    void enterHomeAddress(Address address) throws ServiceException;

    void updateHomeAddress(Address address) throws ServiceException;

    Address getCurrentAddress(int addressId) throws ServiceException;
}
