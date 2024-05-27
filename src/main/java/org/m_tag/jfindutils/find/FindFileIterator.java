package org.m_tag.jfindutils.find;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import org.m_tag.jfindutils.FindIterator;

/**
 * Iterator for files and directories(including sub directories) in path. 
 */
public class FindFileIterator  extends FindIterator  {
	/**
	 * Iterator for current fonder.
	 */
	private Iterator<Path> brothers;
	/**
	 * current path.
	 * <p/>
	 * return value of next()
	 */
	private Path current;
	/**
	 * previous return value of next().
	 */
	private Path prev;
	/**
	 * Iterator for upper folder.
	 */
	private final Deque<Iterator<Path>> stack =  new ArrayDeque<>(64);
	
	/**
	 * constructor 
	 * @param root root folder to find
	 * @throws IOException 
	 */
	public FindFileIterator(final File file){
		this(file.toPath());
	}
	/**
	 * constructor 
	 * @param root root folder to find
	 * @throws IOException 
	 */
	public FindFileIterator(final Path root)  {
		super();
		this.stack.clear();
		this.brothers = null;
		this.prev = root;
		if (!Files.isDirectory(root)) {
			throw new DirectoryReadingException(String.format("%s is not director.", root));
		}
		//  next value on calling next()
		this.current = null;
	}
	/**
	 * constructor
	 * @param root root folder to find
	 * @throws IOException 
	 */
	public FindFileIterator(final String root)  {
		this(Path.of(root));
	}
	@Override
	public boolean hasNext() {
		if (current != null) {
			return true;
		}
		// depth first
		if (prev != null && Files.isDirectory(prev)) {
			listFiles(prev);
		}
		if (brothers.hasNext()) {
			// 現在フォルダ読み込み中
			current = brothers.next();
			return true;
		}
		if (stack.isEmpty()) {
			return false;
		}
		brothers = stack.pop();
		prev = null;
		return hasNext();
	}

	/**
	 * obtain iterator of files and directories in folder.
	 * @param folder iterator for files and directories in folder
	 */
	private void listFiles(final Path folder) {
		try {
			if (this.brothers != null) {
				this.stack.push(this.brothers);
			}
			try(final Stream<Path> files = Files.list(folder)) {
				 final List<Path> list = files.toList();
				 brothers =  list.iterator();
			}
		} catch(IOException ex) {
			throw new DirectoryReadingException(
					String.format("Error in reading directory:%s", folder.toString()), ex);
		}
	}
	@Override
	public Path next() {
		if (!hasNext()) {
			return null;
		}
		if (current == null) {
			return null;
		}
		prev = current;
		current = null;
		return prev;
	}
}
