package org.m_tag.jfind.utils.locate;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.stream.Stream;

/**
 * db file of locate.
 */
public class DbFile {
  /**
   * character set in db.
   */
  private final Charset charset;

  /**
   * file path.
   */
  private final Path path;

  /**
   * constructor.
   *
   * @param fileName db file Path.
   */
  public DbFile(final String fileName) {
    this(Path.of(fileName), StandardCharsets.UTF_8);
  }

  /**
   * constructor.
   *
   * @param path db file Path.
   */
  public DbFile(final Path path) {
    this(path, StandardCharsets.UTF_8);
  }

  /**
   * constructor.
   *
   * @param path db file Path.
   * @param charsetName character set name of db file.
   */
  public DbFile(final Path path, final String charsetName) {
    super();
    this.path = path;
    this.charset = Charset.forName(charsetName);
  }
  
  /**
   * constructor.
   *
   * @param path db file Path.
   * @param charset character set of db file.
   */
  public DbFile(final Path path, final Charset charset) {
    super();
    this.path = path;
    this.charset = charset;
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
   * create iterator.
   *
   * @param replacements replace paths
   * @return locate iterator
   * @throws IOException error on reading db file.
   */
  public DbFileIterator iterator(String[]... replacements) throws IOException {
    return new DbFileIterator(this, replacements);
  }

  /**
   * create stream.
   *
   * @param replacements replace paths
   * @return locate stream
   * @throws IOException error on reading db file.
   */
  public Stream<Path> stream(String[]... replacements) throws IOException {
    return iterator(replacements).stream();
  }
}
