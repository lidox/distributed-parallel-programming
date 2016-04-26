
public interface FrontierList {

	/**
	 * Push back a value/Add a value to the list.
	 * @param p_val Value to add.
	 */
	void pushBack(final long p_val);
	
	/**
	 * Check if a certain value is available/set in the frontier list.
	 * @param p_val Value to check.
	 * @return True if this value is available in the list.
	 */
	boolean contains(final long p_val);
	
	/**
	 * Get the number of elements in the list.
	 * @return
	 */
	long size();
	
	/**
	 * Check if the list contains no elements.
	 * @return True if empty.
	 */
	boolean isEmpty();
	
	/**
	 * Reset the list and clear its contents.
	 */
	void reset();
	
	/**
	 * Remove an element from the list.
	 * @return Element removed or -1 if empty.
	 */
	long popFront();
}
