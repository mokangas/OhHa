package CardStorage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * This class is used to save register to a file. The save format is legible to
 * <code>FileLoader</code>. This class uses the string <code>">#>#>#:::"</code>
 * to separate information fields from each other. Different cards aren't
 * separated by any other thing than the number of fields. That is: even empty
 * content is written to the file.
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
