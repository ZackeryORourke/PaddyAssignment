package bankexercise;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;
import net.miginfocom.swing.MigLayout;
import javax.swing.*;

public class BankApplication extends JFrame {

	private static final long serialVersionUID = 1L;
	ArrayList<BankAccount> accountList = new ArrayList<BankAccount>();
	static HashMap<Integer, BankAccount> table = new HashMap<Integer, BankAccount>();
	public final static int TABLE_SIZE = 29;
	private JMenuBar menuBar;
	private JMenu navigateMenu, recordsMenu, transactionsMenu, fileMenu, exitMenu;
	private JMenuItem nextItem, prevItem, firstItem, lastItem, findByAccount, findBySurname, listAll;
	private JMenuItem createItem, modifyItem, deleteItem, setOverdraft, setInterest;
	private JMenuItem deposit, withdraw, calcInterest;
	private JMenuItem open, save, saveAs;
	private JMenuItem closeApp;
	private JButton firstItemButton, lastItemButton, nextItemButton, prevItemButton;
	private JLabel accountIDLabel, accountNumberLabel, firstNameLabel, surnameLabel, accountTypeLabel, balanceLabel, overdraftLabel;
	private JTextField accountIDTextField, accountNumberTextField, firstNameTextField, surnameTextField, accountTypeTextField, balanceTextField, overdraftTextField;
	static JFileChooser fc;
	private JTable jTable;
	private double interestRate;
	private int currentItem = 0;
	private boolean openValues;
	
	public BankApplication() {
		
		super("Bank Application");
		
	
		initComponents();
	}
	
