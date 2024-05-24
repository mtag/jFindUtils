package org.m_tag.cbtutils.visitor;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.m_tag.cbtutils.acceptor.Acceptor;

/**
 * search file with regular expression.
 */
public class RegexVisitor extends Visitor {
	/**
	 * search with regular expression..
	 */
	private final Pattern pattern;

	/**
	 * constructor.
	 * <P/>
	 * search file with pattern at case sensitive.
	 * @param regex search pattern
	 */
	public RegexVisitor(final String regex) {
		this(regex, isCaseSensitive());
	}

	/**
	 * constructor.
	 * @param regex search pattern
	 * @param ignoreCase true:case insensitive , false:sensitive
	 */
	public RegexVisitor(final String regex, boolean ignoreCase) {
		this(Pattern.compile(regex, ignoreCase ? Pattern.CASE_INSENSITIVE : 0));
	}

	/**
	 * constructor.
	 * @param pattern search pattern
	 */
	public RegexVisitor(final Pattern pattern) {
		super();
		this.pattern = pattern;
	}


	@Override
	protected boolean check(final File fileName, Acceptor acceptor) {
		final Matcher matcher = pattern.matcher(fileName.getAbsolutePath());
		return matcher.matches();
	}
}
