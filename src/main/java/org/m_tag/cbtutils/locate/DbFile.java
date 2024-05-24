package org.m_tag.cbtutils.locate;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import org.m_tag.cbtutils.IllegalFIleFormatException;
import org.m_tag.cbtutils.visitor.Visitor;

/**
 * locate(findutils) DB file.
 * 
 * @see <a href=
 *      "https://www.gnu.org/software/findutils/manual/html_node/find_html/LOCATE02-Database-Format.html">LOCATE02 Database Format</a>
 */
public class DbFile {

	private static final int BUFFER_UNIT_SIZE = 256;

	private static final String MAGIC_NUMBER = "LOCATE02";

	private final File file;
	
	private final String[][] replacements;

	private int lastBufferSize = BUFFER_UNIT_SIZE;
	
	/**
	 * constructor.
	 * 
	 * @param file locate.db file
	 */
	public DbFile(final File file)  {
		this(file, null);
	}

	/**
	 * constructor
	 * 
	 * @param file locate.db file
	 */
	public DbFile(final File file, final String[][] replacements)  {
		this.file = file;
		this.replacements = replacements;
		for(String[] entry : replacements) {
			if (entry.length != 2) {
				throw new IllegalArgumentException("values must be array of array[2]");
			}
		}
	}
	
	/**
	 * Execute find.
	 * @param visitor 
	 * @throws IOException
	 * @throws IllegalFIleFormatException
	 * @throws FileNotFoundException
	 */
	public void find(Visitor visitor) 
			throws IOException, IllegalFIleFormatException {
		final long length = file.length();
		boolean isFirst = true;
		byte[] buffer = new byte[lastBufferSize];
		int start = 0;
		try (RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r")) {
			MappedByteBuffer in = randomAccessFile.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, length);

			while (in.position() < length) {
				int offset = readOffset(in);
				// offset
				start += offset;

				int index = start;
				// read filename
				int b;
				while ((b = in.get()) != 0) {
					if (index >= buffer.length) {
						buffer = extendBuffer(buffer);
					}
					buffer[index++] = (byte) b;
				}
				String fileName = new String(buffer, 0, index);

				if (isFirst) {
					if (offset != 0 || !fileName.equals(MAGIC_NUMBER)) {
						throw new IllegalFIleFormatException("Illegal magic number");
					}
					isFirst = false;
					continue;
				} 
				// check
				visitor.visit(new File(replace(fileName)));
			}
			// keep last buffer size for this db file to init buffer with the size in next find.
			lastBufferSize = buffer.length;
		}
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

	/**
	 * extends buffer size.
	 * @param buffer original buffer
	 * @return extended buffer
	 */
	private byte[] extendBuffer(byte[] buffer) {
		// extend buffer
		byte[] newBuffer = new byte[buffer.length + BUFFER_UNIT_SIZE];
		System.arraycopy(buffer, 0, newBuffer, 0, buffer.length);
		return newBuffer;
	}
}
