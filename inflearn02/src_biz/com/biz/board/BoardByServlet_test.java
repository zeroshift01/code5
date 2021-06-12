package com.biz.board;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;

/**
 * @author zero
 *
 */
public class BoardByServlet_test {

	@Test
	public void testCallList1() {

		HttpServletRequest req1 = null;

		BoardByServlet b = new BoardByServlet();
		b.callList(req1);

	}

	@Autowired
	HttpServletRequest req2 = null;

	@Test
	public void testCallList2() {

		req2.setAttribute("findStr", "A");

		BoardByServlet b = new BoardByServlet();
		b.callList(req2);

	}

}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface Autowired {

}
