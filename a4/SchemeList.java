package a4;

import a4.LexicographicalComparator;
import java.util.AbstractCollection;
import java.util.Iterator;

/**
 * @author new2net (irc.freenode.net)
 */

abstract class SchemeList<T> extends AbstractCollection<T> implements Comparable<T> {

  public interface Fun<T, T2> {
    T2 apply(T obj);
  }

  public interface Proc<T> {
    void apply(T obj);
  }

  private static class Node<T> extends SchemeList<T> {
    
    private T head;
    private SchemeList<T> tail;

    public Node(T head, SchemeList<T> tail) {
      this.head = head;
      this.tail = tail;
    }

    @Override
    public T head() {
      return head;
    }

    @Override
    public SchemeList<T> tail() {
      return tail;
    }

    @Override
    public int length() {
      return tail().length() + 1;
    }


    @Override
    public SchemeList<T> append(SchemeList<T> list) {
      return list == EMPTY ? this : new Node<T>(list.head(), append(list.tail()));
    }

    @Override
    public int size() {
      return length();
    }

    //abstract public <T2> SchemeList<T2> map(Fun<T, T2> f);

    @Override
    public <T2> SchemeList<T2> map(Fun<T, T2> f) {
      return new Node<T2>(f.apply(head), tail.map(f));
    }

    @Override
    public void forEach(Proc<T> f) {
      f.apply(head);
      tail.forEach(f);
    }

    @Override
    public String toString() {
      Iterator<T> toStringIterator = iterator();
      StringBuffer tempString = new StringBuffer("(");
      
      while(toStringIterator.hasNext()) {
        tempString.append(toStringIterator.next() + " ");
      }

      tempString.deleteCharAt(tempString.length() - 1);
      return tempString + ")";
    }

    @Override
    public int compareTo(T o) {
      return new LexicographicalComparator().compare(head.toString(), o.toString());
    }


    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((head == null) ? 0 : head.hashCode());
      result = prime * result + ((tail == null) ? 0 : tail.hashCode());
      return result;
    }

    
    @Override
    public boolean equals(Object obj) {
      if (!(obj instanceof Node))
        return false;
      if (obj == null)
        return false;
      
      //this should work now that I know it's of type Node and not null
      return hashCode() == obj.hashCode();
    }

  }

  private static class Empty<T> extends SchemeList<T> {
    @SuppressWarnings("serial")
    private static class Exception extends UnsupportedOperationException {
    }

    // default constructor -- nothing to construct
    
    @Override
    public T head() throws Exception {
      throw new Exception();
    }

    @Override
    public SchemeList<T> tail() throws Exception {
      throw new Exception();
    }

    @Override
    public SchemeList<T> append(SchemeList<T> list) {
      return list;
    }

    @Override
    public int length() {
      return 0;
    }

    @Override
    public String toString() {
      return "()";
    }

    @Override
    public int size() {
      return length();
    }

    @Override
    public <T2> SchemeList<T2> map(Fun<T, T2> f) {
      return (SchemeList<T2>) this;
    }

    @Override
    public void forEach(Proc f) {
    }

    @Override
    public int compareTo(T o) {
      return new LexicographicalComparator().compare("", o.toString());
    }

  }

  /**
   * @author RLa (irc.freenode.net #java)
   */
  private static class SchemeListIterator<T> implements Iterator<T> {
    private SchemeList<T> list;

    public SchemeListIterator(SchemeList<T> list) {
      this.list = list;
    }

    @Override
    public boolean hasNext() {
      return list != EMPTY;
    }

    @Override
    public T next() {
      T head = list.head();
      list = list.tail();
      return head;
    }

    @Override
    public void remove() {
      throw new UnsupportedOperationException();
    }
  }

  /**
   * This is the empty list. Use it to build new lists. All lists end with the
   * empty list.
   */
  public static final ScemeList<T> EMPTY = new Empty<T>();

  private SchemeList() {} // You must use the emptyList

  abstract public boolean equals(Object obj);

  /**
   * @return The datum in the link list Node
   * @throws Exception
   *           if the node is an empty list.
   */
  abstract public T head();

  /**
   * @return The node (if it exists) linked to the invoking node.
   * @throws Exception
   *           if the node is the empty list.
   */
  abstract public SchemeList<T> tail();

  /**
   * returns the length of the list, 0 if the list is empty.
   * 
   * @return the length of the list.
   */
  abstract public int length();

  abstract public String toString();

  /**
   * Takes any object and links it to the end of the invoking list as a new
   * SchemeList Node with the object in the "head" of the list.
   * 
   * @param obj
   *          - any object
   * @return SchemeList
   */
  public SchemeList<T> cons(T obj) {
    return new Node<T>(obj, this);
  }

  /**
   * Takes all of the nodes from the argument "list" and uses cons to link them
   * to the invoking list.
   * 
   * @param list
   *          SchemeList whos nodes will be attached to the invoking list.
   * @return SchemeList - a new list with the invoking lists nodes preceded by
   *         the nodes in the list passed to append.
   */
  abstract public SchemeList<T> append(SchemeList<T> list);

  /**
   * Applies an anonymous function to the invoking list, and returns a new
   * list (in the same order) with values processed by the anonymous function
   * Fun.apply().
   * 
   * @param f
   *          - SchemeList.Fun.apply (an anonymous function)
   * @return - A list in the same order as the one which called map()
   */
  abstract public <T2> SchemeList<T2> map(Fun<T, T2> f);

  /**
   * Goes through the list in order passing each SchemeList node as an argument
   * (Object) to an anonymous function SchemeList.Proc.apply().
   * 
   * Note that forEach() does not return any value, unlike map().
   * 
   * @param f
   *          - SchemeList.Proc.apply (an anonymous function)
   */
  abstract public void forEach(Proc<T> f);

  /**
   * Compares the parameter o to the current head using lexicographical ordering.
   * If the invoking list is empty, then the parameter o will be compared to an
   * empty string.
   * 
   * @param o - The object to be compared
   * 
   * @return   -1 (o comes before the current list element), 
   *            0 (o equals the current list element), 
   *            1 (o comes after the current list element).
   *            
   * @throws ClassCastException if the specified object's type prevents
   *         it from being compared to this object.
   */
  abstract public int compareTo(T o);
  
  @Override
  public final Iterator<T> iterator() {
    return new SchemeListIterator<T>(this);
  }

  
  
}
