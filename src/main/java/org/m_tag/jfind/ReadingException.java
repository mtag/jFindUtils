package org.m_tag.jfind;

/**
 * Runtime Exception on finding.
 *
 * @author mtag@m-tag.org
 */
public class ReadingException extends RuntimeException {
  /**
   * generated serial.
   */
  private static final long serialVersionUID = -7386094079664697053L;

  /**
   * Constructor.
   *
   * @param message message
   * @param cause errors on reading directory files and sub directories.
   */
  public ReadingException(String message, Exception cause) {
    super(message, cause);
  }
}
