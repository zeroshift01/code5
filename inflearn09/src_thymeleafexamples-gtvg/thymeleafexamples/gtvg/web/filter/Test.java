package thymeleafexamples.gtvg.web.filter;

import java.io.Writer;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.WebApplicationTemplateResolver;
import org.thymeleaf.web.IWebApplication;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.IWebRequest;
import org.thymeleaf.web.servlet.JavaxServletWebApplication;

import thymeleafexamples.gtvg.web.controller.IGTVGController;
import thymeleafexamples.gtvg.web.mapping.ControllerMappings;

public class Test {

	/**
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public static void process(HttpServletRequest request, HttpServletResponse response) throws Exception {

		ServletContext servletContext = request.getServletContext();

		JavaxServletWebApplication application = JavaxServletWebApplication.buildApplication(servletContext);
		ITemplateEngine templateEngine = buildTemplateEngine(application);

		IWebExchange webExchange = application.buildExchange(request, response);
		IWebRequest webRequest = webExchange.getRequest();

		/*
		 * Query controller/URL mapping and obtain the controller that will process the
		 * request. If no controller is available, return false and let other
		 * filters/servlets process the request.
		 */
		IGTVGController controller = ControllerMappings.resolveControllerForRequest(webRequest);

		/*
		 * Write the response headers
		 */
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);

		/*
		 * Obtain the response writer
		 */
		Writer writer = response.getWriter();

		/*JsonMerge
		 * Execute the controller and process view template, writing the results to the
		 * response writer.
		 */
		controller.process(webExchange, templateEngine, writer);

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
