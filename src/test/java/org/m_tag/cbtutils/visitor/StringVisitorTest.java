package org.m_tag.cbtutils.visitor;

import java.io.File;
import java.io.IOException;

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
			Visitor visitor = new OrVisitor(new StringVisitor("java", true)) {
				@Override
				public boolean found(final File fileName) {
					System.out.println(fileName);
					return true;
				}
			};
			FindFile file = new FindFile(".");
			file.find(visitor);
		} catch (IllegalFIleFormatException | IOException e) {
			e.printStackTrace();
		}
	}
}
