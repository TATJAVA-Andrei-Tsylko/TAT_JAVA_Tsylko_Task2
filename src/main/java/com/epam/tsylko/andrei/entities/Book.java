package com.epam.tsylko.andrei.entities;

import java.io.Serializable;
import java.sql.Date;

public class Book implements Serializable {
    private static final long serialVersionUID = 4366337790278150097L;
    private int bookId;
    private String booksName;
    private String authorName;
    private String authorSurname;
    private String publisher;
    private String cityPublisher;
    private Date yearPublished;
    private int ISBN;
    private int printRun;
    private int paperBack;
    private boolean isValidBook;
    private boolean isFree;


    public Book() {
    }

    public Book(int id) {
        this.bookId = id;
    }

    public Book(int bookId, String booksName, String authorName, String authorSurname, String publisher, String cityPublisher, Date yearPublished, int ISBN, int printRun, int paperBack) {
        this.bookId = bookId;
        this.booksName = booksName;
        this.authorName = authorName;
        this.authorSurname = authorSurname;
        this.publisher = publisher;
        this.cityPublisher = cityPublisher;
        this.yearPublished = yearPublished;
        this.ISBN = ISBN;
        this.printRun = printRun;
        this.paperBack = paperBack;
    }

    public int getId() {
        return bookId;
    }

    public void setId(int id) {
        this.bookId = id;
    }

    public String getBooksName() {
        return booksName;
    }

    public void setBooksName(String booksName) {
        this.booksName = booksName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorSurname() {
        return authorSurname;
    }

    public void setAuthorSurname(String authorSurname) {
        this.authorSurname = authorSurname;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getCityPublisher() {
        return cityPublisher;
    }

    public void setCityPublisher(String cityPublisher) {
        this.cityPublisher = cityPublisher;
    }

    public Date getYearPublished() {
        return yearPublished;
    }

    public void setYearPublished(Date yearPublished) {
        this.yearPublished = yearPublished;
    }

    public int getISBN() {
        return ISBN;
    }

    public void setISBN(int ISBN) {
        this.ISBN = ISBN;
    }

    public int getPrintRun() {
        return printRun;
    }

    public void setPrintRun(int printRun) {
        this.printRun = printRun;
    }

    public int getPaperBack() {
        return paperBack;
    }

    public void setPaperBack(int paperBack) {
        this.paperBack = paperBack;
    }

    public boolean isValidBook() {
        return isValidBook;
    }

    public void setValidBook(boolean validBook) {
        isValidBook = validBook;
    }

    public boolean isFree() {
        return isFree;
    }

    public void setFree(boolean free) {
        isFree = free;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        if (bookId != book.bookId) return false;
        if (ISBN != book.ISBN) return false;
        if (printRun != book.printRun) return false;
        if (paperBack != book.paperBack) return false;
        if (isValidBook != book.isValidBook) return false;
        if (isFree != book.isFree) return false;
        if (booksName != null ? !booksName.equals(book.booksName) : book.booksName != null) return false;
        if (authorName != null ? !authorName.equals(book.authorName) : book.authorName != null) return false;
        if (authorSurname != null ? !authorSurname.equals(book.authorSurname) : book.authorSurname != null)
            return false;
        if (publisher != null ? !publisher.equals(book.publisher) : book.publisher != null) return false;
        if (cityPublisher != null ? !cityPublisher.equals(book.cityPublisher) : book.cityPublisher != null)
            return false;
        return yearPublished != null ? yearPublished.equals(book.yearPublished) : book.yearPublished == null;
    }

    @Override
    public int hashCode() {
        int result = bookId;
        result = 31 * result + (booksName != null ? booksName.hashCode() : 0);
        result = 31 * result + (authorName != null ? authorName.hashCode() : 0);
        result = 31 * result + (authorSurname != null ? authorSurname.hashCode() : 0);
        result = 31 * result + (publisher != null ? publisher.hashCode() : 0);
        result = 31 * result + (cityPublisher != null ? cityPublisher.hashCode() : 0);
        result = 31 * result + (yearPublished != null ? yearPublished.hashCode() : 0);
        result = 31 * result + ISBN;
        result = 31 * result + printRun;
        result = 31 * result + paperBack;
        result = 31 * result + (isValidBook ? 1 : 0);
        result = 31 * result + (isFree ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Book{");
        sb.append("id=").append(bookId);
        sb.append(", booksName='").append(booksName).append('\'');
        sb.append(", authorName='").append(authorName).append('\'');
        sb.append(", authorSurname='").append(authorSurname).append('\'');
        sb.append(", publisher='").append(publisher).append('\'');
        sb.append(", cityPublisher='").append(cityPublisher).append('\'');
        sb.append(", yearPublished=").append(yearPublished);
        sb.append(", ISBN=").append(ISBN);
        sb.append(", printRun=").append(printRun);
        sb.append(", paperBack=").append(paperBack);
        sb.append(", isValidBook=").append(isValidBook);
        sb.append(", isFree=").append(isFree);
        sb.append('}');
        return sb.toString();
    }
}
