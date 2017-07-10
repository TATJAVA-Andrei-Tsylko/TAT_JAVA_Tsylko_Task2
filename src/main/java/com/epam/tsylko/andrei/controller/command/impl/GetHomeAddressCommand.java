package com.epam.tsylko.andrei.controller.command.impl;


import com.epam.tsylko.andrei.controller.command.Command;
import com.epam.tsylko.andrei.controller.util.ControllerUtil;
import com.epam.tsylko.andrei.controller.util.ControllerUtilException;
import com.epam.tsylko.andrei.entity.Address;
import com.epam.tsylko.andrei.entity.Role;
import com.epam.tsylko.andrei.service.ClientService;
import com.epam.tsylko.andrei.service.ResidenceService;
import com.epam.tsylko.andrei.service.exception.ServiceException;
import com.epam.tsylko.andrei.service.factory.ServiceFactory;
import org.apache.log4j.Logger;

import java.util.Map;

public class GetHomeAddressCommand implements Command {
    private final static Logger logger = Logger.getLogger(GetHomeAddressCommand.class);

    @Override
    public String execute(String request) {
        String response;

        if (logger.isDebugEnabled()) {
            logger.debug("GetHomeAddressCommand.execute()");
        }

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        ResidenceService service = serviceFactory.getResidenceService();
        Map<String, String> address;

        try{
            address = ControllerUtil.castRequestParamToMap(request);
            Address newAddress = ControllerUtil.initAddressObj(address);

            service.enterHomeAddress(newAddress);
            response = "Address was added";
        } catch (ControllerUtilException e) {
            logger.error("request params " + GetHomeAddressCommand.class.getName() + " was incorrect: " + request,e);
            response = "Incorrect request";

        } catch (ServiceException e) {
            logger.error("Error in service layer", e);
            response = "Error during add address procedure";
        }
        return response;
    }

    @Override
    public boolean getAccess(String request) {
        boolean access = false;

        if (logger.isDebugEnabled()) {
            logger.debug("GetHomeAddressCommand.getAccess()");
        }

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        ClientService clientService = serviceFactory.getClientService();

        try {
            int userId = ControllerUtil.findUserIdInRequest(request);
            access = clientService.checkUserRole(userId, Role.SUPER_ADMIN, Role.ADMIN,Role.USER);
        } catch (ServiceException e) {
            logger.error("Error in service layer", e);
        } catch (ControllerUtilException e) {
            logger.error("Error in ControllerUtil ", e);
        }
        return access;
    }
}
