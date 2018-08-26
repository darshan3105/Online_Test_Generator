/*@author Darshan Prabhune*/

/* The following application is a question portal for BITSAT 2018.
The user interface has been linked with phpMyAdmin for storing the data.
Following are the functionalities provided by the interface:

1. Insert- This functionality lets the user enter a question into the database with one of the following subjects
;Maths,Chemistry or Physics, and one of the following types; MCQ,short answer questions or true/false question.
The 'Insert' function does not accept a question if the same question already exists in the database.

2. Edit- This functionality lets the user update any question present in the database.The 'EDIT' window displays all
the questions present in the database.One of the question can be selected and modified using this window. The 'Edit' function
does not accept a question if it already exists in the database.

3. Delete- This functionality lets the user delete one or more questions from the database. The window displays all
the questions present in the database, of which one or more can be selected to delete.If the user wishes to delete
all the questions present in the database, he can use the select all chech box provided by the window.

4. Generate Test- This functionality lets the user generate a question set of the required number of questions.
The window displays the current status of the database(i.e. the number of questions present).The user has to enter
the number of questions he requires to be present in the question set. If the entered string is not between 0 and 
the total number of questions the function throws an exception.

5. Export - This function lets the user download the question set and the solution set in the form of a .txt file.
The path where the following files are saved are as follows:

Question set- c:/Users//user//Downloads//Question_file
Solution set- c:/Users//user//Downloads//Solution_file
*/

import java.awt.*;
import java.io.*;
import java.util.*;

import javax.security.auth.Subject;
import javax.swing.*;
import java.sql.*;
import java.awt.event.*;
import java.awt.event.*;
import java.util.concurrent.ThreadLocalRandom;


public class Begin extends JFrame  implements ActionListener,WindowListener{
	
	static Connection conn;
	Begin ancestor;
	
	Begin(String title){
		
		setTitle(title);
		setLayout(null);
		setSize(500,300);
		setVisible(true);
		addWindowListener(this);
	}
	
