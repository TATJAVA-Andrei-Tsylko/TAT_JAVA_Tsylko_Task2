package com.epam.tsylko.andrei.controller.command.impl;


import com.epam.tsylko.andrei.controller.command.Command;
import com.epam.tsylko.andrei.controller.util.ControllerUtil;
import com.epam.tsylko.andrei.controller.util.exception.ControllerUtilException;
import com.epam.tsylko.andrei.entities.OrdersRepository;
import com.epam.tsylko.andrei.entities.Role;
import com.epam.tsylko.andrei.service.ClientService;
import com.epam.tsylko.andrei.service.OrdersService;
import com.epam.tsylko.andrei.service.exception.ServiceException;
import com.epam.tsylko.andrei.service.factory.ServiceFactory;
import org.apache.log4j.Logger;

import java.util.Map;

public class CancellationBookReservation implements Command {
    private static Logger logger = Logger.getLogger(CancellationBookReservation.class);
    private String response;

    @Override
    public String execute(String request) {
        logger.debug("CancellationBookReservation.execute");
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        OrdersService service = serviceFactory.getOrdersService();
        Map<String, String> order;
        try {
            order = ControllerUtil.castRequestParamToMap(request);
            OrdersRepository cancellationRepository = ControllerUtil.initOrderObj(order);
            logger.debug(cancellationRepository.toString());
            service.cancelBookReservation(cancellationRepository);
            response = "Book reservation was canceled";
        } catch (ControllerUtilException e) {
            logger.error("request params " + CancellationBookReservation.class.getName() + " was incorrect: " + request, e);
            response = "Incorrect request";
        } catch (ServiceException e) {
            logger.error("Error in service layer", e);
            response = "Error during book cancellation procedure";

        }
        return response;
    }

    @Override
    public boolean getAccess(String request) {

        logger.debug("CancellationBookReservation.getAccess");
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
