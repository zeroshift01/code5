package org.thymeleaf.web.servlet;

import java.io.InputStream;
import java.util.Map;
import java.util.Set;

import org.thymeleaf.web.IWebApplication;

import com.code5.fw.data.Box;

/**
 * @author zero
 *
 */
public class BoxWebExchange implements IWebApplication {

	@Override
	public boolean containsAttribute(String name) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getAttributeCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Set<String> getAllAttributeNames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> getAttributeMap() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getAttributeValue(String name) {
		return box.get(name);
	}

	@Override
	public void setAttributeValue(String name, Object value) {
		box.put(name, value);

	}

	@Override
	public void removeAttribute(String name) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean resourceExists(String path) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public InputStream getResourceAsStream(String path) {
		// TODO Auto-generated method stub
		return null;
	}

	// private JavaxServletWebRequest webRequest;
	private Box box;

	/**
	 * @param box
	 * @param request
	 */
	public BoxWebExchange(Box box) {
		this.box = box;
		// this.webRequest = new JavaxServletWebRequest(request);

	}

}
