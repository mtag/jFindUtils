package org.m_tag.jfindutils.locate;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.NoSuchElementException;

import org.m_tag.jfindutils.FindIterator;

/**
 * locate(findutils) DB file Iterator
 * 
 * @see <a href=
 *      "https://www.gnu.org/software/findutils/manual/html_node/find_html/LOCATE02-Database-Format.html">LOCATE02 Database Format</a>
 */
public class DbFileIterator extends FindIterator implements Closeable {

	private static final int BUFFER_UNIT_SIZE = 256;

	private static final String MAGIC_NUMBER = "LOCATE02";

	/**
	 * buffer for building Sting.
	 */
	private byte[] buffer;

	/**
	 * buffer for db file.
	 */
	private MappedByteBuffer in;
	/**
	 * size of db file.
	 */
	private final long length;	

	/**
	 * db file.
	 */
	private RandomAccessFile randomAccessFile;
	/**
	 * replacements of folder names;
	 */
	private final String[][] replacements;
	/**
	 * 
	 */
	private int start;
	/**
	 * constructor.
	 * 
	 * @param file locate.db file
	 */
	public DbFileIterator(final File file, final String[]... replacements) throws IOException  {
		this(file.toPath(), replacements);
	}
	/**
	 * constructor
	 * 
	 * @param file locate.db file
	 */
	public DbFileIterator(final Path path, final String[]... replacements) throws IOException {
		super();
		this.replacements = replacements;
		for(String[] entry : replacements) {
			if (entry.length != 2) {
				throw new IllegalArgumentException("values must be array of array[2]");
			}
		}	
		this.length = Files.size(path);
		buffer = new byte[BUFFER_UNIT_SIZE];
		start = 0;
		this.randomAccessFile = new RandomAccessFile(path.toFile(), "r");
		this.in = randomAccessFile.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, length);

		if (!this.hasNext() || !this.next().toString().equals(MAGIC_NUMBER)) {
			throw new IOException("no magic number");
		}
	}
	/**
	 * constructor.
	 * 
	 * @param file locate.db file
	 */
	public DbFileIterator(final String  fileName, final String[]... replacements)  throws IOException {
		this(Path.of(fileName), replacements);
	}
	
	@Override
	public void close() throws IOException {
		this.randomAccessFile.close();
	}
	/**
	 * extends buffer size.
	 * @param buffer original buffer
	 * @return extended buffer
	 */
	private void extendBuffer(byte[] buffer) {
		// extend buffer
		byte[] newBuffer = new byte[buffer.length + BUFFER_UNIT_SIZE];
		System.arraycopy(buffer, 0, newBuffer, 0, buffer.length);
		this.buffer = newBuffer;
	}
	@Override
	public boolean hasNext() {
		return in.position() < length;
	}
	@Override
	public Path next() {
		if (!hasNext()) {
			throw new NoSuchElementException("beyond the endof db file.");
		}
		int offset = readOffset(in);
		// offset
		start += offset;

		int index = start;
		// read filename
		int b;
		while ((b = in.get()) != 0) {
			if (index >= buffer.length) {
				extendBuffer(buffer);
			}
			buffer[index++] = (byte) b;
		}
		String fileName = new String(buffer, 0, index);
		return Path.of(replace(fileName));
	}

	/**
	 * Read offset from top of buffer.
	 * @param in buffer
	 * @return offset from last beginning of path.
	 */
	private int readOffset(final MappedByteBuffer in) {
		int offset = in.get();
		if (offset == -128) {
			// large offset
			offset = in.getShort();
		} else if (offset > 0x80) {
			// minus
			offset = (~offset) & 0xff;
		}
		return offset;
	}

	/**
	 * replace pathnames.
	 * @param fileName originalFileName
	 * @return replaced fileName with replacements
	 */
	private String replace(final String fileName) {
		if (replacements != null) {
			for(String[] entry : replacements) {
				if (fileName.startsWith(entry[0])) {
					return entry[1] + fileName.substring(entry[0].length());
				}
			}
		}
		return fileName;
	}
}
