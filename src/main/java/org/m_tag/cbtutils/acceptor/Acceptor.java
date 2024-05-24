package org.m_tag.cbtutils.acceptor;

import java.io.File;

public abstract class Acceptor {

	
	/**
	 * method for visit in visitor pattern.
	 * @param fileName found filename
	 * @return true:accepted, false:not accepted
	 */
	public abstract boolean accept(final File fileName);
}
