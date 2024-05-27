package org.m_tag.jfindutils;

import java.nio.file.Path;

/**
 * Static methods to filter the Stream.
 *
 * @author mtag@m-tag.org
 */
public final class FilterMethods {

  /**
   * hide constructor.
   */
  private FilterMethods() {
    super();
  }

  /** 
   * check file extension in stream to filter the stream.
   *
   * @param path found path
   * @param extension checking extension
   * @return true:matched the extension
   */
  public static boolean checkFileExtention(Path path, String extension) {
    return checkFileExtention(path, extension, true);
  }

  /**
   * check file extension in stream to filter the stream.
   *
   * @param path found path
   * @param extension checking extension
   * @param caseSensitive true:caseSensitive, false:not sensitive
   * @return true:matched the extension
   */
  public static boolean checkFileExtention(Path path, String extension, boolean caseSensitive) {
    final String fileName = path.getFileName().toString();
    final int pos = fileName.lastIndexOf('.');
    if (pos < 0 || pos != fileName.length() - extension.length() - 1) {
      return false;
    }
    final String last = fileName.substring(pos + 1);
    return caseSensitive ? last.equals(extension) : last.equalsIgnoreCase(extension);
  }
}
