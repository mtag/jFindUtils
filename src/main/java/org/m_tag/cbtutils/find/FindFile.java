package org.m_tag.cbtutils.find;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.m_tag.cbtutils.IllegalFIleFormatException;
import org.m_tag.cbtutils.visitor.Visitor;

public class FindFile {
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
	public void find(Visitor visitor) 
			throws IOException, IllegalFIleFormatException {
		find(visitor, root);
	}
	
	/**
	 * Execute find.
	 * @param visitor 
	 * @throws IOException
	 * @throws IllegalFIleFormatException
	 * @throws FileNotFoundException
	 */
	public void find(Visitor visitor, File file) 
			throws IOException, IllegalFIleFormatException {
		if (file.exists()) {
			visitor.visit(file);
			if (file.isDirectory()) {
				final File[] files = file.listFiles();
				if (files != null) {
					for(File child : files) {
						find(visitor, child);
					}
				}
			}
		}
	}
}
