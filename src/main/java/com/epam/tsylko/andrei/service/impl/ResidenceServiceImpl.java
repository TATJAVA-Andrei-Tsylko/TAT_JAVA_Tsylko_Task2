package com.epam.tsylko.andrei.service.impl;

import com.epam.tsylko.andrei.dao.AddressDao;
import com.epam.tsylko.andrei.dao.exceptions.DAOException;
import com.epam.tsylko.andrei.dao.factory.DAOFactory;
import com.epam.tsylko.andrei.entities.Address;
import com.epam.tsylko.andrei.service.ResidenceService;
import com.epam.tsylko.andrei.service.exception.ServiceException;
import com.epam.tsylko.andrei.service.util.Util;
import com.epam.tsylko.andrei.service.util.exception.UtilException;
import org.apache.log4j.Logger;


public class ResidenceServiceImpl implements ResidenceService {

    private static Logger logger = Logger.getLogger(ResidenceServiceImpl.class);
    private DAOFactory daoFactory = DAOFactory.getInstance();

    @Override
    public void enterHomeAddress(Address address) throws ServiceException {
        logger.debug("ResidenceServiceImpl.enterHomeAddress");
        AddressDao dao = daoFactory.getMysqlAddressDao();
        try {
            if (checkInputtedAddressData(address)) {
                dao.addAddressToUser(address);
            }
        } catch (UtilException e) {
            throw new ServiceException(e);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

    }

    @Override
    public void updateHomeAddress(Address address) throws ServiceException {
        logger.debug("ResidenceServiceImpl.updateHomeAddress");
        AddressDao dao = daoFactory.getMysqlAddressDao();
        try {
            if (checkInputtedAddressData(address)) {
                dao.updateUsersAddress(address);
            }
        } catch (UtilException e) {
            throw new ServiceException(e);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Address getCurrentAddress(int addressId) throws ServiceException {
        logger.debug("ResidenceServiceImpl.getCurrentAddress");
        AddressDao dao = daoFactory.getMysqlAddressDao();
        try {
            Address address = dao.getAddress(addressId);
            return address;
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

    }

    private boolean checkInputtedAddressData(Address address) throws UtilException {
        Util.isNull(address.getCountry(), address.getCity(), address.getStreet());
        Util.isEmptyString(address.getCountry(), address.getCity(), address.getStreet());
        Util.isNumberPositive(address.getHouse(),address.getRoom());
        return true;
    }
}
