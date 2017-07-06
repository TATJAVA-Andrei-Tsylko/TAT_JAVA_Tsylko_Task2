package com.epam.tsylko.andrei.controller.command.impl;


import com.epam.tsylko.andrei.controller.command.Command;
import com.epam.tsylko.andrei.controller.util.ControllerUtil;
import com.epam.tsylko.andrei.controller.util.exception.ControllerUtilException;
import com.epam.tsylko.andrei.entities.User;
import com.epam.tsylko.andrei.service.ClientService;
import com.epam.tsylko.andrei.service.exception.ServiceException;
import com.epam.tsylko.andrei.service.factory.ServiceFactory;
import org.apache.log4j.Logger;

import java.util.Map;

public class SignIn implements Command {
    private static Logger logger = Logger.getLogger(SingleUser.class);
    private String response;
    @Override
    public String execute(String request) {
        logger.debug("SignIn.execute");
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        ClientService service = serviceFactory.getClientService();
        Map<String, String> user;
        try {
            user = ControllerUtil.castRequestParamToMap(request);
            User signInUser = ControllerUtil.initUserObj(user);
            logger.debug(signInUser.toString());
            service.signIn(signInUser);
            response = "User was signed in";
        } catch (ServiceException e) {
            logger.error("Error in service layer", e);
            response = "User was not found.";
        } catch (ControllerUtilException e) {
            logger.error("request params " + SignIn.class.getName() + " was incorrect: " + request, e);

            response = "Error during retrieving user";
        }
        return response;
    }

    @Override
    public boolean getAccess(String request) {
        return true;
    }
}
