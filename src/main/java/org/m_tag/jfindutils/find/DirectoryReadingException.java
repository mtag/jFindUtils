package org.m_tag.jfindutils.find;

import java.io.IOException;

/**
 * Runtime Exception on reading files and folders from file system.
 *
 * @author mtag@m-tag.org
 */
public class DirectoryReadingException extends RuntimeException {
  /**
   * generated serial.
   */
  private static final long serialVersionUID = -2243784600865766384L;
  
  /**
   * Constructor.
   *
   * @param message message
   * @param cause errors on reading directory files and sub directories.
   */
  public DirectoryReadingException(String message, IOException cause) {
    super(message, cause);
  }
}
