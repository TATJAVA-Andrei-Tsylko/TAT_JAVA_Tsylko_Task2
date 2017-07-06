package com.epam.tsylko.andrei.controller.command.impl;


import com.epam.tsylko.andrei.controller.command.Command;
import com.epam.tsylko.andrei.controller.util.ControllerUtil;
import com.epam.tsylko.andrei.controller.util.exception.ControllerUtilException;
import com.epam.tsylko.andrei.entities.Role;
import com.epam.tsylko.andrei.entities.User;
import com.epam.tsylko.andrei.service.ClientService;
import com.epam.tsylko.andrei.service.exception.ServiceException;
import com.epam.tsylko.andrei.service.factory.ServiceFactory;
import org.apache.log4j.Logger;

import java.util.List;


public class AllUsers implements Command{
    private static Logger logger = Logger.getLogger(AllUsers.class);
    private String response;

    @Override
    public String execute(String request) {
        logger.debug("AllUsers.execute");
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        ClientService service = serviceFactory.getClientService();
        List<User> users;
        try {

            users = service.getAllUsers();
            logger.debug(users.toString());
            response = "There are " + users.size() + " users.";
        } catch (ServiceException e) {
            logger.error("Error in service layer", e);
            response = "There are no users now.";
        }
        return response;
    }

    @Override
    public boolean getAccess(String request) {
        logger.debug("AllUsers.getAccess");
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        ClientService clientService = serviceFactory.getClientService();
        try {
            return (clientService.checkUserRole(ControllerUtil.findUserIdInRequest(request), Role.SUPER_ADMIN, Role.ADMIN));
        } catch (ServiceException e) {
            logger.error("Error in service layer", e);
        } catch (ControllerUtilException e) {
            logger.error("Error in ControllerUtil ", e);
        }
        return false;
    }
}
