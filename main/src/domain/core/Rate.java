package domain.core;

/**
 * @author In�s Luz (fc57552), Marta Louren�o (fc58249)
 *
 * Rates are immutable objects that represent the ratings of a song,
 * which are given by the user (from 1-5 and 0 if the song hasn't been rated yet)
 *
 */
public class Rate implements Comparable<Rate> {
	
	private int rate;
	private static final int NO_RATE = 0;
	private static final int MIN_RATE = 1;
	private static final int MAX_RATE = 5;
	
	/**
	 * Creates a new Rate (default) with no rate associated
	 */
	public Rate() {
		rate = NO_RATE;
	}
	
	/**
	 * Creates a new Rate with a given value (rate)
	 * 
	 * @param rate the given value of rate
	 * @requires MIN_RATE <= rate <= MAX_RATE
	 */
	public Rate (int rate) {
		this.rate = rate;
	}

	/**
	 * Compares two rates
	 * 
	 * @param o the other rate
	 * @return 0 if the rates are equal, less than 0 if this rate is less than o
	 *         and greater than 0 if this rate is greater than o
	 */
	@Override
	public int compareTo(Rate o) {
		return Integer.compare(rate, o.getRate());
	}
	
	/**
	 * Checks if two rates are equal
	 * @param o the other rate
	 * @return true if the rates are equal, false if otherwise
	 */
	public boolean equalsRate(Rate o) {
		return compareTo(o) == 0;
	}
	
	/**
	 * Returns the value of this rate
	 * 
	 * @return the value of this rate
	 * @ensures 0 <= \result <= 5
	 */
	public int getRate() {
		return rate;
	}
}
