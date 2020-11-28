package Nvidia;

public enum Stock {

	ADD_TO_CART("Add to cart"), OFFERS("Offers");

	// constructor
	private String internalMessage;

	/**
	 * @param problem Receive a problem
	 */
	private Stock(String msg) {
		this.internalMessage = msg;
	}

	// getter

	/**
	 * @return This function return a message
	 */
	public String getMessage() {
		return internalMessage;
	}

}
