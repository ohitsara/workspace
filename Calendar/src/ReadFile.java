import java.io.*;
import java.util.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class ReadFile {
	  

	private static	Date [] time = new Date [100];
	
	public static void main(String [] args)throws Exception {
		
		Scanner scan		= new Scanner(System.in);
		String	file		= "";
		String	fileName	= "";
		String	line		= "";
		
		String	eventStartTime	= "";
		String	eventEndTime	= "";
		String	eventDate		= "";
		
        String [] event = new String[2];
        String [] eventTime = new String [2];
        
         
        System.out.println("Please input the calendar file:");
		file = scan.nextLine();
        fileName = file;
        
        try {
            
            FileReader fileReader = new FileReader(fileName);
   
            BufferedReader bufferedReader = 
                new BufferedReader(fileReader);
            
            while((line = bufferedReader.readLine()) != null) {
                
            	if (line.contains("DTSTART")){
                	
            		line = line.substring(0, line.length()-1);
                	event = line.split(":", 2);
    	        	eventTime = event[1].split("T", 2);
    	        	eventStartTime = eventTime[1];
    	        	eventDate = eventTime[0];

    	        	int eventST = Integer.parseInt(eventStartTime);
    	            Date stime = new SimpleDateFormat("hhmm").parse(String.format("%04d", eventST));
    	            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
    	            eventStartTime = sdf.format(stime);
    	            
    	        	System.out.println(eventStartTime);
    	        	
                }
            	if (line.contains("DTEND")){
            		
            		line = line.substring(0, line.length()-1);
            		event = line.split(":", 2);
    	        	eventTime = event[1].split("T", 2);
    	        	eventEndTime = eventTime[1];
    	        	eventDate = eventTime[0];

    	        	int eventET = Integer.parseInt(eventEndTime);
    	            Date etime = new SimpleDateFormat("hhmm").parse(String.format("%04d", eventET));
    	            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
    	            eventEndTime = sdf.format(etime);
    	        	System.out.println(eventEndTime);
            		
            	}
    
            }  
  

            // Always close files.
            bufferedReader.close();         
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file '" + 
                fileName + "'");                
        }
        catch(IOException ex) {
            System.out.println(
                "Error reading file '" 
                + fileName + "'");                  
            // Or we could just do this: 
            // ex.printStackTrace();
        }scan.close();
    }
}