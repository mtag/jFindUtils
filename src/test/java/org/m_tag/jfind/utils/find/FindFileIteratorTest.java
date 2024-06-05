package org.m_tag.jfind.utils.find;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.m_tag.jfind.utils.FilterMethods.checkFileExtention;
import static org.m_tag.jfind.utils.FilterMethods.checkSize;
import static org.m_tag.jfind.utils.FilterMethods.exists;
import static org.m_tag.jfind.utils.FilterMethods.isDirectory;

import java.util.Arrays;
import java.util.Iterator;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

/**
 * Test class for FindFileIterator.
 *
 * @author mtag@m-tag.org
 */
public class FindFileIteratorTest {
  @Test
  void success() {
    String[] files = {"./src/main/java/org/m_tag/jfind/ReadingException.java",
        "./src/main/java/org/m_tag/jfind/utils/FilterMethods.java",
        "./src/main/java/org/m_tag/jfind/utils/find/DirectoryReadingException.java",
        "./src/main/java/org/m_tag/jfind/utils/find/FindFileIterator.java",
        "./src/main/java/org/m_tag/jfind/utils/FindIterator.java",
        "./src/main/java/org/m_tag/jfind/utils/locate/DbFile.java",
        "./src/main/java/org/m_tag/jfind/utils/locate/DbFileIterator.java"};
    final Iterator<String> expected = Arrays.asList(files).iterator();

    final Stream<String> stream = new FindFileIterator("./src/main/").stream()
        .filter(path -> checkFileExtention(path, "java")).filter(path -> !isDirectory(path))
        .filter(path -> checkSize(path, 1, 1)).filter(path -> exists(path))
        .map(path -> path.toString().replace('\\', '/'));
    final Iterator<String> results = stream.toList().iterator();

    while (expected.hasNext()) {
      String line = expected.next();
      assertTrue(results.hasNext());
      String path = results.next();
      System.out.println(line + '\t' + path.toString());
      assertEquals(line, path.toString());
    }
  }

  /**
   * for testing.
   *
   * @param args arguments from command line
   */
  public static void main(String[] args) {
    final FindFileIterator file = new FindFileIterator(".");
    file.stream().filter(path -> checkFileExtention(path, "java"))
        .forEach(path -> System.out.println(path));
  }
}
