/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.healthDepartment.util;

import java.util.Calendar;

public class GetDate {

    public static String month_val[] = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    public static String full_month_val[] = new String[]{"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

    public static String[] getMonth_val() {
        return month_val;
    }

    public static String getFullMonthName(String month) {
        String monthName = null;
        if (month.equalsIgnoreCase("Jan") || month.equalsIgnoreCase("January")) {
            monthName = "January";
        } else if (month.equalsIgnoreCase("Feb") || month.equalsIgnoreCase("February")) {
            monthName = "February";
        } else if (month.equalsIgnoreCase("Mar") || month.equalsIgnoreCase("March")) {
            monthName = "March";
        } else if (month.equalsIgnoreCase("Apr") || month.equalsIgnoreCase("April")) {
            monthName = "April";
        } else if (month.equalsIgnoreCase("May") || month.equalsIgnoreCase("May")) {
            monthName = "May";
        } else if (month.equalsIgnoreCase("Jun") || month.equalsIgnoreCase("June")) {
            monthName = "June";
        } else if (month.equalsIgnoreCase("Jul") || month.equalsIgnoreCase("July")) {
            monthName = "July";
        } else if (month.equalsIgnoreCase("Aug") || month.equalsIgnoreCase("August")) {
            monthName = "August";
        } else if (month.equalsIgnoreCase("Sep") || month.equalsIgnoreCase("September")) {
            monthName = "September";
        } else if (month.equalsIgnoreCase("OCT") || month.equalsIgnoreCase("October")) {
            monthName = "October";
        } else if (month.equalsIgnoreCase("Nov") || month.equalsIgnoreCase("November")) {
            monthName = "November";
        } else if (month.equalsIgnoreCase("Dec") || month.equalsIgnoreCase("December")) {
            monthName = "December";
        }
        return monthName;
    }

    public static int getMonthNo(String month) {
        int monthNo = 0;
        if (month.equalsIgnoreCase("Jan") || month.equalsIgnoreCase("January")) {
            monthNo = 1;
        } else if (month.equalsIgnoreCase("Feb") || month.equalsIgnoreCase("February")) {
            monthNo = 2;
        } else if (month.equalsIgnoreCase("Mar") || month.equalsIgnoreCase("March")) {
            monthNo = 3;
        } else if (month.equalsIgnoreCase("Apr") || month.equalsIgnoreCase("April")) {
            monthNo = 4;
        } else if (month.equalsIgnoreCase("May") || month.equalsIgnoreCase("May")) {
            monthNo = 5;
        } else if (month.equalsIgnoreCase("Jun") || month.equalsIgnoreCase("June")) {
            monthNo = 6;
        } else if (month.equalsIgnoreCase("Jul") || month.equalsIgnoreCase("July")) {
            monthNo = 7;
        } else if (month.equalsIgnoreCase("Aug") || month.equalsIgnoreCase("August")) {
            monthNo = 8;
        } else if (month.equalsIgnoreCase("Sep") || month.equalsIgnoreCase("September")) {
            monthNo = 9;
        } else if (month.equalsIgnoreCase("OCT") || month.equalsIgnoreCase("October")) {
            monthNo = 10;
        } else if (month.equalsIgnoreCase("Nov") || month.equalsIgnoreCase("November")) {
            monthNo = 11;
        } else if (month.equalsIgnoreCase("Dec") || month.equalsIgnoreCase("December")) {
            monthNo = 12;
        }
        return monthNo;
    }

    public static String getIntegerMonthInString(String bill_month) {
        String month = "";
        if (bill_month.equals("Jan")) {
            month = "01";
        } else if (bill_month.equals("Feb")) {
            month = "02";
        } else if (bill_month.equals("Mar")) {
            month = "03";
        } else if (bill_month.equals("Apr")) {
            month = "04";
        } else if (bill_month.equals("May")) {
            month = "05";
        } else if (bill_month.equals("Jun")) {
            month = "06";
        } else if (bill_month.equals("Jul")) {
            month = "07";
        } else if (bill_month.equals("Aug")) {
            month = "08";
        } else if (bill_month.equals("Sep")) {
            month = "09";
        } else if (bill_month.equals("Oct")) {
            month = "10";
        } else if (bill_month.equals("Nov")) {
            month = "11";
        } else if (bill_month.equals("Dec")) {
            month = "12";
        }
        return month;
    }

    public static String getBill_month() {
        String bill_month = null;
        try {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            System.out.println(" current month" + month);
            bill_month = month_val[month];
            bill_month = bill_month + "-" + year;
        } catch (Exception e) {
            System.out.println("Error in get Month" + e);
        }
        return bill_month;

    }

    public static String getMonthInString(int month_int) {
        String month = "";
        month = full_month_val[month_int];
        return month;
    }

    public static String getPreviousMonth(String bill_month, String year) {

        String prev_month = "";
        int prev_year = Integer.parseInt(year);
        int month = 0;
        if (bill_month.equals("Jan")) {
            month = 0;
        } else if (bill_month.equals("Feb")) {
            month = 1;
        } else if (bill_month.equals("Mar")) {
            month = 2;
        } else if (bill_month.equals("Apr")) {
            month = 3;
        } else if (bill_month.equals("May")) {
            month = 4;
        } else if (bill_month.equals("Jun")) {
            month = 5;
        } else if (bill_month.equals("Jul")) {
            month = 6;
        } else if (bill_month.equals("Aug")) {
            month = 7;
        } else if (bill_month.equals("Sep")) {
            month = 8;
        } else if (bill_month.equals("Oct")) {
            month = 9;
        } else if (bill_month.equals("Nov")) {
            month = 10;
        } else if (bill_month.equals("Dec")) {
            month = 11;
        }
        month--;
        if (bill_month.equals("Jan")) {
            prev_year--;
            month = 11;
        }
        prev_month = month_val[month] + "-" + prev_year;
        return prev_month;
    }

    public static String getPreviousMonth(String bill_month1) {
        String[] split = bill_month1.split("-", bill_month1.length());
        String bill_month = split[0];
        String year = split[1];
        String prev_month = getPreviousMonth(bill_month, year);
        return prev_month;
    }

    public static String getNextMonth(String bill_month, String year) {
        String next_month = "";
        int next_year = Integer.parseInt(year);
        int month = 0;
        if (bill_month.equals("Jan")) {
            month = 0;
        } else if (bill_month.equals("Feb")) {
            month = 1;
        } else if (bill_month.equals("Mar")) {
            month = 2;
        } else if (bill_month.equals("Apr")) {
            month = 3;
        } else if (bill_month.equals("May")) {
            month = 4;
        } else if (bill_month.equals("Jun")) {
            month = 5;
        } else if (bill_month.equals("Jul")) {
            month = 6;
        } else if (bill_month.equals("Aug")) {
            month = 7;
        } else if (bill_month.equals("Sep")) {
            month = 8;
        } else if (bill_month.equals("Oct")) {
            month = 9;
        } else if (bill_month.equals("Nov")) {
            month = 10;
        } else if (bill_month.equals("Dec")) {
            month = 11;
        }
        month++;
        if (bill_month.equals("Dec")) {
            next_year++;
            month = 0;
        }
        next_month = month_val[month] + "-" + next_year;
        return next_month;
    }

    public static String getCurrentMonth() {
        String currMonth = "";
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH);
        currMonth = month_val[month];
        return currMonth;
    }

    public static int getCurrentYear() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);      
        return year;
    }

    public static String getCurrentDate() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.DATE)+"-"+(cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.YEAR);
    }
}
