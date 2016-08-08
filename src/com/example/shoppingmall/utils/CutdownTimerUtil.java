package com.example.shoppingmall.utils;

public class CutdownTimerUtil {
	public static String dealTime(long time) {
		StringBuffer returnString = new StringBuffer();
		long day = time / (24 * 60 * 60);
		long hours = (time % (24 * 60 * 60)) / (60 * 60);
		long minutes = ((time % (24 * 60 * 60)) % (60 * 60)) / 60;
		long second = ((time % (24 * 60 * 60)) % (60 * 60)) % 60;
		String dayStr = String.valueOf(day);
		String hoursStr = timeStrFormat(String.valueOf(hours));
		String minutesStr = timeStrFormat(String.valueOf(minutes));
		String secondStr = timeStrFormat(String.valueOf(second));

		returnString.append(dayStr).append("å¤?").append(hoursStr).append("æ—?").append(minutesStr).append("åˆ?").append(secondStr).append("ç§?");
		return returnString.toString();
	}
	private static String timeStrFormat(String timeStr) {
		switch (timeStr.length()) {
		case 1:
			timeStr = "0" + timeStr;
			break;
		}
		return timeStr;
	}
}
