package bankexercise;

import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Map;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class FileHandling extends BankApplication {
	
	
	private static final long serialVersionUID = 1L;

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
			saveToFileAs();
	   }

	
	
public static void saveToFile(){
		
	
		RandomAccessBankAccount record = new RandomAccessBankAccount();
	
	      for (Map.Entry<Integer, BankAccount> entry : table.entrySet()) {
			   record.setAccountID(entry.getValue().getAccountID());
			   record.setAccountNumber(entry.getValue().getAccountNumber());
			   record.setFirstName(entry.getValue().getFirstName());
			   record.setSurname(entry.getValue().getSurname());
			   record.setAccountType(entry.getValue().getAccountType());
			   record.setBalance(entry.getValue().getBalance());
			   record.setOverdraft(entry.getValue().getOverdraft());
			   
			   if(output!=null){
			   
			      try {
						record.write( output );
					} catch (IOException u) {
						u.printStackTrace();
					}
			   }
			   
			}
	}

public static void readRecords()
{

   RandomAccessBankAccount record = new RandomAccessBankAccount();

   

   try // read a record and display
   {
      while ( true )
      {
         do
         {
         	if(input!=null)
         		record.read( input );
         } while ( record.getAccountID() == 0  ); //seems to be getting stuck in a constant loop here, input is null

         BankAccount ba = new BankAccount(record.getAccountID(), record.getAccountNumber(), record.getFirstName(),
                 record.getSurname(), record.getAccountType(), record.getBalance(), record.getOverdraft());
         
         
         Integer key = Integer.valueOf(ba.getAccountNumber().trim()); 
		
			int hash = (key%TABLE_SIZE);
	
			
			while(table.containsKey(hash)){
		
				hash = hash+1;
			}
			
         table.put(hash, ba);
	

      } // end while
   } // end try
   catch ( EOFException eofException ) // close file
   {
      return; // end of file was reached
   } // end catch
   catch ( IOException ioException )
   {
 	  JOptionPane.showMessageDialog(null, "Error reading file.");
      System.exit( 1 );
   } // end catch
}

public static void closeFile() 
{
   try // close file and exit
   {
      if ( input != null )
         input.close();
   } // end try
   catch ( IOException ioException )
   {
      
 	  JOptionPane.showMessageDialog(null, "Error closing file.");//System.exit( 1 );
   } // end catch
} // end method closeFile


public static void saveToFileAs()
   {
	
	fc = new JFileChooser();
	
	 int returnVal = fc.showSaveDialog(null);
     if (returnVal == JFileChooser.APPROVE_OPTION) {
         File file = fc.getSelectedFile();
       
         fileToSaveAs = file.getName();
         JOptionPane.showMessageDialog(null, "Accounts saved to " + file.getName());
     } else {
         JOptionPane.showMessageDialog(null, "Save cancelled by user");
     }
    
 	    
         try {
        	 if(fc.getSelectedFile()==null){
        		 JOptionPane.showMessageDialog(null, "Cancelled");
        	 }
        	 else
        		 output = new RandomAccessFile(fc.getSelectedFile(), "rw" );
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      
      
     
   }
}


