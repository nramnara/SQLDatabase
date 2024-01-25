package A02App;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;

public class A02App implements ActionListener {
	
	private JPanel panel;
	private JFrame frame;
	private JButton button;
	private JTextField textField;
	private JLabel label1;
	private JLabel label2;
	
	private String inputText;
	
	
	private A02App() {
		
		panel = new JPanel();
		frame = new JFrame();
		textField = new JTextField();
		button = new JButton("Submit");
		label1 = new JLabel("Enter your student ID:");
		label2 = new JLabel("Your earlier number of visits are: X");
		
		button.addActionListener(this);
		
		panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        panel.setLayout(new GridLayout(0, 1));
        
        panel.add(label1);
        panel.add(textField);
        panel.add(button);
        panel.add(label2);
        
        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("A02App");
        frame.pack();
        frame.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
//		visits++;
//		label2.setText(textField.getText());
		inputText = textField.getText();
		Connect();
		
	}
	
	public void setText(int visits) {
		label2.setText("Your earlier number of visits are: " + visits);
	}
	
	public void Connect() {
		
		String queryInsert = "INSERT INTO 3421A02Data (StudentID, VisitCount, LastVisit) "
				+ "VALUES (" + inputText + ", 0, DATE '2023-11-10')";
		String querySelect = "SELECT * FROM 3421A02Data "
				+ "WHERE StudentID = '" + inputText + "'";
		
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://srv572.hstgr.io/u912419555_3421a02","u912419555_3421user","A02@eecs3421");
			System.out.println("Connected to: " + con.toString());
			
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(querySelect);
			
			if(rs.next() != true) {
				st.executeUpdate(queryInsert);
			}
			
			rs = st.executeQuery(querySelect);
			rs.next();
			
			setText(rs.getInt(2));
			
			System.out.print(rs.getString(1) + " "); System.out.print(rs.getString(2) + " "); System.out.println(rs.getString(3));
			
			
			String queryUpdate = "UPDATE 3421A02Data "
					+ "SET VisitCount = '" + (rs.getInt(2) + 1) + "', LastVisit = NOW() "
					+ "WHERE StudentID = " + inputText;
			
			st.executeUpdate(queryUpdate);
			System.out.println("done");
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new A02App();
	}
}