	public static void main(String args[]){
		
		Login login = new Login("OOP Project");
		
		
		try {
			
			final String JDBC_DRIVER = "com.mysql.jdbc.Driver";                  // JDBC Driver Name //
		    final String DB_URL = "jdbc:mysql://localhost:3306/question_bank";   // URL of your Database //
		    final String USER = "root";                                          // Database credentials //
		    final String PASS = "";
		    
		    Class.forName("com.mysql.jdbc.Driver");
		    conn = DriverManager.getConnection(DB_URL,USER,PASS);
		    System.out.println("This is conn "+conn);
		}
		catch(Exception e) {
			
			System.out.println("this is the ex "+e);
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		try {
			JButton source=(JButton)e.getSource();
			String name=source.getName();
			JFrame topframe = (JFrame) SwingUtilities.getWindowAncestor(source);
			Begin parent=(Begin) topframe;

			if(name.equals("INSERT")) {						//////////////////// Insert
				
				Begin insertWindow = new Begin("OOP Project");
				insertWindow.ancestor=parent;
				insertWindow.ancestor.setEnabled(false);
				insertWindow.setName("insertWindow");
				
				JRadioButton mathsRadio = new JRadioButton("Maths",true);  // 0
				mathsRadio.setName("maths");
				mathsRadio.setBounds(6, 32, 109, 23);
				insertWindow.getContentPane().add(mathsRadio);
				
				JRadioButton chemRadio = new JRadioButton("Chemistry"); //1
				chemRadio.setName("chemistry");
				chemRadio.setBounds(6, 58, 109, 23);
				insertWindow.getContentPane().add(chemRadio);
				
				JRadioButton phyRadio = new JRadioButton("Physics");  //2
				phyRadio.setName("physics");
				phyRadio.setBounds(6, 84, 109, 23);
				insertWindow.getContentPane().add(phyRadio);
				
				JLabel type = new JLabel("Select Type");  //3
				type.setName("type");
				type.setBounds(10, 139, 80, 14);
				insertWindow.getContentPane().add(type);
				
				JRadioButton mcqRadio = new JRadioButton("MCQ");  //4
				mcqRadio.setBounds(6, 171, 109, 23);
				mcqRadio.setName("mcq");
				insertWindow.getContentPane().add(mcqRadio);
				
				JRadioButton fibRadio = new JRadioButton("Fill in the blank");  //5
				fibRadio.setBounds(6, 197, 109, 23);
				fibRadio.setName("fib");
				insertWindow.getContentPane().add(fibRadio);
				
				JRadioButton tfRadio = new JRadioButton("True/False");  //6
				tfRadio.setBounds(6, 223, 109, 23);
				tfRadio.setName("tf");
				insertWindow.getContentPane().add(tfRadio);
				
				JLabel subjectLabel = new JLabel("Subject");  //7
				subjectLabel.setName("subject");
				subjectLabel.setBounds(10, 11, 46, 14);
				insertWindow.getContentPane().add(subjectLabel);
				
				ButtonGroup subjectGroup = new ButtonGroup();
				ButtonGroup typeGroup = new ButtonGroup();
				
				subjectGroup.add(mathsRadio);
				subjectGroup.add(chemRadio);
				subjectGroup.add(phyRadio);
				
				typeGroup.add(mcqRadio);
				typeGroup.add(fibRadio);
				typeGroup.add(tfRadio);
				
				//Panel for true/false
				/////////////////////////////////////////////////////
				
				Panel tfPanel = new Panel(); //8
				tfPanel.setName("tfPanel");
				tfPanel.setBounds(106, 11, 318, 235);
				insertWindow.getContentPane().add(tfPanel);
				
				tfPanel.setLayout(null);
				
				TextArea questionText = new TextArea(); //0
				questionText.setBounds(10, 10, 298, 98);
				tfPanel.add(questionText);
				
				JRadioButton trueRadio = new JRadioButton("True");
				trueRadio.setBounds(116, 114, 109, 23);
				tfPanel.add(trueRadio);
				
				JRadioButton falseRadio = new JRadioButton("False");
				falseRadio.setBounds(116, 147, 109, 23);
				tfPanel.add(falseRadio);
				
				JButton submittf = new JButton("Submit");
				submittf.setName("SUBMIT");
				submittf.setBounds(103, 201, 89, 23);
				tfPanel.add(submittf);
				
				ButtonGroup tfGroup = new ButtonGroup();
				
				tfGroup.add(trueRadio);
				tfGroup.add(falseRadio);
				
				tfPanel.setVisible(false);
				tfPanel.setEnabled(false);
				
				//Panel for MCQ
				/////////////////////////////////////////////////////
				
				Panel mcqPanel = new Panel();  //9
				mcqPanel.setName("mcqPanel");
				mcqPanel.setBounds(106, 11, 318, 240);
				insertWindow.getContentPane().add(mcqPanel);
				
				mcqPanel.setLayout(null);
				
				TextArea questionTextMcq = new TextArea();  //9(0)
				questionTextMcq.setBounds(10, 27, 298, 84);
				mcqPanel.add(questionTextMcq);
				
				JLabel questionLabelMcq = new JLabel("Question"); //9(1)
				questionLabelMcq.setBounds(10, 11, 104, 14);
				mcqPanel.add(questionLabelMcq);
				
				JTextField optionA = new JTextField(); //9(2)
				optionA.setBounds(10, 117, 133, 29);
				mcqPanel.add(optionA);
				optionA.setColumns(10);
				
				JTextField optionB = new JTextField(); //9(3)
				optionB.setColumns(10);
				optionB.setBounds(175, 117, 133, 29);
				mcqPanel.add(optionB);
				
				JTextField optionC = new JTextField(); //9(4)
				optionC.setColumns(10);
				optionC.setBounds(10, 146, 133, 29);
				mcqPanel.add(optionC);
				
				JTextField optionD = new JTextField(); //9(5)
				optionD.setColumns(10);
				optionD.setBounds(175, 146, 133, 29);
				mcqPanel.add(optionD);
				
				JButton submitMcq = new JButton("Submit"); //9(6)
				submitMcq.setName("SUBMIT");
				submitMcq.setBounds(219, 206, 89, 23);
				mcqPanel.add(submitMcq);
				
				
				JTextField answer = new JTextField(""); //9(7)
				answer.setColumns(10);
				answer.setBounds(10, 207, 133, 29);
				mcqPanel.add(answer);
				
				JLabel answerLabel = new JLabel("Answer"); //9(8)
				answerLabel.setBounds(10, 193, 46, 14);
				mcqPanel.add(answerLabel);
				
				mcqPanel.setVisible(false);
				mcqPanel.setEnabled(false);
				
				//Panel for FIB
				////////////////////////////////////////////////
				
				Panel fibPanel = new Panel();
				fibPanel.setBounds(106, 11, 318, 240);
				fibPanel.setName("fibPanel");
				insertWindow.getContentPane().add(fibPanel);
				
				fibPanel.setLayout(null);
				
				TextArea questionTextFib = new TextArea(); //0
				questionTextFib.setBounds(10, 27, 298, 84);
				fibPanel.add(questionTextFib);
				
				JLabel questionLabelFib = new JLabel("Question");
				questionLabelFib.setBounds(10, 11, 104, 14);
				fibPanel.add(questionLabelFib);
				
				JButton submitFib = new JButton("Submit");
				submitFib.setName("SUBMIT");
				submitFib.setBounds(114, 213, 89, 23);
				fibPanel.add(submitFib);
				
				TextArea answerTextFib = new TextArea();
				answerTextFib.setBounds(10, 142, 298, 65);
				fibPanel.add(answerTextFib);
				
				JLabel fibanswerLabel = new JLabel("Answer");
				fibanswerLabel.setBounds(10, 122, 46, 14);
				fibPanel.add(fibanswerLabel);
				
				fibPanel.setVisible(false);
				fibPanel.setEnabled(false);
				
				
				mcqRadio.addActionListener(insertWindow);
				fibRadio.addActionListener(insertWindow);
				tfRadio.addActionListener(insertWindow);
				submitFib.addActionListener(insertWindow);
				submitMcq.addActionListener(insertWindow);
				submittf.addActionListener(insertWindow);
				
			
			
			}
			else if(name.equals("EDIT")) {						/////////// EDIT	
				
				Begin editWindow = new Begin("OOP Project");
				editWindow.ancestor=parent;
				editWindow.ancestor.setEnabled(false);
				editWindow.setName("editWindow");
				
				ButtonGroup subjectGroup = new ButtonGroup();
				ButtonGroup typeGroup = new ButtonGroup();
				
				JRadioButton mathsRadio = new JRadioButton("Maths",true); //0
				mathsRadio.setName("maths");
				mathsRadio.setBounds(6, 32, 109, 23);
				editWindow.getContentPane().add(mathsRadio);
				
				JRadioButton chemRadio = new JRadioButton("Chemistry"); //1
				chemRadio.setName("chem");
				chemRadio.setBounds(6, 58, 109, 23);
				editWindow.getContentPane().add(chemRadio);
				
				JRadioButton phyRadio = new JRadioButton("Physics"); //2
				phyRadio.setName("phy");
				phyRadio.setBounds(6, 84, 109, 23);
				editWindow.getContentPane().add(phyRadio);
				
				subjectGroup.add(mathsRadio);
				subjectGroup.add(chemRadio);
				subjectGroup.add(phyRadio);
				
				JLabel type = new JLabel("Select Type"); //3
				type.setBounds(6, 114, 80, 14);
				editWindow.getContentPane().add(type);
				
				JRadioButton mcqRadio = new JRadioButton("MCQ",true);   //4
				mcqRadio.setBounds(6, 135, 109, 23);
				mcqRadio.setName("mcq");
				editWindow.getContentPane().add(mcqRadio);
				
				JRadioButton fibRadio = new JRadioButton("Fill in the blank");  //5
				fibRadio.setBounds(6, 161, 109, 23);
				fibRadio.setName("fib");
				editWindow.getContentPane().add(fibRadio);
				
				JRadioButton tfRadio = new JRadioButton("True/False"); //6
				tfRadio.setBounds(6, 187, 109, 23);
				tfRadio.setName("tf");
				editWindow.getContentPane().add(tfRadio);
				
				typeGroup.add(mcqRadio);
				typeGroup.add(fibRadio);
				typeGroup.add(tfRadio);
				
				JLabel subjectLabel = new JLabel("Subject");  //7
				subjectLabel.setBounds(10, 11, 46, 14);
				editWindow.getContentPane().add(subjectLabel);
				
				
				JButton nextButton = new JButton("Next");  //8
				nextButton.setName("NEXT");
				nextButton.setBounds(11, 228, 89, 23);
				editWindow.getContentPane().add(nextButton);
				
				
				//Panel for selecting question
				//////////////////////////////////////////////
				
				JScrollPane scrollPane = new JScrollPane(); //9
				scrollPane.setBounds(110, 6, 314, 197);
				scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
				scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
				editWindow.getContentPane().add(scrollPane);
				
				JLabel selectLabel = new JLabel("Select from the List");
				selectLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
				scrollPane.setColumnHeaderView(selectLabel);
				
				JPanel editpanel = new JPanel();
				editpanel.setName("editPanel");
				editpanel.setLayout(new BoxLayout(editpanel, BoxLayout.Y_AXIS));
				scrollPane.setViewportView(editpanel);
				
				JButton modifyButton = new JButton("Modify");   //10
				modifyButton.setName("MODIFY");
				modifyButton.setBounds(207, 228, 89, 23);
				editWindow.getContentPane().add(modifyButton);
				
				nextButton.addActionListener(editWindow);
				modifyButton.addActionListener(editWindow);
				
				String query ="SELECT * FROM `question_info`;";
				Statement stmt=conn.createStatement();
			    ResultSet resultSet = stmt.executeQuery(query);
			    ButtonGroup newGroup =  new ButtonGroup();
			    
			    while(resultSet.next()) {
			    	
			    	String question= resultSet.getString("question");
			    	String sr_no= resultSet.getString("sr_no");
			    	JRadioButton newBox = new JRadioButton(question);
			    	newBox.setName(sr_no);
			    	newGroup.add(newBox);
			    	editpanel.add(newBox);
			    }
				
				//Panel for modifying True/False question
				//////////////////////////////////////////////////////
				
				ButtonGroup modifyTfGroup = new ButtonGroup();
				
				JPanel tfpanel = new JPanel(); //11
				tfpanel.setBounds(121, 11, 305, 240);
				editWindow.getContentPane().add(tfpanel);
				tfpanel.setLayout(null);
				
				JLabel questionLabel = new JLabel("Question");  //(11) 0
				questionLabel.setBounds(10, 13, 74, 14);
				tfpanel.add(questionLabel);
				
				TextArea textArea = new TextArea(); //(11) 1
				textArea.setBounds(10, 38, 285, 64);
				tfpanel.add(textArea);
				
				JRadioButton trueRadio = new JRadioButton("True"); //(11) 2
				trueRadio.setBounds(118, 124, 109, 23);
				tfpanel.add(trueRadio);
				
				JRadioButton falseRadio = new JRadioButton("False");  //(11) 3
				falseRadio.setBounds(118, 150, 109, 23);
				tfpanel.add(falseRadio);
				
				JButton submittfButton = new JButton("Submit"); // (11) 4
				submittfButton.setName("SUBMITTF");
				submittfButton.setBounds(107, 206, 89, 23);
				tfpanel.add(submittfButton);
				
				
				modifyTfGroup.add(trueRadio);
				modifyTfGroup.add(falseRadio);
				
				tfpanel.setVisible(false);
				tfpanel.setEnabled(false);
				submittfButton.addActionListener(editWindow);
				
				//Panel for modifying Fill in the blank
				///////////////////////////////////////////////////////
				
				JPanel editFIBpanel = new JPanel(); //12
				editFIBpanel.setBounds(121, 11, 305, 240);
				editWindow.getContentPane().add(editFIBpanel);
				editFIBpanel.setLayout(null);
				
				JLabel editFibquestionLabel = new JLabel("Question");  //12(0)
				editFibquestionLabel.setBounds(10, 13, 74, 14);
				editFIBpanel.add(editFibquestionLabel);
				
				TextArea editFIBtextArea = new TextArea();  //12(1)
				editFIBtextArea.setBounds(10, 38, 285, 64);
				editFIBpanel.add(editFIBtextArea);
				
				JButton submitFIBButton = new JButton("Submit");//12(2)
				submitFIBButton.setName("SUBMITFIB");
				submitFIBButton.setBounds(107, 206, 89, 23);
				editFIBpanel.add(submitFIBButton);
				
				TextArea editFIBanswertextArea = new TextArea();  //12(3)
				editFIBanswertextArea.setBounds(10, 129, 285, 64);
				editFIBpanel.add(editFIBanswertextArea);
				
				JLabel editFIBanswerLabel = new JLabel("Answer");  //12(4)
				editFIBanswerLabel.setBounds(10, 108, 74, 14);
				editFIBpanel.add(editFIBanswerLabel);
				
				editFIBpanel.setVisible(false);
				editFIBpanel.setEnabled(false);
				submitFIBButton.addActionListener(editWindow);
				
				// Panel for modifying MCQ questions
				////////////////////////////////////////////////////////
				
				JPanel editMCQpanel = new JPanel();   //13
				editMCQpanel.setBounds(121, 11, 305, 240);
				editWindow.getContentPane().add(editMCQpanel);
				editMCQpanel.setLayout(null);
				
				JLabel editMcqquestionLabel = new JLabel("Question"); //13(0)
				editMcqquestionLabel.setBounds(10, 13, 74, 14);
				editMCQpanel.add(editMcqquestionLabel);
				
				TextArea editMCQtextArea = new TextArea();  //13(1)
				editMCQtextArea.setBounds(10, 38, 285, 64);
				editMCQpanel.add(editMCQtextArea);
				
				JButton submitMCQButton = new JButton("Submit");  //13(2)
				submitMCQButton.setName("SUBMITMCQ");
				submitMCQButton.setBounds(206, 206, 89, 23);
				editMCQpanel.add(submitMCQButton);
				
				JTextField optionA = new JTextField();  //13(3)
				optionA.setBounds(10, 108, 133, 31);
				editMCQpanel.add(optionA);
				optionA.setColumns(10);
				
				JTextField optionB = new JTextField();  //13(4)
				optionB.setColumns(10);
				optionB.setBounds(162, 108, 133, 31);
				editMCQpanel.add(optionB);
				
				JTextField optionC = new JTextField(); //13(5)
				optionC.setColumns(10);
				optionC.setBounds(10, 139, 133, 34);
				editMCQpanel.add(optionC);
				
				JTextField optionD = new JTextField();  //13(6)
				optionD.setColumns(10);
				optionD.setBounds(162, 139, 133, 34);
				editMCQpanel.add(optionD);
				
				JTextField answer = new JTextField(); //13(7)
				answer.setColumns(10);
				answer.setBounds(10, 206, 133, 23);
				editMCQpanel.add(answer);
				
				JLabel lblAnswer = new JLabel("Answer");  //13(8)
				lblAnswer.setBounds(10, 191, 46, 14);
				editMCQpanel.add(lblAnswer);
				
				editMCQpanel.setVisible(false);
				editMCQpanel.setEnabled(false);
				submitMCQButton.addActionListener(editWindow);
				
			}
			else if(name.equals("DELETE")) {               //////////////////////// DELETE
				
				Begin deleteWindow = new Begin("OOP Project");
				deleteWindow.ancestor=parent;
				deleteWindow.ancestor.setEnabled(false);
				deleteWindow.setName("deleteWindow");
				
				JScrollPane scrollPane = new JScrollPane();  // 0
				scrollPane.setBounds(10, 11, 414, 194);
				deleteWindow.getContentPane().add(scrollPane);
				
				JLabel lblSelectFromThe = new JLabel("Select from the list");
				lblSelectFromThe.setFont(new Font("Tahoma", Font.PLAIN, 14));
				scrollPane.setColumnHeaderView(lblSelectFromThe);
				
				JPanel panel = new JPanel();  
				scrollPane.setViewportView(panel);
				panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
				
				JCheckBox selectAllCheck = new JCheckBox("Select all"); //1
				selectAllCheck.setName("SELECTALL");
				selectAllCheck.setBounds(10, 209, 97, 23);
				deleteWindow.getContentPane().add(selectAllCheck);
				
				JButton delete = new JButton("Delete"); //2
				delete.setName("SUBMITDELETE");
				delete.setBounds(160, 227, 89, 23);
				deleteWindow.getContentPane().add(delete);
				
				selectAllCheck.addActionListener(deleteWindow);
				delete.addActionListener(deleteWindow);
				
				String query ="SELECT * FROM `question_info`;";
				Statement stmt=conn.createStatement();
			    ResultSet resultSet = stmt.executeQuery(query);
			    
			    while(resultSet.next()) {
			    	
			    	String question=resultSet.getString("question");
			    	String sr_no=resultSet.getString("sr_no");
			    	
			    	JCheckBox newBox = new JCheckBox(question);
			    	newBox.setName(sr_no);
			    	panel.add(newBox);
			    }
				
			}
			else if(name.equals("SUBMITDELETE")) {      ////////////////// SUBMIT-DELETE
				
				Component components[]=parent.getContentPane().getComponents();
				JViewport view=((JScrollPane)components[0]).getViewport();
				JPanel deletePanel = (JPanel) view.getView();
				Component panelComponents[] = deletePanel.getComponents();
				boolean flag=false;
				
				for(int i=0;i<panelComponents.length;i++) {
					
					if(((JCheckBox)panelComponents[i]).isSelected()) {
						flag=true;
						break;
					}
				}
				if(!flag) {
					JOptionPane.showMessageDialog(null, "No item selected");
				}
				else {
					
					 Statement stmt = conn.createStatement();
					 
					 for(int i=0;i<panelComponents.length;i++) {
						 
						 if(((JCheckBox)panelComponents[i]).isSelected()) {
							 
							 String sr_no=panelComponents[i].getName();
							 String query="DELETE FROM `question_info` WHERE sr_no LIKE '"+sr_no+"';";
							 stmt.executeUpdate(query);
							
						 }
					 }
					 JOptionPane.showMessageDialog(null, "Succesfully deleted");
					 deletePanel.removeAll();
					 
					String query ="SELECT * FROM `question_info`;";
					stmt=conn.createStatement();
					ResultSet resultSet = stmt.executeQuery(query);
					
					 while(resultSet.next()) {
					    	
					    	String question=resultSet.getString("question");
					    	String sr_no=resultSet.getString("sr_no");
					    	
					    	JCheckBox newBox = new JCheckBox(question);
					    	newBox.setName(sr_no);
					    	deletePanel.add(newBox);
					 }
					 deletePanel.revalidate();
					 deletePanel.repaint();
					 ((JCheckBox)components[1]).setSelected(false);
					 
					 
				}
				
			}
			else if(name.equals("MODIFY")) {                  //////////////// MODIFY
				
				Component components[] = parent.getContentPane().getComponents();
				JViewport view=((JScrollPane)components[9]).getViewport();
				JPanel editPanel = (JPanel) view.getView();
				
				Component panelComponents[]= editPanel.getComponents();
				String sr_no=null;
				
				
				for(int i=0;i<panelComponents.length;i++) {
					
					if(((JRadioButton)panelComponents[i]).isSelected()) {
						sr_no=((JRadioButton)panelComponents[i]).getName();
						break;
					}
				}
				if(sr_no==null) {
					JOptionPane.showMessageDialog(null, "No item selected");
				}
				else {
					
					String query ="SELECT * FROM `question_info`;";
					Statement stmt=conn.createStatement();
				    ResultSet resultSet = stmt.executeQuery(query);
				    
				    while(resultSet.next()) {
				    	
				    	String type=resultSet.getString("question_type");
				    	String subject=resultSet.getString("question_subject");
				    	String id=resultSet.getString("sr_no");
				    	if(id.equals(sr_no)) {
				    		
				    		if(type.equals("TF"))
				    			((JRadioButton)components[6]).setSelected(true);
				    		else if(type.equals("FIB"))
				    			((JRadioButton)components[5]).setSelected(true);
				    		else
				    			((JRadioButton)components[4]).setSelected(true);
				    		
				    		if(subject.equals("maths"))
				    			((JRadioButton)components[0]).setSelected(true);
				    		else if(subject.equals("chemistry"))
				    			((JRadioButton)components[1]).setSelected(true);
				    		else
				    			((JRadioButton)components[2]).setSelected(true);
				    	}
				    	
				    }
				    
					
					components[9].setVisible(false);
					components[9].setEnabled(false);
					components[10].setVisible(false);
					components[10].setEnabled(false);
					components[0].setEnabled(false);
					components[1].setEnabled(false);
					components[2].setEnabled(false);
					components[4].setEnabled(false);
					components[5].setEnabled(false);
					components[6].setEnabled(false);
					components[8].setEnabled(false);
					
					if(((JRadioButton)components[6]).isSelected()) {
						
						components[11].setVisible(true);
						components[11].setEnabled(true);
						
						Component tfpanelComponents[] = ((JPanel)components[11]).getComponents();
						
						query ="SELECT * FROM question_info";
						stmt=conn.createStatement();
					    resultSet = stmt.executeQuery(query);
				
					    while(resultSet.next()) {
					    	
					    	String id=resultSet.getString("sr_no");
					    	if(id.equals(sr_no))
					    		break;
					    }
					    
					    ((TextArea)tfpanelComponents[1]).setText(resultSet.getString("question"));
					    
					    if(resultSet.getString("option1").equals("true")) {
					    	((JRadioButton)tfpanelComponents[2]).setSelected(true);
					    }
					    else {
					    	((JRadioButton)tfpanelComponents[3]).setSelected(true);
					    }
					    
					    
					   
						
					}
					else if(((JRadioButton)components[5]).isSelected()) {
						
						components[12].setVisible(true);
						components[12].setEnabled(true);
						
						Component fibpanelComponents[] = ((JPanel)components[12]).getComponents();
						
						query ="SELECT * FROM question_info";
						stmt=conn.createStatement();
					    resultSet = stmt.executeQuery(query);
				
					    while(resultSet.next()) {
					    	
					    	String id=resultSet.getString("sr_no");
					    	if(id.equals(sr_no))
					    		break;
					    }
					    
					    ((TextArea)fibpanelComponents[1]).setText(resultSet.getString("question"));
					    ((TextArea)fibpanelComponents[3]).setText(resultSet.getString("option1"));
					}
					else if(((JRadioButton)components[4]).isSelected()) {
						
						components[13].setVisible(true);
						components[13].setEnabled(true);
						
						Component mcqpanelComponents[] = ((JPanel)components[13]).getComponents();
						
						query ="SELECT * FROM question_info";
						stmt=conn.createStatement();
					    resultSet = stmt.executeQuery(query);
				
					    while(resultSet.next()) {
					    	
					    	String id=resultSet.getString("sr_no");
					    	if(id.equals(sr_no))
					    		break;
					    }
					    
					    ((TextArea)mcqpanelComponents[1]).setText(resultSet.getString("question"));
					    ((JTextField)mcqpanelComponents[3]).setText(resultSet.getString("option1"));
					    ((JTextField)mcqpanelComponents[4]).setText(resultSet.getString("option2"));
					    ((JTextField)mcqpanelComponents[5]).setText(resultSet.getString("option3"));
					    ((JTextField)mcqpanelComponents[6]).setText(resultSet.getString("option4"));
					    ((JTextField)mcqpanelComponents[7]).setText(resultSet.getString("answer"));
					}
				}
			}
			else if(name.equals("SUBMITTF")) {                      ////////////////// SUBMITTF
				Component components[] = parent.getContentPane().getComponents();
				Component tfPanelComponents[] = ((JPanel)components[11]).getComponents();
				JViewport view=((JScrollPane)components[9]).getViewport();
				JPanel scrollPanel = (JPanel) view.getView();
				Component scrollComponents[] = ((JPanel)scrollPanel).getComponents();
				
				String sr_no=null;
			
					for(int i=0;i<scrollComponents.length;i++) {
					
						if(((JRadioButton)scrollComponents[i]).isSelected()) {
						sr_no=((JRadioButton)scrollComponents[i]).getName();
						break;
					}
				}
				String subject=null;
				String question=((TextArea)tfPanelComponents[1]).getText();
				String answer=null;
				if(((JRadioButton)components[0]).isSelected()) {
					subject="maths";
				}
				else if(((JRadioButton)components[1]).isSelected()) {
					subject="chemistry";
				}
				else
					subject="physics";
				
				if(((JRadioButton)tfPanelComponents[2]).isSelected()) {
					answer="true";
				}
				else
					answer="false";
				
				boolean exists=false;
				String query = "SELECT * FROM question_info;";
			   	Statement stmt = conn.createStatement();
			    ResultSet resultSet=stmt.executeQuery(query);
			    while(resultSet.next()) {
			    	
			    	String temp_que=resultSet.getString("question");
			    	String temp_answer=resultSet.getString("option1");
			    	String temp_subject=resultSet.getString("question_subject");
			    	
			    	if(question.equals(temp_que)  &&  answer.equals(temp_answer) && subject.equals(temp_subject)) {
			    		exists=true;
			    		break;
			    	}
			    
			    }
			   System.out.println("darshan");
			    if(exists) {
			    	JOptionPane.showMessageDialog(null, "Question already exists");
			    }
				
			    else {
					
					if(question==null || question.equals(""))
						JOptionPane.showMessageDialog(null, "Some field is empty");
					else {
						
						 query="UPDATE question_info SET sr_no='"+sr_no+"',`question_type`='TF',`question_subject`='"+subject+"',`question`='"+question+"',`option1`='"+answer+"',`option2`='',`option3`='',`option4`='' ,`answer`='' WHERE sr_no LIKE ("+sr_no+")";

						 stmt = conn.createStatement();
						 stmt.executeUpdate(query);
						 JOptionPane.showMessageDialog(null, "Successful Submission");
						 
					}
			    }
			
				
			}
			else if(name.equals("SUBMITFIB")) {          ////////////////////// SUBMITFIB
				
				Component components[] = parent.getContentPane().getComponents();
				Component fibPanelComponents[] = ((JPanel)components[12]).getComponents();
				JViewport view=((JScrollPane)components[9]).getViewport();
				JPanel scrollPanel = (JPanel) view.getView();
				Component scrollComponents[] = ((JPanel)scrollPanel).getComponents();
				
				String sr_no=null;
			
					for(int i=0;i<scrollComponents.length;i++) {
					
						if(((JRadioButton)scrollComponents[i]).isSelected()) {
						sr_no=((JRadioButton)scrollComponents[i]).getName();
						break;
					}
				}
					
				String subject=null;
				String question=((TextArea)fibPanelComponents[1]).getText();;
				String answer=null;
				if(((JRadioButton)components[0]).isSelected()) {
					subject="maths";
;				}
				else if(((JRadioButton)components[1]).isSelected()) {
					subject="chemistry";
				}
				else
					subject="physics";
				
				answer=((TextArea)fibPanelComponents[3]).getText();
				
				boolean exists=false;
				String query = "SELECT * FROM question_info;";
			   	Statement stmt = conn.createStatement();
			    ResultSet resultSet=stmt.executeQuery(query);
			    while(resultSet.next()) {
			    	
			    	String temp_que=resultSet.getString("question");
			    	String temp_answer=resultSet.getString("option1");
			    	String temp_subject=resultSet.getString("question_subject");
			    	
			    	if(question.equals(temp_que)  &&  answer.equals(temp_answer) && subject.equals(temp_subject)) {
			    		exists=true;
			    		break;
			    	}
			    
			    }
			    if(exists) {
			    	JOptionPane.showMessageDialog(null, "Question already exists");
			    }
				
			    else {
			    	
			    	
					
					if(question==null || question.equals("") || answer==null || answer.equals("")) {
						JOptionPane.showMessageDialog(null, "Some field is empty");
					}
					else {
						
						query="UPDATE question_info SET sr_no='"+sr_no+"',`question_type`='FIB',`question_subject`='"+subject+"',`question`='"+question+"',`option1`='"+answer+"',`option2`='',`option3`='',`option4`='' , `answer`='' WHERE sr_no LIKE ("+sr_no+")";

						stmt = conn.createStatement();
						 stmt.executeUpdate(query);
						 JOptionPane.showMessageDialog(null, "Successful Submission");
					}
			    }
			}
			else if(name.equals("SUBMITMCQ")) {			//////////// SUBMITMCQ
				
				Component components[] = parent.getContentPane().getComponents();
				Component mcqPanelComponents[] = ((JPanel)components[13]).getComponents();
				JViewport view=((JScrollPane)components[9]).getViewport();
				JPanel scrollPanel = (JPanel) view.getView();
				Component scrollComponents[] = ((JPanel)scrollPanel).getComponents();
				
				String sr_no=null;
			
					for(int i=0;i<scrollComponents.length;i++) {
					
						if(((JRadioButton)scrollComponents[i]).isSelected()) {
						sr_no=((JRadioButton)scrollComponents[i]).getName();
						break;
					}
				}
					
				String subject=null;
				String question=null;
				String option1=null,option2=null,option3=null,option4=null,answer=null;
				
			    	if(((JRadioButton)components[0]).isSelected()) {
						subject="maths";
					}
					else if(((JRadioButton)components[1]).isSelected()) {
						subject="chemistry";
					}
					else
						subject="physics";
					
					question=((TextArea)mcqPanelComponents[1]).getText();
					option1=((JTextField)mcqPanelComponents[3]).getText();
					option2=((JTextField)mcqPanelComponents[4]).getText();
					option3=((JTextField)mcqPanelComponents[5]).getText();
					option4=((JTextField)mcqPanelComponents[6]).getText();
					answer=((JTextField)mcqPanelComponents[7]).getText();
					
					boolean exists=false;
					String query = "SELECT * FROM question_info;";
				   	Statement stmt = conn.createStatement();
				    ResultSet resultSet=stmt.executeQuery(query);
				    while(resultSet.next()) {
				    	
				    	String temp_que=resultSet.getString("question");
				    	String temp_option1=resultSet.getString("option1");
				    	String temp_option2=resultSet.getString("option2");
				    	String temp_option3=resultSet.getString("option3");
				    	String temp_option4=resultSet.getString("option4");
				    	String temp_answer=resultSet.getString("answer");
				    	String temp_subject=resultSet.getString("question_subject");
				    	
				    	if(question.equals(temp_que) && subject.equals(temp_subject) && option1.equals(temp_option1)
				    			&& option2.equals(temp_option2) && option3.equals(temp_option3) && option4.equals(temp_option4)
				    			&& answer.equals(temp_answer)) {
				    		exists=true;
				    		break;
				    	}
				    
				    }
				    if(exists) {
				    	JOptionPane.showMessageDialog(null, "Question already exists");
				    }
					
				    else if(question==null || question.equals("") || option1==null || option1.equals("") || option2==null || option2.equals("")
							|| option3==null || option3.equals("") || option4==null || option4.equals("") || answer==null || answer.equals("")) {
						
						JOptionPane.showMessageDialog(null, "Some field is empty");
					}
					else if(!answer.equals(option1) && !answer.equals(option2) && !answer.equals(option3) && !answer.equals(option4)) {
						JOptionPane.showMessageDialog(null, "Answer does not match any options");
					}
					else {
						
						 query="UPDATE question_info SET sr_no='"+sr_no+"',`question_type`='MCQ',`question_subject`='"+subject+"',`question`='"+question+"',`option1`='"+option1+"',`option2`='"+option2+"',`option3`='"+option3+"',`option4`='"+option4+"' , `answer`='"+answer+"' WHERE sr_no LIKE ("+sr_no+")";

						 stmt = conn.createStatement();
						 stmt.executeUpdate(query);
						 JOptionPane.showMessageDialog(null, "Successful Submission");
					}
			    }
				
			else if(name.equals("NEXT")) {                 //////////////////// NEXT
				
				
				Component editcomponents[] = parent.getContentPane().getComponents();
				editcomponents[9].setVisible(true);
				editcomponents[9].setEnabled(true);
				editcomponents[10].setVisible(true);
				editcomponents[10].setEnabled(true);
				
				
				JViewport view=((JScrollPane)editcomponents[9]).getViewport();
				JPanel editPanel = (JPanel) view.getView();
				
				Component editPanelComponents[] = editPanel.getComponents();
				
				for(int i=0;i<editPanelComponents.length;i++) {
					
					editPanel.remove(editPanelComponents[i]);
				}
				
				String editsubject=null,type=null;
				
				if(((JRadioButton)editcomponents[0]).isSelected()) {
					editsubject="maths";
				}
				else if(((JRadioButton)editcomponents[1]).isSelected()) {
					editsubject="chemistry";
				}
					
				else 
					editsubject="physics";
				
				if(((JRadioButton)editcomponents[4]).isSelected()) {
					type="MCQ";
				}
				else if(((JRadioButton)editcomponents[5]).isSelected()) {
					type="FIB";
				}
					
				else 
					type="TF";

				String query ="SELECT * FROM `question_info`;";
				Statement stmt=conn.createStatement();
			    ResultSet resultSet = stmt.executeQuery(query);
			    ButtonGroup questionList  = new ButtonGroup();
			    
			    while(resultSet.next()) {
			    	
			    	
			    	String sr_no=resultSet.getString("sr_no");
			    	String question=resultSet.getString("question");
			    	String subject=resultSet.getString("question_subject");
			    	String qtype=resultSet.getString("question_type");
			    	
			    	if(type.equals(qtype) && editsubject.equals(subject)) {
			    		
			    		
			    		JRadioButton radio = new JRadioButton(question);
			    		radio.setName(sr_no);
			    		editPanel.add(radio);
			    		questionList.add(radio);
			    	}
			    }
			    editPanel.revalidate();
			    editPanel.repaint();
				
			}
			else if(name.equals("SUBMIT")){   					////////////// SUBMIT
				
					Component components[]= parent.getContentPane().getComponents();
					String subject;
					
					if(((JRadioButton)components[0]).isSelected())
						subject="maths";
					else if(((JRadioButton)components[1]).isSelected())
						subject="chemistry";
					else 
						subject="physics";
					
					
					//Condition for True/False Panel
					////////////////////////////////////////////////
					if(((JRadioButton)components[6]).isSelected()){
						boolean flag=false;
						boolean flag1=false;
						
						Panel tf=(Panel)components[8];
						Component panelComponents[] = tf.getComponents();

						String question=(String)(((TextArea) panelComponents[0]).getText());
						if(!(question==null || question.equals("")))
								flag1=true;
						
						String answer=null;
						if(((JRadioButton)panelComponents[1]).isSelected())
						{
							answer="true";
							flag=true;
						}
						else if(((JRadioButton)panelComponents[2]).isSelected())
						{
							answer="false";
							flag=true;
						}

						boolean exists=false;
						String query = "SELECT * FROM question_info;";
					   	Statement stmt = conn.createStatement();
					    ResultSet resultSet=stmt.executeQuery(query);
					    while(resultSet.next()) {
					    	
					    	String temp_que=resultSet.getString("question");
					    	String temp_answer=resultSet.getString("option1");
					    	String temp_subject=resultSet.getString("question_subject");
					    	
					    	if(question.equals(temp_que)  &&  answer.equals(temp_answer) && subject.equals("temp_subject")) {
					    		exists=true;
					    		break;
					    	}
					    
					    }
						System.out.println(subject);

					    if(exists) {
					    	JOptionPane.showMessageDialog(null, "Question already exists");
					    }
					    else {
					    	
					    	if(flag && flag1) {
								
								query = "INSERT INTO question_info(question_type,question_subject,question,option1,option2,option3,option4,answer) VALUES (\"TF\", \""+subject+"\", \""+question+"\", \""+answer+"\",\"\", \"\", \"\",\"\");";
							   	stmt = conn.createStatement();
							    stmt.executeUpdate(query);
							    JOptionPane.showMessageDialog(null, "Successful Submission");
							    
							}
							else {
								JOptionPane.showMessageDialog(null, "Some field is empty");
							}
					    }
						
					    
					}
					//Condition for Fill in the blank
					/////////////////////////////////////////////
					else if(((JRadioButton)components[5]).isSelected()) {
						
						
						boolean flag=false,flag1=false;
						
						Panel fib=(Panel)components[10];
						Component panelComponents[] = fib.getComponents();
						
						String question=(String)(((TextArea) panelComponents[0]).getText());
						if(!(question==null || question.equals("")))
								flag=true;
						
						String answer=(String)(((TextArea) panelComponents[3]).getText());
						if(!(answer==null || answer.equals("")))
								flag1=true;
						boolean exists=false;
						String query = "SELECT * FROM question_info;";
					   	Statement stmt = conn.createStatement();
					    ResultSet resultSet=stmt.executeQuery(query);
					    while(resultSet.next()) {
					    	
					    	String temp_que=resultSet.getString("question");
					    	String temp_answer=resultSet.getString("option1");
					    	String temp_subject=resultSet.getString("question_subject");
					    	
					    	if(question.equals(temp_que) && answer.equals(temp_answer) && subject.equals(temp_subject)) {
					    		exists=true;
					    		break;
					    	}
					    
					    }
					    if(exists) {
					    	JOptionPane.showMessageDialog(null, "Question already exists");
					    }
					    else {
					    	
					    	if(flag && flag1) {
								
								query = "INSERT INTO question_info(question_type,question_subject,question,option1,option2,option3,option4,answer) VALUES (\"FIB\", \""+subject+"\", \""+question+"\", \""+answer+"\",\"\", \"\", \"\",\"\");";
							   	System.out.println(conn);
							   	stmt = conn.createStatement();
							    stmt.executeUpdate(query);
							    JOptionPane.showMessageDialog(null, "Successful Submission");
							}
							else
								JOptionPane.showMessageDialog(null, "Some field is empty");
								
					    }
					}
					//Condition for MCQ
					/////////////////////////////////
					else if(((JRadioButton)components[4]).isSelected()) {
						
						
						Panel mcq=(Panel)components[9];
						Component panelComponents[] = mcq.getComponents();
						
						String question=(String)(((TextArea) panelComponents[0]).getText());
						String optionA=(String)(((JTextField) panelComponents[2]).getText());
						String optionB=(String)(((JTextField) panelComponents[3]).getText());
						String optionC=(String)(((JTextField) panelComponents[4]).getText());
						String optionD=(String)(((JTextField) panelComponents[5]).getText());
						String answer=(String)(((JTextField) panelComponents[7]).getText());
						
						boolean exists=false;
						String query = "SELECT * FROM question_info;";
					   	Statement stmt = conn.createStatement();
					    ResultSet resultSet=stmt.executeQuery(query);
					    while(resultSet.next()) {
					    	
					    	String temp_que=resultSet.getString("question");
					    	String temp_option1=resultSet.getString("option1");
					    	String temp_option2=resultSet.getString("option2");
					    	String temp_option3=resultSet.getString("option3");
					    	String temp_option4=resultSet.getString("option4");
					    	String temp_answer=resultSet.getString("answer");
					    	String temp_subject=resultSet.getString("question_subject");
					    	
					    	if(question.equals(temp_que) && subject.equals(temp_subject) && optionA.equals(temp_option1)
					    			&& optionB.equals(temp_option2) && optionC.equals(temp_option3) && optionD.equals(temp_option4)
					    			&& answer.equals(temp_answer)) {
					    		exists=true;
					    		break;
					    	}
					    
					    }
					    if(exists) {
					    	JOptionPane.showMessageDialog(null, "Question already exists");
					    }
					    else {
					    	
					    	if(question==null || question.equals("") || optionA==null || optionA.equals("") ||optionB==null || optionB.equals("")
									|| optionC==null || optionC.equals("") || optionD==null || optionD.equals("") || answer==null || answer.equals("")) {
								
								JOptionPane.showMessageDialog(null, "Some field is empty");
							}
							else if(!answer.equals(optionA) && !answer.equals(optionB) && !answer.equals(optionC) && !answer.equals(optionD)) {
								JOptionPane.showMessageDialog(null, "Answer does not match any options");
							}
							else {
								
								query = "INSERT INTO question_info(question_type,question_subject,question,option1,option2,option3,option4,answer) VALUES ('MCQ', '"+subject+"','"+question+"','"+optionA+"','"+optionB+"', '"+optionC+"','"+optionD+"','"+answer+"');";
							   	System.out.println(conn);
							   	stmt = conn.createStatement();
							    stmt.executeUpdate(query);
							    JOptionPane.showMessageDialog(null, "Successful Submission");
							}
					    }
					
			}
			
		}
			else if(name.equals("GENERATE")) {			/////////////////// GENERATE
				
				Begin generateWindow =new Begin("OOP Project");
				generateWindow.ancestor=parent;
				generateWindow.ancestor.setEnabled(false);
				generateWindow.setName("generateWindow");
				
				JLabel label = new JLabel("Enter the number of questions to be generated:");
				label.setFont(new Font("Tahoma", Font.PLAIN, 13));
				label.setBounds(10, 30, 285, 14);
				generateWindow.getContentPane().add(label);
				
				JTextField field = new JTextField();
				field.setBounds(288, 28, 86, 20);
				generateWindow.getContentPane().add(field);
				field.setColumns(10);
				
				JButton generateButton = new JButton("Generate");
				generateButton.setName("GENERATEQUE");
				generateButton.setBounds(162, 104, 89, 23);
				generateWindow.getContentPane().add(generateButton);
				
				String query="SELECT * FROM `question_info`;";
			   	Statement stmt = conn.createStatement();
			    ResultSet resultSet=stmt.executeQuery(query);
			    
			    int size=0;
			    while(resultSet.next()) {
			    	size++;
			    }
				
				JLabel status = new JLabel("Current status of question bank is "+size+" questions");
				status.setFont(new Font("Tahoma", Font.PLAIN, 14));
				status.setBounds(10, 224, 392, 26);
				generateWindow.getContentPane().add(status);
				
				generateButton.addActionListener(generateWindow);
			}
			else if(name.equals("GENERATEQUE")) {						///////// GENERATE QUE
				
				Component components[] = parent.getContentPane().getComponents();
				
				String query="SELECT * FROM `question_info`;";
			   	Statement stmt = conn.createStatement();
			    ResultSet resultSet=stmt.executeQuery(query);
			    
			    int size=0;
			    while(resultSet.next()) {
			    	size++;
			    }
			    
			    String inputString= (((JTextField)components[1]).getText());
			    try {
			    	
			    	String arr[] = new String[size];
			    	int input=Integer.parseInt(inputString);
				    if(input>size) {
				    	JOptionPane.showMessageDialog(null, "Input exceeds the size of database");
				    }
				    else if(input<0) {
				    	JOptionPane.showMessageDialog(null, "Input is invalid");

				    }
				    else {
				    	
				    	Begin exportWindow = new Begin("OOP Project");
				    	exportWindow.ancestor=parent;
				    	exportWindow.ancestor.setEnabled(false);
				    	exportWindow.setName("exportWindow");
				    	exportWindow.setBounds(100, 100, 478, 382);
				    	
				    	TextArea questionTextArea = new TextArea(); //0
						questionTextArea.setBounds(10, 43, 220, 245);
						exportWindow.getContentPane().add(questionTextArea);
						
						TextArea answerTextArea = new TextArea();  //1
						answerTextArea.setBounds(246, 43, 206, 245);
						exportWindow.getContentPane().add(answerTextArea);
						
						JButton exportQuestionButton = new JButton("Export");
						exportQuestionButton.setName("EXPORTQUE");
						exportQuestionButton.setBounds(69, 309, 89, 23);
						exportWindow.getContentPane().add(exportQuestionButton);
						
						JButton exportAnswerButton = new JButton("Export");
						exportAnswerButton.setName("EXPORTSOL");
						exportAnswerButton.setBounds(306, 309, 89, 23);
						exportWindow.getContentPane().add(exportAnswerButton);
						
						JLabel questionLabel = new JLabel("Questions");
						questionLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
						questionLabel.setBounds(10, 23, 99, 14);
						exportWindow.getContentPane().add(questionLabel);
						
						JLabel answerLabel = new JLabel("Answers");
						answerLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
						answerLabel.setBounds(256, 23, 56, 14);
						exportWindow.getContentPane().add(answerLabel);
						
						exportAnswerButton.addActionListener(exportWindow);
						exportQuestionButton.addActionListener(exportWindow);
						
						query="SELECT * FROM `question_info`;";
					    stmt = conn.createStatement();
					    resultSet=stmt.executeQuery(query);
					    
					    int i=0;
					    while(resultSet.next()) {
					    	arr[i++]= resultSet.getString("sr_no");
					    }
					    Random rnd = ThreadLocalRandom.current();
					    
					    for(i=size-1;i>0;i--) {
					    	int index = rnd.nextInt(i + 1);
					    	String a=arr[i];
					    	arr[i]=arr[index];
					    	arr[index]=a;
					    }
					    i=0;
					    while(i<input) {
					    	
					    	System.out.println(arr[i]);
					    	query="SELECT * FROM  question_info;";
						    stmt = conn.createStatement();
						    resultSet=stmt.executeQuery(query);
					    	while(resultSet.next()) {
					    		if(resultSet.getString("sr_no").equals(arr[i])) 
					    			break;
					    	}
					    	String question=resultSet.getString("question");
					    	String type=resultSet.getString("question_type");
					    	
					    	
					    	if(type.equals("MCQ")) {
					    		
					    		String option1=resultSet.getString("option1");
					    		String option2=resultSet.getString("option2");
					    		String option3=resultSet.getString("option3");
					    		String option4=resultSet.getString("option4");
					    		String answer=resultSet.getString("answer");
					    		
					    		questionTextArea.append(""+(i+1)+") ");
					    		questionTextArea.append(question);
					    		questionTextArea.append("\n");
					    		questionTextArea.append("(A) "+option1+" ");
					    		questionTextArea.append("(B) "+option2+" ");
					    		questionTextArea.append("(C) "+option3+" ");
					    		questionTextArea.append("(D) "+option4+"\n");
					    		questionTextArea.append("\n");
					    		questionTextArea.append("\n");
					    		
					    		answerTextArea.append(""+(i+1)+") ");
					    		answerTextArea.append(answer);
					    		answerTextArea.append("\n");
					    		answerTextArea.append("\n");
					    		
					    	}
					    	else {
					    		
					    		String answer=resultSet.getString("option1");
					    		
					    		questionTextArea.append(""+(i+1)+") ");
					    		questionTextArea.append(question);
					    		questionTextArea.append("\n");
					    		questionTextArea.append("\n");
					    		
					    		answerTextArea.append(""+(i+1)+") ");
					    		answerTextArea.append(answer);
					    		answerTextArea.append("\n");
					    		answerTextArea.append("\n");
					    		
					    	}
					    	i++;
					    }
					    questionTextArea.setEditable(false);
					    answerTextArea.setEditable(false);
					   
				    }
			    }
			    catch(Exception d) {
			    	System.out.println(d);
			    	
			    	JOptionPane.showMessageDialog(null, "Input is invalid");
			    }
			    
			}
			else if(name.equals("EXPORTQUE")) {						///////////// EXPORTQUE
				
				Component components[] = parent.getContentPane().getComponents();
				String s=((TextArea)components[0]).getText();
				
				File file = new File("C://Users//user//Downloads//Question_file.txt");  
				
				 FileWriter writer = new FileWriter(file);
				 writer.write("Question Set\n\n");
		         writer.write(s);
		         writer.close();
		         
		         JOptionPane.showMessageDialog(null, "Saved succesfully in downloads");
			}
			else if(name.equals("EXPORTSOL")) {						///////////// EXPORTSOL
				
				Component components[] = parent.getContentPane().getComponents();
				String s=((TextArea)components[1]).getText();
				
				File file = new File("C://Users//user//Downloads//Solution_file.txt");  
				
				 FileWriter writer = new FileWriter(file);
				 writer.write("Solution Set\n\n");
		         writer.write(s);
		         writer.close();
		         
		         JOptionPane.showMessageDialog(null, "Saved succesfully in downloads");
			}
	}
		
		catch(Exception exp){
				
				try {
					
					JRadioButton source=(JRadioButton)e.getSource();
					String name=source.getName();
					JFrame topframe = (JFrame) SwingUtilities.getWindowAncestor(source);
					Begin parent=(Begin) topframe;
					Panel tf=null;
					Panel Mcq=null;
					Panel Fib=null;
					
					Component components[] = parent.getContentPane().getComponents();
					
					//System.out.println(components.length);
					for(int i=0;i<components.length;i++) {
						
						
						if(components[i].getName().equals("tfPanel"))
							tf=(Panel)components[i];
						else if(components[i].getName().equals("mcqPanel"))
							Mcq=(Panel)components[i];
						else if(components[i].getName().equals("fibPanel"))
							Fib=(Panel)components[i];
								
					}
					
					tf.setVisible(false);
					tf.setEnabled(false);
					Component tfComponents[]=tf.getComponents();
					((TextArea)tfComponents[0]).setText("");
					((JRadioButton)tfComponents[1]).setSelected(false);
					((JRadioButton)tfComponents[2]).setSelected(false);
					
					
					Mcq.setVisible(false);
					Mcq.setEnabled(false);
					Component mcqComponents[]=Mcq.getComponents();
					((TextArea)mcqComponents[0]).setText("");
					((JTextField)mcqComponents[2]).setText("");
					((JTextField)mcqComponents[3]).setText("");
					((JTextField)mcqComponents[4]).setText("");
					((JTextField)mcqComponents[5]).setText("");
					((JTextField)mcqComponents[7]).setText("");
					
					Fib.setVisible(false);
					Fib.setEnabled(false);
					Component fibComponents[]=Fib.getComponents();
					((TextArea)fibComponents[0]).setText("");
					((TextArea)fibComponents[3]).setText("");
					
					if(name.equals("mcq")){
						
						Mcq.setVisible(true);
						Mcq.setEnabled(true);
					
					}
					else if(name.equals("tf")){
						
						tf.setVisible(true);
						tf.setEnabled(true);
					}
					
					else if(name.equals("fib")) {
						
						Fib.setVisible(true);
						Fib.setEnabled(true);
					}
				}
				catch(Exception a) {
				
					JCheckBox source=(JCheckBox)e.getSource();
					JFrame topframe = (JFrame) SwingUtilities.getWindowAncestor(source);
					Begin parent= (Begin) topframe;
					Component components[] = parent.getContentPane().getComponents();
					JViewport view=((JScrollPane)components[0]).getViewport();
					JPanel deletePanel = (JPanel) view.getView();
					
					Component panelComponents[] = deletePanel.getComponents();
					
					if(source.isSelected()) {
						
						for(int i=0;i<panelComponents.length;i++) {
							
							((JCheckBox)panelComponents[i]).setSelected(true);
						}
					}
					else {
						
							for(int i=0;i<panelComponents.length;i++) {
							
							((JCheckBox)panelComponents[i]).setSelected(false);
						}
					}
					
					
				}
			
		}
		
	

		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		
		
	}


	public void windowActivated(WindowEvent e) {
		
	}

	
	public void windowClosing(WindowEvent e) {
		
		Window current =e.getWindow();
		Begin temp=(Begin)current;
		System.out.println(temp.getName());
		temp.ancestor.setEnabled(true);
		temp.dispose();
		
	}

	
	public void windowDeactivated(WindowEvent e) {
		
		
		
	}

	
	public void windowDeiconified(WindowEvent e) {
	
		
	}


	public void windowIconified(WindowEvent e) {
		
		
	}

	
	public void windowOpened(WindowEvent e) {
		
		
	}

	
	

}




