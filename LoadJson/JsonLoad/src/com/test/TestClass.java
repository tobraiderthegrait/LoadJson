package com.test;
import java.awt.Robot;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

public class TestClass {
    public static final int FIVE_SECONDS = 5000;
    public static final int FIFTEEN_MINITES = 150000;

    public static final int MAX_Y = 400;
    public static final int MAX_X = 400;

    public static void main(String... args) throws Exception {
        Robot robot = new Robot();
        Random random = new Random();
        int  i = 0;
        while (true) {
        	i++;
        	if(i==720) {
        		i=0;
                Thread.sleep(FIFTEEN_MINITES);
        		
        	}else {
                Thread.sleep(FIVE_SECONDS);
        	}
        	
            robot.mouseMove(random.nextInt(MAX_X), random.nextInt(MAX_Y));
            
            Date date = new Date();   // given date
            Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
            calendar.setTime(date);   // assigns calendar to given date 
           if(calendar.get(Calendar.HOUR_OF_DAY) == 18) {
        	   System.out.println("Go home!!!");
        	  break; 
           }
        }
    }
}