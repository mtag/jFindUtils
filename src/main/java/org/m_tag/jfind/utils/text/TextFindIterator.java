package org.m_tag.jfind.utils.text;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import org.m_tag.jfind.utils.FindIterator;

/**
 * Iterator to reading text file containing file names.
 */
public class TextFindIterator extends FindIterator implements Closeable {

  private final File file;
  
  private final BufferedReader reader;
  
  private String line;
  

  /**
   * constructor.
   *
   * @param path text file containing file names
   * @throws FileNotFoundException File of named file does not exists.
   */
  public TextFindIterator(final Path path) throws FileNotFoundException {
    this(path.toFile());
  }
  
  /**
   * constructor.
   *
   * @param fileName text file containing file names
   * @throws FileNotFoundException File of named file does not exists.
   */
  public TextFindIterator(final String fileName) throws FileNotFoundException {
    this(new File(fileName));
  }
  
  
  /**
   * constructor.
   *
   * @param file text file containing file names
   * @throws FileNotFoundException File of named file does not exists.
   */
  public TextFindIterator(final File file) throws FileNotFoundException {
    super();
    this.file = file; 
    this.reader = new BufferedReader(
        new InputStreamReader(new FileInputStream(file)));
    this.line = null;
  }
  
  protected File getFile() {
    return file;
  }

  @Override
  public boolean hasNext() {
    if (line != null) {
      return true;
    }
    while (true) {
      try {
        line = reader.readLine();
        if (line == null) {
          return false;
        }
      } catch (IOException ex) {
        return false;
      }
      if (new File(line).exists()) {
        return true;
      }
    }
  }

  @Override
  public Path next() {
    if (line == null) {
      if (!hasNext()) {
        return null;
      }
    }
    final Path path = Path.of(line);
    line = null;
    return path;
  }

  @Override
  public void close() throws IOException {
    this.reader.close();
  }
}
