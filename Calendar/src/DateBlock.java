/*
 * 
 * @author: TeamSeis
 * ICS 314-SP16 
 * 
 * This class sets up the date, start time
 * and end time of the CalendarMaker class
 * Comment: Code recycled and modified from
 * previous homework/class taken. 
 */
public class DateBlock implements Comparable<DateBlock> {
  private DateData	startTime;
  private DateData	endTime;

  public DateBlock() {
    startTime = new DateData();
    endTime = new DateData();
  }

  public DateBlock(DateData start, DateData end) {
    startTime = start;
    endTime = end;
  }

  public DateBlock(String start, String end) {
    startTime = new DateData(start);
    endTime = new DateData(end);
  }

  public static DateBlock difference(DateBlock left, DateBlock right) {
    DateBlock diff = new DateBlock();
    diff.setStartTime(new DateData(left.getEndTime()));
    diff.setEndTime(new DateData(right.getStartTime()));
    if (DateData.timeDifference(diff.getStartTime(), diff.getEndTime()) <= 0) return null;
    return diff;
  }

  public DateData getStartTime() {
    return startTime;
  }

  public DateData getEndTime() {
    return endTime;
  }

  public void setStartTime(DateData start) {
    startTime = start;
  }

  public void setEndTime(DateData end) {
    endTime = end;
  }

  public int compareTo(DateBlock date) {
    return startTime.compareTo(date.startTime);
  }
}
