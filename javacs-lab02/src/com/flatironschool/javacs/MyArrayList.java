/**
 * 
 */
package com.flatironschool.javacs;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * @author downey
 * @param <E>: Type of the elements in the List.
 * Edited by Lindsey Gillaspie
 */
public class MyArrayList<E> implements List<E> {
	int size;                    // keeps track of the number of elements
	private E[] array;           // stores the elements
	
	/**
	 * 
	 */
	public MyArrayList() {
		// You can't instantiate an array of T[], but you can instantiate an
		// array of Object and then typecast it.  Details at
		// http://www.ibm.com/developerworks/java/library/j-jtp01255/index.html
		array = (E[]) new Object[10];
		size = 0;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// run a few simple tests
		MyArrayList<Integer> mal = new MyArrayList<Integer>();
		mal.add(1);
		mal.add(2);
		mal.add(3);
		System.out.println(Arrays.toString(mal.toArray()) + " size = " + mal.size);
		
		mal.remove(new Integer(2));
		System.out.println(Arrays.toString(mal.toArray()) + " size = " + mal.size);
	}

	@Override
	public boolean add(E element) {
		if (size >= array.length) {
			// make a bigger array and copy over the elements
			E[] bigger = (E[]) new Object[array.length * 2];
			System.arraycopy(array, 0, bigger, 0, array.length);
			array = bigger;
		} 
		array[size] = element;
		size++;
		return true;
	}

	@Override
	public void add(int index, E element) {
		if (index < 0 || index > size) {
			throw new IndexOutOfBoundsException();
		}
		// TODO3: fill in the rest of this method
		//Shifts the element currently at that position (if any) and any
		//subsequent elements to the right (+1 to their indices)
		if(array[index] == null)
		{
			array[index] = element;
			size++;
		}
		else
		{
			size++;
			if (size >= array.length){
			  	// make a bigger array and copy over the elements
			  	E[] bigger = (E[]) new Object[array.length+1];
				System.arraycopy(array, 0, bigger, 0, array.length);
			 	array = bigger;
			}
			for(int i = index; i < size; i++)
			{
				array[i++] = array[i];
			}
			E old = set(index, element);
			//which is the same thing as: array[index] = element;
		}
	}

	@Override
	public boolean addAll(Collection<? extends E> collection) {
		boolean flag = true;
		for (E element: collection) {
			flag &= add(element);
		}
		return flag;
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> collection) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void clear() {
		// note: this version does not actually null out the references
		// in the array, so it might delay garbage collection.
		size = 0;
	}

	@Override
	public boolean contains(Object obj) {
		return indexOf(obj) != -1;
	}

	@Override
	public boolean containsAll(Collection<?> collection) {
		for (Object element: collection) {
			if (!contains(element)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public E get(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		return array[index];
	}

	@Override
	public int indexOf(Object target) {
		// TODO2: fill in this method
		if(target == null)
		{
			for(int i = 0; i < size; i++)
			{
				if(array[i]==null)
					return i;
			}
				
		}
		else {
			for(int i = 0; i < size; i++)
                        {									       		if(target.equals(array[i]))
				return i;
			}
		}
		// returns the index of 1st occurence of the specified element
		// or -1 if this list does not contain the element
		return -1;
	}

	/** Checks whether an element of the array is the target.
	 * 
	 * Handles the special case that the target is null.
	 * 
	 * @param target
	 * @param object
	 */
	private boolean equals(Object target, Object element) {
		if (target == null) {
			return element == null;
		} else {
			return target.equals(element);
		}
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public Iterator<E> iterator() {
		// make a copy of the array
		E[] copy = Arrays.copyOf(array, size);
		// make a list and return an iterator
		return Arrays.asList(copy).iterator();
	}

	@Override
	public int lastIndexOf(Object target) {
		// see notes on indexOf
		for (int i = size-1; i>=0; i--) {
			if (equals(target, array[i])) {
				return i;
			}
		}
		return -1;
	}

	@Override
	public ListIterator<E> listIterator() {
		// make a copy of the array
		E[] copy = Arrays.copyOf(array, size);
		// make a list and return an iterator
		return Arrays.asList(copy).listIterator();
	}

	@Override
	public ListIterator<E> listIterator(int index) {
		// make a copy of the array
		E[] copy = Arrays.copyOf(array, size);
		// make a list and return an iterator
		return Arrays.asList(copy).listIterator(index);
	}

	@Override
	public boolean remove(Object obj) {
		int index = indexOf(obj);
		if (index == -1) {
			return false;
		}
		remove(index);
		return true;
	}

	@Override
	public E remove(int index) {
		// TODO4: fill in this method.
		//Removes the element at the specified position in the list
		//Shifts any subsequent elements to the left (-1 from indices)
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		
		E removed = array[index];
		array[index] = null;
		int tmp = index;
		while(tmp < size)
		{
			array[tmp] = array[tmp+1];
			array[tmp+1] = null;
			tmp++;
		}
		size--;
		
		//Returns the element that was removed from the list
		return removed;
	}

	@Override
	public boolean removeAll(Collection<?> collection) {
		boolean flag = true;
		for (Object obj: collection) {
			flag &= remove(obj);
		}
		return flag;
	}

	@Override
	public boolean retainAll(Collection<?> collection) {
		throw new UnsupportedOperationException();
	}

	@Override
	public E set(int index, E element) {
		// TODO1: I filled in this method
		//If the index is out of range, throw an exception
		if(index < 0 || index >= size())
		{
			throw new IndexOutOfBoundsException();
		}
		E savedElement = array[index];
		array[index] = element;
		//return the element previously at the specified position
		return savedElement;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public List<E> subList(int fromIndex, int toIndex) {
		if (fromIndex < 0 || toIndex >= size || fromIndex > toIndex) {
			throw new IndexOutOfBoundsException();
		}
		E[] copy = Arrays.copyOfRange(array, fromIndex, toIndex);
		return Arrays.asList(copy);
	}

	@Override
	public Object[] toArray() {
		return Arrays.copyOf(array, size);
	}

	@Override
	public <T> T[] toArray(T[] array) {
		throw new UnsupportedOperationException();		
	}
}
