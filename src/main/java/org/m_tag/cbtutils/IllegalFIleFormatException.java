package org.m_tag.cbtutils;

/**
 * Illegal File Format.
 */
public class IllegalFIleFormatException extends Exception {
	/**
	 * UUID.
	 */
	private static final long serialVersionUID = 5916015499113715981L;

	/**
	 * constructor.
	 * @param message message
	 */
	public IllegalFIleFormatException(final String message) {
		super(message);
	}
}
