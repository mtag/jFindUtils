package org.m_tag.cbtutils.locate.mlocate;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import org.m_tag.cbtutils.IllegalFIleFormatException;
import org.m_tag.cbtutils.visitor.Visitor;

/**
 * locate(findutils) DBファイル.
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
				// read offset
				int offset = in.get();
				if (offset == -128) {
					// large offset
					offset = in.getShort();
				} else if (offset > 0x80) {
					// minus
					offset = (~offset) & 0xff;
				}
				// offset
				start += offset;

				int index = start;
				// read filename
				int b;
				while ((b = in.get()) != 0) {
					if (index >= buffer.length) {
						// extend buffer
						byte[] newBuffer = new byte[buffer.length + BUFFER_UNIT_SIZE];
						System.arraycopy(buffer, 0, newBuffer, 0, buffer.length);
						buffer = newBuffer;
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
				String replaced = fileName;
				if (replacements != null) {
					for(String[] entry : replacements) {
						if (replaced.startsWith(entry[0])) {
							replaced = entry[1] + replaced.substring(entry[0].length());
						}
					}
				}
				visitor.visit(new File(replaced));
			}
			// keep last buffer size for this db file to init buffer with the size in next find.
			lastBufferSize = buffer.length;
		}
	}
}
