package gui.swingview;

import java.awt.*;

import javax.swing.*;

/**
 * Represents a window that only shows a text message and a close button.
 */
public class PopupDialog extends JFrame {

  /**
   * Creates a new popup dialog.
   * @param msg the message to display
   */
  public PopupDialog(String msg) {
    super("ImageEditor");
    this.setSize(400, 300);
    this.setResizable(false);
    this.setLocationRelativeTo(null);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

    JLabel text = new JLabel(msg);
    text.setSize(250, 200);
    text.setAlignmentX(Component.CENTER_ALIGNMENT);
    text.setBorder(BorderFactory.createTitledBorder("JLabel"));

    JTextArea text2 = new JTextArea(msg);
    text2.setLineWrap(true);
    text2.setWrapStyleWord(true);
    text2.setEditable(false);

    text2.setSize(15, 10);
    text2.setAlignmentX(Component.CENTER_ALIGNMENT);
    text2.setBorder(BorderFactory.createTitledBorder("JTextArea"));

    JTextPane text3 = new JTextPane();
    text3.setText(msg);

    text3.setEditable(false);
    text3.setAlignmentX(Component.CENTER_ALIGNMENT);
    text3.setBorder(BorderFactory.createTitledBorder("JTextPane"));

    this.getContentPane().add(text);
    this.getContentPane().add(text2);
    this.getContentPane().add(text3);

    this.setVisible(true);
  }
}
