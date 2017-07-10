package com.epam.tsylko.andrei.controller;


import com.epam.tsylko.andrei.controller.command.Command;
import org.apache.log4j.Logger;

public final class Controller {

    private final static Logger logger = Logger.getLogger(Controller.class);
    private final CommandProvider provider = new CommandProvider();

    private final char paramDelimiter = '&';

    private final char start = '=';

    public String executeTask(String request) {
        String commandName;
        Command executionCommand;

        commandName = request.substring(request.indexOf(start) + 1, request.indexOf(paramDelimiter));
        logger.debug(commandName);
        executionCommand = provider.getCommand(commandName);
        String response;
        if (executionCommand.getAccess(request)) {

            if (logger.isDebugEnabled()) {
                logger.debug("Access level is applied");
            }

            response = executionCommand.execute(request);

        } else {

            if (logger.isDebugEnabled()) {
                logger.debug("Access denied: " + request);
            }

            response = "Access denied";
        }
        return response;
    }


}


