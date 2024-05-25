package org.m_tag.cbtutils.visitor;

import java.io.IOException;

import org.m_tag.cbtutils.IllegalFIleFormatException;
import org.m_tag.cbtutils.acceptor.ListAcceptor;
import org.m_tag.cbtutils.locate.DbFile;

public class RegexVisitorTest {

	/**
	 * for testing.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			final Visitor visitor = new RegexVisitor("^.*[.]rar$", true);
			final DbFile file = new DbFile("Y:\\.db\\y.db",
					new String[][] {
						new String[] {"/data16/", "/home/mtag/y/"}
					});
			file.find(visitor, ListAcceptor.LIST_ACCEPTOR);
		} catch (IllegalFIleFormatException | IOException e) {
			e.printStackTrace();
		}
	}
}
