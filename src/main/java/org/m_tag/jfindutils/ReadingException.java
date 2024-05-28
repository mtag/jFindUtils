package org.m_tag.jfindutils;

import java.io.IOException;

public class ReadingException extends RuntimeException {
	  /**
	   * Constructor.
	   *
	   * @param message message
	   * @param cause errors on reading directory files and sub directories.
	   */
	  public ReadingException(String message, IOException cause) {
	    super(message, cause);
	  }
}
