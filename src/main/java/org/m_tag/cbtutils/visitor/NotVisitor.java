package org.m_tag.cbtutils.visitor;

import java.io.File;

public class NotVisitor extends Visitor {
	private Visitor condition;
	
	public NotVisitor(Visitor condition) {
		super();
		this.condition = condition;
	}

	@Override
	protected boolean check(final File fileName) {
		return !condition.check(fileName);
	}
}
