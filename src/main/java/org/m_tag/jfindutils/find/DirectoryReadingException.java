package org.m_tag.jfindutils.find;

/**
 * Runtime Exception on reading files and folders from file system.
 *
 * @author mtag@m-tag.org
 */
public class DirectoryReadingException extends RuntimeException {

  public DirectoryReadingException(String message, Throwable cause) {
    super(message, cause);
  }

  public DirectoryReadingException(String message) {
    super(message);
  }

  private static final long serialVersionUID = -2243784600865766384L;

}
