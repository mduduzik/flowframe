package org.flowframe.kernel.common.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;

public class SortedArrayList<E> extends ArrayList<E> {

	public SortedArrayList() {
	}

	public SortedArrayList(Collection<? extends E> c) {
		addAll(c);
	}

	public SortedArrayList(Comparator<E> comparator) {
		_comparator = comparator;
	}

	@Override
	public boolean add(E e) {
		int index = 0;

		if (!isEmpty()) {
			index = _findInsertionPoint(e);
		}

		super.add(index, e);

		return true;
	}

	@Override
	public void add(int index, E e) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		boolean modified = false;

		Iterator<? extends E> itr = c.iterator();

		while (itr.hasNext()) {
			if (add(itr.next()) && !modified) {
				modified = true;
			}
		}

		return modified;
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public E set(int index, E e) {
		throw new UnsupportedOperationException();
	}

	protected int compare(E e1, E e2) {
		if (_comparator == null) {
			Comparable<E> comparator1 = (Comparable<E>)e1;

			return comparator1.compareTo(e2);
		}

		return _comparator.compare(e1, e2);
	}

	private int _findInsertionPoint(E e) {
		return _findInsertionPoint(e, 0, size() - 1);
	}

	private int _findInsertionPoint(E e, int low, int high) {
		while (low <= high) {
			int mid = (low + high) >>> 1;

			int delta = compare(get(mid), e);

			if (delta > 0) {
				high = mid - 1;
			}
			else {
				low = mid + 1;
			}
		}

		return low;
	}

	private Comparator<E> _comparator;

}