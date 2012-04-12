package com.walkin.common;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class Common {
	

	public static String changeTime(String time) {

		if (time != null && time.length() > 2) {
			time = time.substring(0, time.length() - 2);
		}
		return time;
	}

	public static String getMonthday(String time) {
		Date date = VeDate.fomatTime(time);
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(date);
		int month = gc.get(Calendar.MONTH);
		int day = gc.get(Calendar.DAY_OF_MONTH);
		time = month + "-" + day;
		return time;
	}

	public static String stringChange(String str) {
		str = str.replaceAll("\r\n", "<br>");
		str = str.replaceAll(" ", "&nbsp;");
		return str;
	}

	public static String stringChangebr(String str) {
		str = str.replaceAll("<br>", "\r\n");
		str = str.replaceAll("&nbsp;", " ");
		return str;
	}

	public static String charSubString(String str, int num) {
		char[] cs = str.toCharArray();
		int count = 0;
		int last = cs.length;
		for (int i = 0; i < cs.length; i++) {
			if (cs[i] > 255)
				count = count + 2;
			else
				count++;
			if (count > num) {
				last = i + 1;
				break;
			}
		}
		// System.out.println(count);
		// System.out.println(last);
		if (count < num)
			return str;
		num -= 2;

		for (int i = last - 1; i >= 0; i--) {
			if (cs[i] > 255)
				count = count - 2;
			else
				count--;
			if (count <= num) {
				return str.substring(0, i) + "..";
			}
		}

		return "..";
	}

	public static int getAnnState(String isNotice, String isLock,
			String isBoutique, String annNewTime, String clickCount) {
		// 普通帖子
		int state = 0;
		// 锁定帖子
		try {
			int _isNotice = Integer.parseInt(isNotice);
			if (_isNotice == 1) {
				return 1;
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		try {
			int _isLock = Integer.parseInt(isLock);
			if (_isLock == 1) {
				return 2;
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		try {
			int _isBoutique = Integer.parseInt(isBoutique);
			if (_isBoutique == 1) {
				return 3;
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}

		// 热门帖子:500次点击
		try {
			int count = Integer.parseInt(clickCount);
			if (count > 500) {
				return 4;
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		// 最新帖子:两小时内
		Date newTime = VeDate.fomatTime(annNewTime);
		if (newTime != null) {
			Date now = new Date();
			if (((now.getTime() - newTime.getTime()) / (2 * 60 * 60 * 1000)) < 1) {
				return 5;
			}
		}
		// System.out.println(annNewTime);
		return state;
	}

	public static String fileNameChange(String str) {
		// System.out.println(str);
		str = str.replaceAll("\\\\", " ");
		str = str.replaceAll("/", "");
		str = str.replaceAll(":", "");
		str = str.replaceAll("\\*", "");
		str = str.replaceAll("\\?", "");
		str = str.replaceAll("\"", "");
		str = str.replaceAll("<", "");
		str = str.replaceAll(">", "");
		str = str.replaceAll("\\|", "");
		return str;
	}

	/**
	 * 去除字符串中的空格,回车,换行符,制表符
	 * */
	public static String rmStrBlank(String str) {
		str = str.trim();
		return str.replaceAll("\\s*|\t|\r|\n", "");
	}

	public static String stringChangeString(String str) {
		System.out.println(str);
		str = str.replaceAll("'", "");
		str = str.replaceAll("\\.", "");
		str = str.replaceAll("&", "and");
		str = str.replaceAll(" ", "");
		return str;
	}
}
