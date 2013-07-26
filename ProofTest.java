import static org.junit.Assert.*;

import org.junit.Test;


public class ProofTest {
	
	//all expressions are assumed to be valid expressions since the expression class should catch those errors

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
			assertTrue(theorems.equals("t1", new Expression("(~~(p&q)=>(p&q))")));
			assertTrue(theorems.equals("t1", new Expression("(~~a=>a)")));
			assertFalse(theorems.equals("t1", new Expression("(p|q)")));
			assertTrue(theorems.equals("t2", new Expression("(((a&b)=>c)=>(~(a&b)|c))")));
			assertFalse(theorems.equals("t2", new Expression("((p=>q)=>(~p&q))")));
			assertTrue(theorems.equals("t3", new Expression("(~((a|b)&(c|d))=>(~(a|b)|~(c|d)))")));
			assertTrue(theorems.equals("t4", new Expression("(~~~~~~~~~(p&q)=>~(p&q))")));
			assertTrue(theorems.equals("t5", new Expression("(~(p&q)=>(p&q))")));
			assertFalse(theorems.equals("t6", new Expression("(~~p=>p)"))); 
			assertTrue(theorems.equals("t7", new Expression("(~~(((a&b)&c)&d)=>((a&b)&(c&d)))")))
			
		} catch (IllegalLineException e) {
			System.out.println("Mike Clancy's beard is awesome");
		}
	}
	
  @Test
	public void testIsModusPonens() {
		try {
			assertTrue(Proof.isMP(new Expression("(p=>q)"), new Expression("p"), new Expression("q")));
			assertTrue(Proof.isMP(new Expression("((p=>q)=>(t=>r))"), new Expression("(p=>q)"), new Expression("(t=>r)")));
			assertTrue(Proof.isMP(new Expression("((p&d)=>q)"), new Expression("(p&d)"), new Expression("q")));
			
			//the test below is logically true, but should not pass this test as IsModusPonies is written right now.
			assertFalse(Proof.isMP(new Expression("(~p|q)"), new Expression("p"), new Expression("q")));
			assertFalse(Proof.isMP(new Expression("((p|q)=>z)"), new Expression("p"), new Expression("z")));
			
			assertFalse(Proof.isMP(new Expression("(p=>q)"), new Expression("~q"), new Expression("~q")));
			assertFalse(Proof.isMP(new Expression("((p=>q)=>(t=>r))"), new Expression("(t=>r)"), new Expression("(p=>q)")));
			assertFalse(Proof.isMP(new Expression("(p=>q)"), new Expression("q"), new Expression("q")));
			
		} catch (IllegalLineException e){
		}
	}
@Test
	public void testIsModusTollens() {
		try {
			assertTrue(Proof.isMT(new Expression("(p=>q)"), new Expression("~q"), new Expression("~p")));
			assertTrue(Proof.isMT(new Expression("((r&s)=>(t|u))"), new Expression("~(t|u)"), new Expression("~(r&s)")));
			assertTrue(Proof.isMT(new Expression("(~(z&x)=>~(h|g))"), new Expression("~~(h|g)"), new Expression("~~(z&x)")));
			assertTrue(Proof.isMT(new Expression("(~(z=>x)=>~(h=>g))"), new Expression("~~(h=>g)"), new Expression("~~(z=>x)")));
			
			// while the line below is logically valid, for the purposes of our program, it should not equate double negation to no negation
			assertFalse(Proof.isMT(new Expression("(~(z&x)=>~(h|g))"), new Expression("(h|g)"), new Expression("(z&x)")));
			
			assertFalse(Proof.isMT(new Expression("(p=>q)"), new Expression("q"), new Expression("~p")));
		} catch (IllegalLineException e){
		}

	}

@Test
	public void testisImplicationConstruction() {
		try {			
			assertTrue(Proof.isIC(new Expression("q"), new Expression("(p=>q)")));
			assertTrue(Proof.isIC(new Expression("(p&q)"), new Expression("((q|p)=>(p&q))")));
			assertTrue(Proof.isIC(new Expression("~(q&r)"), new Expression("(p=>~(q&r))")));
			
			// Again, the below case is logically valid but for the purposes of our program, should return false			
			assertFalse(Proof.isIC(new Expression("~~(p&q)"), new Expression ("(p&q")));
			
			assertFalse(Proof.isIC(new Expression("(p&r)"), new Expression("(p=>(p&q)")));
			
		} catch (IllegalLineException e){
		}
}

@Test
public void testisContradiction() {
	try {			
		assertTrue(Proof.isC(new Expression("~p"), new Expression("p")));
		assertTrue(Proof.isC(new Expression("~(p&q)"), new Expression("(p&q)")));
		assertTrue(Proof.isC(new Expression("~(p&(q&r))"), new Expression("~~(p&(q&r))")));
		
		assertFalse(Proof.isC(new Expression("~~p"), new Expression("p")));
		
		// Again, the below case is logically valid but for the purposes of our program, should return false		
		assertFalse(Proof.isC(new Expression("~~~~~p"), new Expression("p")));
		

	} catch (IllegalLineException e){
	}
}



}
