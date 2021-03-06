/*
 * 
 * @Author: TeamSeis
 * ICS314-SP16
 * iCal event maker
 * Final Version
 * 
 **/

import java.io.*;
import java.util.*;
import java.lang.Float;

public class CalendarMaker {
	
  /*
   * @param
   * For user input into the switch statements
   * private: variables are only use for this class
   * Final: values are constant
   * Static: to be read on void methods
   */
  private final static int          CLASSIFICATION = 1;
  private final static int          LOCATION = 2;
  private final static int			GEOGRAPHIC_POSITION = 3;
  private final static int          EVENT = 4;
  private final static int          START_TIME = 5;
  private final static int          END_TIME = 6;
  private final static int          DISPLAY_DATA = 7;
  private final static int          MAKE_CALENDAR = 8;
  private final static int          QUIT = 9;

  private boolean                   isPublic;
  private String					classification;
  private String                    location;
  private String                    event;
  private String					geoLat;
  private String					geoLon;
  private float						geoLocateLat;
  private float						geoLocateLon;
  
  //Object instantiation: DateBlock from class DateBlock
  private DateBlock             	eventDate;
  private Scanner                   scanner;

  public static void main(String[] args) {
	  int userChoice = 0;
		final int quit = 2;
		
		Scanner scan = new Scanner(System.in);
		System.out.println("Welcome to Calendar Event Maker");
		System.out.println("[1]Create Event [2]Quit");
		System.out.print("Your Choice:");
		userChoice = scan.nextInt();
		
		try{
			if(userChoice == 1){
				CalendarMaker myCalendar = new CalendarMaker();
			    myCalendar.start();
			}else if(userChoice == quit){
				System.out.println("Thank you for using Calendar Maker.");	
			}else
				System.out.println("Invalid Input:Program is closing.");
		}catch (InputMismatchException e){
			System.out.println("Error: The choice you entered is not a number.");
			System.exit(1);
		}catch (NoSuchElementException e){
			System.out.println("Error: Invalid Input.");
			System.exit(1);
		}
		scan.close();
	  }
  /*
   * Class constructor
   */
  public CalendarMaker() {
    scanner = new Scanner(System.in);
    isPublic = false;
    location = new String("");
    event = new String("");
    eventDate = new DateBlock();
  }

