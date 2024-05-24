package org.m_tag.cbtutils.find;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.m_tag.cbtutils.Finder;
import org.m_tag.cbtutils.IllegalFIleFormatException;
import org.m_tag.cbtutils.acceptor.Acceptor;
import org.m_tag.cbtutils.visitor.Visitor;

/**
 * Find files from file systems.
 * @author mtag@m-tag.org
 */
public class FindFile implements Finder {
	private final File root;
	public FindFile(final String root) {
		this(new File(root));
	}
	public FindFile(final File root) {
		super();
		this.root = root;
	}

	/**
	 * Execute find.
	 * @param visitor 
	 * @throws IOException
	 * @throws IllegalFIleFormatException
	 * @throws FileNotFoundException
	 */
	public void find(final Visitor visitor, final Acceptor acceptor) 
			throws IOException, IllegalFIleFormatException {
		find(visitor, acceptor, root);
	}
	
	/**
	 * Execute find.
	 * @param visitor 
	 * @param acceptor TODO
	 * @throws IOException
	 * @throws IllegalFIleFormatException
	 * @throws FileNotFoundException
	 */
	private void find(final Visitor visitor, final Acceptor acceptor, final File file) 
			throws IOException, IllegalFIleFormatException {
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
