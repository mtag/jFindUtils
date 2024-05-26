package org.m_tag.cbtutils;

import java.nio.file.Path;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;


public abstract class FindIterator implements Iterator<Path> {

	protected FindIterator() {
		super();
	}

	public  Stream<Path> stream() {
		final Spliterator<Path> spliterator = Spliterators.spliteratorUnknownSize(this, Spliterator.ORDERED | Spliterator.NONNULL);
		final Stream<Path> stream = StreamSupport.stream(spliterator, false);
		return stream;
	}
	

	public static boolean checkFileExtention(Path path, String extention) {
		return checkFileExtention(path, extention, true);
	}
	public static boolean checkFileExtention(Path path, String extention, boolean caseSensitive) {
		final String fileName = path.getFileName().toString();
		final int pos = fileName.lastIndexOf('.');
		if (pos < 0 || pos != fileName.length() - extention.length() - 1) {
			return false;
		}
		final String last = fileName.substring(pos + 1);
		return caseSensitive ? last.equals(extention) : last.equalsIgnoreCase(extention);
	}
}
