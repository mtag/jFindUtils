package org.m_tag.cbtutils.locate.mlocate;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

import org.m_tag.cbtutils.Visitor;

/**
 * locate(findutils) DBファイル.
 * 
 * @see <a href=
 *      "https://www.gnu.org/software/findutils/manual/html_node/find_html/LOCATE02-Database-Format.html">LOCATE02 Database Format</a>
 */
public class DbFile {

	private static final String MAGIC_NUMBER = "LOCATE02";

	private final File file;

	/**
	 * コンストラクタ
	 * 
	 * @param file mlocate.dbファイル
	 * @throws IOException
	 * @throws FileNotFoundException
	 * @throws IllegalFIleFormatException
	 */
	public DbFile(final File file) throws IllegalFIleFormatException, FileNotFoundException, IOException {
		super();
		this.file = file;

	}

	/**
	 * Execute find.
	 * @param visitor 
	 * @throws IOException
	 * @throws IllegalFIleFormatException
	 * @throws FileNotFoundException
	 */
	public void find(Visitor visitor) 
			throws IOException, IllegalFIleFormatException, FileNotFoundException {
		final long length = file.length();
		boolean isFirst = true;
		byte[] buffer = new byte[65535];
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
				while (((b = in.get())) != 0) {
					buffer[index++] = (byte) b;
				}
				String fileName = new String(buffer, 0, index);

				if (isFirst) {
					if (offset != 0 || !fileName.equals(MAGIC_NUMBER)) {
						throw new IllegalFIleFormatException("Illegal magic number");
					}
					isFirst = false;
				} else {
					// check
					visitor.visit(fileName);
				}
			}
		}
	}
}
