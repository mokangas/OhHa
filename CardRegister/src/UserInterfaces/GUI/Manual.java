/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UserInterfaces.GUI;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

/**
 *
 * @author IstuvaHarka
 */
public class Manual extends JDialog {
    
    public Manual(JFrame owner){
        super(owner);
        
        setTitle("Ohje");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        createComponents();
        pack();
        setVisible(true);
    }

    private void createComponents() {
        JTextArea textArea = new JTextArea("werjhewrkjhasfj,h\nkjhgsadfjhgasdfjgh"
                + "\nasdfkjhgasdfjgh\nmasdfkhgsdfkjghasdf\nsdflasdfkhjasdfklh\nsdfsdfhsdfhkl");
        textArea.setEditable(false);
        add(textArea);
    }
    
}
