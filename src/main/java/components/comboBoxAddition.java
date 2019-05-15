package components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;


public class comboBoxAddition extends JFrame{
	
	ArrayList<String> item = new ArrayList<String>();
	JComboBox cb;
	JButton save = new JButton("Save");
	
	public comboBoxAddition() {
		// TODO Auto-generated constructor stub
		setSize(500,500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		
		  item.add("Select Item");
	      item.add("RG_UPS");
	      item.add("RG_Stablizer");
	      item.add("Add New Item");
	      
	      cb=new JComboBox(item.toArray());
	      cb.setBounds(50,50,100,25);
	      
	      save.setBounds(50,90,100,25);
			save.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e){
	            	 if(cb.getSelectedItem().toString().equals("Add New Item")){
	       	    	  String newItem=JOptionPane.showInputDialog("Enter Item");
	       	    	  item.add(newItem);
	            	}
	            }
	            
	        }); 
	      
		add(cb);
		add(save);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new comboBoxAddition();
	}

}