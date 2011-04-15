package a4;

import java.util.Iterator;
import java.util.Stack;
import a4.SchemeList;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.junit.Test;


public class SchemeListTest extends TestCase {
  private SchemeList SchemeListEngine;
  
  private SchemeList zeroToNine = SchemeList.EMPTY.cons(9).cons(8).cons(7)
      .cons(6).cons(5).cons(4).cons(3).cons(2).cons(1).cons(0);


 @Test  public final void testCons() {
    assertSame(SchemeListEngine, SchemeList.EMPTY);
    
    assertTrue(SchemeListEngine.cons(null).length() == 1);
    assertTrue(SchemeListEngine.cons(zeroToNine).length() == 1);
    
    assertEquals(SchemeListEngine.cons("foo").toString(), "(foo)");
    assertEquals(SchemeListEngine.cons(zeroToNine).toString(), "((0 1 2 3 4 5 6 7 8 9))");
    assertEquals(SchemeListEngine.cons(SchemeList.EMPTY).toString(), "(())");
  }

  @Test public final void testAppend() {
    assertTrue(SchemeListEngine.length() == 0);
    assertTrue(zeroToNine.length() == 10);

    assertEquals(SchemeListEngine.append(zeroToNine).length(),
        zeroToNine.length());
    assertEquals(SchemeListEngine.append(zeroToNine).toString(),
        "(0 1 2 3 4 5 6 7 8 9)");
    assertSame(SchemeListEngine.append(SchemeListEngine), SchemeList.EMPTY);
  }

  @Test public final void testHead() {
    try {
      SchemeListEngine.head();
    } catch (Exception e) {
      assertSame(SchemeListEngine, SchemeList.EMPTY);
    }
    
    assertEquals(SchemeListEngine.cons(99).head(), 99);
    
    assertSame(SchemeListEngine.append(zeroToNine).head(), zeroToNine.head());
  }

  @Test public final void testTail() {
    try {
      SchemeListEngine.tail();
    } catch (Exception e) {
      assertSame(SchemeListEngine, SchemeList.EMPTY);
    }
    assertSame(SchemeListEngine.cons(null).tail(), SchemeList.EMPTY);
    assertTrue(SchemeListEngine.cons(SchemeList.EMPTY).cons(SchemeList.EMPTY).tail() instanceof SchemeList);
    assertTrue(SchemeListEngine.append(zeroToNine).tail().length() == zeroToNine.length() - 1);
    assertSame(SchemeListEngine.append(zeroToNine).tail(), zeroToNine.tail());
  }

  @Test public final void testLength() {
    assertTrue(SchemeListEngine.length() == 0);
    assertTrue(SchemeList.EMPTY.length() == 0);
    assertTrue(SchemeList.EMPTY.cons(null).length() == 1);
    assertTrue(zeroToNine.length() == 10);
    assertTrue(SchemeListEngine.append(zeroToNine).append(zeroToNine).length() == 20);
  }

  @Test public final void testToString() {
    assertEquals(SchemeListEngine.toString(), "()");
    assertEquals(SchemeListEngine.cons(1).toString(), "(1)");
    assertEquals(SchemeListEngine.cons(null).toString(), "(null)");
    assertEquals(zeroToNine.toString(), "(0 1 2 3 4 5 6 7 8 9)");
    assertEquals(SchemeListEngine.cons(null).append(zeroToNine).toString(), 
      "(0 1 2 3 4 5 6 7 8 9 null)");
    assertEquals(SchemeListEngine.cons(null).cons(zeroToNine).toString(), 
      "((0 1 2 3 4 5 6 7 8 9) null)");
  }
  
  @Test public final void testMap() {
    assertEquals(SchemeListEngine.append(zeroToNine).map(new SchemeList.Fun() {
      public Object apply(Object n) {
        return n instanceof Integer ? Integer.parseInt(n.toString()) + 1 : n.toString();
      }
    }).toString(), "(1 2 3 4 5 6 7 8 9 10)" ); 
  }
  
  @Test public final void testForEach() {
    Stack<Integer> mutateThis = new Stack<Integer>();
    mutateThis.push(3);
    mutateThis.push(2);
    mutateThis.push(1); //will be removed by pop()
    SchemeListEngine.cons(mutateThis).forEach(new SchemeList.Proc() {
      @SuppressWarnings("unchecked")
      public void apply(Object n) {
        ((Stack<Integer>) n).pop(); //this will work for now I suppose
      }
    });
    
    assertEquals(mutateThis.toString(), "[3, 2]");
    
    SchemeListEngine.append(zeroToNine).forEach(new SchemeList.Proc() {
      public void apply(Object n) {
       assertTrue(Integer.parseInt(n.toString()) < 10);
      }
    });
  }
  
  @SuppressWarnings("rawtypes")
  @Test public final void testIterator() {
    //test for EMPTY
    Iterator emptyIterator = SchemeList.EMPTY.iterator();
    assertEquals(emptyIterator.hasNext(), false);
    
    //test for a list with elements
    Iterator zeroToNineIterator = zeroToNine.iterator();
    Integer iteratorSum = 0; 
    while (zeroToNineIterator.hasNext()) { //i really need generic classes...
      iteratorSum += Integer.parseInt( zeroToNineIterator.next().toString() );
    }
    assertEquals(iteratorSum, new Integer(45));
    
  }
  

  @Test public final void testCompareTo() {
    assertEquals(zeroToNine.compareTo(-999),-1);
    assertEquals(zeroToNine.compareTo(999),-1);
    
    assertEquals(zeroToNine.compareTo(0),0);
    assertEquals(zeroToNine.compareTo('z'),-1);
    
    assertEquals(SchemeListEngine.compareTo(999),-1);
  }
  
  public static void main(String[] args) {
    junit.textui.TestRunner.run(SchemeListTest.suite());
  }

  private static TestSuite suite() {
    TestSuite suite = new TestSuite("Test for a4.SchemeList.java");
    suite.addTestSuite(SchemeListTest.class);
    return suite;
  }
  
  protected void setUp() throws Exception {
    super.setUp();
  }

  /**
   * Constructor for SchemeListTest (junit test)
   */
  public SchemeListTest(String arg0) {
    super(arg0);
    SchemeListEngine = SchemeList.EMPTY;
  }  
  
}
