package com.code5.fw.data;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author zero
 *
 */
public class BoxByMap implements Map<String, String> {

	/**
	 * 
	 */
	private Box box = null;

	/**
	 * @param box
	 */
	public BoxByMap(Box box) {
		this.box = box;
	}

	@Override
	public String get(Object key) {

		String keyx = null;

		if (key instanceof Character) {
			keyx = Character.toString((Character) key);
		}

		if (key instanceof String) {
			keyx = (String) key;
		}

		if (keyx == null) {
			return "";
		}

		return box.s(keyx);
	}

	@Override
	public int size() {
		return box.getKeys().length;
	}

	@Override
	public boolean isEmpty() {
		return box.isNull();
	}

	@Override
	public boolean containsKey(Object key) {

		if (!(key instanceof String)) {
			return false;
		}

		if (box.get((String) key) == null) {
			return true;
		}
		return false;
	}

	@Override
	public boolean containsValue(Object value) {

		String[] keys = box.getKeys();
		for (int i = 0; i < keys.length; i++) {

			Object x = box.get(keys[i]);
			if (x.equals(value)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Set<String> keySet() {
		String[] keys = box.getKeys();
		Set<String> set = new HashSet<>(Arrays.asList(keys));
		return set;
	}

	private Set<Entry<String, String>> set = null;

	@Override
	public Set<Entry<String, String>> entrySet() {

		if (set != null) {
			return set;
		}

		Set<Entry<String, String>> set = new HashSet<Entry<String, String>>();

		String[] keys = box.getKeys();
		for (int i = 0; i < keys.length; i++) {

			String key = keys[i];
			String val = get(keys[i]);

			Map.Entry<String, String> entry = new AbstractMap.SimpleEntry<String, String>(key, val);
			set.add(entry);
		}

		this.set = set;
		return this.set;

	}

	@Override
	public String put(String key, String value) {
		throw new RuntimeException();
	}

	@Override
	public String remove(Object key) {
		throw new RuntimeException();
	}

	@Override
	public void putAll(Map<? extends String, ? extends String> m) {
		throw new RuntimeException();

	}

	@Override
	public void clear() {
		throw new RuntimeException();
	}

	@Override
	public Collection<String> values() {
		throw new RuntimeException();
	}

}
