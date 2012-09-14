/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CardStorage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author IstuvaHarka
 */
public class FileSaver {

    private File file;
    private FileWriter writer;
    
    public FileSaver(File file) throws IOException{
        this.file = file;
        this.writer = new FileWriter(file);
    }
    
    public void save(String[][] data) throws IOException{
        writer.flush();
        for (int i = 0; i <data.length; i++) {
            writer.append("<#card>").append("\n");
            for (int j = 0; j < data[i].length; j++) {
                writer.append("<#field>").append("\n");
                writer.append(data[i][j]);
            }
        }
    }
}
