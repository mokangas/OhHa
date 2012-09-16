package UserInterfaces.GUI;

import javax.swing.table.DefaultTableModel;

/**
 * A custom TableModel, whose only difference to
 * <code>DefaultTableModel</code> is that the cells aren't editable.
 *
 * @author mokangas
 */
public class DataTableModel extends DefaultTableModel {

    /**
     * Constructor.
     * @param data The data to be displayed on the table.
     * @param columnNames Column names.
     */
    public DataTableModel(Object[][] data, String[] columnNames) {
        super(data, columnNames);
    }

    /**
     * Tells if the specified cell is editable.
     *
     * @param row The cell's row.
     * @param col The cell's column.
     * @return Always false.
     */
    @Override
    public boolean isCellEditable(int row, int col) {
        return false;
    }
}