package org.m_tag.cbtutils.visitor;

import java.nio.file.Path;

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
	public boolean visit(final Path path, Acceptor acceptor) {
		final boolean result = check(path, acceptor);
		if (result) {
			return acceptor.accept(path);
		}
		return false;
	}
	
	/**
	 * method for visit in visitor pattern.
	 * @param fileName found filename
	 * @return true:accepted, false:not accepted
	 */
	public boolean found(final Path path) {
		return true;
	}
	

	protected abstract boolean check(final Path path, Acceptor acceptor);
}