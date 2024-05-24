package org.m_tag.cbtutils.acceptor;

import java.io.File;

public class ConsoleAcceptor extends Acceptor {
	public boolean accept(final File fileName) {
		System.out.println(fileName);
		return true;
	}
}