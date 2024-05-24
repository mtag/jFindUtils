package org.m_tag.cbtutils.visitor;

import java.io.File;

public class AndVisitor extends Visitor {
	private Visitor[] conditions;
	
	public AndVisitor(Visitor... conditions) {
		super();
		this.conditions = conditions;
	}

	@Override
	protected boolean check(final File fileName) {
		for(Visitor condition : conditions) {
			if (!condition.visit(fileName)) {
				return false;
			}
		}
		return true;
	}
}
