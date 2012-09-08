/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UserInterfaces.GUI;

import javax.swing.table.DefaultTableModel;

/**
 *
 * @author IstuvaHarka
 */
public class DataTableModel extends DefaultTableModel {
    
    public DataTableModel(Object[][] data, String[] columnNames){
        super(data, columnNames);
    }
    
    @Override
    public boolean isCellEditable(int row, int col){
        return false;
    }
}