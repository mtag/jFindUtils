package org.m_tag.cbtutils.visitor;

import java.nio.file.Path;

import org.m_tag.cbtutils.acceptor.Acceptor;

public class NotVisitor extends Visitor {
	private Visitor condition;
	
	public NotVisitor(Visitor condition) {
		super();
		this.condition = condition;
	}

	@Override
	protected boolean check(final Path path, Acceptor acceptor) {
		return !condition.check(path, acceptor);
	}
}
