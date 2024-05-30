package pr1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;

public class NimGame extends JFrame{
	private int[] heaps; //number of piles
	private JPanel[] Panels;
	private final JLabel sLabel;
	private int level=1; //difficulty
	public chopsticks s=new chopsticks();

	public NimGame(int[] stickValues) //constructor
	{
		//jForm
		super("Nim's Game");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		//panel difficulty
		JPanel leftPanel=new JPanel();
		 leftPanel.setBorder(BorderFactory.createEmptyBorder(200,10,10,10));
		String[] difficulties={"Easy","Medium","Hard"};
		  JComboBox<String> levelComboBox=new JComboBox<>(difficulties);
		levelComboBox.addActionListener(this::setlevel);
		levelComboBox.setBackground(Color.black);
		 levelComboBox.setForeground(Color.white);
		leftPanel.add(levelComboBox, BorderLayout.CENTER);
		leftPanel.setBackground(Color.white);

		//Heaps panel
		JPanel cPanel=new JPanel(new GridLayout(stickValues.length,1,5,5));
		cPanel.setBackground(new Color(102,51,0));
		heaps=stickValues; //array of piles
		Panels=new JPanel[stickValues.length];

		//show the panel for heaps and piles
		for(int i=0; i<stickValues.length; i++){
			Panels[i]=new JPanel(new FlowLayout(FlowLayout.CENTER,5,5));
			  Panels[i].setOpaque(false);
		    	update(i); //update the panel of heaps
			cPanel.add(Panels[i]); //add the heap panel of center panel
		}

		//status
		sLabel=new JLabel("Select difficulty and start your move",JLabel.CENTER);
		sLabel.setForeground(Color.black);
		  sLabel.setBackground(Color.white);
		sLabel.setFont(new Font("Times New Roman",Font.BOLD,20));
		  add(sLabel, BorderLayout.NORTH);
		
		add(new JScrollPane(cPanel),BorderLayout.CENTER);
		 add(leftPanel,BorderLayout.WEST); //add left panel to frame

	    	setSize(new Dimension(800,600)); //size of frame
		setLocationRelativeTo(null);
		setVisible(true);

		JOptionPane.showMessageDialog(this,"Welcome to Nim Game,Choose your difficulty and play","Welcome",JOptionPane.INFORMATION_MESSAGE);
	}

	//update the heaps after every move
	private void update(int heaprow){
		Panels[heaprow].removeAll();
		//for every row
		for(int j=0; j<heaps[heaprow]; j++){
			BufferedImage chopStick=s.createStick(); //Making sticks
			JButton cButton=new JButton(new ImageIcon(chopStick)); //Each stick is a button
			  cButton.setBorder(BorderFactory.createEmptyBorder());
			cButton.setContentAreaFilled(false);
			 cButton.setPreferredSize(new Dimension(20,100));
			int removej=j;
			  cButton.addActionListener(e-> player(heaprow,removej+1)); //action player move
			Panels[heaprow].add(cButton); //add the sticks for the panel index
			}
		Panels[heaprow].revalidate();
	     	Panels[heaprow].repaint();
	}



	//the difficulty levels
	private void setlevel(ActionEvent e){
		JComboBox<String> comboBox=(JComboBox<String>) e.getSource();
		  String selectlevel=(String) comboBox.getSelectedItem();
		level="Easy".equals(selectlevel)? 1: "Medium".equals(selectlevel)? 2:3;
		 sLabel.setText("Difficulty set to "+selectlevel+". Your turn");
	}

	//player
	private void player(int heaprow, int RemoveSticks){
		//check If the number of sticks deleted is less than the number of complete
		//sticks in the same row
		if(RemoveSticks<=heaps[heaprow]){
			heaps[heaprow] -=RemoveSticks;
			 update(heaprow);//update after remove
			//check the win
			if(check()){
				sLabel.setText("You lose!");//Player who removes the last sticks is the loser
				  disableButtons();
				showMessage("You've lost the game!");
			} else {
				ai(); //If they don't reach the end of the game
			}
		}else{
			//If he makes a move to remove sticks from more than one row at the same time
			 JOptionPane.showMessageDialog(this,"Invalid move. Try again.","Invalid Move",JOptionPane.ERROR_MESSAGE);
		}}

