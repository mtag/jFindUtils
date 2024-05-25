package org.m_tag.cbtutils.visitor;

import java.nio.file.Path;


public class OrVisitor extends Visitor {
	private Visitor[] conditions;
	
	public OrVisitor(Visitor... conditions) {
		super();
		this.conditions = conditions;
	}

	@Override
	public boolean check(final Path path) {
		for(Visitor condition : conditions) {
			if (condition.check(path)) {
				return true;
			}
		}
		return false;
	}
}
