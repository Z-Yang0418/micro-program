package com.company.program.resource.resource.common.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);
    /**
     * 时间戳转换成日期格式字符串
     * @param seconds 精确到秒的字符串
     * @param format
     * @return
     */
    public static String timeStamp2Date(String seconds,String format) {
        if(seconds == null || seconds.isEmpty() || seconds.equals("null")){
            return "";
        }
        if(format == null || format.isEmpty()){
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(Long.valueOf(seconds+"000")));
    }
    /**
     * 日期格式字符串转换成时间戳
     * @param date_str 字符串日期
     * @param format 如：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String date2TimeStamp(String date_str,String format){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return String.valueOf(sdf.parse(date_str).getTime()/1000);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return "";
    }

    public static Date timeStampToDate(String time_stamp, String format){
        try {
            //时间戳转化为Date
            SimpleDateFormat tsd =  new SimpleDateFormat("yyyy-MM-dd");
            Long time = Long.parseLong(time_stamp);
            String d = tsd.format(time*1000);
            return tsd.parse(d);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }



    /**
     * 取得当前时间戳（精确到秒）
     * @return
     */
    public static String timeStamp(){
        long time = System.currentTimeMillis();
        String t = String.valueOf(time/1000);
        return t;
    }

    public static Date yyyymmddNow(){

        Date date = new Date();
        Date nowDate = null;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            nowDate = df.parse(df.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
       return nowDate;
    }

    /**
     * 判断传入时间戳是否是未来天数
     * [精确到天，即同一天也返回false]
     * @param dateParam
     * @return
     */
    public static boolean isFutureDay(Date dateParam) {
        boolean isFutureDay = false;

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(sdf.format(dateParam));
            Date now = sdf.parse(sdf.format(new Date()));
            if (date.after(now)) {
                isFutureDay = true;
            }
        } catch (ParseException e) {
           logger.error(e.getMessage());
        }
        return isFutureDay;
    }


    /**
     * 获取星期号
     * @param date
     */
    public static String parseDateToWeek(Date date){

        //获取默认选中的日期的年月日星期的值，并赋值
        Calendar calendar = Calendar.getInstance();//日历对象
        calendar.setTime(date);//设置当前日期

//        String yearStr = calendar.get(Calendar.YEAR)+"";//获取年份
//        int month = calendar.get(Calendar.MONTH) + 1;//获取月份
//        String monthStr = month < 10 ? "0" + month : month + "";
//        int day = calendar.get(Calendar.DATE);//获取日
//        String dayStr = day < 10 ? "0" + day : day + "";
        int week = calendar.get(Calendar.DAY_OF_WEEK);
        String weekStr = "";
        /*星期日:Calendar.SUNDAY=1
         *星期一:Calendar.MONDAY=2
         *星期二:Calendar.TUESDAY=3
         *星期三:Calendar.WEDNESDAY=4
         *星期四:Calendar.THURSDAY=5
         *星期五:Calendar.FRIDAY=6
         *星期六:Calendar.SATURDAY=7 */
        switch (week) {
            case 1:
                weekStr = "0";
                break;
            case 2:
                weekStr = "1";
                break;
            case 3:
                weekStr = "2";
                break;
            case 4:
                weekStr = "3";
                break;
            case 5:
                weekStr = "4";
                break;
            case 6:
                weekStr = "5";
                break;
            case 7:
                weekStr = "6";
                break;
            default:
                break;
        }
        return weekStr;
    }


    public static void main(String[] args) {
//        String timeStamp = timeStamp();
//        System.out.println("timeStamp="+timeStamp); //运行输出:timeStamp=1470278082
//        System.out.println(System.currentTimeMillis());//运行输出:1470278082980
        //该方法的作用是返回当前的计算机时间，时间的表达格式为当前计算机时间和GMT时间(格林威治时间)1970年1月1号0时0分0秒所差的毫秒数

        boolean isFutureDay = isFutureDay(new Date());
        System.out.println("isFutureDay="+isFutureDay);


        String date = timeStamp2Date("1547609400", "yyyy-MM-dd");
        System.out.println("date="+date);//运行输出:date=2019-01-16 11:30:00

        String timeStamp2 = date2TimeStamp("2019-1-16 11:30", "yyyy-MM-dd HH:mm");
        System.out.println(timeStamp2);  //运行输出:1547609400

        Date date1 = timeStampToDate("1547609400", "yyyy-MM-dd");
        System.out.println(date1);  //运行输出:1547609400

        String weekStr = parseDateToWeek(new Date());
        System.out.println("weekStr="+weekStr);
    }
}
