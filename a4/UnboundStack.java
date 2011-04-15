package a4;

import static a4.SchemeList.EMPTY;
import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * UnboundStack is a stack with no fixed length. 
 * It's size is determined by the available space in the heap.
 * 
 * Homework Assignment: #4
 * 
 * @author Ian Good (igood)
 */
public class UnboundStack {
  private SchemeList linkedList = EMPTY; 
  
  /**
   * @return boolean - true if UnboundStack is empty
   */
  public boolean isEmpty() {
    return linkedList == EMPTY;
  }

  /**
   * @return the number of elements in the stack (0 if empty).
   */
  public int length() {
    return linkedList.length();
  }

  /**
   * Precondition: none 
   * Postcondition: The object is added to the top of Stack and returned
   * 
   * @param head
   *          - the instance to be added to the top of the StaticStack
   * @return the parameter head
   */
  public Object push(Object head) {
    linkedList = linkedList.cons(head);
    return linkedList.head();
  }

  /**
   * Precondition The StaticStack is not empty. Postcondition The object at the
   * top of the Stack is removed from the stack and returned.
   * 
   * @throws EmptyStackException
   *           if the stack is empty
   * @return The object at the top of the StaticStack
   */
  public Object pop() {
    if (isEmpty()) {
      throw new EmptyStackException();
    }
    
    Object head = linkedList.head();
    linkedList = linkedList.tail();
    return head;
  }

  /**
   * Precondition The stack contains at least 2 objects. Postcondition The
   * object under the topmost object is returned without being removed
   * 
   * @return The object instance under the top (if it exists)
   * 
   * @throws NoSuchElementException
   *           if the stack contains less than 2 elements
   */
  public Object peek() {
    if (length() < 2) {
      throw new NoSuchElementException();
    }
    return linkedList.tail().head();
  }

  /**
   * returns the distance from the top of the stack of the occurrence nearest
   * the top of the stack
   * 
   * @return 1 if the top of the stack.equals(obj) + 1 for each step from the
   *         top -1 if the object was not found
   * 
   */
  public int search(Object obj) {
    @SuppressWarnings("rawtypes")
    Iterator searchIterator = linkedList.iterator();
    
    int distance = 1;
    while(searchIterator.hasNext()) {
      if (searchIterator.next().equals(obj))
        return distance;
      distance++;
    }
    return -1;
  }  
}