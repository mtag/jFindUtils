package org.m_tag.cbtutils.visitor;

import java.io.File;

import org.m_tag.cbtutils.acceptor.Acceptor;

public class OrVisitor extends Visitor {
	private Visitor[] conditions;
	
	public OrVisitor(Visitor... conditions) {
		super();
		this.conditions = conditions;
	}

	@Override
	protected boolean check(final File fileName, Acceptor acceptor) {
		for(Visitor condition : conditions) {
			if (condition.visit(fileName, acceptor)) {
				return true;
			}
		}
		return false;
	}
}