  /*
   * Method: start()
   * Main call in to the main method
   * This asks for the user input.
   */
  public void start() {
    int inputInt = 0;
    boolean test = true;
    while (inputInt != QUIT) {
      System.out.println("[1]Event Classification,[2]Location,[3]Geographic Position ");
      System.out.println("[4]Event Title, [5]Begin Time,[6]End Time,");
      System.out.println("7]Display Data,[8]Make Calendar, [9]Quit");
      System.out.print("Your Choice:");
      try {
        inputInt = Integer.parseInt(scanner.nextLine());
      } catch (Exception e) {
        inputInt = -1;
      }
      test = true;
      switch (inputInt) {
      case CLASSIFICATION:
        test = setPublic();
        break;
      case LOCATION:
        setLocation();
        break;
      case GEOGRAPHIC_POSITION:
    	 setGeoPosition();
    	 break;
      case EVENT:
        setEvent();
        break;
      case START_TIME:
        test = setStart();
        break;
      case END_TIME:
        test = setEnd();
        break;
      case DISPLAY_DATA:
        displayData();
        break;
      case MAKE_CALENDAR:
        System.out.println("Enter a filename to save data");
        String filename = scanner.nextLine();
        makeCalendar(filename);
        break;
      case QUIT:
        System.out.println("Quitting Program");
        return;
      default:
        System.out.println("Invalid Command");
      }
      if (!test) {
        System.out.println("Invalid Data Entered");
      }
    }
  }
  /*
   * Method: setPublic()
   * To set the event classification
   * Private/Public
   * Property: Boolean
   */
  public boolean setPublic() {
	System.out.print("Is the event,");
    System.out.print("[1]Public or [2]Private?");
    int inputInt = Integer.parseInt(scanner.nextLine());
    if (inputInt != 1 && inputInt != 2)
      return false;
    if (inputInt == 1) {
      isPublic = true;
      classification = "Public";
    } else {
      isPublic = false;
      classification = "Private";
    }
    return true;
  }
  /*
   * Method: setLocation()
   * User assigns the event location.
   */
  public void setLocation() {
    System.out.print("Enter the Location:");
    location = scanner.nextLine();
  }
/*
 * Method: setGeoPosition()
 * User assigns the coordinates of the location
 */
  public void setGeoPosition(){
	  System.out.println("If you're not sure of the coordinates,");
	  System.out.println("See google maps: https://www.google.com/maps/");
	  System.out.print("Enter Latitude coordinate:");
	  geoLocateLat = scanner.nextFloat();
	  System.out.print("Enter Longitude coordinate:");
	  geoLocateLon = scanner.nextFloat();
	  
	  geoLat = Float.toString(geoLocateLat);
	  geoLon = Float.toString(geoLocateLon);
	
  }
  /*
   * Method: setEvent()
   * User assigns header/title of the event
   */
  public void setEvent() {
    System.out.print("Enter the Event Title/Summary:");
    event = scanner.nextLine();
  }
/*
 * Method: setStart()
 * User assigns start time and date of the event
 */
  public boolean setStart() {
    System.out.println("------StartTime------");
    return eventDate.getStartTime().grab();
  }
/*
 * Method: setEnd()
 * User assigns end time and date of the event
 */
  public boolean setEnd() {
    System.out.println("-------EndTime-------");
    return eventDate.getEndTime().grab();
  }
/*
 * Method: displayData()
 * Displays the input made by the user
 * into an iCalendar format in the console.
 */
  public void displayData() {
    System.out.println("Calendar Information:");
    getClassification();
    getLocation();
    getGeoPosition();
    getEvent();
    getStartTime();
    getEndTime();
  }
/*
 * Method: getClassification()
 * Grabs the user input from
 * method call setPublic().
 */
  public void getClassification() {
    System.out.println("Public or Private Event: " + classification);
 /*
  * Method: getLocation()
  * Grabs the user input from
  * method call setLocation() 
  */
  }
  public void getLocation() {
    System.out.println("Location: " + location);
   
  }
  /*
   * Method: getGeoPosition()
   * Grabs the user input from
   * method call setGeoPosition() 
   */
  public void getGeoPosition(){
	  System.out.println("Geographic Position: " + "\n Latitude:" + geoLat
	  					+ "\n Longitude:" + geoLon);
  }
  /*
   * Method: getEvent()
   * Grabs the user input from
   * method call setEvent() 
   */
  public void getEvent() {
    System.out.println("Event Title: " + event);
  }
  /*
   * Method: getStartTime()
   * Grabs the user input from
   * method call setStartTime() 
   */
  public void getStartTime() {
    System.out.println("------StartTime------");
    eventDate.getStartTime().show();
    System.out.println("---------------------");
  }
  /*
   * Method: getEndTime()
   * Grabs the user input from
   * method call setEndTime() 
   */
  public void getEndTime() {
    System.out.println("-------EndTime-------");
    eventDate.getEndTime().show();
    System.out.println("---------------------");
  }
  /*
   * Method: makeCalendar
   * @param: filename - title of the output file
   * given by the user.
   * Prints all the user input into a .ics file
   * in the same directory of the class
   */
  public void makeCalendar(String filename) {
    PrintWriter writer = null;
    try {
      File file = new File(filename + ".ics");
      writer = new PrintWriter(file);
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("Error with file name");
      return;
    }
    writer.println("BEGIN:VCALENDAR");
    writer.println("VERSION:2.0");
    writer.println("BEGIN:VEVENT");
    if (isPublic) {
      writer.println("CLASS:PUBLIC");
    } else {
      writer.println("CLASS:PRIVATE");
    }
    writer.println("LOCATION:" + location);
    writer.println("GEOGRAPHIC POSITION:" + geoLat + "/" + geoLon);
    writer.println("SUMMARY:" + event);
    writer.println("DTSTART;TZID=Pacific/Honolulu:"
    				+ eventDate.getStartTime().format());
    writer.println("DTEND;TZID=Pacific/Honolulu:"
    				+ eventDate.getEndTime().format());
    writer.println("END:VEVENT");
    writer.println("END:VCALENDAR");
    System.out.println("Finished Creating File");
    writer.close();
  }
}
