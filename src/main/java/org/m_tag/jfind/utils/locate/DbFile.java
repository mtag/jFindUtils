package org.m_tag.jfind.utils.locate;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import org.m_tag.jfind.utils.FindIterator;

/**
 * db file of locate.
 */
public class DbFile {
  /**
   * file path.
   */
  private final Path path;
  /**
   * replacement for path names.
   */
  private final String[][] replacements;
  
  /**
   * constructor.
   *
   * @param file db file Path.
   * @param replacements replacements.
   */
  public DbFile(File file, String[]... replacements) {
    this(file.toPath(), replacements);
  }
  
  /**
   * constructor.
   *
   * @param fileName db file name.
   * @param replacements replacements.
   */
  public DbFile(String fileName, String[]... replacements) {
    this(Path.of(fileName), replacements);
  }
  
  /**
   * constructor.
   *
   * @param path db file Path.
   * @param replacements replacements.
   */
  public DbFile(Path path, String[]... replacements) {
    super();
    this.path = path;
    this.replacements = replacements;
  }
  
  /**
   * getter for db file path.
   *
   * @return db file path
   */
  public Path getPath() {
    return path;
  }
  
  /**
   * getter for  replacement for path names.
   *
   * @return  replacement for path names.
   */
  public String[][] getReplacements() {
    return replacements;
  }
  
  /**
   * replace pathnames.
   *
   * @param fileName originalFileName
   * @return replaced fileName with replacements
   */
  public String replace(final String fileName) {
    if (replacements != null) {
      for (String[] entry : replacements) {
        if (fileName.startsWith(entry[0])) {
          return entry[1] + fileName.substring(entry[0].length());
        }
      }
    }
    return fileName;
  }
  
  public DbFileIterator iterator() throws IOException {
    return new DbFileIterator(this);
  }
}
