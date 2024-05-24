package org.m_tag.cbtutils.visitor;

import java.io.File;

public class OrVisitor extends Visitor {
	private Visitor[] conditions;
	
	public OrVisitor(Visitor... conditions) {
		super();
		this.conditions = conditions;
	}

	@Override
	protected boolean check(final File fileName) {
		for(Visitor condition : conditions) {
			if (condition.visit(fileName)) {
				return true;
			}
		}
		return false;
	}
}
