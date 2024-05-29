package org.m_tag.jfind;

public class ReadingException extends RuntimeException {
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
