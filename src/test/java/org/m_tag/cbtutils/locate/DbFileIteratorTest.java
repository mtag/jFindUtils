package org.m_tag.cbtutils.locate;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;

import org.junit.jupiter.api.Test;
import org.m_tag.cbtutils.FindIterator;

public class DbFileIteratorTest {
	/***
	 * to update test.db : ` updatedb  --localpaths=/home/mtag/eclipse-jit/jFindUtils/src/main --output=src/test/resources/test.db`
	 * @throws IOException
	 */
	@Test
	void success() throws IOException {
		try(final DbFileIterator target = new DbFileIterator("./src/test/resources/test.db",
				new String[]{"/home/mtag/eclipse-jit/jFindUtils/", "./"})) {
			final Iterator<Path> results = target.stream()
				.toList().iterator();
			
			while(results.hasNext()) {
				Path path = results.next();
				System.out.println(path.toString());
				assertTrue(Files.exists(path));
			}
		}
	}

	/**
	 * for testing.
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		// final Visitor visitor = new RegexVisitor("^.*[.]rar$", true);
		try (final DbFileIterator file = new DbFileIterator("Y:\\.db\\y.db",
				new String[][] { new String[] { "/data16/", "/home/mtag/y/" } })) {
			file.stream()
				.filter(path->FindIterator.checkFileExtention(path, "java"))
				.forEach(path -> System.out.println(path));
		}
	}
}
