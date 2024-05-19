package org.m_tag.cbtutils;

/**
 * search file with string.
 */
public class StringVisitor implements Visitor {
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
		this(finding, Visitor.CASE_SENSITIVE_DEFAULT);
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

	/**
	 * method for visit in visitor pattern.
	 * @param fileName found filename
	 * @return true:accepted, false:not accepted
	 */
	public boolean found(final String fileName) {
		return true;
	}

	@Override
	public boolean visit(final String fileName) {
		String name = fileName;
		if (ignoreCase) {
			name = name.toUpperCase();
		}
		if (name.contains(searchValue)) {
			return found(fileName);
		}
		return false;
	}
}
