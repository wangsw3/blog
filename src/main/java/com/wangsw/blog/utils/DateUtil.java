package com.wangsw.blog.utils;

import com.wangsw.blog.controller.TUserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 *
 */
public class DateUtil {

  private static Logger logger = LoggerFactory.getLogger(TUserController.class);

  /** yyyy-MM-dd hh:mm:ss */
  public static final String FORMAT_YYYY_MM_DD_HH12_MM_SS = "yyyy-MM-dd hh:mm:ss";
  /** yyyy/MM/dd hh:mm:ss */
  public static final String FORMAT2_YYYY_MM_DD_HH12_MM_SS = "yyyy/MM/dd hh:mm:ss";
  /** yyyy-MM-dd HH:mm:ss */
  public static final String FORMAT_YYYY_MM_DD_HH24_MM_SS = "yyyy-MM-dd HH:mm:ss";
  /** yyyy/MM/dd HH:mm:ss */
  public static final String FORMAT2_YYYY_MM_DD_HH24_MM_SS = "yyyy/MM/dd HH:mm:ss";
  /** yyyy-MM-dd */
  public static final String FORMAT_YYYY_MM_DD = "yyyy-MM-dd";
  /** yyyy */
  public static final String FORMAT_YYYY = "yyyy";
  /** yyyy/MM/dd */
  public static final String FORMAT2_YYYY_MM_DD = "yyyy/MM/dd";

  /** yyyy-MM-dd HH:mm */
  public static final String FORMAT_YYYY_MM_DD_HH24_MM = "yyyy-MM-dd HH:mm";

  /** yyyy/MM/dd HH:mm */
  public static final String FORMAT2_YYYY_MM_DD_HH24_MM = "yyyy/MM/dd HH:mm";

  /** yyyy/MM/dd hh:mm */
  public static final String FORMAT2_YYYY_MM_DD_HH12_MM = "yyyy/MM/dd hh:mm:ss";
  /** HH:mm */
  public static final String FORMAT_HH24_MM = "HH:mm";

  /**
   * 以一定的格式转换日期 返回Date类型
   *
   * @param str
   * @param format 日期格式，如yyyy/MM/dd
   * @return
   * @throws ParseException
   */
  public static Date parseDate(String str, String format) {
    SimpleDateFormat sdf = new SimpleDateFormat(format);
    try {
      return sdf.parse(str);
    } catch (ParseException e) {
      logger.error("parseDate 转换时间出错" + e);
      return null;
    }
  }

  /**
   * 以一定的格式转换日期 返回String类型
   *
   * @param str
   * @param format 日期格式，如yyyy/MM/dd
   * @return
   * @throws ParseException
   */
  public static String parseString(String str, String format) {
    SimpleDateFormat sdf = new SimpleDateFormat(format);
    try {
      Date date = sdf.parse(str);
      return sdf.format(date);
    } catch (ParseException e) {
      logger.error("parseDate 转换时间出错" + e);
      return null;
    }
  }

  /**
   * 以一定的格式的日期转换字符串日期 返回String类型
   *
   * @param date
   * @param format
   * @return
   */
  public static String fomatStr(Date date, String format) {
    try {
      SimpleDateFormat sdf = new SimpleDateFormat(format);
      return sdf.format(date);
    } catch (Exception e) {
      logger.error("fomatStr 转换时间出错" + e);
      return null;
    }
  }

  /**
   * 获取当天的时间
   *
   * @return
   * @throws ParseException
   */
  public static String getDefaultCurrentDateString(String format) {
    try {
      SimpleDateFormat sdf = new SimpleDateFormat(format);
      return sdf.format(new Date());
    } catch (Exception e) {
      logger.error("getDefaultCurrentDateString 获取当前时间错误:" + e);
      return null;
    }
  }

  /**
   * 比较d1与d2的差值，单位天
   *
   * @param d1
   * @param d2
   * @return 两个日期相差的天数
   * @throws ParseException
   */
  public static int beforeDay(Date d1, Date d2) throws ParseException {
    if (d1 == null || d2 == null) {
      return 0;
    }
    int i = 3600 * 24 * 1000;
    return (int) ((d1.getTime() - d2.getTime()) / i);
  }

  /**
   * 比较当前日期和指定日期
   *
   * @param str 指定日期字符串 return 如果指定日期早于当前日期返回true,否则返回flase
   */
  public static boolean dateCompareNow(String str) {
    boolean bea = false;
    SimpleDateFormat sdf_d = new SimpleDateFormat("yyyy-MM-dd");
    String now = sdf_d.format(new Date());
    Date date1;
    Date date_now; // 当前日期
    try {
      date1 = sdf_d.parse(str);
      date_now = sdf_d.parse(now);
      if (date1.before(date_now)) {
        bea = true;
      }
    } catch (ParseException e) {
      bea = false;
    }
    return bea;
  }

  /**
   * 对时间进行偏移
   *
   * @param field 偏移的域
   * @param start 为空，则取当前时间
   * @param offset 正数往前推，即加；负数往后退，即减
   * @return
   */
  public static Date getDateOffset(int field, Date start, int offset) {
    Calendar cal = Calendar.getInstance();
    if (start != null) {
      cal.setTime(start);
    }
    int _d = cal.get(field);
    cal.set(field, _d + offset);
    return cal.getTime();
  }

