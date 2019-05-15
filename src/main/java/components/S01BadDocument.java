package components;

//Code from: http://www.orbital-computer.de/JComboBox/
/*
Inside JComboBox: adding automatic completion

Author: Thomas Bierhance
      thomas@orbital-computer.de
*/

/*
Catching user input

Although there are possibly many ways to catch the input to a text component, only one is 
shown here. It is the same that once has been suggested by Sun's Java Tutorial to add 
validation to text components (replaced by JFormattedTextField ). It suggests to get control 
over the user input by implementing a specialized Document overriding the methods 
insertString and remove. Input to the text component can then be rejected, accepted or 
transformed in these two methods.

Let us start with an editable combo box that is not accepting anything to demonstrate how to 
change the editor component's document.
*/
import javax.swing.*;
import javax.swing.text.*;

public class S01BadDocument extends PlainDocument {
  
  public void insertString(int offs, String str, AttributeSet a) {
      // reject the insert but print a message on the console
      System.out.println("insert " + str + " at " + offs);
  }
  
  private static void createAndShowGUI() {
      // the combo box (add/modify items if you like to)
      JComboBox comboBox = new JComboBox(new Object[] {"Ester", "Jordi", "Jordina", "Jorge", "Sergi"});
      // has to be editable
      comboBox.setEditable(true);
      // get the combo boxes editor component
      JTextComponent editor = (JTextComponent) comboBox.getEditor().getEditorComponent();
      // change the editor's document
      editor.setDocument(new S01BadDocument());
      
      // create and show a window containing the combo box
      JFrame frame = new JFrame();
      frame.setDefaultCloseOperation(3);
      frame.getContentPane().add(comboBox);
      frame.pack(); frame.setVisible(true);
  }
  
  public static void main(String[] args) {
      javax.swing.SwingUtilities.invokeLater(new Runnable() {
          public void run() {
              createAndShowGUI();
          }
      });
  }
}
