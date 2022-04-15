package com.biz;

import java.io.Writer;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templateresolver.WebApplicationTemplateResolver;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JavaxServletWebApplication;

public class Test {

	/**
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public static void process(HttpServletRequest request, HttpServletResponse response) throws Exception {

		request.setAttribute("a", "aaaa");
		request.setAttribute("b", "bbbb");

		ServletContext servletContext = request.getServletContext();
		JavaxServletWebApplication application = JavaxServletWebApplication.buildApplication(servletContext);

		WebApplicationTemplateResolver templateResolver = new WebApplicationTemplateResolver(application);
		TemplateEngine templateEngine = new TemplateEngine();
		templateEngine.setTemplateResolver(templateResolver);

		IWebExchange webExchange = application.buildExchange(request, response);
		WebContext ctx = new WebContext(webExchange, webExchange.getLocale());

		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);

		Writer writer = response.getWriter();
		templateEngine.process("/test.html", ctx, writer);

	}

}
