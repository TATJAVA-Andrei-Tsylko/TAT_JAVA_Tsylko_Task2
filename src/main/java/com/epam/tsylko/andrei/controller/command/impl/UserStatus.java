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

import java.util.Map;


public class UserStatus implements Command {
    private static Logger logger = Logger.getLogger(UserStatus.class);
    private String response;

    @Override
    public String execute(String request) {
        logger.debug("UserStatus.execute");
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        ClientService service = serviceFactory.getClientService();

        Map<String, String> user;
        try {
            user = ControllerUtil.castRequestParamToMap(request);
            User userStatus = ControllerUtil.initUserObjWithBlockParam(user);
            logger.debug(userStatus.toString());

            service.changeUserBlockStatus(userStatus.getId(), userStatus.isEnabled());
            response = "Status was changed";
        } catch (ControllerUtilException e) {
            logger.error("request params " + UserStatus.class.getName() + " was incorrect: " + request, e);
            response = "Incorrect request";
        } catch (ServiceException e) {
            logger.error("Error in service layer", e);
            response = "Error during change status procedure";
        }
            return response;

    }

    @Override
    public boolean getAccess(String request) {
        logger.debug("UserStatus.getAccess");
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
