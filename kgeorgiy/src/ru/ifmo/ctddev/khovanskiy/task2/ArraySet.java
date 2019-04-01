package ru.ifmo.ctddev.khovanskiy.task2;

import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.NavigableSet;
import java.util.Set;
import java.util.SortedSet;

public class ArraySet<E> extends AbstractSet<E> implements
		NavigableSet<E> {
	class MyIterator implements Iterator<E> {
		int current;
		int last;
		boolean rev;
		private ArraySet<E> s;

		private MyIterator(ArraySet<E> s) {
			this(s, s.reverse);
		}
		
		private MyIterator(ArraySet<E> s, boolean rev) {
			this.s = s;
			this.rev = rev;
			if (rev)
			{
				current = s.end;
				last = s.start;
			}
			else
			{
				current = s.start;
				last = s.end;
			}
		}

		@Override
		public boolean hasNext() {
			if (rev)
			{
				return current + 1 != last;
			}
			else
			{
				return current != last + 1;
			}
		}

		@Override
		public E next() {
			E t = s.array[current];
			if (rev)
			{
				--current;
			}
			else
			{
				++current;
			}
			return t;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}

	}

	private E[] array;
	private int start;
	private int end;
	boolean reverse = false;

	public ArraySet(Collection<E> list) {
		Set<E> set = new HashSet<E>(list);
		array = (E[]) set.toArray();
		Arrays.sort(array);
		start = 0;
		end = array.length - 1;
	}
	
	private ArraySet(E[] array, int start, int end, boolean reverse)
	{
		this.array = array;
		this.start = start;
		this.end = end;
		this.reverse = reverse;
	}

	@Override
	public Comparator<? super E> comparator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public E first() {
		return array[0];
	}

	@Override
	public E last() {
		return array[array.length - 1];
	}

	@Override
	public boolean add(E arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addAll(Collection<? extends E> arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void clear() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean contains(Object arg0) {
		return ceilingStraight((E)arg0) != -1;
	}

	@Override
	public boolean containsAll(Collection<?> arg0) {
		Iterator<?> i = arg0.iterator();
		while (i.hasNext())
		{
			if (ceilingStraight((E)i.next()) == -1)
			{
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean isEmpty() {
		return size() == 0;
	}

	@Override
	public boolean remove(Object arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean removeAll(Collection<?> arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean retainAll(Collection<?> arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int size() {
		return end - start + 1;
	}

	@Override
	public Object[] toArray() {
		Object[] temp = new Object[size()];
		for (int i = start; i <= end; ++i) {
			temp[i - start] = array[i];
		}
		return temp;
	}

	@Override
	public <T> T[] toArray(T[] arg0) {
		return (T[]) toArray();
	}

	@Override
	public Iterator<E> descendingIterator() {
		return new MyIterator(this, !reverse);
	}

	@Override
	public NavigableSet<E> descendingSet() {
		return new ArraySet(array, start, end, !reverse);
	}
	
	public int ceilingStraight(E key) {
		int p = Arrays.binarySearch(array, start, end + 1, key);
		if (p < 0) {
			p = -p - 1;
			if (p >= size()) {
				return -1;
			}
			return p;
		} else {
			return p;
		}
	}
	
	public int floorStraight(E key) {
		int p = Arrays.binarySearch(array, start, end + 1, key);
		if (p < 0) {
			p = -p - 2;
			if (p < 0) {
				return -1;
			}
			return p;
		} else {
			return p;
		}
	}
	
	public int higherStraight(E key) {
		int p = Arrays.binarySearch(array, start, end + 1, key);
		if (p < 0) {
			p = -p - 1;
			if (p >= size()) {
				return -1;
			}
			return p;
		} else {
			++p;
			if (p >= size()) {
				return -1;
			}
			return p;
		}
	}
	
	public int lowerStraight(E key) {
		int p = Arrays.binarySearch(array, start, end + 1, key);
		if (p < 0) {
			p = -p - 2;
			if (p < 0) {
				return -1;
			}
			return p;
		} else {
			--p;
			if (p < 0)
			{
				return -1;
			}
			return p;
		}
	}
	
	@Override
	public E higher(E key) {
		int p;
		if (reverse)
		{
			p = lowerStraight(key);
		}
		else
		{
			p = higherStraight(key);
		}
		if (p == -1)
		{
			return null;
		}
		return array[p];
	}
	
	@Override
	public E ceiling(E key) {
		int p;
		if (reverse)
		{
			p =floorStraight(key);
		}
		else
		{
			p = ceilingStraight(key);
		}
		if (p == -1)
		{
			return null;
		}
		return array[p];
	}
	
	@Override
	public E lower(E key) {
		int p;
		if (reverse)
		{
			p = higherStraight(key);
		}
		else
		{
			p = lowerStraight(key);
		}
		if (p == -1)
		{
			return null;
		}
		return array[p];
	}
	
	@Override
	public E floor(E key) {
		int p;
		if (reverse)
		{
			p = ceilingStraight(key);
		}
		else
		{
			p = floorStraight(key);
		}
		if (p == -1)
		{
			return null;
		}
		return array[p];
	}

	@Override
	public Iterator<E> iterator() {
		return new MyIterator(this);
	}

	@Override
	public E pollFirst() {
		throw new UnsupportedOperationException();
	}

	@Override
	public E pollLast() {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public SortedSet<E> headSet(E toElement) {
		return headSetStraight(toElement, false);
	}
	
	private NavigableSet<E> headSetStraight(E key, boolean inclusive) {
		int p;
		if (inclusive)
		{
			p = floorStraight(key);
		}
		else
		{
			p = lowerStraight(key);
		}
		return new ArraySet<E>(array, start, p, reverse);
	}

	@Override
	public NavigableSet<E> headSet(E toElement, boolean inclusive) {
		return headSetStraight(toElement, inclusive);
	}

	@Override
	public SortedSet<E> subSet(E fromElement, E toElement) {
		return subSetStraight(fromElement, true, toElement, false);
	}
	
	private NavigableSet<E> subSetStraight(E fromElement, boolean fromInclusive, E toElement, boolean toInclusive) {
		int p1;
		if (fromInclusive)
		{
			p1 = ceilingStraight(fromElement);
		}
		else
		{
			p1 = higherStraight(fromElement);
		}
		int p2;
		if (toInclusive)
		{
			p2 = floorStraight(toElement);
		}
		else
		{
			p2 = lowerStraight(toElement);
		}
		return new ArraySet<E>(array, p1, p2, reverse);
	}

	@Override
	public NavigableSet<E> subSet(E fromElement, boolean fromInclusive, E toElement, boolean toInclusive) {
		return subSetStraight(fromElement, fromInclusive, toElement, toInclusive);
	}

	@Override
	public SortedSet<E> tailSet(E fromElement) {
		return tailSetStraight(fromElement, false);
	}
	
	private NavigableSet<E> tailSetStraight(E key, boolean inclusive) {
		int p;
		if (inclusive)
		{
			p = ceilingStraight(key);
		}
		else
		{
			p = higherStraight(key);
		}
		return new ArraySet<E>(array, p, end, reverse);
	}

	@Override
	public NavigableSet<E> tailSet(E fromElement, boolean inclusive) {
		return tailSetStraight(fromElement, inclusive);
	}

	@Override
	public String toString() {
		if (size() == 0) {
			return "[]";
		}
		StringBuilder sb = new StringBuilder();

		sb.append("[");
		for (int i = start; i <= end - 1; ++i) {
			sb.append(array[i] + ", ");
		}
		sb.append(array[end]);
		sb.append("]");
		return sb.toString();
	}

	@Override
	public int hashCode() {
		int temp = 0;
		Iterator i = iterator();
		while (i.hasNext()) {
			temp += i.next().hashCode();
		}
		return temp;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof ArraySet) {
			ArraySet other = (ArraySet) o;

			if (this.hashCode() != other.hashCode()
					|| this.size() != other.size()) {
				return false;
			}

			/*
			 * int ax = start; int ay = end; int bx = other.start; int by =
			 * other.end;
			 * 
			 * while (ax <= ay) { if
			 * (!array.get(ax).equals(other.array.get(bx))) { return false; }
			 * ++ax; ++bx; }
			 */

			Iterator a = this.iterator();
			Iterator b = other.iterator();

			while (a.hasNext()) {
				if (!a.next().equals(b.next())) {
					return false;
				}
			}

			return true;
		}
		return false;
	}
}
