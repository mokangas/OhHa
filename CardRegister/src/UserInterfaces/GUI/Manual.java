/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UserInterfaces.GUI;

import java.awt.Dimension;
import java.io.InputStream;
import java.util.Scanner;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.WindowConstants;
import sun.applet.Main;

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
        setPreferredSize(new Dimension(500,500));
        pack();
        setVisible(true);
    }

    private void createComponents() {
        
        InputStream help = Manual.class.getClassLoader().getResourceAsStream("help.html");
        Scanner scanner = new Scanner(help);
        scanner.useDelimiter("\\Z");
        String content = scanner.next();
        
        JLabel textContainer = new JLabel(content);
        JScrollPane scrollP = new JScrollPane(textContainer);
        add(scrollP);
    }
    
}
