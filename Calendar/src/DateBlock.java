public class DateBlock implements Comparable<DateBlock> {
  private DateData                mStartTime;
  private DateData                mEndTime;

  public DateBlock() {
    mStartTime = new DateData();
    mEndTime = new DateData();
  }

  public DateBlock(DateData start, DateData end) {
    mStartTime = start;
    mEndTime = end;
  }

  public DateBlock(String start, String end) {
    mStartTime = new DateData(start);
    mEndTime = new DateData(end);
  }

  public static DateBlock difference(DateBlock left, DateBlock right) {
    DateBlock diff = new DateBlock();
    diff.setStartTime(new DateData(left.getEndTime()));
    diff.setEndTime(new DateData(right.getStartTime()));
    if (DateData.timeDifference(diff.getStartTime(), diff.getEndTime()) <= 0) return null;
    return diff;
  }

  public DateData getStartTime() {
    return mStartTime;
  }

  public DateData getEndTime() {
    return mEndTime;
  }

  public void setStartTime(DateData start) {
    mStartTime = start;
  }

  public void setEndTime(DateData end) {
    mEndTime = end;
  }

  @Override
  public int compareTo(DateBlock o) {
    return mStartTime.compareTo(o.mStartTime);
  }
}
