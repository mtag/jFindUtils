package org.m_tag.cbtutils.visitor;

import java.nio.file.Path;
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
	 * method for visit in visitor pattern.
	 * @param fileName found filename
	 * @return true:accepted, false:not accepted
	 */
	public boolean found(final Path path) {
		return true;
	}
	

	public abstract boolean check(final Path path);
}