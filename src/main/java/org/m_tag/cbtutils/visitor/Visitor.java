package org.m_tag.cbtutils.visitor;

import java.io.File;

/**
 * File visitor.
 */
public abstract class Visitor {
	/**
	 * default value for case sensitivity.
	 */
	private static boolean caseSensitive = false;

	public static boolean isCaseSensitive() {
		return caseSensitive;
	}

	public static void setCaseSensitive(boolean caseSensitive) {
		Visitor.caseSensitive = caseSensitive;
	}

	/**
	 * visit in visitor pattern.
	 * @param fileName fileName
	 * @return true:accepted , false :not accepted
	 */
	public boolean visit(final File fileName) {
		final boolean result = check(fileName);
		if (result) {
			return found(fileName);
		}
		return false;
	}
	
	/**
	 * method for visit in visitor pattern.
	 * @param fileName found filename
	 * @return true:accepted, false:not accepted
	 */
	public boolean found(final File fileName) {
		return true;
	}
	

	protected abstract boolean check(final File fileName);
}