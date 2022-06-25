package gui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

/**
 * Represents a popup that supports text with escape sequences and colors it accordingly. This code
 * was copied and slightly modified from a stack overflow post. You can find it here: <a
 * href="https://stackoverflow.com/questions/6899282/ansi-colors-in-java-swing-text-fields">...</a>
 */
public class ColoredPopup extends JTextPane {

  static private final Color Black = Color.BLACK;
  static private final Color Red = Color.RED;
  static private final Color Blue = Color.BLUE;
  static private final Color Magenta = Color.MAGENTA;
  static private final Color Green = Color.GREEN;
  static private final Color Yellow = Color.YELLOW;
  static private final Color Cyan = Color.CYAN;
  static private final Color Orange = new Color(252, 161, 3);
  static private final Color Gray = Color.GRAY;
  static private final Color cReset = Color.BLACK;

  static private Color colorCurrent = cReset;
  private String remaining = "";

  public ColoredPopup(String text) {
    this.appendANSI(text);


    StyledDocument styledDocument = super.getStyledDocument();

    SimpleAttributeSet center = new SimpleAttributeSet();
    StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
    styledDocument.setParagraphAttributes(0, styledDocument.getLength(), center, false);

    Style styledText = super.addStyle("", null);
    StyleConstants.setForeground(styledText, new Color(109, 109, 109));

    this.setEnabled(true);
    this.setEditable(false);
    this.setBackground(new Color(234, 234, 234));
    this.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
  }


  private void appendColor(Color c, String s) {
    StyleContext sc = StyleContext.getDefaultStyleContext();
    AttributeSet aSet = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);
    int len = getDocument().getLength(); // same value as getText().length();
    setCaretPosition(len);  // place caret at the end (with no selection)
    setCharacterAttributes(aSet, false);
    replaceSelection(s); // there is no selection, so inserts at caret
  }

  private void appendANSI(String s) { // convert ANSI color codes first
    int aPos = 0;   // current char position in addString
    int aIndex; // index of next Escape sequence
    int mIndex; // index of "m" terminating Escape sequence
    String tmpString;
    boolean stillSearching; // true until no more Escape sequences
    String addString = remaining + s;
    remaining = "";

    if (addString.length() > 0) {
      aIndex = addString.indexOf("\u001B"); // find first escape
      if (aIndex ==
              -1) { // no escape/color change in this string, so just send it with current color
        this.appendColor(colorCurrent, addString);
        return;
      }
      // otherwise There is an escape character in the string, so we must process it

      if (aIndex > 0) { // Escape is not first char, so send text up to first escape
        tmpString = addString.substring(0, aIndex);

        this.appendColor(colorCurrent, tmpString);
        aPos = aIndex;

      }
      // aPos is now at the beginning of the first escape sequence

      stillSearching = true;
      while (stillSearching) {
        mIndex = addString.indexOf("m", aPos); // find the end of the escape sequence
        if (mIndex < 0) { // the buffer ends halfway through the ansi string!
          remaining = addString.substring(aPos);
          stillSearching = false;
          continue;
        } else {
          tmpString = addString.substring(aPos, mIndex + 1);
          colorCurrent = getANSIColor(tmpString);
        }
        aPos = mIndex + 1;
        // now we have the color, send text that is in that color (up to next escape)

        aIndex = addString.indexOf("\u001B", aPos);

        if (aIndex == -1) { // if that was the last sequence of the input, send remaining text
          tmpString = addString.substring(aPos);
          this.appendColor(colorCurrent, tmpString);
          stillSearching = false;
          continue; // jump out of loop early, as the whole string has been sent now
        }

        // there is another escape sequence, so send part of the string and prepare for the next
        tmpString = addString.substring(aPos, aIndex);
        aPos = aIndex;
        this.appendColor(colorCurrent, tmpString);

      } // while there's text in the input buffer
    }
  }

  private Color getANSIColor(String ANSIColor) {
    switch (ANSIColor) {
      case "\u001B[0;31m":
      case "\u001B[1;31m":
        return Red;
      case "\u001B[32m":
      case "\u001B[0;32m":
      case "\u001B[1;32m":
        return Green;
      case "\u001B[33m":
      case "\u001B[0;33m":
      case "\u001B[1;33m":
        return Yellow;
      case "\u001B[34m":
      case "\u001B[0;34m":
      case "\u001B[1;34m":
        return Blue;
      case "\u001B[35m":
      case "\u001B[0;35m":
      case "\u001B[1;35m":
        return Magenta;
      case "\u001B[36m":
      case "\u001B[0;36m":
      case "\u001B[1;36m":
        return Cyan;
      case "\u001B[0m":
        return cReset;
      case "\u001b[38;5;31m":
        return Gray;
      case "\u001B[31m":
        return Orange;
      default:
        return Black;
    }
  }
}
