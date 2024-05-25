package org.m_tag.cbtutils.visitor;

import java.io.IOException;

import org.m_tag.cbtutils.Finder;
import org.m_tag.cbtutils.IllegalFIleFormatException;
import org.m_tag.cbtutils.find.FindFile;

public class StringVisitorTest {

	/**
	 * for testing.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			final Visitor visitor = new OrVisitor(new StringVisitor("java", true));
			final Finder file = new FindFile(".");
			file.stream(visitor).forEach(path -> System.out.println(path));
		} catch (IllegalFIleFormatException | IOException e) {
			e.printStackTrace();
		}
	}
}