	//check win
	private boolean check(){
		return Arrays.stream(heaps).allMatch(heap->heap==0); //check if all heaps are empty to determine the end of the game or a winning condition
																}

	private void disableButtons(){
		for(JPanel heapPanel:Panels){
			Component[] components=heapPanel.getComponents();
			for(Component component:components){
				component.setEnabled(false);
			}}}

	private void showMessage(String message){
		JOptionPane.showMessageDialog(this,message,"Game Over",JOptionPane.INFORMATION_MESSAGE);
		  int again=JOptionPane.showConfirmDialog(this,"Do you like to play again?","Play Again?",JOptionPane.YES_NO_OPTION);
		if(again==JOptionPane.YES_OPTION){
			resetGame();
		}else{
			dispose();}}

	//reset game
	private void resetGame(){
		int numRows = Integer.parseInt(JOptionPane.showInputDialog("Enter the number of rows:"));
		  int[] stickValues=new int[numRows];
		 for(int i=0;i<numRows;i++){
			stickValues[i] = Integer.parseInt(JOptionPane.showInputDialog("Enter the stick value for row "+(i+1)+ ":"));
		}

		heaps=stickValues;

		for(int i=0;i<heaps.length;i++){
			update(i);}
		
	 	sLabel.setText("Select difficulty and start your move.");
		enableButtons();}

	private void enableButtons(){
		for(JPanel heapPanel:Panels){
			Component[] components=heapPanel.getComponents();
			for(Component component:components){
				component.setEnabled(true);
			}}}

	private void ai(){
		int Heap=-1;
		int count=-1; // the best count of sticks to remove
		int bestValue=Integer.MIN_VALUE; // example minus infinity

		int depth=level==3?10:level; // Increase depth for hard difficulty

		for(int i=0;i<heaps.length;i++){
			for(int k = 1;k<=heaps[i];k++){
				int[]Heaps =heaps.clone();
				 Heaps[i]-=k;
				int value=minimax(Heaps,depth,Integer.MIN_VALUE,Integer.MAX_VALUE,false);
				if(value>bestValue){
					   bestValue=value;
					 Heap=i;
					count=k;
				}}}

		if(Heap!=-1 && count!= -1){
			heaps[Heap]-=count;
			update(Heap);
			if(check()){
				sLabel.setText("AI lose!");
				disableButtons();
				showMessage("AI has lost the game!");
			}else{
				sLabel.setText("AI has made its move and Remove "+count+ " Sticks ,Now your move!");

			}}
	}

	//function for strategy alpha beta
	private int minimax(int[] heap, int depth, int alpha, int beta, boolean maximizingPlayer){
		//If we reach the last depth, or if we reach the end of the game, there are no sticks left
		if(depth==0||checkWin(heap)){
			return evaluate(heap);
		}
		//player move
		if(maximizingPlayer){
			int v=Integer.MIN_VALUE;//minus infinity
			for(int i=0;i<heap.length;i++){
				for(int k=1; k<= heap[i];k++){
					int[] news=heap.clone();
					news[i]-=k;
					int value=minimax(news,depth-1,alpha,beta,false);
					v=Math.max(v,value);
					alpha=Math.max(alpha,v);
					if (beta<=alpha){
						break;//cut-off
					}
				}}
			return v;
		}
		//ai move
		else{
			int v=Integer.MAX_VALUE; //positive infinity
			for(int i=0;i<heap.length;i++){
				for(int k=1; k<= heap[i];k++){
					int[] news = heap.clone();
					news[i]-=k;
					int value =minimax(news,depth-1,alpha,beta,true);
					v =Math.min(v,value);
					beta =Math.min(beta,v);
					if(beta<=alpha){
						break;//cut-off
					}}
			}
			return v;
		}}

	private int evaluate(int[] state){
		if(level==1){
			//Easy level
			return new Random().nextInt(10); //Random move
		}else if(level==2){
			//Medium level
			return new Random().nextBoolean()?1:-1;
		}else{
			//Hard level
			int nim=0;
			for(int heap:state){
				nim^=heap;//to calculate the nim sum of all elements in the array state
			}
			return nim==0?-1:1;//Perfect move for ai if zero num
		}}

	private boolean checkWin(int[] state){
		return Arrays.stream(state).allMatch(heap->heap==0);//if not left sticks
	}}