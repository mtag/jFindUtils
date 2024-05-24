package org.m_tag.cbtutils;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.m_tag.cbtutils.acceptor.Acceptor;
import org.m_tag.cbtutils.visitor.Visitor;

public interface Finder {
	/**
	 * Execute find.
	 * @param visitor 
	 * @param acceptor TODO
	 * @throws IOException
	 * @throws IllegalFIleFormatException
	 * @throws FileNotFoundException
	 */
	public void find(final Visitor visitor, final Acceptor acceptor) 
			throws IOException, IllegalFIleFormatException;
}
