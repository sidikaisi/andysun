package utils;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtils {

	public static String YYYY_MM_DD = "yyyy-MM-dd";

	public static String YYYYMMDD = "yyyyMMdd";

	public static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

	public static String yyyyMMddHHmmssSSS = "yyyyMMddHHmmssSSS";

	public static String YYYY_MM_DD2 = "yyyy年MM月dd日";

	/**
	 * @Description date转换成LocalDate
	 * @Author zhangxiaojun
	 * @Date 2018/8/2
	 * @Params [date]
	 * @Return java.time.LocalDate
	 * @Version 1.0.0
	 **/
	public static LocalDate convertDate(Date date) {
		if (date == null) {
			date = new Date();
		}
		Instant instant = date.toInstant();
		ZoneId zoneId = ZoneId.systemDefault();
		return instant.atZone(zoneId).toLocalDate();
	}

	/**
	 *  @Description 转换日期
	 *  @Author zhangxiaojun
	 *  @Date 2018/8/13
	 *  @Params [date, formatter]
	 *  @Return java.util.Date
	 *  @Version 1.0.0
	 *
	 **/
	public static Date formatDate2date(Date date, String formatter) {
		if (formatter == null) {
			formatter = YYYY_MM_DD;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(formatter);
		Date newDate = null;
		try {
			newDate = sdf.parse(sdf.format(date));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newDate;
	}

	/**
	 * @Description date转换成LocalDateTime
	 * @Author zhangxiaojun
	 * @Date 2018/8/8
	 * @Params [date]
	 * @Return java.time.LocalDateTime
	 * @Version 1.0.0
	 **/
	public static LocalDateTime convertDateTime(Date date) {
		if (date == null) {
			date = new Date();
		}
		Instant instant = date.toInstant();
		ZoneId zoneId = ZoneId.systemDefault();
		return instant.atZone(zoneId).toLocalDateTime();
	}

	/**
	 * @Description 格式化日期
	 * @Author zhangxiaojun
	 * @Date 2018/8/2
	 * @Params [date, formatter]
	 * @Return java.lang.String
	 * @Version 1.0.0
	 **/
	public static String formatDate2Str(Date date, String formatter) {
		LocalDateTime localDate = convertDateTime(date);
		if (formatter == null) {
			formatter = YYYY_MM_DD;
		}
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(formatter);
		return dtf.format(localDate);
	}

	/**
	 *  @Description 转换日期
	 *  @Author zhangxiaojun
	 *  @Date 2018/8/13
	 *  @Params [dateStr, formatter]
	 *  @Return java.util.Date
	 *  @Version 1.0.0
	 *
	 **/
	public static Date formatStr2Date(String dateStr, String formatter) {
		if (formatter == null) {
			formatter = YYYY_MM_DD;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(formatter);
		Date newDate = null;
		try {
			newDate = sdf.parse(dateStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newDate;
	}

	/**
	 * @Description 获取两个日期之间相差的天数
	 * @Author zhangxiaojun
	 * @Date 2018/8/8
	 * @Params [start, end]
	 * @Return int
	 * @Version 1.0.0
	 **/
	public static long caclDiffDays(Date start, Date end) {
		if (start == null || end == null) {
			return -1L;
		}
		return Duration.between(convertDateTime(start), convertDateTime(end)).toDays();
	}

	/**
	 * @Description 获取当前时间的时间戳
	 * @Author zhangxiaojun
	 * @Date 2018/8/8
	 * @Params []
	 * @Return java.lang.String
	 * @Version 1.0.0
	 **/
	public static String getCurrTimestamp() {
		return formatDate2Str(new Date(), yyyyMMddHHmmssSSS);
	}

}
