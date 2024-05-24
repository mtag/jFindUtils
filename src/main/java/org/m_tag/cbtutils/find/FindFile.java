package org.m_tag.cbtutils.find;

import java.io.File;
import java.io.IOException;

import org.m_tag.cbtutils.Finder;
import org.m_tag.cbtutils.acceptor.Acceptor;
import org.m_tag.cbtutils.visitor.Visitor;

/**
 * Find files from file systems.
 * @author mtag@m-tag.org
 */
public class FindFile implements Finder {
	private final File root;
	/**
	 * constructor
	 * @param root root folder to find
	 */
	public FindFile(final String root) {
		this(new File(root));
	}
	/**
	 * constructor 
	 * @param root root folder to findb
	 */
	public FindFile(final File root) {
		super();
		this.root = root;
	}

	/**
	 * Execute find.
	 * @param visitor file name checker whether it is target or not.
	 * @param acceptor accepts targeted file name
	 * @throws IOException read error in finding
	 */
	public void find(final Visitor visitor, final Acceptor acceptor) 
			throws IOException {
		find(visitor, acceptor, root);
	}
	
	/**
	 * Execute find.
	 * @param visitor 
	 * @param acceptor accepts targeted file name
	 * @throws IOException
	 */
	private void find(final Visitor visitor, final Acceptor acceptor, final File file) 
			throws IOException {
		if (file.exists()) {
			visitor.visit(file, acceptor);
			if (file.isDirectory()) {
				final File[] files = file.listFiles();
				if (files != null) {
					for(File child : files) {
						find(visitor, acceptor, child);
					}
				}
			}
		}
	}
}
