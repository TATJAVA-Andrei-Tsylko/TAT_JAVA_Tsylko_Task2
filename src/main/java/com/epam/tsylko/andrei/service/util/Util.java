package com.epam.tsylko.andrei.service.util;


import com.epam.tsylko.andrei.entity.User;
import com.epam.tsylko.andrei.service.util.exception.UtilException;
import org.apache.log4j.Logger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Util {
    private static Logger logger = Logger.getLogger(Util.class);
    private static final String ISBN = "^(97(8|9))?\\d{5}(\\d|X)$";
    private static final String EMAIL = "\\w+@[a-zA-Z_]+?\\.[a-zA-Z]{2,6}";
    private static final String DATE = "\\d{4}-\\d{2}-\\d{2}";

    public final static void isNull(Object... objects) throws UtilException {
        for (Object ob : objects) {
            if (ob == null) {
                throw new UtilException("Entered data was invalid (null)");
            }
        }
    }

    public final static void isEmptyString(String... strings) throws UtilException {
        for (String s : strings) {
            if (s.isEmpty()) {
                throw new UtilException("Entered data was invalid (empty)");
            }
        }
    }

    public final static void isNumberPositive(int... number) throws UtilException {
        for (int n : number) {
            if (n < 0) {
                throw new UtilException("Entered data was invalid (empty)");
            }
        }
    }

    //TODO it's fake ISBN
    public final static void checkISBN(int number) throws UtilException {
        String checkedNumber = String.valueOf(number);
        if (!checkedNumber.matches(ISBN)) {
            throw new UtilException("Entered ISBN isn't valid");
        }
    }

    public final static void checkEmail(User user) throws UtilException {
        if (user.getEmail() == null || user.getEmail().isEmpty()) {

        } else {
            Pattern pattern = Pattern.compile(EMAIL);
            Matcher matcher = pattern.matcher(user.getEmail());
            if (!matcher.matches()) {
                throw new UtilException("email isn't correct");
            }
        }
    }

    public final static void matchDate(String string) throws UtilException {
        if (string == null || string.isEmpty()) {

        } else {
            Pattern pattern = Pattern.compile(DATE);

            Matcher matcher = pattern.matcher(string);
            if (!matcher.matches()) {
                throw new UtilException("Error. Date isn't correct.");
            }

        }


    }


    public final static String convertDateToString(Date indate) throws UtilException {
        String dateString = null;
        SimpleDateFormat sdfr = new SimpleDateFormat("yyyy-MM-dd");
        try {

            dateString = sdfr.format(indate.getTime());
        } catch (Exception e) {
            throw new UtilException("Error in method castStringToSqlDate. Cannot cast date from SQLDate to String", e);
        }
        return dateString;
    }

    public final static String getHashForPassword(String password) throws UtilException {
        logger.debug("Util.getHashForPassword()");
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new UtilException("Error in method getHashForPassword. Cannot encrypt password", e);
        }
        md.update(password.getBytes());

        byte byteData[] = md.digest();

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }
        logger.debug("hash " + sb.toString());
        return sb.toString();
    }

    public final static boolean checkHash(String hash, String password) throws UtilException {
        if (hash.equals(getHashForPassword(password))) {
            return true;
        }
        throw new UtilException("Util.checkHash() false");

    }
}
