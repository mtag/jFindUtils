package org.m_tag.cbtutils.find;

import org.m_tag.cbtutils.FindIterator;

public class FindFileIteratorTest {

	/**
	 * for testing.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		//final Visitor visitor = new OrVisitor(new StringVisitor("java", true));
		final FindFileIterator file = new FindFileIterator(".");
		//file.stream().filter(path -> path.getFileName().endsWith("java")).forEach(path -> System.out.println(path));
		file.stream()
			.filter(path->FindIterator.checkFileExtention(path, "java"))
			.forEach(path -> System.out.println(path));
	}

}
