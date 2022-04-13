package com.biz;

import java.io.Writer;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templateresolver.WebApplicationTemplateResolver;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JavaxServletWebApplication;

import com.code5.fw.data.Box;
import com.code5.fw.data.BoxHttp;
import com.code5.fw.data.Table;
import com.code5.fw.data.TableByList;
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

		// GTVGFilter

		Box box = new BoxHttp(request);
		box.put("A", "aaa");
		box.put("B", "1");
		box.put("C", "true");

		String[] cols = new String[] { "N", "A", "B", "C" };
		String[] data1 = new String[] { "1", "aaa1", "1", "true" };
		String[] data2 = new String[] { "2", "aaa2", "2", "false" };
		String[] data3 = new String[] { "3", "aaa3", "3", "true" };

		Table table = new TableRecodeBase(cols);
		table.addRecode(data1);
		table.addRecode(data2);
		table.addRecode(data3);

		box.put("table", table);

		TableByList list = new TableByList(table);
		box.put("list", list);

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

		ProductService productService = new ProductService();
		List<Product> allProducts = productService.findAll();

		ctx.setVariable("prods", allProducts);
		templateEngine.process("/WEB-INF/templates/product/list.html", ctx, writer);

	}

}
