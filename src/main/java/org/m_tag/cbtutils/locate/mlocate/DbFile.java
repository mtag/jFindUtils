package org.m_tag.cbtutils.locate.mlocate;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * locate(findutils) DBファイル.
 * @see <a href="https://docs.oracle.com/cd/E88353_01/html/E37852/mlocate-db-5.html">mlocate.db</a>
 */
public class DbFile {

	private static final String MAGIC_NUMBER = "LOCATE02";

	/**
	 * コンストラクタ
	 * @param file mlocate.dbファイル
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 * @throws IllegalFIleFormatException 
	 */
	public DbFile(final File file) throws IllegalFIleFormatException, FileNotFoundException, IOException {
		super();

	    final long length = file.length();
	    byte[] prev =null;
	    int prevLength = 0;
		try(RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r")) {
			MappedByteBuffer in = randomAccessFile
                .getChannel().map(FileChannel.MapMode.READ_ONLY, 0, length);

			while(in.position()<length) {
				// read entry
				int offset = in.get();
				if (offset == -128) {
					offset = in.getShort();
				} else if (offset > 0x80) {
					offset = (~offset) & 0xff;
				}
				
				int beginning = in.position();
				int b;
				while(((b = in.get())) != 0 ) {
				}
				int last = in.position();
				
				in.position(beginning);
				int size = last - beginning - 1;
				byte[] buff = new byte[size];
				in.get(buff	);
				int start = prevLength + offset;
				byte[] concatenated  = new byte[size + start];
				if (prev != null) {
					System.arraycopy(prev, 0, concatenated, 0, start);
				}
				System.arraycopy(buff, 0, concatenated, start, size);
				buff = concatenated;
				prevLength  = start;
				String value = new String(buff);
				
				System.out.println(value);
				in.position(last);
				
				if (prev == null) {
					if (offset != 0 || !value.equals(MAGIC_NUMBER)) {
						throw new IllegalFIleFormatException("Illegal magic number");
					}
				}
				prev = buff;
			}
		}
	}
	public static void main(String[] args) {
			try {
				DbFile file = new DbFile(new File("Y:\\.db\\y.db"));
				/*for(Entry entry : file.getEntries()) {
					System.out.println(entry.getValue());
				}*/
			} catch (IllegalFIleFormatException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}
}
