package com.epam.tsylko.andrei.controller.command.impl;


import com.epam.tsylko.andrei.controller.command.Command;
import com.epam.tsylko.andrei.controller.util.ControllerUtil;
import com.epam.tsylko.andrei.controller.util.exception.ControllerUtilException;
import com.epam.tsylko.andrei.entities.Book;
import com.epam.tsylko.andrei.service.LibraryService;
import com.epam.tsylko.andrei.service.exception.ServiceException;
import com.epam.tsylko.andrei.service.factory.ServiceFactory;
import org.apache.log4j.Logger;

import java.util.List;

public class SortedFreeBooksByDate implements Command {
    private static Logger logger = Logger.getLogger(SortedFreeBooksByDate.class);
    private String response;

    @Override
    public String execute(String request) {
        logger.debug("SortedFreeBooksByDate.execute()");
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        LibraryService service = serviceFactory.getLibraryService();
        List<Book> books = null;
        try {

            books = service.sortFreeBooksByDate(ControllerUtil.getSortOrderFromRequest(ControllerUtil.castRequestParamToMap(request)));
            logger.debug(books.toString());
            response = "Free books size " + books.size();
        } catch (ServiceException e) {
            logger.error("Error occurred in service layer", e);
            response = "There are no books now.";
        } catch (ControllerUtilException e) {
            logger.error("request params " + SortedFreeBooksByDate.class.getName() + " was incorrect: " + request, e);

            response = "Error during sorting operation";
        }


        return response;

    }


    @Override
    public boolean getAccess(String request) {
        logger.debug("SortedFreeBooksByDate.getAccess()");
        return true;
    }
}
