package com.winit.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Girish Velivela on 08-07-2016.
 */
public class CalendarUtil {
    public static final String DATE_STD_PATTERN = "yyyy-MM-dd";
    public static final String DD_MM_YYYY_PATTERN = "dd-MM-yyyy";
    public static final String DD_MMM_YYYY_COMMA_PATTERN = "dd MMM, yyyy";
    public static final String DD_MMM_YYYY_PATTERN = "dd-MMM-yyyy";
    public static final String MM_DD_YYYY_PATTERN = "MM-dd-yyyy";
    public static final String DATE_STD_PATTERN_MONTH = "yyyy-MM";
    public static final String MM_YYYY_PATTERN = "MM-yyyy";
    public static final String MMM_YYYY_PATTERN = "MMM-yyyy";
    public static final String MMM_PATTERN = "MMM";
    public static final String DATE_STD_PATTERN_FULLDATE = "MMM dd,yyyy";
    public static final String DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String DATE_PATTERN_dd_MMM_YYYY = "dd-MMM-yyyy HH:mm:ss";
    public static final String DATE_PATTERN_dd_MM_YYYY = "dd-MM-yyyy HH:mm:ss";
    public static final String YYYY_MM_DD_FULL_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_PATTERN_MMM_dd = "MMM dd";
    public static final String dd_MMM_PATTERN = "dd MMM";
    public static final String EEEE_PATTERN = "EEEE";


