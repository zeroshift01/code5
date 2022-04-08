<%@page import="org.thymeleaf.ITemplateEngine"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%
	try {


	/*
	 * Query controller/URL mapping and obtain the controller
	 * that will process the request. If no controller is available,
	 * return false and let other filters/servlets process the request.
	 */
	IGTVGController controller = this.application.resolveControllerForRequest(request);
	if (controller == null) {
		return false;
	}

	/*
	 * Obtain the TemplateEngine instance.
	 */
	ITemplateEngine templateEngine = this.application.getTemplateEngine();

	/*
	 * Write the response headers
	 */
	response.setContentType("text/html;charset=UTF-8");
	response.setHeader("Pragma", "no-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setDateHeader("Expires", 0);

	/*
	 * Execute the controller and process view template,
	 * writing the results to the response writer. 
	 */
	controller.process(request, response, this.servletContext, templateEngine);

	return true;

} catch (Exception e) {
	try {
		response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	} catch (final IOException ignored) {
		// Just ignore this
	}
	throw new ServletException(e);
}
%>
