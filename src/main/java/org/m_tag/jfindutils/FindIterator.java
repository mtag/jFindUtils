package org.m_tag.jfindutils;

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

  protected FindIterator() {
    super();
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
}
