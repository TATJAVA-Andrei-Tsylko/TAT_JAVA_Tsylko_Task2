package com.epam.tsylko.andrei.controller;

import com.epam.tsylko.andrei.controller.command.Command;
import com.epam.tsylko.andrei.controller.command.impl.*;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;


public final class CommandProvider {
    private static Logger logger = Logger.getLogger(CommandProvider.class);

    private final Map<CommandName, Command> repository = new HashMap<>();

    //TODO private or public??
    public CommandProvider() {
        repository.put(CommandName.SIGN_IN, new SignIn());
        repository.put(CommandName.ADD_BOOK, new AddedBook());
        repository.put(CommandName.SHOW_ALL_BOOKS, new AllBooks());
        repository.put(CommandName.EDIT_BOOK, new EditedBook());
        repository.put(CommandName.GET_BOOK, new GetBookCommand());
        repository.put(CommandName.BOOK_AVAILABILITY_STATUS, new BookAvailabilityStatus());
        repository.put(CommandName.HOME_ADDRESS, new HomeAddress());
        repository.put(CommandName.EDIT_ADDRESS, new EditedAddress());
        repository.put(CommandName.CURRENT_ADDRESS, new CurrentAddress());
        repository.put(CommandName.USER_REGISTRATION, new Registration());
        repository.put(CommandName.USER_EDITED, new EditedUser());
        repository.put(CommandName.ALL_USERS, new AllUsers());
        repository.put(CommandName.GET_USER, new SingleUser());
        repository.put(CommandName.USER_ROLE, new UserRole());
        repository.put(CommandName.USER_STATUS, new UserStatus());
        repository.put(CommandName.BOOK_RESERVATION, new BookReservation());
        repository.put(CommandName.CANCELLATION_BOOK_RESERVATION,new CancellationBookReservation());
        repository.put(CommandName.BOOK_LEAVED_LIBRARY,new BookLeavedLibrary());
        repository.put(CommandName.BOOK_RETURNED_COMMAND,new BookReturnCommand());
        repository.put(CommandName.REDUCE_ACCESS_LEVEL_COMMAND,new ReduceAccessLevelCommand());
    }


    Command getCommand(String name) {
        CommandName commandName = null;
        Command command = null;

        try {
            commandName = CommandName.valueOf(name.toUpperCase());
            command = repository.get(commandName);
        } catch (IllegalArgumentException | NullPointerException e) {
            logger.error(CommandName.valueOf(name.toUpperCase()) + " is invalid.", e);
            command = repository.get(CommandName.WRONG_REQUEST);
        }
        return command;
    }
}
