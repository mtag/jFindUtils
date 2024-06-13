package org.m_tag.jfind.utils.locate;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.security.InvalidParameterException;
import java.util.stream.Stream;

/**
 * db file of locate.
 */
public class DbFile {
  private static String[][] splitArray(final String[] pathAndReplace, int j) {
    final String[][] array = new String[(pathAndReplace.length - j) / 2][];
    int i = 0;
    while (i < array.length) {
      array[i] = new String[2];
      array[i][0] = pathAndReplace[j++];
      array[i][1] = pathAndReplace[j++];
      i++;
    }
    return array;
  }
  
  /**
   * character set in db.
   */
  private final Charset charset;
  
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
  public DbFile(File file, String... replacements) {
    this(file.toPath(), StandardCharsets.UTF_8, splitArray(replacements, 0));
  }
  
  /**
   * constructor.
   *
   * @param file db file Path.
   * @param replacements replacements.
   */
  public DbFile(File file, String[][] replacements) {
    this(file.toPath(), StandardCharsets.UTF_8, replacements);
  }

  /**
   * constructor.
   *
   * @param path db file Path.
   * @param replacements replacements.
   */
  public DbFile(Path path, Charset charset, String[]... replacements) {
    super();
    this.path = path;
    this.replacements = replacements;
    this.charset = charset;
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
    this.replacements = splitArray(pathAndReplace, 1);
    this.charset = StandardCharsets.UTF_8;
  }

  Charset getCharset() {
    return charset;
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

  public DbFileIterator iterator() throws IOException {
    return new DbFileIterator(this);
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

  public Stream<Path> stream() throws IOException {
    return iterator().stream();
  }
}
