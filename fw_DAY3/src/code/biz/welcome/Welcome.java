package code.biz.welcome;

import com.code5.fw.web.Box;

/**
 * @author seuk
 *
 */
public class Welcome {

	/**
	 * @param box
	 * @return
	 */
	public String service() throws Exception {

		// [1]
		Box box = Box.getThread();

		String name = box.s("name");
		String welcome = "welcome " + name;
		box.put("welcome", welcome);

		return "/WEB-INF/classes/com/biz/welcome/welcome.jsp";
	}

}
