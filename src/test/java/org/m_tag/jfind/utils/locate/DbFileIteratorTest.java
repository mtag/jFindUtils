package org.m_tag.jfind.utils.locate;

import static org.junit.Assert.assertTrue;
import static org.m_tag.jfind.utils.FilterMethods.checkFileExtention;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import org.junit.jupiter.api.Test;

/**
 * Test class for DbFileIterator.
 *
 * @author mtag@m-tag.org
 */
public class DbFileIteratorTest {
  /**
   * to update test.db.
   * <p/>
   * updatedb --localpaths=/home/mtag/eclipse-workspace/jFindUtils/src/main
   * --output=src/test/resources/test.db
   *
   * @throws IOException raised at failed to open or read db file.
   */
  @Test
  void success() throws IOException {
    DbFile file = new DbFile("./src/test/resources/test.db",
        new String[] {"/home/mtag/eclipse-workspace/jFindUtils/", "./"});
    try (final DbFileIterator target = file.iterator()) {
      final Iterator<Path> results = target.stream().toList().iterator();

      while (results.hasNext()) {
        Path path = results.next();
        System.out.println(path.toString() + ':' + Files.exists(path));
        assertTrue(Files.exists(path));
      }
    }
  }

  /**
   * for testing.
   *
   * @param args arguments from command line
   * @throws IOException raised at failed to open or read db file.
   */
  public static void main(String[] args) throws IOException {
    // final Visitor visitor = new RegexVisitor("^.*[.]rar$", true);
    DbFile file = new DbFile("Y:\\.db\\y.db",
        new String[][] {new String[] {"/data16/", "/home/mtag/y/"}});
    try (final DbFileIterator itetator = file.iterator()) {
      itetator.stream().filter(path -> checkFileExtention(path, "java"))
          .forEach(path -> System.out.println(path));
    }
  }
}