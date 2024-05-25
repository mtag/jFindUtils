package org.m_tag.cbtutils.find;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashSet;
import java.util.stream.Stream;
import java.io.IOException;

import org.m_tag.cbtutils.Finder;
import org.m_tag.cbtutils.visitor.Visitor;

/**
 * Find Paths from Path systems.
 * @author mtag@m-tag.org
 */
public class FindFile extends Finder {
	private final Path root;
	/**
	 * constructor
	 * @param root root folder to find
	 */
	public FindFile(final String root) {
		this(Path.of(root));
	}
	/**
	 * constructor 
	 * @param root root folder to find
	 */
	public FindFile(final Path root) {
		super();
		this.root = root;
	}

	/**
	 * Execute find.
	 * @param visitor Path name checker whether it is target or not.
	 * @param acceptor accepts targeted Path name
	 * @throws IOException read error in finding
	 */
	protected void find(final Visitor visitor, final LinkedHashSet<Path> set) 
			throws IOException {
		find(visitor, set, root);
	}

	
	/**
	 * Execute find.
	 * @param visitor 
	 * @param acceptor accepts targeted Path name
	 * @throws IOException
	 */
	private void find(final Visitor visitor, final LinkedHashSet<Path> set, final Path path) 
			throws IOException {
		if (Files.exists(path)) {
			if (visitor.check(path)) {
				set.add(path);
			}
			set.add(path);
			if (Files.isDirectory(path)) {
				try(final Stream<Path> list = Files.list(path)){
					list.forEach(child -> {
						try {
							find(visitor, set, child);
						} catch(IOException ex) {
							throw new RuntimeException(ex);
						}
					});
				}
			}
		}
	}
}
