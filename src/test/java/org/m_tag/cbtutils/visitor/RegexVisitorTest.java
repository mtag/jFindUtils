package org.m_tag.cbtutils.visitor;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;

import org.m_tag.cbtutils.IllegalFIleFormatException;
import org.m_tag.cbtutils.locate.mlocate.DbFile;

public class RegexVisitorTest {

	/**
	 * for testing.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Visitor visitor = new RegexVisitor("^.*[.]rar$", true) {
				@Override
				public boolean found(final File fileName, final Matcher matcher) {
					System.out.println(fileName);
					return true;
				}
			};
			DbFile file = new DbFile(new File("Y:\\.db\\y.db"),
					new String[][] {
						new String[] {"/data16/", "/home/mtag/y/"}
					});
			file.find(visitor);
		} catch (IllegalFIleFormatException | IOException e) {
			e.printStackTrace();
		}
	}
}
