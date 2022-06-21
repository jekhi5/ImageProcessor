package gui.swingview;

import javax.swing.*;

public class Tester {
  public static void main(String[] args) {
//    JFrame popup = new PopupDialog("Hello World");
    JFrame test = new JFrame("Menu test");
    test.setJMenuBar(new MenuBar());
    test.setSize(400, 300);
    test.setVisible(true);
    test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    test.setLocationRelativeTo(null);
  }
}
