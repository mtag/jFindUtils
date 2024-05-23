package org.m_tag.cbtutils.visitor;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.m_tag.cbtutils.locate.mlocate.DbFile;
import org.m_tag.cbtutils.locate.mlocate.IllegalFIleFormatException;

/**
 * search file with regular expression.
 */
public class RegexVisitor implements Visitor {
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
		this(regex, CASE_SENSITIVE_DEFAULT);
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

	/**
	 * method for visit in visitor pattern.
	 * @param fileName found filename
	 * @param matcher matched result.
	 * @return true:accepted, false:not accepted
	 */
	public boolean found(final String fileName, final Matcher matcher) {
		return true;
	}

	@Override
	public boolean visit(final String fileName) {
		final Matcher matcher = pattern.matcher(fileName);
		if (matcher.matches()) {
			return found(fileName, matcher);
		}
		return false;
	}

	/**
	 * for testing.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Visitor visitor = new RegexVisitor("^.*[.]rar$", true) {
				@Override
				public boolean found(final String fileName, final Matcher matcher) {
					System.out.println(fileName);
					return true;
				}
			};
			DbFile file = new DbFile(new File("Y:\\.db\\y.db"));
			file.find(visitor);
		} catch (IllegalFIleFormatException | IOException e) {
			e.printStackTrace();
		}
	}
}
