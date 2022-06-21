package gui.swingview;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

/**
 * Represents a window that only shows a text message and a close button.
 */
public class PopupDialog extends JFrame implements ImageEditorSwingFeature {

  private static final int WIDTH = 300;
  private static final int MAX_HEIGHT = 500;

  /**
   * Creates a new popup dialog.
   *
   * @param msg the message to display
   */
  public PopupDialog(String msg) {
    super("Image Processor");

    JTextPane textPane = new JTextPane();
    textPane.setEnabled(true);
    textPane.setEditable(false);

    textPane.setBackground(new Color(234, 234, 234));
    textPane.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
    textPane.setBorder(BorderFactory.createTitledBorder("Notice:"));

    StyledDocument styledDocument = textPane.getStyledDocument();
    SimpleAttributeSet centeringAttribute = new SimpleAttributeSet();
    StyleConstants.setAlignment(centeringAttribute, StyleConstants.ALIGN_CENTER);
    styledDocument.setParagraphAttributes(0, styledDocument.getLength(), centeringAttribute, false);

    Style styledText = textPane.addStyle("", null);
    StyleConstants.setForeground(styledText, new Color(109, 109, 109));
    try {
      styledDocument.insertString(0, msg, styledText);
    } catch (BadLocationException e) {
      textPane.setText(msg);
    }

    int height = Math.min((int) (Math.ceil(textPane.getText().length() / (double) WIDTH)) * 112,
            MAX_HEIGHT);
    this.setSize(WIDTH, height);
    this.add(textPane);
    this.setVisible(true);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLocationRelativeTo(null);
  }
}
