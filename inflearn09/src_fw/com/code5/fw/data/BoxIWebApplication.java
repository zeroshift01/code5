package com.code5.fw.data;

import java.io.InputStream;
import java.util.Map;
import java.util.Set;

import org.thymeleaf.web.IWebApplication;

/**
 * @author zero
 *
 */
public class BoxIWebApplication implements IWebApplication {

	private Box box = null;

	public BoxIWebApplication(Box box) {
		this.box = box;
	}

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
		// TODO Auto-generated method stub

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

}
