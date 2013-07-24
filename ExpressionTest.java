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
	
	// need to test negate
	
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
			Expression.print(exp1, "testValid Tree");
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
			Expression exp5 = new Expression("p");
			Expression exp6 = new Expression("p");
			Expression exp7 = new Expression("~p");
			Expression exp8 = new Expression("~p");
			assertTrue(exp1.equals(exp2));
			assertTrue(exp3.equals(exp4));
			assertTrue(exp5.equals(exp6));
			assertTrue(exp7.equals(exp8));
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
	
	@Test
	public void testNegate ( ) {
		try {
			Expression exp1 = new Expression("p");
			Expression exp2 = new Expression("~p");
			exp1 = exp1.negate();
			assertTrue(exp1.equals(exp2));
			exp1 = new Expression("(p&q)");
			exp2 = new Expression("~(p&q)");
			exp1 = exp1.negate();
			assertTrue(exp1.equals(exp2));
			exp1 = exp1.negate();
			assertFalse(exp1.equals(new Expression("(p&q)")));
			assertTrue(exp1.equals(new Expression("~~(p&q)")));
		} catch (IllegalLineException e) {
		}
		
	}

}
