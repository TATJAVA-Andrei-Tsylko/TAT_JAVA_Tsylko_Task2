package com.epam.tsylko.andrei.controller.command.impl;


import com.epam.tsylko.andrei.controller.command.Command;
import com.epam.tsylko.andrei.controller.util.ControllerUtil;
import com.epam.tsylko.andrei.controller.util.exception.ControllerUtilException;
import com.epam.tsylko.andrei.entities.Book;
import com.epam.tsylko.andrei.entities.Role;
import com.epam.tsylko.andrei.service.ClientService;
import com.epam.tsylko.andrei.service.LibraryService;
import com.epam.tsylko.andrei.service.exception.ServiceException;
import com.epam.tsylko.andrei.service.factory.ServiceFactory;
import org.apache.log4j.Logger;

import java.util.Map;

public class GetBookCommand implements Command {
    private static Logger logger = Logger.getLogger(AddedBook.class);
    private String response = "";

    @Override
    public String execute(String request) {

        logger.debug("GetBookCommand.execute");
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        LibraryService service = serviceFactory.getLibraryService();
        Map<String, String> book;
        try {
            book = ControllerUtil.castRequestParamToMap(request);
            Book newBook = ControllerUtil.initBookObj(book);
            logger.debug(newBook.toString());
            return service.getBookFromTheLibrary(newBook.getId()).toString();
        } catch (ServiceException e) {
            response = "Incorrect request";
        } catch (ControllerUtilException e) {
            logger.error("Error in service layer", e);
            response = "Error during retrieving book";
        }

        return response;

    }

    @Override
    public boolean getAccess(String request) {
        logger.debug("GetBookCommand.getAccess");
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        ClientService clientService = serviceFactory.getClientService();
        try {
            return (clientService.checkUserRole(ControllerUtil.findUserIdInRequest(request), Role.SUPER_ADMIN, Role.ADMIN, Role.USER));
        } catch (ServiceException e) {
            logger.error("Error in service layer", e);
        } catch (ControllerUtilException e) {
            logger.error("Error in ControllerUtil ", e);
        }
        return false;
    }
}
