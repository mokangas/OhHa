package UserInterfaces.GUI;

import java.awt.Dimension;
import java.io.InputStream;
import java.util.Scanner;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;

/**
 * The help window. The only function of this is to tell to the user how to use
 * the program.
 *
 * @author mokangas
 */
public class Manual extends JDialog {

    /**
     * Constructor.
     *
     * @param owner The frame that launches this dialog.
     */
    public Manual(JFrame owner) {
        super(owner);

        setTitle("Ohje");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        createComponents();
        setPreferredSize(new Dimension(500, 500));
        pack();
        setVisible(true);
    }

    /**
     * Creates the components of this dialog.
     */
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
