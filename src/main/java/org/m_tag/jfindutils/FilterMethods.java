package org.m_tag.jfindutils;

import java.nio.file.Path;

public final class FilterMethods {

	/**
	 * hide constructor.
	 */
	private FilterMethods() {
		super();
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

	public static boolean checkFileExtention(Path path, String extention) {
		return checkFileExtention(path, extention, true);
	}

}
