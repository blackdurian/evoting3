package com.evoting.util;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateUtil {
    private static final String PATTERN = "yyyy-MM-dd";

    public DateUtil() {
    }

    public Date textToDate(String text) throws ParseException {
        return utilDateToSqlDate(new SimpleDateFormat(PATTERN).parse(text));
    }

    public Date utilDateToSqlDate(java.util.Date date){
        return new java.sql.Date(date.getTime());
    }

    public Date getNow(){
        return new Date(new java.util.Date().getTime());
    }
}
