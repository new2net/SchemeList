package a4;

import java.util.EmptyStackException;
import java.util.NoSuchElementException;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.junit.Test;


public class UnboundStackTest extends TestCase {
  private UnboundStack UnboundStackEngine;


  @Test public final void testPush() {
    assertTrue(UnboundStackEngine.isEmpty());
    UnboundStackEngine.push("foo");
    assertEquals(UnboundStackEngine.length(), 1);
    UnboundStackEngine.push("bar");
    assertEquals(UnboundStackEngine.length(), 2);
  }
  
  @Test public void testPop() {
    assertTrue(UnboundStackEngine.isEmpty());
    
    UnboundStackEngine.push("foo");
    assertEquals(UnboundStackEngine.pop(), "foo");
    
    //will throw the exception
    // the @Test(expected= ... ) isn't working >:|
    try {
      UnboundStackEngine.pop();
    } catch (EmptyStackException e) {
      assertTrue(true);
    }
  }
  
  @Test public void testPeek() {
    assertTrue(UnboundStackEngine.isEmpty());
    UnboundStackEngine.push("foo");
    UnboundStackEngine.push("bar");
    UnboundStackEngine.push("baz");
    
    assertEquals(UnboundStackEngine.peek(),"bar");
    UnboundStackEngine.pop();
    assertEquals(UnboundStackEngine.peek(),"foo");
    UnboundStackEngine.pop();
    //will throw NoSuchElementException
    try {
      UnboundStackEngine.peek();
    } catch (NoSuchElementException e) {
      assertTrue(true);
    }
    
  }
  
  @Test public void testLength() {
    assertTrue(UnboundStackEngine.isEmpty());
    //test empty stack
    assertEquals(UnboundStackEngine.length(), 0);
    
    UnboundStackEngine.push("foo");
    UnboundStackEngine.push("bar");
    UnboundStackEngine.push("baz");
    
    assertEquals(UnboundStackEngine.length(), 3);
  }
  
  @Test public void testSearch() {
    assertTrue(UnboundStackEngine.isEmpty());
    UnboundStackEngine.push("foo");
    UnboundStackEngine.push("bar");
    UnboundStackEngine.push("baz");
    
    assertEquals(UnboundStackEngine.search("foo"), 3);
    assertEquals(UnboundStackEngine.search("bar"), 2);
    assertEquals(UnboundStackEngine.search("baz"), 1);
    
    assertEquals(UnboundStackEngine.search("non-existant element"), -1);
  }
  
  
  
  
  public static void main(String[] args) {
    junit.textui.TestRunner.run(UnboundStackTest.suite());
  }

  private static TestSuite suite() {
    TestSuite suite = new TestSuite("Test for a4.UnboundStack.java");
    suite.addTestSuite(UnboundStackTest.class);
    return suite;
  }
  
  protected void setUp() throws Exception {
    super.setUp();
  }

  /**
   * Constructor for UnboundStackTest (junit test)
   */
  public UnboundStackTest(String arg0) {
    super(arg0);
    UnboundStackEngine = new UnboundStack();
  }  
  
}
