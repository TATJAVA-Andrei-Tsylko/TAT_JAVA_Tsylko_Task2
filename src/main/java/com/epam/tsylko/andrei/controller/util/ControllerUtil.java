package com.epam.tsylko.andrei.controller.util;


import com.epam.tsylko.andrei.controller.util.exception.ControllerUtilException;
import com.epam.tsylko.andrei.entities.*;
import com.epam.tsylko.andrei.service.OrdersService;
import org.apache.log4j.Logger;


import java.text.ParseException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ControllerUtil {
    private static Logger logger = Logger.getLogger(ControllerUtil.class);
    private final static String ID = "&id=";
    private final static String BOOLEAN_TRUE = "true";
    private final static String BOOLEAN_FALSE = "false";

    public final static String cutActionPart(String request) {
        String start = "&";
        int end = request.length();
        return request.substring(request.indexOf(start) + 1, end);
    }

    public final static Map<String, String> castRequestParamToMap(String request) throws ControllerUtilException {
        Map<String, String> requestParams = new HashMap<>();
        if (request != null) {

            for (String pair : splitAmp(request)) {
                String params[] = splitEqual(pair);
                if (params.length != 2) {
                    throw new ControllerUtilException("Check params");
                }
                requestParams.put(params[0], params[1]);
            }
        }
        return requestParams;

    }

    private static String[] splitAmp(String request) {
        return request.split("[&]");
    }

    private static String[] splitEqual(String request) {
        return request.split("[=]");
    }

    public final static java.sql.Date castStringToSqlDate(String time) throws ControllerUtilException {
        java.util.Date date = null;
        if (time != null && !time.isEmpty()) {
            time = time.replaceAll("[\\./]", "-");
            java.text.DateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");

            try {
                date = format.parse(time);
            } catch (ParseException e) {
                throw new ControllerUtilException("Error in method castStringToSqlDate. Cannot cast date from String to SQLDate", e);
            }
        } else {
            date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        }

        return new java.sql.Date(date.getTime());
    }


    public final static Book initBookObj(Map<String, String> dataFromRequest) throws ControllerUtilException {
        logger.debug("ControllerUtil.initBookObj");
        Book book;
        try {
            book = new Book(parseNumberOrNullStringToInt(getValueFromMapByKey(dataFromRequest, "bookId")), getValueFromMapByKey(dataFromRequest, "booksName"), getValueFromMapByKey(dataFromRequest, "authorName"), getValueFromMapByKey(dataFromRequest, "authorSurname"),
                    getValueFromMapByKey(dataFromRequest, "publisher"), getValueFromMapByKey(dataFromRequest, "cityPublisher"), ControllerUtil.castStringToSqlDate(getValueFromMapByKey(dataFromRequest, "yearPublished")),
                    parseNumberOrNullStringToInt(getValueFromMapByKey(dataFromRequest, "ISBN")), parseNumberOrNullStringToInt(getValueFromMapByKey(dataFromRequest, "printRun")), parseNumberOrNullStringToInt(getValueFromMapByKey(dataFromRequest, "paperBack")));
        } catch (NumberFormatException e) {
            throw new ControllerUtilException("Numbers isn't parsable in method initBookObj");
        }

        return book;
    }


    public final static Address initAddressObj(Map<String, String> dataFromRequest) throws ControllerUtilException {
        logger.debug("ControllerUtil.initAddressObj");
        Address address;
        try {
            address = new Address(parseNumberOrNullStringToInt(getValueFromMapByKey(dataFromRequest, "addressId")), getValueFromMapByKey(dataFromRequest, "country"),
                    getValueFromMapByKey(dataFromRequest, "city"), getValueFromMapByKey(dataFromRequest, "street"), parseNumberOrNullStringToInt(getValueFromMapByKey(dataFromRequest, "house")),
                    parseNumberOrNullStringToInt(getValueFromMapByKey(dataFromRequest, "room")));

        } catch (NumberFormatException e) {
            throw new ControllerUtilException("Numbers isn't parsable in method initAddressObj ");
        }

        return address;
    }

    public final static User initUserObj(Map<String, String> dataFromRequest) throws ControllerUtilException {
        logger.debug("ControllerUtil.initUserObj");
        User user;
        try {
            user = new User(parseNumberOrNullStringToInt(getValueFromMapByKey(dataFromRequest, "userId")), getValueFromMapByKey(dataFromRequest, "login"),
                    getValueFromMapByKey(dataFromRequest, "password"), getValueFromMapByKey(dataFromRequest, "userName"),
                    getValueFromMapByKey(dataFromRequest, "userSurname"), castStringToSqlDate(getValueFromMapByKey(dataFromRequest, "birthday")),
                    new Address(parseNumberOrNullStringToInt(getValueFromMapByKey(dataFromRequest, "address"))), getValueFromMapByKey(dataFromRequest, "email"), getValueFromMapByKey(dataFromRequest, "phone"));

        } catch (NumberFormatException e) {
            throw new ControllerUtilException("Numbers isn't parsable in method initUserObj");
        }
        return user;
    }

    public final static User initUserObjWithRoleParam(Map<String, String> dataFromRequest) throws ControllerUtilException {
        logger.debug("ControllerUtil.initUserObjWithRoleParam");
        User user;
//        TODO check another methods
        if (getValueFromMapByKey(dataFromRequest, "role") != null && Role.getByName(getValueFromMapByKey(dataFromRequest, "role")) != Role.SUPER_ADMIN) {
            try {

                    user = new User(parseNumberOrNullStringToInt(getValueFromMapByKey(dataFromRequest, "userId")), Role.getByName(getValueFromMapByKey(dataFromRequest, "role")));


            } catch (NumberFormatException e) {
                throw new ControllerUtilException("Numbers isn't parsable in method initUserObjWithRoleParam");
            }
        }else{
            throw new ControllerUtilException("Request doesn't content role");
        }
        return user;
    }
    public final static User initUserObjWithBlockParam(Map<String, String> dataFromRequest) throws ControllerUtilException {
        logger.debug("ControllerUtil.initUserObjWithBlockParam");
        User user;
//        TODO check another methods
        if (getValueFromMapByKey(dataFromRequest, "enabled") != null) {
            try {

                user = new User(parseNumberOrNullStringToInt(getValueFromMapByKey(dataFromRequest, "userId")), parseBooleanValueFromString(getValueFromMapByKey(dataFromRequest, "enabled")));


            } catch (NumberFormatException e) {
                throw new ControllerUtilException("Numbers isn't parsable in method initUserObjWithBlockParam");
            }
        }else{
            throw new ControllerUtilException("Request doesn't content user enable status");
        }
        return user;
    }

    public final static OrdersRepository initOrderObj(Map<String, String> dataFromRequest) throws ControllerUtilException {
        logger.debug("ControllerUtil.initOrderObj");
        OrdersRepository repository;
        try {
            repository = new OrdersRepository(parseNumberOrNullStringToInt(getValueFromMapByKey(dataFromRequest, "orderId")), new Book (parseNumberOrNullStringToInt(getValueFromMapByKey(dataFromRequest, "bookId"))),
                    new User (parseNumberOrNullStringToInt(getValueFromMapByKey(dataFromRequest, "userId"))));

        } catch (NumberFormatException e) {
            throw new ControllerUtilException("Numbers isn't parsable in method initUserObj");
        }
        return repository;
    }


    private static int parseNumberOrNullStringToInt(String param) {

        int number = 0;
        try {
            if (param != null)
                number = Integer.parseInt(param);
        } catch (NumberFormatException e) {
            number = 0;
        }
        return number;
    }

    public static boolean parseBooleanValueFromString(String param) throws ControllerUtilException {
        if (param.equalsIgnoreCase(BOOLEAN_FALSE) || param.equalsIgnoreCase(BOOLEAN_TRUE)) {
            return Boolean.valueOf(param);
        } else {
            throw new ControllerUtilException("Check inputed boolean value");
        }

    }

    public static String getValueFromMapByKey(Map<String, String> requestMap, String key) {
        String value = requestMap.entrySet().stream()
                .filter(e -> e.getKey().equals(key))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse(null);
        return value;
    }

    public final static int findUserIdInRequest(String request) throws ControllerUtilException {
        int end = request.length();
        String id = request.substring(request.indexOf(ID) + 4, end);
        int userId;
        try {
            userId = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            throw new ControllerUtilException("UserId isn't parsable");
        }

        return userId;
    }

}
