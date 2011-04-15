package a4;

import java.text.Collator;
import java.util.Comparator;

/**
 * @author new2net
 * Taken from: java2s.com/Tutorial/Java/
 */
class LexicographicalComparator implements Comparator<String> {
  Collator col;

  LexicographicalComparator() {
    col = Collator.getInstance();
    col.setStrength(Collator.PRIMARY);
  }

  public int compare(String strA, String strB) {
    return col.compare(strA, strB);
  }
}
