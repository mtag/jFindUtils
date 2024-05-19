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
	    boolean isFirst = true;
	    byte[] buffer = new byte[65535];
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

				final int start = prevLength + offset;
				int index = start;
				int beginning = in.position();
				int b;
				while(((b = in.get())) != 0 ) {
					index ++;
				}
				int last = in.position();
				
				in.position(beginning);
				int size = last - beginning - 1;
				byte[] add = new byte[size];
				in.get(add	);
				System.arraycopy(add, 0, buffer, start, size);
				byte[] buff = buffer;
				prevLength  = start;
				String value = new String(buff, 0, size + start);
				
				//System.out.println((size+start) + "\t" + index + "\t"+ value);
				in.position(last);
				
				if (isFirst) {
					if (offset != 0 || !value.equals(MAGIC_NUMBER)) {
						throw new IllegalFIleFormatException("Illegal magic number");
					}
					isFirst = false;
				}
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
