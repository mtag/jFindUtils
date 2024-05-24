package org.m_tag.cbtutils.visitor;

import java.io.File;

import org.m_tag.cbtutils.acceptor.Acceptor;

public class NotVisitor extends Visitor {
	private Visitor condition;
	
	public NotVisitor(Visitor condition) {
		super();
		this.condition = condition;
	}

	@Override
	protected boolean check(final File fileName, Acceptor acceptor) {
		return !condition.check(fileName, acceptor);
	}
}
