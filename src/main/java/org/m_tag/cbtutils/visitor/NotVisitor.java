package org.m_tag.cbtutils.visitor;

import java.nio.file.Path;

public class NotVisitor extends Visitor {
	private Visitor condition;
	
	public NotVisitor(Visitor condition) {
		super();
		this.condition = condition;
	}

	@Override
	public boolean check(final Path path) {
		return !condition.check(path);
	}
}
