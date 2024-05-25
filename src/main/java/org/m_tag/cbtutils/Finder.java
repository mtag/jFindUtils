package org.m_tag.cbtutils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.LinkedHashSet;
import java.util.stream.Stream;

import org.m_tag.cbtutils.visitor.Visitor;

public abstract class Finder {
	/**
	 * Execute find.
	 * @param visitor 
	 * @param root root folder to find
	 * @throws IOException
	 * @throws IllegalFIleFormatException
	 * @throws FileNotFoundException
	 */
	public abstract void find(final Visitor visitor, final LinkedHashSet<Path> set) 
			throws IOException, IllegalFIleFormatException;
	
	public Stream<Path> stream(final Visitor visitor) throws IOException, IllegalFIleFormatException {
		LinkedHashSet<Path> set = new LinkedHashSet<>();
		find(visitor, set);
		return set.stream();
	}
}
