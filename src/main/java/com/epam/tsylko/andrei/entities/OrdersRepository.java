package com.epam.tsylko.andrei.entities;


import java.io.Serializable;
import java.sql.Date;

public class OrdersRepository implements Serializable {

    private static final long serialVersionUID = 5088938112858300320L;

    private int id;
    private Book book;
    private User user;
    private Date dateOfIssuance;
    private Date dateOfTheReturn;
    private boolean isBooked;
    private boolean isTakenAway;
    private boolean isReturned;

    public OrdersRepository(){

    }

    public OrdersRepository(int id, Book book, User user) {
        this.id = id;
        this.book = book;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getDateOfIssuance() {
        return dateOfIssuance;
    }

    public void setDateOfIssuance(Date dateOfIssuance) {
        this.dateOfIssuance = dateOfIssuance;
    }

    public Date getDateOfTheReturn() {
        return dateOfTheReturn;
    }

    public void setDateOfTheReturn(Date dateOfTheReturn) {
        this.dateOfTheReturn = dateOfTheReturn;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public void setBooked(boolean booked) {
        isBooked = booked;
    }

    public boolean isTakenAway() {
        return isTakenAway;
    }

    public void setTakenAway(boolean takenAway) {
        isTakenAway = takenAway;
    }

    public boolean isReturned() {
        return isReturned;
    }

    public void setReturned(boolean returned) {
        isReturned = returned;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrdersRepository that = (OrdersRepository) o;

        if (id != that.id) return false;
        if (isBooked != that.isBooked) return false;
        if (isTakenAway != that.isTakenAway) return false;
        if (isReturned != that.isReturned) return false;
        if (book != null ? !book.equals(that.book) : that.book != null) return false;
        if (user != null ? !user.equals(that.user) : that.user != null) return false;
        if (dateOfIssuance != null ? !dateOfIssuance.equals(that.dateOfIssuance) : that.dateOfIssuance != null)
            return false;
        return dateOfTheReturn != null ? dateOfTheReturn.equals(that.dateOfTheReturn) : that.dateOfTheReturn == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (book != null ? book.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (dateOfIssuance != null ? dateOfIssuance.hashCode() : 0);
        result = 31 * result + (dateOfTheReturn != null ? dateOfTheReturn.hashCode() : 0);
        result = 31 * result + (isBooked ? 1 : 0);
        result = 31 * result + (isTakenAway ? 1 : 0);
        result = 31 * result + (isReturned ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("OrdersRepository{");
        sb.append("id=").append(id);
        sb.append(", book=").append(book);
        sb.append(", user=").append(user);
        sb.append(", dateOfIssuance=").append(dateOfIssuance);
        sb.append(", dateOfTheReturn=").append(dateOfTheReturn);
        sb.append(", isBooked=").append(isBooked);
        sb.append(", isTakenAway=").append(isTakenAway);
        sb.append(", isReturned=").append(isReturned);
        sb.append('}');
        return sb.toString();
    }
}
