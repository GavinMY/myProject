package com.bluelight.tools;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class FindDate {

//
//		public static void main(String[] args) {
//			// TODO Auto-generated method stub
//			System.out.println("当前时间："+ new Date().toLocaleString());
//			System.out.println("当天0点时间："+ getTimesmorning().toLocaleString());
//			System.out.println("当天24点时间："+ getTimesnight().toLocaleString());
//			System.out.println("本周周一0点时间："+ getTimesWeekmorning().toLocaleString());
//			System.out.println("本周周日24点时间："+ getTimesWeeknight().toLocaleString());
//			System.out.println("本月初0点时间："+ getTimesMonthmorning().toLocaleString());
//			System.out.println("本月未24点时间："+ getTimesMonthnight().toLocaleString());
//		}
	
		// 获得当天0点时间
		public static Date getTimesmorning() {
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.MILLISECOND, 0);
			System.out.println("date---"+cal.getTime());
			return cal.getTime();
			
		}

		// 获得当天24点时间
		public static Date getTimesnight() {
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.HOUR_OF_DAY, 24);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.MILLISECOND, 0);
			return  cal.getTime();
		}

		// 获得本周一0点时间
		public static Date getTimesWeekmorning() {
			Calendar cal = Calendar.getInstance();
			cal.setFirstDayOfWeek(Calendar.MONDAY);
			cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
			cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			return  cal.getTime();
		}

		// 获得本周日24点时间
		public  static Date getTimesWeeknight() {
			Calendar cal = Calendar.getInstance();
			cal.setFirstDayOfWeek(Calendar.MONDAY);
			cal.setTime(getTimesWeekmorning());
			cal.add(Calendar.DAY_OF_WEEK, 7);
			return cal.getTime();
		}

		// 获得本月第一天0点时间
		public static Date getTimesMonthmorning() {
			Calendar cal = Calendar.getInstance();
			cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
			cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
			return  cal.getTime();
		}

		// 获得本月最后一天24点时间
		public static Date getTimesMonthnight() {
			Calendar cal = Calendar.getInstance();
			cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
			cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
			cal.set(Calendar.HOUR_OF_DAY, 24);
			return cal.getTime();
		}
		public static void printfWeeks(String date) throws Exception {
	        // String date = "2013-09";
	        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
	        Date date1 = dateFormat.parse(date);
	        Calendar calendar = new GregorianCalendar();
	        calendar.setTime(date1);
	        int days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	        System.out.println("days:" + days);
	        int count = 0;
	        for (int i = 1; i <= days; i++) {
	            DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
	            Date date2 = dateFormat1.parse(date + "-" + i);
	            calendar.clear();
	            calendar.setTime(date2);
	            int k = new Integer(calendar.get(Calendar.DAY_OF_WEEK));
	            if (k == 1) {// 若当天是周日
	                count++;
	                System.out.println("-----------------------------------");
	                System.out.println("第" + count + "周");
	                if (i - 6 <= 1) {
	                    System.out.println("本周开始日期:" + date + "-" + 1);
	                } else {
	                    System.out.println("本周开始日期:" + date + "-" + (i - 6));
	                }
	                System.out.println("本周结束日期:" + date + "-" + i);
	                System.out.println("-----------------------------------");
	            }
	            if (k != 1 && i == days) {// 若是本月最好一天，且不是周日
	                count++;
	                System.out.println("-----------------------------------");
	                System.out.println("第" + count + "周");
	                System.out.println("本周开始日期:" + date + "-" + (i - k + 2));
	                System.out.println("本周结束日期:" + date + "-" + i);
	                System.out.println("-----------------------------------");
	            }
	        }
	    }
     
		
		 public String getFirstDayOfMonth(int year,int month){
		        String monthStr = month < 10 ? "0" + month : String.valueOf(month);
		        return year + "-"+monthStr+"-" +"01";
		    }
		    
		    /**
		     * get the last date of given month and year
		     * @param year
		     * @param month
		     * @return
		     */
		    public String getLastDayOfMonth(int year,int month){
		        Calendar calendar = Calendar.getInstance();
		        calendar.set(Calendar.YEAR , year);
		        calendar.set(Calendar.MONTH , month - 1);
		        calendar.set(Calendar.DATE , 1);
		        calendar.add(Calendar.MONTH, 1);
		        calendar.add(Calendar.DAY_OF_YEAR , -1);
		        return calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" +
		               calendar.get(Calendar.DAY_OF_MONTH);
		    }
		    
		    /**
		     * get Calendar of given year
		     * @param year
		     * @return
		     */
		    private Calendar getCalendarFormYear(int year){
		        Calendar cal = Calendar.getInstance();
		        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);      
		        cal.set(Calendar.YEAR, year);
		        return cal;
		    }
		    
		    /**
		     * get start date of given week no of a year
		     * @param year
		     * @param weekNo
		     * @return
		     */
		    public String getStartDayOfWeekNo(int year,int weekNo){
		        Calendar cal = getCalendarFormYear(year);
		        cal.set(Calendar.WEEK_OF_YEAR, weekNo);
		        return (cal.get(Calendar.MONTH) + 1) + "." +
		               cal.get(Calendar.DAY_OF_MONTH);    
		        
		    }
		    
		    /**
		     * get the end day of given week no of a year.
		     * @param year
		     * @param weekNo
		     * @return
		     */
		    public String getEndDayOfWeekNo(int year,int weekNo){
		        Calendar cal = getCalendarFormYear(year);
		        cal.set(Calendar.WEEK_OF_YEAR, weekNo);
		        cal.add(Calendar.DAY_OF_WEEK, 6);
		        return (cal.get(Calendar.MONTH) + 1) + "." +
		               cal.get(Calendar.DAY_OF_MONTH);    
		    }
}
