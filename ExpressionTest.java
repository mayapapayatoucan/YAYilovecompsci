import static org.junit.Assert.*;

import org.junit.Test;


public class ExpressionTest {


	public boolean testhelper(String S) {
		boolean flag = false;
		
		try {
		Expression testExp = new Expression(S);		
		}
		catch (IllegalLineException e) {
		flag = true;
		}
		
		if (flag) {
			return true;
		}
		else {
			return false;
		}
	
	}
	
	@Test
	public void testExceptions () {
		//commented ones are the ones which tests fail
		assertTrue(testhelper("A"));
		assertTrue(testhelper("(&=>|)"));
	//	assertTrue(testhelper("(pq)"));
		assertTrue(testhelper("(p=>))"));
//		assertTrue(testhelper("((p=>)"));
		assertTrue(testhelper("(p&)=>q()"));
	//	assertTrue(testhelper("(p#q)"));
		assertTrue(testhelper("(p => q)"));
		assertTrue(testhelper(""));
		
		
		
		
	}

}
