package com.epam.tsylko.andrei.controller.command.impl;


import com.epam.tsylko.andrei.controller.command.Command;
import com.epam.tsylko.andrei.entities.Book;
import com.epam.tsylko.andrei.service.LibraryService;
import com.epam.tsylko.andrei.service.exception.ServiceException;
import com.epam.tsylko.andrei.service.factory.ServiceFactory;
import org.apache.log4j.Logger;

import java.util.List;

public class AllBooks implements Command {
    private static Logger logger = Logger.getLogger(AllBooks.class);
    private String response;
    @Override
    public String execute(String request) {
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        LibraryService service = serviceFactory.getLibraryService();
        List<Book> books = null;

            try {
                books = service.retrieveAllBooksFromLibrary();
                response = "Library size " + books.size();
            } catch (ServiceException e) {
                logger.error("Error occurred in service layer",e);
                response = "There are no books now.";
            }


        return response;

    }

    @Override
    public boolean getAccess(String request) {
        return true;
    }
}
