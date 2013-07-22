import static org.junit.Assert.*;

import org.junit.Test;


public class ExpressionTest {


	public boolean testhelper(String S) {

		try {
			new Expression(S);	
			return false;
		}
		catch (IllegalLineException e) {
			System.out.println(e.getMessage());
			return true;
		}	
	}
	
	public void printTree (Expression exp, int depth, String direction) {
		if (exp != null) {
			printTree(exp.getLeft(), depth + 1, "left");
			System.out.println("root: " + exp.getRoot() + " depth: " + depth + " direction: " + direction);
			printTree(exp.getRight(), depth + 1, "right");
		}	
	}
	
	@Test
	public void testNegate ( ) {
		try {
			Expression dpicks = new Expression("p");
			assertTrue(dpicks.negate().equals(new Expression("~p"))); // this fails
		}
		catch (IllegalLineException e) {
		}
	}
	
	
	@Test
	public void testValid () {
		assertFalse(testhelper("(p=>q)"));
		assertFalse(testhelper("~p"));
		assertFalse(testhelper("~~p"));
		assertFalse(testhelper("~~~p"));
		assertFalse(testhelper("~~~~~~~~~~~~~~~~~~~~~~~~~~p"));
		assertFalse(testhelper("(p&q)"));
		assertFalse(testhelper("(p|q)"));
		assertFalse(testhelper("(((p=>q)=>q)=>((q=>p)=>p))"));
		assertFalse(testhelper("((p&(p=>q))=>q)"));		// our goal
		assertFalse(testhelper("(~p&q)"));
		try {
			Expression exp1 = new Expression("(((p=>q)=>q)=>((q=>p)=>p))");
			printTree(exp1, 0, "Root");
		} catch (IllegalLineException e) {
			
		}
	}
	@Test
	public void testEquals() {
		try {
			Expression exp1 = new Expression("(p=>q)");
			Expression exp2 = new Expression("(p=>q)");
			Expression exp3 = new Expression("(((p=>q)=>q)=>((q=>p)=>p))");
			Expression exp4 = new Expression("(((p=>q)=>q)=>((q=>p)=>p))");
			assertTrue(exp1.equals(exp2));
			assertTrue(exp3.equals(exp4));
		} catch (IllegalLineException e) {
			
		}
	}
	
	@Test
	public void testExceptions () {
		//commented ones are the ones which tests fail
		assertTrue(testhelper("A"));
		assertTrue(testhelper("(&=>|)"));
		assertTrue(testhelper("(pq)"));
		assertTrue(testhelper("(p=>q))"));
		assertTrue(testhelper("(p=>)"));
		assertTrue(testhelper("((p=>q)"));
		assertTrue(testhelper("(p&)=>q()"));
		assertTrue(testhelper("(p#q)"));
		assertTrue(testhelper("(p => q)"));
		assertTrue(testhelper(""));
		assertTrue(testhelper("(p&q&r)"));
		assertTrue(testhelper("(p|q|r)"));
		assertTrue(testhelper("(p=>q)()(~q=>p)"));
		
		
		
	}

}
