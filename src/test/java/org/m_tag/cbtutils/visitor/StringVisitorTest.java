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
			Visitor visitor = new StringVisitor("zip", true) {
				@Override
				public boolean found(final File fileName) {
					System.out.println(fileName);
					return true;
				}
			};
			FindFile file = new FindFile("c:\\Users\\mtag");
			file.find(visitor);
		} catch (IllegalFIleFormatException | IOException e) {
			e.printStackTrace();
		}
	}
}
