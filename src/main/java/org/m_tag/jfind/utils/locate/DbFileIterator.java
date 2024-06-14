package org.m_tag.jfind.utils.locate;

import java.io.Closeable;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.NoSuchElementException;
import org.m_tag.jfind.utils.FindIterator;

/**
 * locate(findutils) DB file Iterator.
 *
 * @see <a href=
 *      "https://www.gnu.org/software/findutils/manual/html_node/find_html/LOCATE02-Database-Format.html">LOCATE02
 *      Database Format</a>
 */
public class DbFileIterator extends FindIterator implements Closeable {

  private static final int BUFFER_UNIT_SIZE = 256;

  private static final String MAGIC_NUMBER = "LOCATE02";
  
  /**
   * replacement for path names.
   */
  private final String[][] replacements;

  /**
   * buffer for building Sting.
   */
  private byte[] buffer;

  /**
   * buffer for db file.
   */
  private final MappedByteBuffer in;
  /**
   * size of db file.
   */
  private final long length;

  /**
   * db file.
   */
  private final RandomAccessFile randomAccessFile;
  /**
   * replacements of folder names.
   */
  private final DbFile file;
  /**
   * start position of string.
   */
  private int start;

  /**
   * constructor.
   *
   * @param file locate.db file
   */
  DbFileIterator(final DbFile file, String[]... replacements) throws IOException {
    super();
    this.file = file;
    this.replacements = replacements == null ? new String[0][] : replacements;
    for (String[] entry : this.replacements) {
      if (entry.length != 2) {
        throw new IllegalArgumentException("values must be array of array[2]");
      }
    }
    this.length = Files.size(file.getPath());
    buffer = new byte[BUFFER_UNIT_SIZE];
    start = 0;
    this.randomAccessFile = new RandomAccessFile(file.getPath().toFile(), "r");
    this.in = randomAccessFile.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, length);

    if (!this.hasNext() || !this.next().toString().equals(MAGIC_NUMBER)) {
      throw new IOException("no magic number");
    }
  }

  @Override
  public void close() throws IOException {
    this.randomAccessFile.close();
  }

  /**
   * extends buffer size.
   *
   * @param buffer original buffer
   * @return extended buffer
   */
  private void extendBuffer() {
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
    final int offset = readOffset(in);
    // offset
    start += offset;

    int index = start;
    // read filename
    int b;
    while ((b = in.get()) != 0) {
      if (index >= buffer.length) {
        extendBuffer();
      }
      buffer[index++] = (byte) b;
    }
    final String fileName = new String(buffer, 0, index, file.getCharset());
    return Path.of(replace(fileName));
  }

  /**
   * replace pathnames.
   *
   * @param fileName originalFileName
   * @return replaced fileName with replacements
   */
  public String replace(final String fileName) {
    for (String[] entry : replacements) {
      if (fileName.startsWith(entry[0])) {
        return entry[1] + fileName.substring(entry[0].length());
      }
    }
    return fileName;
  }

  /**
   * Read offset from top of buffer.
   *
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
}
