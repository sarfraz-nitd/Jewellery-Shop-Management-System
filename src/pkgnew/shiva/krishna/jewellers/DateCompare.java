/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgnew.shiva.krishna.jewellers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DateCompare {
    /*

     try {
     String startDate = "2014/09/12 00:00";
     String endDate = "2014/09/13 00:00";
            
     Date start = new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.ENGLISH)
     .parse(startDate);
     Date end = new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.ENGLISH)
     .parse(endDate);
     System.out.println(start);
     System.out.println(end);
     if (start.compareTo(end) > 0) {
     System.out.println("start is after end");
     } else if (start.compareTo(end) < 0) {
     System.out.println("start is before end");
     } else if (start.compareTo(end) == 0) {
     System.out.println("start is equal to end");
     } else {
     System.out.println("Something weird happened...");
     }
     } catch (ParseException ex) {
     Logger.getLogger(DateCompare.class.getName()).log(Level.SEVERE, null, ex);
     }

     }*/
    
    public static boolean inRange(Date a, Date b, Date c){
        boolean res = true;
        if((a.compareTo(b) < 0 || a.compareTo(b) == 0) && (c.compareTo(b) >0 || c.compareTo(b) == 0)){
            return true;
        } else{
            return false;
        }
    }

    public static boolean isDatePassed(String d) {
        boolean res = true;
        try {
            //String today = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
            Date today = Calendar.getInstance().getTime();
            Date targetDate = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.ENGLISH).parse(d);

            if (today.compareTo(targetDate) > 0) {
                System.out.println("Yes this date has passed");
                res = true;

            } else if (today.compareTo(targetDate) < 0) {
                System.out.println("no this date hasn't passed");
                res = false;
            } else if (today.compareTo(targetDate) == 0) {
                System.out.println("today is this date");
                res = false;
            }

        } catch (ParseException ex) {
            Logger.getLogger(DateCompare.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }
    public static boolean isDatePassed(Date targetDate) {
        boolean res;
    
            //String today = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
            Date today = Calendar.getInstance().getTime();
            //Date targetDate = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.ENGLISH).parse(d);

            if (today.compareTo(targetDate) > 0) {
                //System.out.println("Yes this date has passed");
                res = true;

            } else if (today.compareTo(targetDate) < 0) {
                //System.out.println("no this date hasn't passed");
                res = false;
            } else  {
                //System.out.println("today is this date");
                res = true;
            }

        
        return res;
    }

    public static boolean isToday(String d){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date targetDate = new SimpleDateFormat("dd/MM/yy", Locale.ENGLISH).parse(d);
            
            Date today  = Calendar.getInstance().getTime();
            
            String a = sdf.format(targetDate);
            String b = sdf.format(today);
            if(a.equals(b)){
                return true;
            }else return false;
        } catch (ParseException ex) {
            Logger.getLogger(DateCompare.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public static String afterOneYearDate() {
        SimpleDateFormat y = new SimpleDateFormat("dd/MM/yyyy");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.YEAR, 1);
        Date after = c.getTime();
        return y.format(after);
    }
    
    public static String afterTwoYearDate(){
        SimpleDateFormat y = new SimpleDateFormat("dd/MM/yyyy");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.YEAR, 2);
        Date after = c.getTime();
        return y.format(after);
    }
    
    public static String afterThreeYearDate(){
        SimpleDateFormat y = new SimpleDateFormat("dd/MM/yyyy");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.YEAR, 3);
        Date after = c.getTime();
        return y.format(after);
    }

    public static Date nextDate(Date curDate) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(curDate);
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        return calendar.getTime();
    }
    
    public static Date getDateDaysAfter(String date, String n){
        try {
            Date targetDate = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.ENGLISH).parse(date);
            Calendar c = Calendar.getInstance();
            c.setTime(targetDate);
            c.add(Calendar.DAY_OF_YEAR, Math.round(Float.parseFloat(n)));
            return c.getTime();
        } catch (ParseException ex) {
            Logger.getLogger(DateCompare.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Calendar.getInstance().getTime();
    }

}
