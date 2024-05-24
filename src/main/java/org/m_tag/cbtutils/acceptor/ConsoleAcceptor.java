package org.m_tag.cbtutils.acceptor;

import java.io.File;

/**
 * list acceptor to list file names to console like `ls -l'.
 * @author mtag@m-tag.org
 *
 */
public class ConsoleAcceptor extends Acceptor {
	/**
	 * default instance.
	 */
	public static final Acceptor CONSOLE_ACCEPTOR = new ConsoleAcceptor();
	
	public boolean accept(final File file) {
		System.out.println(file);
		return true;

	}
}