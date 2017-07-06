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

public class Registration implements Command {
    private static Logger logger = Logger.getLogger(Registration.class);
    private String response;

    @Override
    public String execute(String request) {

        logger.debug("Registration.execute");
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        ClientService service = serviceFactory.getClientService();
        Map<String, String> user;
        try {
            user = ControllerUtil.castRequestParamToMap(request);
            User newUser = ControllerUtil.initUserObj(user);
            logger.debug(newUser.toString());
//            TODO Address set as 0;
//            TODO check incorrect value
            service.registration(newUser);
            response = "User was added";
        } catch (ControllerUtilException e) {
            logger.error("request params " + Registration.class.getName() + " was incorrect: " + request, e);
            response = "Incorrect request";
        } catch (ServiceException e) {
            logger.error("Error in service layer", e);
            response = "Error during add procedure";
        }
        return response;
    }

    @Override
    public boolean getAccess(String request) {
        return true;
    }
}
