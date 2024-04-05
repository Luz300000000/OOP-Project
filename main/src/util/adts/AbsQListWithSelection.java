package util.adts;

/**
 * @author In�s Luz (fc57552), Marta Louren�o (fc58249)
 *
 * @param <E>
 * 
 * An abstract implementation of QListWithSelection to be used by a concrete implementation.
 * 
 */
public abstract class AbsQListWithSelection<E> implements QListWithSelection<E>{
	
	private int selected;
	
	/**
	 * Creates the list
	 * @ensures !someSelected()
	 */
	protected AbsQListWithSelection() {
		selected = -1;	// no index selected
	}
	
	/**
	 * Selects the element at position i
	 * 
	 * @param i the position of the element to select
	 * @requires 0 <= i < size()
	 */
	@Override
	public void select(int i) {
		selected = i;
	}
	
	/**
	 * Returns true if some element is selected.
	 * 
	 * @return true if some element is selected, false otherwise
	 */
	@Override
	public boolean someSelected() {
		return selected != -1;
	}
	
	/**
	 * Gets the index of the selected element.
	 * 
	 * @return the index of the selected element
	 * @ensures 0 <= \return < size()
	 */
	@Override
	public int getIndexSelected() {
		return selected;
	}
	
	/**
	 * Selects the previous element if it applies, otherwise, no element is selected
	 */
	@Override
	public void next() {
		if (selected < size() - 1)
			selected += 1;
		else
			selected = -1;
	}
	
	/**
	 * Selects the previous element if it applies, otherwise, no element is selected
	 */
	@Override
	public void previous() {
		if (selected > 0)
			selected -= 1;
		else
			selected = -1;
	}
	
}
