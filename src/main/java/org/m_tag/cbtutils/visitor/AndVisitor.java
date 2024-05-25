package org.m_tag.cbtutils.visitor;

import java.nio.file.Path;


public class AndVisitor extends Visitor {
	private Visitor[] conditions;
	
	public AndVisitor(Visitor... conditions) {
		super();
		this.conditions = conditions;
	}

	@Override
	public boolean check(final Path path) {
		for(Visitor condition : conditions) {
			if (!condition.check(path)) {
				return false;
			}
		}
		return true;
	}
}
