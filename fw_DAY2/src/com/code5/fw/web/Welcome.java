package com.code5.fw.web;

/**
 * @author seuk
 *
 */
public class Welcome {
	
	いさけさ

	/**
	 * @param box
	 * @return
	 */
	public String service() throws Exception {
	
		Box box = Box.getThread();

		String name = (String) box.get("name");
		String welcome = "welcome " + name;
		box.put("welcome", welcome);

		return "/WEB-INF/classes/com/code5/fw/web/welcome.jsp";
	}

	/**
	 * @param xxx
	 * @throws Exception
	 * 
	 *                   [1]
	 */
	public static void main(String[] xxx) throws Exception {

		// [1]
		Box box = new BoxLocal();
		box.put("name", "code5");

		// [2]
		//Welcome welcome = new Welcome();

	}

}
