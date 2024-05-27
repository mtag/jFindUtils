package org.m_tag.jfindutils.find;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.Iterator;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import static org.m_tag.jfindutils.FilterMethods.checkFileExtention;

public class FindFileIteratorTest {
	@Test
	void success() {
		String[] files = {
				"./src/main/java/org/m_tag/cbtutils/find/DirectoryReadingException.java",
				"./src/main/java/org/m_tag/cbtutils/find/FindFileIterator.java",
				"./src/main/java/org/m_tag/cbtutils/FindIterator.java",
				"./src/main/java/org/m_tag/cbtutils/locate/DbFileIterator.java",
		};
		final Iterator<String> expected = Arrays.asList(files).iterator();
		final Stream<String> stream = new FindFileIterator("./src/main/").stream()
				.filter(path->checkFileExtention(path, "java"))
				.map(path -> path.toString().replace('\\', '/'));
		final Iterator<String> results = stream.toList().iterator();
		
		while(expected.hasNext()) {
			String line = expected.next();
			System.out.println(line);
			assertTrue(results.hasNext());
			String path = results.next();
			assertEquals(line, path.toString());
		}
	}

	/**
	 * for testing.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		final FindFileIterator file = new FindFileIterator(".");
		file.stream().filter(path -> checkFileExtention(path, "java"))
				.forEach(path -> System.out.println(path));
	}

}
