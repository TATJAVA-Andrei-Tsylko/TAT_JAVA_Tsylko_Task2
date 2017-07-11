package com.epam.tsylko.andrei;


import com.epam.tsylko.andrei.controller.Controller;
import com.epam.tsylko.andrei.controller.util.ControllerUtil;
import com.epam.tsylko.andrei.controller.util.ControllerUtilException;

public class Main {
    public static void main(String[] args) {
//        try {
//            ConnectionPool connectionPool = new ConnectionPool();
//            Book book = new Book("Azazel","vico","pico","boon","london",12313123,122,100);
//            DAOFactory factory = DAOFactory.getInstance();
//            BookDao bookDao= factory.getMysqlBookImpl();
////            bookDao.addBook(book);
//            Book book1 = bookDao.getBook(9);
//            book1.setBooksName("Monteno");
//            bookDao.editBook(book1);
//            List<Book> bookList = bookDao.getBooks();
//            bookList.forEach(System.out::println);
//        } catch (ConnectionPoolException e) {
//            e.printStackTrace();
//        } catch (DAOException e) {
//            e.printStackTrace();
//        }

        Controller controller = new Controller();
//        String addBook = "action=ADD_BOOK&booksName=Java&authorName=Mark&" +
//                "authorSurname=Horn&publisher=Oreilly&cityPublisher=London&ISBN=978067&id=2";
//
        String showAllBooks1= "action=SHOW_ALL_BOOKS&id=1";
        String showAllBooks2= "action=SHOW_ALL_BOOKS";
        String showAllBooks3= "action=";
        try {
            System.out.println(ControllerUtil.checkRequestLinkWithoutParams(showAllBooks3) + "\n");
            System.out.println(ControllerUtil.checkRequestLink(showAllBooks3));
        } catch (ControllerUtilException e) {
            e.printStackTrace();
        }
//
//        System.out.println(controller.executeTask(showAllBooks));

//        String editedBook = "action=EDIT_BOOK&bookId=10&booksName=C#&authorName=Mark&" +
//                "authorSurname=Horn&publisher=Prisma&cityPublisher=London&ISBN=978067&id=3";
//        System.out.println(controller.executeTask(editedBook));

//        String getBook="action=GET_BOOK&bookId=3&id=3";
//                System.out.println(controller.executeTask(getBook));

//        String blockedBook="action=BOOK_AVAILABILITY_STATUS&bookId=3&isAvailable=false&id=3";
//        System.out.println(controller.executeTask(blockedBook));


//
//        String addAddress="action=HOME_ADDRESS&country=UK&city=London&street=Green Street&house=20&room=201&id=2";
//        System.out.println(controller.executeTask(addAddress));

//        String editAddress="action=EDIT_ADDRESS&addressId=4&country=UK&city=Manchester&street=Green Street&house=20&room=201&id=2";
//        System.out.println(controller.executeTask(editAddress));

//        String currentAddress="action=CURRENT_ADDRESS&addressId=4&country=UK&city=Manchester&street=Green Street&house=20&room=201&id=2";
//        System.out.println(controller.executeTask(currentAddress));

//        String addUser="action=USER_REGISTRATION&login=monk1&password=monk&userName=Anto&userSurname=Anto&birthday=1986-02-03&email=anto@mail.ru&phone=37525515666";
//        System.out.println(controller.executeTask(addUser));

//        String addUser2="action=USER_REGISTRATION&login=monk3&password=monk3&userName=Anto&userSurname=Anto&birthday=1986-02-03&email=anto@mail.ru&phone=37525515666";
//        System.out.println(controller.executeTask(addUser2));

//        String editUser="action=USER_EDITED&userId=10&password=monkM&userName=Anto&userSurname=Anto&birthday=1986-02-03&email=anto@mail.ru&phone=37525515666&id=10";
//        System.out.println(controller.executeTask(editUser));


//        String allUsers="action=ALL_USERS&email=anto@mail.ru&phone=37525515666&id=3";
//        System.out.println(controller.executeTask(allUsers));



//        String oneUser="action=GET_USER&userId=10&email=anto@mail.ru&phone=37525515666&id=10";
//        System.out.println(controller.executeTask(oneUser));





//                String userStatus="action=USER_STATUS&userId=11&id=3";
//
//        System.out.println(controller.executeTask(userStatus));

//                        String reservBook="action=BOOK_RESERVATION&userId=11&bookId=4&id=3";
//        System.out.println(controller.executeTask(reservBook));

//        String reservBookBanUser="action=BOOK_RESERVATION&userId=11&bookId=4&id=11";
//        System.out.println(controller.executeTask(reservBookBanUser));



//        String reservBookCancel="action=CANCELLATION_BOOK_RESERVATION&orderId=6&bookId=4&id=3";
//        System.out.println(controller.executeTask(reservBookCancel));

//        String leavesBook="action=BOOK_LEAVED_LIBRARY&orderId=5&bookId=4&id=3";
//        System.out.println(controller.executeTask(leavesBook));

//        String returnedBook="action=BOOK_RETURNED_COMMAND&orderId=5&bookId=4&id=3";
//        System.out.println(controller.executeTask(returnedBook));

//                String userRole="action=USER_ROLE&userId=11&role=SUPER_ADMIN&id=3";
//        System.out.println(controller.executeTask(userRole));



//        String reduceRoleCommand="action=REDUCE_ACCESS_LEVEL_COMMAND&userId=11&role=USER&id=4";
//        System.out.println(controller.executeTask(reduceRoleCommand));

//        String signIn="action=SIGN_IN&login=monk3&password=monk3";
//        System.out.println(controller.executeTask(signIn));
//
//        String sordtedFreeBooks="action=SORTED_BOOKS&order=desc";
//        System.out.println(controller.executeTask(sordtedFreeBooks));


//        System.out.println(ControllerUtil.cutActionPart(a));
//        try {
//            Map<String,String> map = ControllerUtil.castRequestParamToMap(a);
////            map.forEach((k,v)->{System.out.println("key : " + k + " Value : " + v);});
//
////            map.values().stream().anyMatch("booksName");
//        } catch (ControllerUtilException e) {
//            e.printStackTrace();
//        }
//
//        ServiceFactory factory = ServiceFactory.getInstance();
//        ClientService service = factory.getClientService();
//        try {
//            System.out.println(service.checkUserRole(2, Role.USER));
//        } catch (ServiceException e) {
//            e.printStackTrace();
//        }
//
//        try {
//            System.out.println(ControllerUtil.findUserIdInRequest(a));
//        } catch (ControllerUtilException e) {
//            e.printStackTrace();
//        }



    }
}
