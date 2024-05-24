package org.m_tag.cbtutils.visitor;

import java.io.File;

/**
 * search file with string.
 */
public class StringVisitor extends Visitor {
	/**
	 * original searching string.
	 */
	private final CharSequence finding;

	/**
	 * case sensitivity.
	 * <p/>
	 * true:case insensitive , false:sensitive
	 */
	private final boolean ignoreCase;

	/**
	 *  search string. This is upper-cased value in case of ignore case is true.
	 */
	private final CharSequence searchValue;

	/**
	 * constructor
	 * @param finding searching string.
	 */
	public StringVisitor(final String finding) {
		this(finding, isCaseSensitive());
	}

	/**
	 * constructor
	 * @param finding searching string.
	 * @param ignoreCase true:case insensitive , false:sensitive
	 */
	public StringVisitor(final String finding, final boolean ignoreCase) {
		super();
		this.ignoreCase = ignoreCase;
		this.finding = finding;
		this.searchValue = ignoreCase ? finding.toUpperCase() : finding;
	}


	protected boolean check(final File fileName) {
		String name = fileName.getAbsolutePath();
		if (ignoreCase) {
			name = name.toUpperCase();
		}
		return name.contains(searchValue);
	}
}
