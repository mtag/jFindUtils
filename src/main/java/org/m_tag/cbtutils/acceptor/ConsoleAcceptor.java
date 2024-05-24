package org.m_tag.cbtutils.acceptor;

import java.io.File;

/**
 * basic acceptor to dump file names to console.
 * @author mtag@m-tag.org
 *
 */
public class ConsoleAcceptor extends Acceptor {
	public boolean accept(final File fileName) {
		System.out.println(fileName);
		return true;
	}
}