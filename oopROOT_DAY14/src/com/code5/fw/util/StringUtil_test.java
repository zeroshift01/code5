package com.code5.fw.util;

import junit.framework.TestCase;

/**
 * @author seuk
 *
 */
public class StringUtil_test extends TestCase {

	public void test_01() {

		StringUtil.hexStringToByteArray("B638138E66B1455B17A1B6894A0BA860");
		StringUtil.hexStringToByteArray("FC8F9093E4987715F6D6A02F2BEF6BD0");
		StringUtil.hexStringToByteArray("847179244D0C11679FFEC3FC28CE4BB92E3DB886D27ACD269295A7541236A012");
		StringUtil.hexStringToByteArray("2FA2AF567706ADD9B8A8C90D56A90826DE965A5FBBAB465D838AA2BC66FA3651");

	}

}
