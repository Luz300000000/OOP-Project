package util.adts;

/**
 * @author Inês Luz (fc57552), Marta Lourenço (fc58249)
 *
 * @param <E>
 * 
 * A list of elements E that allows new elements to be added only
 * at the end of the list and allows selection.
 * 
 */
public interface QListWithSelection<E> extends QList<E>{
	
	/**
	 * Selects the element at position i.
	 * 
	 * @param i the position of the element to select
	 * @requires 0 <= i < size()
	 */
	void select(int i);
	
	/**
	 * Adds an element at the end of the list and selects it.
	 * 
	 * @param e the element to be added and selected
	 */
	@Override
	void add(E e);
	
	/**
	 * Returns true if some element is selected.
	 * 
	 * @return true if some element is selected, false otherwise
	 */
	boolean someSelected();
	
	/**
	 * Gets the index of the selected element.
	 * 
	 * @return the index of the selected element
	 */
	int getIndexSelected();
	
	/**
	 * Selects the next element, if any. Otherwise, no element is selected.
	*/
	void next();
	
	/**
	 * Selects the previous element, if any. Otherwise, no element is selected.
	 */
	void previous();
	
	/**
	 * Removes the selected element from the list, if possible.
	 *
	 * @return true if the element was removed, false otherwise
	 */
	void remove();
	
	/**
	 * Gets the selected element.
	 * 
	 * @requires someSelected()
	 * @return the selected element
	 */
	E getSelected();
		
}
