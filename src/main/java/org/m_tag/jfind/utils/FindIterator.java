package org.m_tag.jfind.utils;

import java.nio.file.Path;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Iterator for files and directories in target.
 *
 * @author mtag@m-tag.org
 *
 */
public abstract class FindIterator implements Iterator<Path> {
  /**
   * replacement for path names.
   */
  private final String[][] replacements;
  
  protected FindIterator(String[]... replacements) {
    super();

    this.replacements = replacements == null ? new String[0][] : replacements;
    for (String[] entry : this.replacements) {
      if (entry.length != 2) {
        throw new IllegalArgumentException("values must be array of array[2]");
      }
    }
  }
  
  /**
   * create stream for listed files.
   *
   * @return stream for listed files.
   */
  public Stream<Path> stream() {
    final Spliterator<Path> spliterator =
        Spliterators.spliteratorUnknownSize(this, Spliterator.ORDERED | Spliterator.NONNULL);
    return StreamSupport.stream(spliterator, false);
  }
  

  /**
   * replace pathnames.
   *
   * @param fileName originalFileName
   * @return replaced fileName with replacements
   */
  protected String replace(final String fileName) {
    for (String[] entry : replacements) {
      if (fileName.startsWith(entry[0])) {
        return entry[1] + fileName.substring(entry[0].length());
      }
    }
    return fileName;
  }
}
