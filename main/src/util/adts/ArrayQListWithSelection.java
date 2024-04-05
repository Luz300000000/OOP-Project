package util.adts;

import java.util.ArrayList;
import java.util.Iterator;


/**
 * @author In�s Luz (fc57552), Marta Louren�o (fc58249)
 *
 * @param <E>
 * 
 * TODO
 * 
 */
public final class ArrayQListWithSelection<E> extends AbsQListWithSelection<E> {
	
	private ArrayList<E> list;
	
	/**
	 * Creates the list
	 */
	public ArrayQListWithSelection() {
		super();
		list = new ArrayList<>();
	}
	
	/**
	 * Adds an element at the end of the list and selects it
	 * 
	 * @param e the element to be added and selected
	 * @ensures size() == \old(size()) + 1
	 */
	@Override
	public void add(E e) {
		list.add(e);
	}

	/**
	 * Removes the selected element from the list, if possible
	 *
	 * @return true if the element was removed, false if otherwise
	 * @ensures someSelected() ==> size() == \old(size()) - 1
	 * @ensures !someSelected() ==> size() == \old(size())
	 */
	@Override
	public void remove() {
		if(someSelected())
			list.remove(getIndexSelected());
	}

	/**
	 * Returns the number of elements in the list 
	 * 
	 * @return the number of elements in the list
	 */
	@Override
	public int size() {
		return list.size();
	}

	/**
	 * Returns the element at position i
	 * 
	 * @param i the position of the element to return
	 * @requires 0 <= i < size()
	 * @return the element at position i
	 */
	@Override
	public E get(int i) {
		return list.get(i);
	}

	/**
	 * Returns an iterator over the elements in the list
	 * 
	 * @return an iterator over the elements in this list in proper sequence
	 */
	@Override
	public Iterator<E> iterator() {
		return list.iterator();
	}

	/**
	 * Gets the selected element
	 * 
	 * @requires someSelected()
	 * @return the selected element
	 */
	@Override
	public E getSelected() {
		return list.get(super.getIndexSelected());
	}
	
}