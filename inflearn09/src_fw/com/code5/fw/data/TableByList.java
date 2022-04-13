package com.code5.fw.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

/**
 * @author zero
 *
 */
public class TableByList implements List<Map<String, String>> {

	/**
	 * @return
	 */
	public boolean isLimitRecode() {
		return table.isLimitRecode();
	}

	/**
	 * @return
	 */
	public Map<String, String> getLimitBox() {
		return new BoxByMap(table.getLimitBox());
	}

	/**
	 * 
	 */
	private Table table = null;

	/**
	 * @param table
	 */
	public TableByList(Table table) {
		this.table = table;
	}

	@Override
	public int size() {
		return table.size();
	}

	@Override
	public boolean isEmpty() {
		if (size() == 0) {
			return true;
		}
		return false;
	}

	@Override
	public Map<String, String> get(int index) {
		Box box = table.getBox(index);
		Map<String, String> map = new BoxByMap(box);
		return map;
	}

	Iterator<Map<String, String>> iterator = null;

	@Override
	public Iterator<Map<String, String>> iterator() {

		if (this.iterator != null) {
			return this.iterator;
		}

		ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();
		for (int i = 0; i < table.size(); i++) {
			Box thisBox = table.getBox(i);
			BoxByMap thisBoxByMap = new BoxByMap(thisBox);
			list.add(thisBoxByMap);
		}

		this.iterator = list.iterator();

		return this.iterator;
	}

	@Override
	public boolean contains(Object o) {
		throw new RuntimeException();
	}

	@Override
	public Object[] toArray() {
		throw new RuntimeException();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		throw new RuntimeException();
	}

	@Override
	public boolean add(Map<String, String> e) {
		throw new RuntimeException();
	}

	@Override
	public boolean remove(Object o) {
		throw new RuntimeException();
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		throw new RuntimeException();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean addAll(Collection c) {
		throw new RuntimeException();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean addAll(int index, Collection c) {
		throw new RuntimeException();
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		throw new RuntimeException();
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		throw new RuntimeException();
	}

	@Override
	public void clear() {
		throw new RuntimeException();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Map set(int index, Map element) {
		throw new RuntimeException();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void add(int index, Map element) {
		throw new RuntimeException();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Map remove(int index) {
		throw new RuntimeException();
	}

	@Override
	public int indexOf(Object o) {
		throw new RuntimeException();
	}

	@Override
	public int lastIndexOf(Object o) {
		throw new RuntimeException();
	}

	@Override
	public ListIterator<Map<String, String>> listIterator() {
		throw new RuntimeException();
	}

	@Override
	public ListIterator<Map<String, String>> listIterator(int index) {
		throw new RuntimeException();
	}

	@Override
	public List<Map<String, String>> subList(int fromIndex, int toIndex) {
		throw new RuntimeException();
	}

}
