package org.m_tag.cbtutils.visitor;

import java.nio.file.Path;

import org.m_tag.cbtutils.acceptor.Acceptor;

public class OrVisitor extends Visitor {
	private Visitor[] conditions;
	
	public OrVisitor(Visitor... conditions) {
		super();
		this.conditions = conditions;
	}

	@Override
	protected boolean check(final Path path, Acceptor acceptor) {
		for(Visitor condition : conditions) {
			if (condition.visit(path, acceptor)) {
				return true;
			}
		}
		return false;
	}
}
