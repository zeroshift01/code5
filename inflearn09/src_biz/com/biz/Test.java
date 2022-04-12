package com.biz;

import java.io.Writer;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.WebApplicationTemplateResolver;
import org.thymeleaf.web.IWebApplication;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JavaxServletWebApplication;

import com.code5.fw.data.Box;
import com.code5.fw.data.BoxHttp;
import com.code5.fw.data.Table;
import com.code5.fw.data.TableRecodeBase;

import thymeleafexamples.gtvg.business.entities.Product;
import thymeleafexamples.gtvg.business.services.ProductService;

public class Test {

	/**
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public static void process(HttpServletRequest request, HttpServletResponse response) throws Exception {

		Box box = new BoxHttp(request);
		box.put("A", "A-B");
		box.put("B", "A-C");
		String[] cols = new String[] { "A", "B" };
		Table table = new TableRecodeBase(cols);
		table.addRecode(cols);
		table.addRecode(cols);

		box.put("table", table);

		ServletContext servletContext = request.getServletContext();

		JavaxServletWebApplication application = JavaxServletWebApplication.buildApplication(servletContext);
		ITemplateEngine templateEngine = buildTemplateEngine(application);

		IWebExchange webExchange = application.buildExchange(request, response);
		WebContext ctx = new WebContext(webExchange, webExchange.getLocale());

		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);

		Writer writer = response.getWriter();

		ProductService productService = new ProductService();
		List<Product> allProducts = productService.findAll();

		ctx.setVariable("prods", allProducts);
		templateEngine.process("product/list", ctx, writer);

	}

	private static ITemplateEngine buildTemplateEngine(final IWebApplication application) {

		final WebApplicationTemplateResolver templateResolver = new WebApplicationTemplateResolver(application);

		// HTML is the default mode, but we will set it anyway for better understanding
		// of code
		templateResolver.setTemplateMode(TemplateMode.HTML);
		// This will convert "home" to "/WEB-INF/templates/home.html"
		templateResolver.setPrefix("/WEB-INF/templates/");
		templateResolver.setSuffix(".html");
		// Set template cache TTL to 1 hour. If not set, entries would live in cache
		// until expelled by LRU
		templateResolver.setCacheTTLMs(Long.valueOf(3600000L));

		// Cache is set to true by default. Set to false if you want templates to
		// be automatically updated when modified.
		templateResolver.setCacheable(true);

		final TemplateEngine templateEngine = new TemplateEngine();
		templateEngine.setTemplateResolver(templateResolver);

		return templateEngine;

	}

}