  /**
   * 设置date的时间
   *
   * @param start
   * @param hour
   * @param minute
   * @param second
   * @return
   */
  public static Date resetTime(Date start, int hour, int minute, int second) {
    Calendar cal = Calendar.getInstance();
    if (start != null) {
      cal.setTime(start);
    }
    cal.set(Calendar.HOUR_OF_DAY, hour);
    cal.set(Calendar.MINUTE, minute);
    cal.set(Calendar.SECOND, second);

    return cal.getTime();
  }

  /**
   * 获取当前月份
   *
   * @return
   */
  public static int currentMonth() {
    Calendar cal = Calendar.getInstance();
    return cal.get(Calendar.MONTH) + 1;
  }

  /**
   * 获取当前年份
   *
   * @return
   */
  public static int currentYear() {
    Calendar cal = Calendar.getInstance();
    return cal.get(Calendar.YEAR);
  }

  /**
   * long类型转换为String类型
   *
   * @param currentTime
   * @param formatType
   * @return
   * @throws ParseException
   */
  public static String longToString(long currentTime, String formatType) {
    Date dateOld = new Date(currentTime); // 根据long类型的毫秒数生命一个date类型的时间
    return fomatStr(dateOld, formatType);
  }

  /**
   * String类型转换为long类型
   *
   * @param
   * @param formatType
   * @return
   * @throws ParseException
   */
  public static Long StringToLong(String str, String formatType) {
    Date date = parseDate(str, formatType); // String类型转成date类型
    if (date == null) {
      return 0L;
    } else {
      long currentTime = dateToLong(date); // date类型转成long类型
      return currentTime;
    }
  }

  /**
   * Date类型转换为long类型
   *
   * @param date
   * @return
   */
  public static long dateToLong(Date date) {
    return date.getTime();
  }

  /**
   * long类型转换为Date类型
   *
   * @param dateTime
   * @param formatType
   * @return
   */
  public static Date longToDate(long dateTime, String formatType) {
    String dateStr = longToString(dateTime, formatType);
    return parseDate(dateStr, formatType);
  }

  /**
   * Timestamp类型转换为Date类型
   *
   * @param
   * @param formatType
   * @return
   */
  public static Date timestampToDate(Timestamp date, String formatType) {
    return date != null ? longToDate(date.getTime(), formatType) : null;
  }

  /**
   * 得到当前时间的年份
   *
   * @return
   */
  public static String getCurrentYear() {

    Calendar date = Calendar.getInstance();
    String year = String.valueOf(date.get(Calendar.YEAR));
    return year;
  }

  /**
   * 判断日期是否为当周
   *
   * @param time
   * @return
   */
  public static boolean isThisWeek(long time) {
    Calendar calendar = Calendar.getInstance();
    int currentWeek = calendar.get(Calendar.WEEK_OF_YEAR);
    calendar.setTime(new Date(time));
    int paramWeek = calendar.get(Calendar.WEEK_OF_YEAR);
    if (paramWeek == currentWeek) {
      return true;
    }
    return false;
  }

  /**
   * 判断选择的日期是否是今天
   *
   * @param time
   * @return
   */
  public static boolean isToday(long time) {
    return isThisTime(time, "yyyy-MM-dd");
  }

  /**
   * 判断选择的日期是否是本月
   *
   * @param time
   * @return
   */
  public static boolean isThisMonth(long time) {
    return isThisTime(time, "yyyy-MM");
  }

  private static boolean isThisTime(long time, String pattern) {
    Date date = new Date(time);
    SimpleDateFormat sdf = new SimpleDateFormat(pattern);
    String param = sdf.format(date); // 参数时间
    String now = sdf.format(new Date()); // 当前时间
    if (param.equals(now)) {
      return true;
    }
    return false;
  }

  /**
   * 判断指定时间为 今天 昨天还是某个时间
   *
   * @param day
   * @return
   * @throws ParseException
   */
  public static String TodayOrYesterday(String day) throws ParseException {
    StringBuilder dayStr = new StringBuilder();
    Calendar pre = Calendar.getInstance();
    Date predate = new Date(System.currentTimeMillis());
    pre.setTime(predate);

    Calendar cal = Calendar.getInstance();
    Date date = parseDate(day, FORMAT_YYYY_MM_DD);
    cal.setTime(date);
    String string = parseString(day, FORMAT_YYYY_MM_DD);

    if (cal.get(Calendar.YEAR) == (pre.get(Calendar.YEAR))) {
      int diffDay = cal.get(Calendar.DAY_OF_YEAR) - pre.get(Calendar.DAY_OF_YEAR);
      if (diffDay == 0) {
        dayStr.append("今天");
      } else if (diffDay == -1) {
        dayStr.append("昨天");
      } else {
        String[] str = string.split("-");
        dayStr.append(str[1]).append("月").append(str[2]).append("天");
      }
    } else {
      String[] str = string.split("-");
      dayStr
          .append(str[0])
          .append("年")
          .append(str[1])
          .append("月")
          .append(str[2])
          .append("天");
    }
    return dayStr.toString();
  }
}
