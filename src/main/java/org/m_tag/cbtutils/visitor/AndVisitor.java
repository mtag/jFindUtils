package org.m_tag.cbtutils.visitor;

import java.io.File;

import org.m_tag.cbtutils.acceptor.Acceptor;

public class AndVisitor extends Visitor {
	private Visitor[] conditions;
	
	public AndVisitor(Visitor... conditions) {
		super();
		this.conditions = conditions;
	}

	@Override
	protected boolean check(final File fileName, Acceptor acceptor) {
		for(Visitor condition : conditions) {
			if (!condition.visit(fileName, acceptor)) {
				return false;
			}
		}
		return true;
	}
}
