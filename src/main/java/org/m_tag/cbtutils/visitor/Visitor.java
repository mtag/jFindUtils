package org.m_tag.cbtutils.visitor;

import java.io.File;

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
	boolean visit(final File fileName);
}