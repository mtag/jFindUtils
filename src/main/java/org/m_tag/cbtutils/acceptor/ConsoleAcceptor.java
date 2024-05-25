package org.m_tag.cbtutils.acceptor;

import java.nio.file.Path;

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
	
	public boolean accept(final Path path) {
		System.out.println(path);
		return true;

	}
}