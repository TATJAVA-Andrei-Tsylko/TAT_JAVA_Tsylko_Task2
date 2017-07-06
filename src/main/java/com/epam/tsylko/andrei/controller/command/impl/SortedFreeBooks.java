package com.epam.tsylko.andrei.controller.command.impl;


import com.epam.tsylko.andrei.controller.command.Command;

public class SortedFreeBooks implements Command {
    @Override
    public String execute(String request) {
        return null;
    }

    @Override
    public boolean getAccess(String request) {
        return false;
    }
}
