package org.m_tag.cbtutils.acceptor;

import java.nio.file.Path;

/**
 * Accepting results of find files.
 * @author mtag@m-tag.org
 */
public abstract class Acceptor {
	/**
	 * method for visit in visitor pattern.
	 * @param path path of found file
	 * @return true:accepted, false:not accepted
	 */
	public abstract boolean accept(final Path path);
}
