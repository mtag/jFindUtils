package org.m_tag.jfind.utils.locate;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.security.InvalidParameterException;

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
   * @param fileNameAndReplaces db file name and replacements.
   */
  public DbFile(String fileNameAndReplaces) {
    super();
    final String[] pathAndReplace = fileNameAndReplaces.split(File.pathSeparator);
    if (pathAndReplace.length % 2 != 1) {
      throw new InvalidParameterException(
          "illegal path format for locate db:" + fileNameAndReplaces);
    }
    this.path = Path.of(pathAndReplace[0]);
    if (!path.toFile().exists()) {
      throw new InvalidParameterException("db file doesn't exists:" + fileNameAndReplaces);
    }
    this.replacements = new String[(pathAndReplace.length - 1) / 2][];
    
    for (int i = 0, j = 1; i < replacements.length; i++) {
      this.replacements[i] = new String[2];
      this.replacements[i][0] = pathAndReplace[j++];
      this.replacements[i][1] = pathAndReplace[j++];
    }
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
   * getter for replacement for path names.
   *
   * @return replacement for path names.
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
