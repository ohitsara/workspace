import java.io.*;
import java.util.*;

public class CalendarMaker {
  private final static int          CLASSIFICATION = 1;
  private final static int          LOCATION = 2;
  private final static int          SUMMARY = 3;
  private final static int          START_TIME = 4;
  private final static int          END_TIME = 5;
  private final static int          DISPLAY_DATA = 6;
  private final static int          MAKE_CALENDAR = 7;
  private final static int          QUIT = 8;

  private boolean                   mIsPublic;
  private int                       mPriority;
  private String                    eLocation;
  private String                    eSummary;

  private DateBlock             mDateBlock;
  private Scanner                   eScan;

  public static void main(String[] args) {
	int userChoice = 0;
	final int quit = 2;
	
	Scanner scan = new Scanner(System.in);
	System.out.println("Welcome to Calendar Event Maker");
	System.out.println("[1]Create Event [2]Quit");
	System.out.print("Your Choice:");
	userChoice = scan.nextInt();
	
	try{
		if(userChoice != quit){
			CalendarMaker myCalendar = new CalendarMaker();
		    myCalendar.start();
		}else
			System.out.println("Thank you for using Calendar Maker.");	
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
    eScan = new Scanner(System.in);
    mIsPublic = false;
    eLocation = new String("");
    eSummary = new String("");
    mDateBlock = new DateBlock();
  }

  public void start() {
    int inputInt = 0;
    boolean test = true;
    while (inputInt != QUIT) {
      System.out.println("(1)Public/Private, (2)Location, "
          + "(3)Summary, (4)Start Time, (5)End Time,(6)Show Data, "
          + "(7)Make Calendar, (8)Quit");
      try {
        inputInt = eScan.nextInt();
      } catch (Exception e) {
        inputInt = -1;
      }
      test = true;
      switch (inputInt) {
      case CLASSIFICATION:
        test = grabPublic();
        break;
      case LOCATION:
        grabLocation();
        break;
      case SUMMARY:
        grabSummary();
        break;
      case START_TIME:
        test = grabStart();
        break;
      case END_TIME:
        test = grabEnd();
        break;
      case DISPLAY_DATA:
        showCurrentData();
        break;
      case MAKE_CALENDAR:
        System.out.println("Enter a filename to save data");
        String filename = eScan.nextLine();
        makeCalendar(filename);
        break;
      case QUIT:
        System.out.println("Thank you for using Calendar Maker");
        return;
      default:
        System.out.println("Invalid Command");
      }
      if (!test) {
        System.out.println("Invalid Data Entered");
      }
    }
  }

  public void createFreeTime(String[] filenames) {
    ArrayList<DateBlock> mDateBlocks = new ArrayList<DateBlock>();
    for (String filename : filenames) {
      if (validFile(filename)) {
        File file = new File(filename);
        Scanner fileScanner = null;
        try {
          fileScanner = new Scanner(file);
        } catch (Exception e) {
        }
        String start = null;
        String end = null;
        // parse .ics for DTSTART and DTEND and init objects
        while (fileScanner.hasNext()) {
          String[] splitLine = fileScanner.nextLine().split(":");
          if (stringMatch(splitLine[0], "DTSTART"))
            start = splitLine[1];
          if (stringMatch(splitLine[0], "DTEND"))
            end = splitLine[1];
        }
        // if the .ics file has proper DTSTART and DTEND
        if (start != null && end != null) {
          DateBlock block = new DateBlock(start, end);
          block.getStartTime().show();
          block.getEndTime().show();
          // all date blocks must be on the same day, only add if they are
          if (mDateBlocks.isEmpty() || (!mDateBlocks.isEmpty() && 
              DateData.isSameDate(mDateBlocks.get(0).getStartTime(), block.getStartTime()))) {
            mDateBlocks.add(block);
          }
        }
      }
    }
    if (mDateBlocks.size() == 0) {
      return;
    }
    int year = mDateBlocks.get(0).getStartTime().getCal().get(Calendar.YEAR);
    // add one because cal.get(Calendar.MONTH) starts at 0 for jan
    int month = mDateBlocks.get(0).getStartTime().getCal().get(Calendar.MONTH) + 1;
    int day = mDateBlocks.get(0).getStartTime().getCal()
        .get(Calendar.DAY_OF_MONTH);
    mDateBlocks.add(new DateBlock(new DateData(year, month, day, 0, 0, 0),
        new DateData(year, month, day, 0, 0, 0)));
    mDateBlocks.add(new DateBlock(
        new DateData(year, month, day, 23, 59, 59), new DateData(year, month,
            day, 23, 59, 59)));
    Collections.sort(mDateBlocks);
    eSummary = "Free Time";
    for (int i = 1; i < mDateBlocks.size(); i++) {
      DateBlock temp = DateBlock.difference(mDateBlocks.get(i-1), 
          mDateBlocks.get(i));
      if (temp != null) {
        mDateBlock = temp;
        makeCalendar(Integer.toString(i) + ".ics");
      }
    }
  }

  public boolean grabPublic() {
    System.out.println("(1)Public or (2)Private?");
    int inputInt = Integer.parseInt(eScan.nextLine());
    if (inputInt != 1 && inputInt != 2)
      return false;
    if (inputInt == 1) {
      mIsPublic = true;
    } else {
      mIsPublic = false;
    }
    return true;
  }

  public boolean grabPriority() {
    System.out.println("Enter a Priority (1-9)");
    int inputInt = Integer.parseInt(eScan.nextLine());
    if (inputInt < 1 || inputInt > 9)
      return false;
    mPriority = inputInt;
    return true;
  }

  public void grabLocation() {
    System.out.println("Enter a Location");
    eLocation = eScan.nextLine();
  }

  public void grabSummary() {
    System.out.println("Enter a Summary");
    eSummary = eScan.nextLine();
  }

  public boolean grabStart() {
    System.out.println("------StartTime------");
    return mDateBlock.getStartTime().grab();
  }

  public boolean grabEnd() {
    System.out.println("-------EndTime-------");
    return mDateBlock.getEndTime().grab();
  }

  public void showCurrentData() {
    System.out.println("Calendar Information:");
    showPublic();
    showPriority();
    showLocation();
    showSummary();
    showStartTime();
    showEndTime();
  }

  public void showPublic() {
    System.out.println("Public: " + Boolean.toString(mIsPublic));
  }

  public void showPriority() {
    System.out.println("Priority: " + mPriority);
  }

  public void showLocation() {
    System.out.println("Location: " + eLocation);
  }

  public void showSummary() {
    System.out.println("Summary: " + eSummary);
  }

  public void showStartTime() {
    System.out.println("------StartTime------");
    mDateBlock.getStartTime().show();
    System.out.println("---------------------");
  }

  public void showEndTime() {
    System.out.println("-------EndTime-------");
    mDateBlock.getEndTime().show();
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
    if (mIsPublic) {
      writer.println("CLASS:PUBLIC");
    } else {
      writer.println("CLASS:PRIVATE");
    }
    writer.println("PRIORITY:" + mPriority);
    writer.println("LOCATION:" + eLocation);
    writer.println("SUMMARY:" + eSummary);
    writer.println("DTSTART;TZID=Pacific/Honolulu:"
        + mDateBlock.getStartTime().format());
    writer.println("DTEND;TZID=Pacific/Honolulu:"
        + mDateBlock.getEndTime().format());
    writer.println("END:VEVENT");
    writer.println("END:VCALENDAR");
    System.out.println("Finished Creating File");
    writer.close();
  }

  private boolean validFile(String filename) {
    File file = new File(filename);
    if (file.exists() && !file.isDirectory())
      return true;
    return false;
  }

  private boolean stringMatch(String string, String pattern) {
    if (string.isEmpty() || pattern.isEmpty()
        || pattern.length() > string.length())
      return false;
    for (int i = 0; i < pattern.length(); i++) {
      if (string.charAt(i) != pattern.charAt(i))
        return false;
    }
    return true;
  }

}
