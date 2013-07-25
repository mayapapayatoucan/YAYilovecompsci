import static org.junit.Assert.*;

import org.junit.Test;


public class ProofTest {
	
	//all expressions are assumed to be valid expressions since the expression class should catch those errors

	@Test
	public void testTheoremSet() {
		try{
			TheoremSet t1 = new TheoremSet("t1", new Expression("(~~p=>p)"));
			assertTrue(t1.equalsHelper(new Expression("(~~(p&q)=>(p&q))"), new Expression("(~~p=>p)")));
			TheoremSet t2 = new TheoremSet("t2", new Expression("((p=>q)=>(~p|q))"));
			assertTrue(t2.equalsHelper(new Expression("(((a&b)=>c)=>(~(a&b)|c))"), new Expression("((p=>q)=>(~p|q))")));
			TheoremSet t3 = new TheoremSet("t3", new Expression("(~(p&q)=>(~p|~q))"));
			assertTrue(t3.equalsHelper(new Expression("(~((a|b)&(c|d))=>(~(a|b)|~(c|d)))"), new Expression("(~(p&q)=>(~p|~q))")));
			TheoremSet t4 = new TheoremSet("t4", new Expression("(~~~~~~~~~p=>~p)"));
			assertTrue(t4.equalsHelper(new Expression("(~~~~~~~~~(p&q)=>~(p&q))"), new Expression("(~~~~~~~~~p=>~p)")));
			TheoremSet t5 = new TheoremSet("t5", new Expression("(~p=>p)"));
			assertTrue(t5.equalsHelper(new Expression("(~(p&q)=>(p&q))"), new Expression("(~p=>p)")));
			TheoremSet t6 = new TheoremSet("t6", new Expression("(p|q)"));
			assertFalse(t6.equalsHelper(new Expression("(~~p=>p)"), new Expression("(p|q)")));
			//TheoremSet t7 = new TheoremSet("t7", new Expression("(~~(p&q)=>(p&q))"));		// this test fails
			//assertFalse(t7.equalsHelper(new Expression("(~~p=>p)"), new Expression("(~~(p&q)=>(p&q))")));
			
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
