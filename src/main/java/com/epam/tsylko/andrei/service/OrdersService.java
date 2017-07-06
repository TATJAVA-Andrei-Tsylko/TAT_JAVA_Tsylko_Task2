package com.epam.tsylko.andrei.service;


import com.epam.tsylko.andrei.entities.Book;
import com.epam.tsylko.andrei.entities.OrdersRepository;
import com.epam.tsylko.andrei.entities.User;
import com.epam.tsylko.andrei.service.exception.ServiceException;

import java.util.List;

public interface OrdersService {
    void reserveBook(Book book, User user)throws ServiceException;

    List<OrdersRepository> getAllBooksBookedOrTakenByUser(User user)throws ServiceException;

    void cancelBookReservation(OrdersRepository ordersRepository)throws ServiceException;

    void setBookIsTakenAwayByUser(int ordersRepositoryId)throws ServiceException;

    void setBookIsReturnedByUser(OrdersRepository ordersRepository)throws ServiceException;
}
