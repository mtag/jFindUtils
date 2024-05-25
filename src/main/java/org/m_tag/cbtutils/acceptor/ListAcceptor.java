package org.m_tag.cbtutils.acceptor;

import java.nio.file.Path;

/**
 * basic acceptor to dump file names to console.
 * @author mtag@m-tag.org
 *
 */
public class ListAcceptor extends Acceptor {
	/**
	 * default instance.
	 */
	public static final Acceptor LIST_ACCEPTOR = new ListAcceptor();
	
	public boolean accept(final Path path) {
		StringBuilder builder = new StringBuilder();
		builder.append(String.format("%10d ", path.toFile().length()));
		builder.append(path);
		System.out.println(builder.toString());
		return true;
	}
}