package com.code5.fw.data;

/**
 * @author zero
 *
 */
public class XssConvert {

	/**
	 * @param data
	 * @return
	 */
	public static String xssConvert(String data) {

		data = data.replaceAll("&", "&amp;");
		data = data.replaceAll("#", "&#35;");
		data = data.replaceAll(";", "&#59;");
		data = data.replaceAll("\\\\", "&#92;");
		data = data.replaceAll("<", "&lt;");
		data = data.replaceAll(">", "&gt;");
		data = data.replaceAll("\\(", "&#40;");
		data = data.replaceAll("\\)", "&#41;");
		data = data.replaceAll("'", "&#39;");
		data = data.replaceAll("\"", "&quot;");
		data = data.replaceAll("[$]", "&#36;");
		data = data.replaceAll("[*]", "&#42;");
		data = data.replaceAll("[+]", "&#43;");
		data = data.replaceAll("[|]", "&#124;");
		data = data.replaceAll("\\.", "&#46;");
		data = data.replaceAll("\\?", "&#63;");
		data = data.replaceAll("\\[", "&#91;");
		data = data.replaceAll("\\]", "&#93;");
		data = data.replaceAll("\\^", "&#94;");
		data = data.replaceAll("\\{", "&#123;");
		data = data.replaceAll("\\}", "&#125;");
		data = data.replaceAll("!", "&#33;");
		data = data.replaceAll("%", "&#37;");
		data = data.replaceAll(",", "&#44;");
		data = data.replaceAll("-", "&#45;");
		data = data.replaceAll("/", "&#47;");
		data = data.replaceAll(":", "&#58;");
		data = data.replaceAll("=", "&#61;");
		data = data.replaceAll("@", "&#64;");
		data = data.replaceAll("_", "&#95;");
		data = data.replaceAll("`", "&#96;");
		data = data.replaceAll("~", "&#126;");

		return data;
	}

	/**
	 * @param data
	 * @return
	 */
	public static String xssRevert(String data) {

		data = data.replaceAll("&amp;", "&");
		data = data.replaceAll("&#35;", "#");
		data = data.replaceAll("&#59;", ";");
		data = data.replaceAll("&#92;", "\\\\");
		data = data.replaceAll("&lt;", "<");
		data = data.replaceAll("&gt;", ">");
		data = data.replaceAll("&#40;", "\\(");
		data = data.replaceAll("&#41;", "\\)");
		data = data.replaceAll("&#39;", "'");
		data = data.replaceAll("&quot;", "\"");
		data = data.replaceAll("&#36;", "[$]");
		data = data.replaceAll("&#42;", "[*]");
		data = data.replaceAll("&#43;", "[+]");
		data = data.replaceAll("&#124;", "[|]");
		data = data.replaceAll("&#46;", "\\.");
		data = data.replaceAll("&#63;", "\\?");
		data = data.replaceAll("&#91;", "\\[");
		data = data.replaceAll("&#93;", "\\]");
		data = data.replaceAll("&#94;", "\\^");
		data = data.replaceAll("&#123;", "\\{");
		data = data.replaceAll("&#125;", "\\}");
		data = data.replaceAll("&#33;", "!");
		data = data.replaceAll("&#37;", "%");
		data = data.replaceAll("&#44;", ",");
		data = data.replaceAll("&#45;", "-");
		data = data.replaceAll("&#47;", "/");
		data = data.replaceAll("&#58;", ":");
		data = data.replaceAll("&#61;", "=");
		data = data.replaceAll("&#64;", "@");
		data = data.replaceAll("&#95;", "_");
		data = data.replaceAll("&#96;", "`");
		data = data.replaceAll("&#126;", "~");

		return data;
	}

}
