package org.m_tag.cbtutils.acceptor;

import java.io.File;

/**
 * Accepting results of find files.
 * @author mtag@m-tag.org
 */
public abstract class Acceptor {
	/**
	 * method for visit in visitor pattern.
	 * @param fileName found filename
	 * @return true:accepted, false:not accepted
	 */
	public abstract boolean accept(final File file);
}
