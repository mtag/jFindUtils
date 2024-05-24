package org.m_tag.cbtutils.visitor;

import java.io.IOException;

import org.m_tag.cbtutils.Finder;
import org.m_tag.cbtutils.acceptor.Acceptor;
import org.m_tag.cbtutils.acceptor.ConsoleAcceptor;
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
			final Acceptor acceptor = new ConsoleAcceptor();
			final Finder file = new FindFile(".");
			file.find(visitor, acceptor);
		} catch (IllegalFIleFormatException | IOException e) {
			e.printStackTrace();
		}
	}
}
