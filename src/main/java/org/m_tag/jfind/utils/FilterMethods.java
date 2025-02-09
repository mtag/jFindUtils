package org.m_tag.jfind.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.m_tag.jfind.ReadingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Static methods to filter the Stream.
 *
 * @author mtag@m-tag.org
 */
public final class FilterMethods {
  protected static final Logger logger = LoggerFactory.getLogger(FilterMethods.class);

  /**
   * hide constructor.
   */
  private FilterMethods() {
    super();
  }

  /**
   * check file exists or not.
   *
   * @param path found path
   * @return true:exists , false:not exists
   */
  public static boolean exists(final Path path) {
    final boolean exists = Files.exists(path);
    logger.debug("check exists:{}:{}", exists, path);
    return exists;
  }

  /**
   * check file is directory or not.
   *
   * @param path found path
   * @return true:directory , false:not directory
   */
  public static boolean isDirectory(final Path path) {
    return Files.isDirectory(path);
  }

  /**
   * check file size.
   *
   * @param path found path
   * @param flags desired flags for size: 0:exactly, less than 0:smaller, more than 0:greater
   * @param size specified size of file:path
   * @return true:matched , false:not matched
   */
  public static boolean checkSize(final Path path, final int flags, final long size) {
    try {
      return Long.compare(Files.size(path), size) == flags;
    } catch (IOException ex) {
      throw new ReadingException(String.format("Failed to get size:%s", path.toString()), ex);
    }
  }

  /**
   * check file extension in stream to filter the stream.
   *
   * @param path found path
   * @param extension checking extension
   * @return true:matched the extension
   */
  public static boolean checkFileExtention(final Path path, String extension) {
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
  public static boolean checkFileExtention(final Path path, String extension,
      boolean caseSensitive) {
    final String fileName = path.getFileName().toString();
    final int pos = fileName.lastIndexOf('.');
    if (pos < 0 || pos != fileName.length() - extension.length() - 1) {
      return false;
    }
    final String last = fileName.substring(pos + 1);
    return caseSensitive ? last.equals(extension) : last.equalsIgnoreCase(extension);
  }

  public static boolean equalsIgnoreCase(final Path path, final String target) {
    return path.getFileName().toString().equalsIgnoreCase(target);
  }
}
