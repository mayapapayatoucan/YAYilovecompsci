import static org.junit.Assert.*;

import org.junit.Test;


public class TheoremSetTest {

	@Test
	public void testTheoremSet() {
		try{
			TheoremSet theorems = new TheoremSet();
			theorems.put("t1", new Expression("(~~p=>p)"));
			theorems.put("t2", new Expression("((p=>q)=>(~p|q))"));
			theorems.put("t3", new Expression("(~(p&q)=>(~p|~q))"));
			theorems.put("t4", new Expression("(~~~~~~~~~p=>~p)"));
			theorems.put("t5", new Expression("(~p=>p)"));
			theorems.put("t6", new Expression("(~~(p&q)=>(p&q))"));
			theorems.put("t7", new Expression("(~~((p&q)&r)=>(p&(q&r)))"));
			theorems.put("not-or-creation", new Expression("(~p=>(~q=>~(p|q)))"));
			assertTrue(theorems.equals("t1", new Expression("(~~(p&q)=>(p&q))")));
			assertTrue(theorems.equals("t1", new Expression("(~~a=>a)")));
			assertFalse(theorems.equals("t1", new Expression("(p|q)")));
			assertTrue(theorems.equals("t2", new Expression("(((a&b)=>c)=>(~(a&b)|c))")));
			assertFalse(theorems.equals("t2", new Expression("((p=>q)=>(~p&q))")));
			assertTrue(theorems.equals("t3", new Expression("(~((a|b)&(c|d))=>(~(a|b)|~(c|d)))")));
			assertTrue(theorems.equals("t4", new Expression("(~~~~~~~~~(p&q)=>~(p&q))")));
			assertTrue(theorems.equals("t5", new Expression("(~(p&q)=>(p&q))")));
			assertFalse(theorems.equals("t6", new Expression("(~~p=>p)")));
			assertTrue(theorems.equals("t7", new Expression("(~~(((a&b)&c)&d)=>((a&b)&(c&d)))")));
			assertFalse(theorems.equals("not-or-creation", new Expression("(~a=>(~b=>~(b|b)))")));
			assertTrue(theorems.equals("not-or-creation", new Expression("(~a=>(~b=>~(a|b)))")));
			
		} catch (IllegalLineException e) {
			fail();
		}
	}

}
