package pr1;

import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StartFrame extends JFrame{
	private JPanel initialPanel;
	private final JLabel dateLabel;
	private final JLabel title;
	private final JLabel dayLabel;

	public StartFrame(){
		
		setupInitialPanel();
		
		setTitle("Nim Game");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(800,600);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		getContentPane().setBackground(new Color(102,51,0));

		 JPanel topPanel=new JPanel(new BorderLayout());

		 title=new JLabel("Made By Eman & Malak",JLabel.CENTER);
		 title.setFont(new Font("Arial",Font.BOLD,16));
		 title.setForeground(Color.black);
		   topPanel.add(title,BorderLayout.CENTER);

		 dateLabel=new JLabel(getCurrentDate(),JLabel.LEFT);
		  dateLabel.setFont(new Font("Arial",Font.BOLD,16));
		 dateLabel.setForeground(Color.black);
		topPanel.add(dateLabel,BorderLayout.WEST);

		dayLabel=new JLabel(getCurrentDay(),JLabel.RIGHT);
		  dayLabel.setFont(new Font("Arial",Font.BOLD,16));
		 dayLabel.setForeground(Color.black);
		topPanel.add(dayLabel,BorderLayout.EAST);

		 topPanel.setBackground(Color.white);

		add(topPanel,BorderLayout.SOUTH);

		
		add(initialPanel, BorderLayout.CENTER);
		 
		setVisible(true);
	}
	private void setupInitialPanel(){
		initialPanel=new JPanel(new BorderLayout());
		initialPanel.setBackground(new Color(102,51,0));
		initialPanel.setPreferredSize(new Dimension(500,300));

	
		JLabel titleLabel=new JLabel("Nim Game",SwingConstants.CENTER);
		titleLabel.setBounds(100,200,300,30);
		titleLabel.setFont(new Font("Times New Roman",Font.BOLD,120));
		titleLabel.setForeground(Color.WHITE);
		titleLabel.setBackground(new Color(102,51,0));
		titleLabel.setOpaque(true);
		initialPanel.add(titleLabel);
		

		
		JPanel startButtonsPanel=new JPanel();
		startButtonsPanel.setBackground(new Color(102,51,0));
		startButtonsPanel.setPreferredSize(new Dimension(200,200));
		JButton version1Button=new JButton("Start !");
		  version1Button.addActionListener(this::startVersion2);
	 version1Button.setBackground(Color.yellow);
		version1Button.setForeground(Color.black);
	version1Button.setFocusPainted(false);
		version1Button.setFont(new Font("Times New Roman",Font.BOLD,40));
		  version1Button.setBounds(150,240,330,50);
		version1Button.setBorder(new LineBorder(Color.white));
		
		startButtonsPanel.add(version1Button);
		initialPanel.add(startButtonsPanel,BorderLayout.SOUTH);
	}

	private String getCurrentDate(){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		return "Date:"+sdf.format(new Date());}

        	private String getCurrentDay(){
		SimpleDateFormat sdf=new SimpleDateFormat("EEEE");
		return "Day:"+sdf.format(new Date());}


	private void startVersion2(ActionEvent m){
		int numRows=Integer.parseInt(JOptionPane.showInputDialog("Enter the number of Rows:"));
		int[] stickValues=new int[numRows];
		
		for(int i=0;i<numRows;i++){
	stickValues[i]=Integer.parseInt(JOptionPane.showInputDialog("Enter the sticks value for Row " +(i+1)+ ":"));
		}
		
		SwingUtilities.invokeLater(()->{
			new NimGame(stickValues);});
	}

	public static void main(String[] args){
		SwingUtilities.invokeLater(StartFrame::new);
	}}
