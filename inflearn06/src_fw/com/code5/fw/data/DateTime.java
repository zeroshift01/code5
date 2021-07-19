package com.code5.fw.data;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author zero
 *
 */
public class DateTime {

	/**
	 * @return
	 */
	public static String getThisDTM() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREA);
		return simpleDateFormat.format(new Date());
	}

	/**
	 * @return
	 */
	public static String getThisDTMByForm() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.KOREA);
		return simpleDateFormat.format(new Date());
	}
}
