/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UserInterfaces.GUI;

import java.util.Observable;
import java.util.Observer;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author IstuvaHarka
 */
public class DataTableModel extends AbstractTableModel implements Observer {

    @Override
    public int getRowCount() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getColumnCount() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void update(Observable o, Object arg) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    
}
