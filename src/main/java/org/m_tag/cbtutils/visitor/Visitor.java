package org.m_tag.cbtutils.visitor;

import java.io.File;

import org.m_tag.cbtutils.acceptor.Acceptor;

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
	 * @param acceptor accepts targeted file name
	 * @return true:accepted , false :not accepted
	 */
	public boolean visit(final File fileName, Acceptor acceptor) {
		final boolean result = check(fileName, acceptor);
		if (result) {
			return acceptor.accept(fileName);
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
	

	protected abstract boolean check(final File fileName, Acceptor acceptor);
}