/*****************************
 * 
 * @Author: TeamSeis
 * ICS 314: Calendar Maker
 * 
 *****************************/

import java.io.*;
import java.util.*;
 
public class CalendarMaker {
  private final static int          CLASSIFICATION = 1;
  private final static int          LOCATION = 2;
  private final static int          EVENT = 3;
  private final static int          START_TIME = 4;
  private final static int          END_TIME = 5;
  private final static int          DISPLAY_DATA = 6;
  private final static int          MAKE_CALENDAR = 7;
  private final static int          QUIT = 8;

  private boolean                   isPublic;
  private String					classification;
  private String                    location;
  private String                    event;

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
  
  public CalendarMaker() {
    scanner = new Scanner(System.in);
    isPublic = false;
    location = new String("");
    event = new String("");
    eventDate = new DateBlock();
  }

  public void start() {
    int inputInt = 0;
    boolean test = true;
    while (inputInt != QUIT) {
      System.out.print("[1]Public/Private,[2]Location, "
          + "[3]Event Title, [4]Begin Time,[5]End Time, [6]Display Data, "
          + "[8]Quit:");
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
  
  public void setLocation() {
    System.out.print("Enter the Location:");
    location = scanner.nextLine();
  }

  public void setEvent() {
    System.out.print("Enter the Event Title/Summary:");
    event = scanner.nextLine();
  }

  public boolean setStart() {
    System.out.println("------StartTime------");
    return eventDate.getStartTime().grab();
  }

  public boolean setEnd() {
    System.out.println("-------EndTime-------");
    return eventDate.getEndTime().grab();
  }

  public void displayData() {
    System.out.println("Calendar Information:");
    getClassification();
    getLocation();
    getEvent();
    getStartTime();
    getEndTime();
  }

  public void getClassification() {
    System.out.println("Public or Private Event: " + classification);
   
  }
  public void getLocation() {
    System.out.println("Location: " + location);
   
  }

  public void getEvent() {
    System.out.println("Event Title: " + event);
  }

  public void getStartTime() {
    System.out.println("------StartTime------");
    eventDate.getStartTime().show();
    System.out.println("---------------------");
  }

  public void getEndTime() {
    System.out.println("-------EndTime-------");
    eventDate.getEndTime().show();
    System.out.println("---------------------");
  }

  public void makeCalendar(String filename) {
    PrintWriter writer = null;
    try {
      writer = new PrintWriter(filename, "UTF-8");
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
