package me;

/**
 * NOTE: Everything in the "provided" package IS NOT representative for
 * the level of quality we are expecting for the coding task submission.
 */
public class Event {

  public String someData;
  public long time;

  @Override
  public String toString() {
    return "Event(" + time + ", " + someData + ')';
  }
}