	public void initComponents() {
		setLayout(new BorderLayout());
		JPanel displayPanel = new JPanel(new MigLayout());
		
		accountIDLabel = new JLabel("Account ID: ");
		accountIDTextField = new JTextField(15);
		accountIDTextField.setEditable(false);
		
		displayPanel.add(accountIDLabel, "growx, pushx");
		displayPanel.add(accountIDTextField, "growx, pushx, wrap");
		
		accountNumberLabel = new JLabel("Account Number: ");
		accountNumberTextField = new JTextField(15);
		accountNumberTextField.setEditable(false);
		
		displayPanel.add(accountNumberLabel, "growx, pushx");
		displayPanel.add(accountNumberTextField, "growx, pushx, wrap");

		surnameLabel = new JLabel("Last Name: ");
		surnameTextField = new JTextField(15);
		surnameTextField.setEditable(false);
		
		displayPanel.add(surnameLabel, "growx, pushx");
		displayPanel.add(surnameTextField, "growx, pushx, wrap");

		firstNameLabel = new JLabel("First Name: ");
		firstNameTextField = new JTextField(15);
		firstNameTextField.setEditable(false);
		
		displayPanel.add(firstNameLabel, "growx, pushx");
		displayPanel.add(firstNameTextField, "growx, pushx, wrap");

		accountTypeLabel = new JLabel("Account Type: ");
		accountTypeTextField = new JTextField(5);
		accountTypeTextField.setEditable(false);
		
		displayPanel.add(accountTypeLabel, "growx, pushx");
		displayPanel.add(accountTypeTextField, "growx, pushx, wrap");

		balanceLabel = new JLabel("Balance: ");
		balanceTextField = new JTextField(10);
		balanceTextField.setEditable(false);
		
		displayPanel.add(balanceLabel, "growx, pushx");
		displayPanel.add(balanceTextField, "growx, pushx, wrap");
		
		overdraftLabel = new JLabel("Overdraft: ");
		overdraftTextField = new JTextField(10);
		overdraftTextField.setEditable(false);
		
		displayPanel.add(overdraftLabel, "growx, pushx");
		displayPanel.add(overdraftTextField, "growx, pushx, wrap");
		
		add(displayPanel, BorderLayout.CENTER);
		
		JPanel buttonPanel = new JPanel(new GridLayout(1, 4));

		nextItemButton = new JButton(new ImageIcon("next.png"));
		prevItemButton = new JButton(new ImageIcon("prev.png"));
		firstItemButton = new JButton(new ImageIcon("first.png"));
		lastItemButton = new JButton(new ImageIcon("last.png"));
		
		buttonPanel.add(firstItemButton);
		buttonPanel.add(prevItemButton);
		buttonPanel.add(nextItemButton);
		buttonPanel.add(lastItemButton);
	
		
		add(buttonPanel, BorderLayout.SOUTH);
		
		menuBar = new JMenuBar();
    	setJMenuBar(menuBar);
		
		navigateMenu = new JMenu("Navigate");
    	
    	nextItem = new JMenuItem("Next Item");
    	prevItem = new JMenuItem("Previous Item");
    	firstItem = new JMenuItem("First Item");
    	lastItem = new JMenuItem("Last Item");
    	findByAccount = new JMenuItem("Find by Account Number");
    	findBySurname = new JMenuItem("Find by Surname");
    	listAll = new JMenuItem("List All Records");
    	
    	navigateMenu.add(nextItem);
    	navigateMenu.add(prevItem);
    	navigateMenu.add(firstItem);
    	navigateMenu.add(lastItem);
    	navigateMenu.add(findByAccount);
    	navigateMenu.add(findBySurname);
    	navigateMenu.add(listAll);
    	
    	menuBar.add(navigateMenu);
    	
    	recordsMenu = new JMenu("Records");
    	
    	createItem = new JMenuItem("Create Item");
    	modifyItem = new JMenuItem("Modify Item");
    	deleteItem = new JMenuItem("Delete Item");
    	setOverdraft = new JMenuItem("Set Overdraft");
    	setInterest = new JMenuItem("Set Interest");
    	
    	recordsMenu.add(createItem);
    	recordsMenu.add(modifyItem);
    	recordsMenu.add(deleteItem);
    	recordsMenu.add(setOverdraft);
    	recordsMenu.add(setInterest);
    	
    	menuBar.add(recordsMenu);
    	
    	transactionsMenu = new JMenu("Transactions");
    	
    	deposit = new JMenuItem("Deposit");
    	withdraw = new JMenuItem("Withdraw");
    	calcInterest = new JMenuItem("Calculate Interest");
    	
    	transactionsMenu.add(deposit);
    	transactionsMenu.add(withdraw);
    	transactionsMenu.add(calcInterest);
    	
    	menuBar.add(transactionsMenu);
    	
    	fileMenu = new JMenu("File");
    	
    	open = new JMenuItem("Open File");
    	save = new JMenuItem("Save File");
    	saveAs = new JMenuItem("Save As");
    	
    	fileMenu.add(open);
    	fileMenu.add(save);
    	fileMenu.add(saveAs);
    	
    	menuBar.add(fileMenu);
    	
    	exitMenu = new JMenu("Exit");
    	
    	closeApp = new JMenuItem("Close Application");
    	
    	exitMenu.add(closeApp);
    	
    	menuBar.add(exitMenu);
    	
    	setDefaultCloseOperation(EXIT_ON_CLOSE);
	
		setOverdraft.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(table.get(currentItem).getAccountType().trim().equals("Current")){
					String newOverdraftStr = JOptionPane.showInputDialog(null, "Enter new Overdraft", JOptionPane.OK_CANCEL_OPTION);
					overdraftTextField.setText(newOverdraftStr);
					table.get(currentItem).setOverdraft(Double.parseDouble(newOverdraftStr));
				}
				else
					JOptionPane.showMessageDialog(null, "Overdraft only applies to Current Accounts");
			
			}
		});
	
		ActionListener first = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				saveOpenValues();
				
				currentItem=0;
				while(!table.containsKey(currentItem)){
					currentItem++;
				}
				displayDetails(currentItem);
			}
		};
		

		ActionListener next = new ActionListener(){
			public void actionPerformed(ActionEvent e){
				
				ArrayList<Integer> keyList = new ArrayList<Integer>();
				int i=0;
		
				while(i<TABLE_SIZE){
					i++;
					if(table.containsKey(i))
						keyList.add(i);
				}
				
				int maxKey = Collections.max(keyList);
		
				saveOpenValues();	
		
					if(currentItem<maxKey){
						currentItem++;
						while(!table.containsKey(currentItem)){
							currentItem++;
						}
					}
					displayDetails(currentItem);			
			}
		};
		

		ActionListener prev = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				ArrayList<Integer> keyList = new ArrayList<Integer>();
				int i=0;
		
				while(i<TABLE_SIZE){
					i++;
					if(table.containsKey(i))
						keyList.add(i);
				}
				
				int minKey = Collections.min(keyList);
				//System.out.println(minKey);
				
				if(currentItem>minKey){
					currentItem--;
					while(!table.containsKey(currentItem)){
						//System.out.println("Current: " + currentItem + ", min key: " + minKey);
						currentItem--;
					}
				}
				displayDetails(currentItem);				
			}
		};
	
		ActionListener last = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveOpenValues();
				
				currentItem =29;
								
				while(!table.containsKey(currentItem)){
					currentItem--;
					
				}
				
				displayDetails(currentItem);
			}
		};
		
		nextItemButton.addActionListener(next);
		nextItem.addActionListener(next);
		
		prevItemButton.addActionListener(prev);
		prevItem.addActionListener(prev);

		firstItemButton.addActionListener(first);
		firstItem.addActionListener(first);

		lastItemButton.addActionListener(last);
		lastItem.addActionListener(last);
		
		deleteItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
						
							table.remove(currentItem);
							JOptionPane.showMessageDialog(null, "Account Deleted");
							

							currentItem=0;
							while(!table.containsKey(currentItem)){
								currentItem++;
							}
							displayDetails(currentItem);
							
			}
		});
		
		createItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				new CreateBankDialog(table);		
			}
		});
		
		
		modifyItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				surnameTextField.setEditable(true);
				firstNameTextField.setEditable(true);
				
				openValues = true;
			}
		});
		
		setInterest.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				
				 String interestRateStr = JOptionPane.showInputDialog("Enter Interest Rate: (do not type the % sign)");
				 if(interestRateStr!=null)
					 interestRate = Double.parseDouble(interestRateStr);
			
			}
		});
		
		listAll.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
		
				JFrame frame = new JFrame("TableDemo");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				String col[] = {"ID","Number","Name", "Account Type", "Balance", "Overdraft"};
				
				DefaultTableModel tableModel = new DefaultTableModel(col, 0);
				jTable = new JTable(tableModel);
				JScrollPane scrollPane = new JScrollPane(jTable);
				jTable.setAutoCreateRowSorter(true);
				
				for (Map.Entry<Integer, BankAccount> entry : table.entrySet()) {
				   
				    
				    Object[] objs = {entry.getValue().getAccountID(), entry.getValue().getAccountNumber(), 
				    				entry.getValue().getFirstName().trim() + " " + entry.getValue().getSurname().trim(), 
				    				entry.getValue().getAccountType(), entry.getValue().getBalance(), 
				    				entry.getValue().getOverdraft()};

				    tableModel.addRow(objs);
				}
				frame.setSize(600,500);
				frame.add(scrollPane);
				//frame.pack();
		        frame.setVisible(true);			
			}
		});
		
		open.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				readFile();
				currentItem=0;
				while(!table.containsKey(currentItem)){
					currentItem++;
				}
				displayDetails(currentItem);
			}
		});
		
		save.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				writeFile();
			}
		});
		
		saveAs.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				saveFileAs();
			}
		});
		
		closeApp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int answer = JOptionPane.showConfirmDialog(BankApplication.this, "Do you want to save before quitting?");
				if (answer == JOptionPane.YES_OPTION) {
					saveFileAs();
					dispose();
				}
				else if(answer == JOptionPane.NO_OPTION)
					dispose();
				else if(answer==0)
					;
				
				
				
			}
		});	
		
	
		findBySurname.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				
				String sName = JOptionPane.showInputDialog("Search for surname: ");
				boolean found = false;
				
				 for (Map.Entry<Integer, BankAccount> entry : table.entrySet()) {
					   
					 BankAccount value = entry.getValue();
					 
					if(sName.equalsIgnoreCase((value.getSurname().trim()))){
						 found = true;
						 accountIDTextField.setText(value.getAccountID()+"");
						 accountNumberTextField.setText(value.getAccountNumber());
						 surnameTextField.setText(value.getSurname());
						 firstNameTextField.setText(value.getFirstName());
						 accountTypeTextField.setText(value.getAccountType());
						 balanceTextField.setText(value.getBalance()+"");
						 overdraftTextField.setText(value.getOverdraft()+"");
					 }
				 }		
				 if(found)
					 JOptionPane.showMessageDialog(null, "Surname  " + sName + " found.");
				 else
					 JOptionPane.showMessageDialog(null, "Surname " + sName + " not found.");
			}
		});
		
		findByAccount.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				
				String accNum = JOptionPane.showInputDialog("Search for account number: ");
				boolean found = false;
			
				 for (Map.Entry<Integer, BankAccount> entry : table.entrySet()) {
					   
					 BankAccount value = entry.getValue();
					if(accNum.equals(value.getAccountNumber().trim())){
						 found = true;
						 accountIDTextField.setText(value.getAccountID()+"");
						 accountNumberTextField.setText(value.getAccountNumber());
						 surnameTextField.setText(value.getSurname());
						 firstNameTextField.setText(value.getFirstName());
						 accountTypeTextField.setText(value.getAccountType());
						 balanceTextField.setText(value.getBalance()+"");
						 overdraftTextField.setText(value.getOverdraft()+"");						
						 
					 }			 
				 }
				 if(found)
					 JOptionPane.showMessageDialog(null, "Account number " + accNum + " found.");
				 else
					 JOptionPane.showMessageDialog(null, "Account number " + accNum + " not found.");
				
			}
		});
		
		deposit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String accNum = JOptionPane.showInputDialog("Account number to deposit into: ");
				boolean found = false;
				
				for (Map.Entry<Integer, BankAccount> entry : table.entrySet()) {
					BankAccount value = entry.getValue();
					if(accNum.equals(value.getAccountNumber().trim())){
						found = true;
						String toDeposit = JOptionPane.showInputDialog("Account found, Enter Amount to Deposit: ");
						value.setBalance(value.getBalance() + Double.parseDouble(toDeposit));
						displayDetails(entry.getKey());
						//balanceTextField.setText(entry.getValue().getBalance()+"");
					}
				}
				if (!found)
					JOptionPane.showMessageDialog(null, "Account number " + accNum + " not found.");
			}
		});
		
		withdraw.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String accNum = JOptionPane.showInputDialog("Account number to withdraw from: ");
				String toWithdraw = JOptionPane.showInputDialog("Account found, Enter Amount to Withdraw: ");
				for (Map.Entry<Integer, BankAccount> entry : table.entrySet()) {
					

					BankAccount value = entry.getValue();
					if(accNum.equals(value.getAccountNumber().trim())){
						
						if(value.getAccountType().trim().equals("Current")){
							if(Double.parseDouble(toWithdraw) > value.getBalance() + value.getOverdraft())
								JOptionPane.showMessageDialog(null, "Transaction exceeds overdraft limit");
							else{
								value.setBalance(value.getBalance() - Double.parseDouble(toWithdraw));
								displayDetails(entry.getKey());
							}
						}
						else if(value.getAccountType().trim().equals("Deposit")){
							if(Double.parseDouble(toWithdraw) <= value.getBalance()){
								value.setBalance(value.getBalance()-Double.parseDouble(toWithdraw));
								displayDetails(entry.getKey());
							}
							else
								JOptionPane.showMessageDialog(null, "Insufficient funds.");
						}
					}					
				}
			}
		});
		
		calcInterest.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				for (Map.Entry<Integer, BankAccount> entry : table.entrySet()) {
					if(entry.getValue().getAccountType().equals("Deposit")){
						double equation = 1 + ((interestRate)/100);
						entry.getValue().setBalance(entry.getValue().getBalance()*equation);
						//System.out.println(equation);
						JOptionPane.showMessageDialog(null, "Balances Updated");
						displayDetails(entry.getKey());
					}
				}
			}
		});		
	}
	
	public void saveOpenValues(){		
		if (openValues){
			surnameTextField.setEditable(false);
			firstNameTextField.setEditable(false);
				
			table.get(currentItem).setSurname(surnameTextField.getText());
			table.get(currentItem).setFirstName(firstNameTextField.getText());
		}
	}	
	
	public void displayDetails(int currentItem) {	
				
		BankAccount tableItems = table.get(currentItem);
		accountIDTextField.setText(tableItems.getAccountID()+"");
		accountNumberTextField.setText(tableItems.getAccountNumber());
		surnameTextField.setText(tableItems.getSurname());
		firstNameTextField.setText(tableItems.getFirstName());
		accountTypeTextField.setText(tableItems.getAccountType());
		balanceTextField.setText(tableItems.getBalance()+"");
		if(accountTypeTextField.getText().trim().equals("Current"))
			overdraftTextField.setText(tableItems.getOverdraft()+"");
		else
			overdraftTextField.setText("Only applies to current accs");
	
	}
	
	public static RandomAccessFile input;
	public static RandomAccessFile output;
	static String fileToSaveAs = "";
	
	public static void openFileWrite()
	   {
		if(fileToSaveAs!=""){
	      try // open file
	      {
	         output = new RandomAccessFile( fileToSaveAs, "rw" );
	         JOptionPane.showMessageDialog(null, "Accounts saved to " + fileToSaveAs);
	      } // end try
	      catch ( IOException ioException )
	      {
	    	  JOptionPane.showMessageDialog(null, "File does not exist.");
	      } // end catch
		}
		else
			 FileHandling.saveToFileAs();
	   }

	

	


	public static void writeFile(){
	
		 FileHandling.saveToFile();
		//addRecords();
		 FileHandling.closeFile();
	}
	
	public static void saveFileAs(){
		 FileHandling.saveToFileAs();
		 FileHandling.saveToFile();	
		 FileHandling.closeFile();
	}
	
	public static void readFile(){
		FileHandling.openFileWrite();
	    FileHandling.readRecords();
	    FileHandling.closeFile();		
	}
	
	public void put(int key, BankAccount value){
		int hash = (key%TABLE_SIZE);
	
		while(table.containsKey(key)){
			hash = hash+1;
		
		}
		table.put(hash, value);

	}
	
	public static void main(String[] args) {
		BankApplication ba = new BankApplication();
		ba.setSize(1200,400);
		ba.pack();
		ba.setVisible(true);
	}
	
	
}


