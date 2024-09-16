package org.m_tag.jfind.utils.text;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

class TextFindIteratorTest {

  @Test
  void test() throws IOException {
    try (TextFindIterator file = new TextFindIterator("./src/test/resources/filenames.txt")) {
      Path path = file.next();
      final String fileName = path.toString().replace(File.separatorChar, '/');
      assertEquals("./src/test/resources/test.db", fileName);
      assertEquals(null, file.next());
    }
  }
}
