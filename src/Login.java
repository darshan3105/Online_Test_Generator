import java.awt.Component;
import java.awt.Font;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class Login extends JFrame implements ActionListener {

	JLabel loginLabel = new JLabel("LOGIN");
	JLabel UsernameLabel = new JLabel("Username:");
	JLabel passwordLabel = new JLabel("Password:");
	
	JTextField usernameField = new JTextField();
	JPasswordField passwordField = new JPasswordField();
	JButton loginButton = new JButton("Login");

	
	Login(String title){
		
		setTitle(title);
		setLayout(null);
		setSize(500,300);
		setVisible(true);
		
		loginLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		loginLabel.setBounds(174, 11, 90, 32);
		this.getContentPane().add(loginLabel);
		
		UsernameLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		UsernameLabel.setBounds(35, 107, 77, 14);
		this.getContentPane().add(UsernameLabel);
		
		passwordLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		passwordLabel.setBounds(35, 147, 77, 14);
		this.getContentPane().add(passwordLabel);
		
		usernameField.setBounds(108, 105, 156, 20);
		this.getContentPane().add(usernameField);
		usernameField.setColumns(10);
		
		passwordField.setBounds(108, 145, 156, 20);
		this.getContentPane().add(passwordField);
		
		loginButton.setBounds(163, 210, 89, 23);
		this.getContentPane().add(loginButton);
		
		loginButton.addActionListener(this);
		
	}
	
	
	
	public void actionPerformed(ActionEvent e) {
		
		JButton source=(JButton)e.getSource();
		JFrame topframe = (JFrame) SwingUtilities.getWindowAncestor(source);
		Login parent=(Login) topframe;
		
		Component components[] = parent.getContentPane().getComponents();
		String username= ((JTextField)components[3]).getText();
		String password= ((JPasswordField)components[4]).getText();
		
		if(username.equals("username") && password.equals("password")) {
			
			parent.dispose();
			
			Begin home=new Begin("OOP Project");
			home.ancestor=home;
			home.setName("home");
			
			JLabel label = new JLabel("Welcome to BITSAT 2018 Question Portal");
			label.setFont(new Font("Stencil", Font.PLAIN, 15));
			label.setBounds(57, 11, 361, 14);
			home.getContentPane().add(label);
			
			JButton insertJButton = new JButton("Insert");
			insertJButton.setName("INSERT");
			insertJButton.setBounds(36, 92, 89, 23);
			home.getContentPane().add(insertJButton);
			
			JButton editJButton = new JButton("Edit");
			editJButton.setBounds(36, 151, 89, 23);
			editJButton.setName("EDIT");
			home.getContentPane().add(editJButton);
			
			JButton deleteJButton = new JButton("Delete");
			deleteJButton.setBounds(36, 206, 89, 23);
			deleteJButton.setName("DELETE");
			home.getContentPane().add(deleteJButton);

			JButton generateJButton = new JButton("Generate Test");
			generateJButton.setBounds(217, 151, 139, 23);
			generateJButton.setName("GENERATE");
			home.getContentPane().add(generateJButton);
			
			home.addWindowListener(home);
			insertJButton.addActionListener(home);
			editJButton.addActionListener(home);
			deleteJButton.addActionListener(home);
			generateJButton.addActionListener(home);
		}
		else
			JOptionPane.showMessageDialog(null, "Invalid username or password");
	}

}
