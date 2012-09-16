package CardStorage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * This class is used to save register to a file. The save format is legible to
 * <code>FileLoader</code>, but not to
 * <code>TextFileLoader</code>. This allows multiple lines in a text field of a
 * card, but not the a substring
 * <code>">#>#>#:::"</code>.
 *
 * @see FileLoader
 * @author mokangas
 */
public class FileSaver {

    private File file;
    private FileWriter writer;

    /**
     * The constructor.
     *
     * @param file The file to which the register will be saved to.
     * @throws IOException If the save doesn't succeed.
     */
    public FileSaver(File file) throws IOException {
        this.file = file;
        this.writer = new FileWriter(file);
    }

    /**
     * Saves the given data using string
     * <code>">#>#>#:::"</code> as the delimiter.
     *
     * @param data The data to be saved.
     * @throws IOException If the save doesn't succeed.
     */
    public void save(String[][] data) throws IOException {
        writer.flush();
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length; j++) {
                writer.append(data[i][j]).append(">#>#>#:::");
            }
        }
        writer.close();
    }
}
