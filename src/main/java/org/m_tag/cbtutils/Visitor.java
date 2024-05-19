package org.m_tag.cbtutils;

/**
 * File visitor.
 */
public interface Visitor {
	/**
	 * default value for case sensitivity.
	 */
	boolean CASE_SENSITIVE_DEFAULT = false;

	/**
	 * visit in visitor pattern.
	 * @param fileName fileName
	 * @return true:accepted , false :not accepted
	 */
	boolean visit(final String fileName);
}