    public static String getDate(Date date, String pattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, Locale.ENGLISH);
        return simpleDateFormat.format(date);
    }

    public static String getDate(Date date, String pattern, Locale locale) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, locale);
        return simpleDateFormat.format(date);
    }

    public static String getDate(String date, String parsePattern, String pattern, Locale locale) {
        try {
            SimpleDateFormat parseDateFormat = new SimpleDateFormat(parsePattern,locale);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, locale);
            return simpleDateFormat.format(parseDateFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getDate(String strDate, String parsePattern, String pattern, long delay, Locale locale) {
        try {
            SimpleDateFormat parseDateFormat = new SimpleDateFormat(parsePattern,locale);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, locale);
            Date date = parseDateFormat.parse(strDate);
            return simpleDateFormat.format(date.getTime() - delay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static long getEpochTime() {
        return (new Date().getTime())/1000;
    }

    public static int[] getCurrentDayMonthYear() {
        Calendar calendar = Calendar.getInstance();
        int[] dayMonth = new int[3];
        dayMonth[0] = calendar.get(Calendar.DAY_OF_MONTH);
        dayMonth[1] = calendar.get(Calendar.MONTH);
        dayMonth[2] = calendar.get(Calendar.YEAR);
        return dayMonth;
    }

    public static boolean compareDate(String startDateStr, String endDateStr) {
        try {
            SimpleDateFormat parseDateFormat = new SimpleDateFormat(DATE_PATTERN_dd_MM_YYYY);
            long startDate;
            if (!StringUtils.isEmpty(startDateStr))
                startDate = parseDateFormat.parse(startDateStr).getTime();
            else
                startDate = new Date().getTime();
            long endDate;
            if (!StringUtils.isEmpty(endDateStr))
                endDate = parseDateFormat.parse(endDateStr).getTime();
            else
                endDate = new Date().getTime();
            if (startDate < endDate)
                return true;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static long getDifference(String startDateStr, String endDateStr) {
        try {
            SimpleDateFormat parseDateFormat = new SimpleDateFormat(DATE_PATTERN);
            long startDate;
            if (!StringUtils.isEmpty(startDateStr))
                startDate = parseDateFormat.parse(startDateStr).getTime();
            else
                startDate = new Date().getTime();
            long endDate;
            if (!StringUtils.isEmpty(endDateStr))
                endDate = parseDateFormat.parse(endDateStr).getTime();
            else
                endDate = new Date().getTime();
            return (endDate - startDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static long getDifference(String startDateStr, String stratDayPattern, String endDateStr, String endDayPattern) {
        try {
            SimpleDateFormat parseDateFormat = new SimpleDateFormat(stratDayPattern);
            long startDate;
            if (!StringUtils.isEmpty(startDateStr))
                startDate = parseDateFormat.parse(startDateStr).getTime();
            else
                startDate = new Date().getTime();
            long endDate;
            parseDateFormat = new SimpleDateFormat(endDayPattern);
            if (!StringUtils.isEmpty(endDateStr))
                endDate = parseDateFormat.parse(endDateStr).getTime();
            else
                endDate = new Date().getTime();
            return (endDate - (startDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static long getDifference(String startDateStr, String stratDayPattern, String endDateStr, String endDayPattern,int delay) {
        try {
            SimpleDateFormat parseDateFormat = new SimpleDateFormat(stratDayPattern);
            long startDate;
            if (!StringUtils.isEmpty(startDateStr))
                startDate = parseDateFormat.parse(startDateStr).getTime();
            else
                startDate = new Date().getTime();
            long endDate;
            parseDateFormat = new SimpleDateFormat(endDayPattern);
            if (!StringUtils.isEmpty(endDateStr))
                endDate = parseDateFormat.parse(endDateStr).getTime();
            else
                endDate = new Date().getTime();
            return (endDate+delay - (startDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String getCurrentMonth() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM");
        return simpleDateFormat.format(new Date());
    }

    public static int getDifferenceOfWeek(String startDateStr, String stratDayPattern, String endDateStr, String endDayPattern) {
        try {
            SimpleDateFormat parseDateFormat = new SimpleDateFormat(stratDayPattern);
            Date startDate;
            if (!StringUtils.isEmpty(startDateStr))
                startDate = parseDateFormat.parse(startDateStr);
            else
                startDate = new Date();
            Date endDate;
            parseDateFormat = new SimpleDateFormat(endDayPattern);
            if (!StringUtils.isEmpty(endDateStr))
                endDate = parseDateFormat.parse(endDateStr);
            else
                endDate = new Date();
            Calendar startCalendar = Calendar.getInstance();
            startCalendar.setTime(startDate);
            Calendar endCalendar = Calendar.getInstance();
            endCalendar.setTime(endDate);
            return endCalendar.get(Calendar.WEEK_OF_YEAR) - startCalendar.get(Calendar.WEEK_OF_YEAR);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int getDifferenceOfMonth(String startDateStr, String stratDayPattern, String endDateStr, String endDayPattern) {
        try {
            SimpleDateFormat parseDateFormat = new SimpleDateFormat(stratDayPattern);
            Date startDate;
            if (!StringUtils.isEmpty(startDateStr))
                startDate = parseDateFormat.parse(startDateStr);
            else
                startDate = new Date();
            Date endDate;
            parseDateFormat = new SimpleDateFormat(endDayPattern);
            if (!StringUtils.isEmpty(endDateStr))
                endDate = parseDateFormat.parse(endDateStr);
            else
                endDate = new Date();
            Calendar startCalendar = Calendar.getInstance();
            startCalendar.setTime(startDate);
            Calendar endCalendar = Calendar.getInstance();
            endCalendar.setTime(endDate);
            int endYear = endCalendar.get(Calendar.YEAR);
            return (((endYear - startCalendar.get(Calendar.YEAR)) * 12) + (endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String getMonth(int month, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, month);
        if (year != 0)
            calendar.set(Calendar.YEAR, year);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM");
        return simpleDateFormat.format(calendar.getTime());
    }

    public static String getDay(int day, int month, int year) {
        Calendar calendar = Calendar.getInstance();
        if (day != 0)
            calendar.set(Calendar.DAY_OF_MONTH, day);
        if (month != 0)
            calendar.set(Calendar.MONTH, month);
        if (year != 0)
            calendar.set(Calendar.YEAR, year);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE");
        return simpleDateFormat.format(calendar.getTime());
    }

    public static String getDate(int day, int month, int year) {
        Calendar calendar = Calendar.getInstance();
        if (day != 0)
            calendar.set(Calendar.DAY_OF_MONTH, day);
        if (month != 0)
            calendar.set(Calendar.MONTH, month);
        if (year != 0)
            calendar.set(Calendar.YEAR, year);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DD_MMM_YYYY_PATTERN, Locale.ENGLISH);
        return simpleDateFormat.format(calendar.getTime());
    }

    public static String getValidDate(int day, int month, int year) {
        Calendar calendar = Calendar.getInstance();
        if (day != 0)
            calendar.set(Calendar.DAY_OF_MONTH, day);
        if (month != 0)
            calendar.set(Calendar.MONTH, month);
        if (year != 0)
            calendar.set(Calendar.YEAR, year);
       /* if(calendar.getTimeInMillis() > new Date().getTime())
            return "";*/
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DD_MMM_YYYY_PATTERN, Locale.ENGLISH);
        return simpleDateFormat.format(calendar.getTime());
    }

    public static String getDay(String date, String pattern) {
        try {
            SimpleDateFormat parseDateFormat = new SimpleDateFormat(pattern);
            Date startDate;
            if (!StringUtils.isEmpty(date))
                startDate = parseDateFormat.parse(date);
            else
                startDate = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE", Locale.ENGLISH);
            return simpleDateFormat.format(startDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getTodayDate() {
        Calendar calendar = Calendar.getInstance();
        StringBuilder sb = new StringBuilder();
        String d = null;
        int day = calendar.get(Calendar.DAY_OF_WEEK);

        switch (day) {
            case Calendar.SUNDAY:
                d = "Sunday";
                break;
            case Calendar.MONDAY:
                d = "Monday";
                break;

            case Calendar.TUESDAY:
                d = "Tuesday";
                break;
            case Calendar.WEDNESDAY:
                d = "Wednesday";
                break;
            case Calendar.THURSDAY:
                d = "Thursday";
                break;
            case Calendar.FRIDAY:
                d = "Friday";
                break;
            case Calendar.SATURDAY:
                d = "Saturday";
                break;
        }
        String month = String.format(Locale.US, "%tb", calendar);
        int date = calendar.get(Calendar.DAY_OF_MONTH);
        int year = calendar.get(Calendar.YEAR);
        return (sb.append(d).append(",").append(" ").append(month).append(" ").append(date).append(",").append(" ").append(year).toString());

    }

    public static String getTodayDate(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        StringBuilder sb = new StringBuilder();
        String d = null;
        int day = calendar.get(Calendar.DAY_OF_WEEK);

        switch (day) {
            case Calendar.SUNDAY:
                d = "Sunday";
                break;
            case Calendar.MONDAY:
                d = "Monday";
                break;

            case Calendar.TUESDAY:
                d = "Tuesday";
                break;
            case Calendar.WEDNESDAY:
                d = "Wednesday";
                break;
            case Calendar.THURSDAY:
                d = "Thursday";
                break;
            case Calendar.FRIDAY:
                d = "Friday";
                break;
            case Calendar.SATURDAY:
                d = "Saturday";
                break;
        }
        String month = String.format(Locale.US, "%tb", calendar);
        int date = calendar.get(Calendar.DAY_OF_MONTH);
        int year = calendar.get(Calendar.YEAR);
        return (sb.append(d).append(",").append(" ").append(month).append(" ").append(date).append(",").append(" ").append(year).toString());

    }

    public static String getCurrentDate() {
        String dateStr = null;
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_STD_PATTERN, Locale.ENGLISH);
        dateStr = sdf.format(date);
        return dateStr;
    }

    public static String getCurrentDateWithTime() {
        String dateStr = null;
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN, Locale.ENGLISH);
        dateStr = sdf.format(date);
        return dateStr;
    }

    public static long getCurrentTimeInMilli() {
        return System.currentTimeMillis();
    }

    public static String getTimeFromTimestamp(long timeStamp) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeStamp);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm",Locale.US);
        return simpleDateFormat.format(calendar.getTime());
    }

    public static String getTimeFromDate(String date) {
        String[] strDate = date.split("T");
        String[] strTime = strDate[1].split(":");

        return strTime[0] + ":" + strTime[1];

    }

    public static String getFormatedDatefromStringWithTime(String strDate){
        String formatedDate  = null;
        try
        {
            formatedDate = strDate;
            LogUtils.errorLog(strDate, strDate);
            if(strDate.contains("T"))
            {
                String dateTime[]  	= strDate.split("T");
                String arrDate[]	= dateTime[0].split("-");
                formatedDate = arrDate[2]+" "+getMonthFromNumber(StringUtils.getInt(arrDate[1]))+", "+arrDate[0];

                if(dateTime[1].contains(":"))
                {
                    String arrTime[]	= dateTime[1].split(":");
                    formatedDate = formatedDate+" at "+arrTime[0]+":"+arrTime[1];

                }

            }
            else
            {
                String arrDate[]= strDate.split("-");
                formatedDate = arrDate[2]+" "+getMonthFromNumber(StringUtils.getInt(arrDate[1]))+", "+arrDate[0];
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            formatedDate =null;
        }
        return formatedDate;
    }

    /** this method returns Month name from the int value of month
     *@param  intMonth
     **/
    public static String getMonthFromNumber(int intMonth){
        String strMonth = "";

        switch(intMonth)
        {
            case 1:
                strMonth = "Jan";break;
            case 2:
                strMonth = "Feb";break;
            case 3:
                strMonth = "Mar";break;
            case 4:
                strMonth = "Apr";break;
            case 5:
                strMonth = "May";break;
            case 6:
                strMonth = "Jun";break;
            case 7:
                strMonth = "Jul";break;
            case 8:
                strMonth = "Aug";break;
            case 9:
                strMonth = "Sep";break;
            case 10:
                strMonth = "Oct";break;
            case 11:
                strMonth = "Nov";break;
            case 12:
                strMonth = "Dec";break;
        }

        return strMonth;
    }

    public static String[] getLastMonths() {
        String[] dateStr = new String[3];
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(CalendarUtil.DATE_STD_PATTERN_MONTH,Locale.ENGLISH);
        dateStr[2] = simpleDateFormat.format(calendar.getTime());
        calendar.add(Calendar.MONTH,-1);
        dateStr[1] = simpleDateFormat.format(calendar.getTime());
        calendar.add(Calendar.MONTH,-1);
        dateStr[0] = simpleDateFormat.format(calendar.getTime());
        return dateStr;
    }

    public static Object[] getLastMonthLabels(int noOfMonths) {
        String[] dateFullStr = new String[noOfMonths];
        String[] dateStr = new String[noOfMonths];
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(CalendarUtil.MMM_PATTERN);
        for(int i=1;i<=noOfMonths;i++) {
            dateFullStr[noOfMonths-i] = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
            dateStr[noOfMonths-i] = simpleDateFormat.format(calendar.getTime());
            calendar.add(Calendar.MONTH, -1);
        }
        return new Object[]{dateFullStr,dateStr};
    }
}
