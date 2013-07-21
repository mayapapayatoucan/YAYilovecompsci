public class ExpressionTest {
	
	public static void main (String[] args) {

		Expression expressingmyself;
		Expression stillexpressing;

		try {

	expressingmyself = new Expression("a");
		stillexpressing = new Expression("((p&q)=>q)");
		Expression youdontknowme = new Expression("~((p|q)&p)");
	//Expression expressingmyself2 = new Expression("(&(p|q))");
	//	Expression mybloodyexpression = new Expression("(p=q)");
		Expression hashtagamandabynes = new Expression("A");
		Expression hashtagjokesaboutmyd = new Expression("(pq)");
		Expression dpicks = new Expression("(&=>|)");
		Expression theresalwaysmoneyinthebananastand = new Expression("");
		Expression wut = new Expression("(p=>))");
		Expression leanback = new Expression("((p=>q)");
		Expression winstoned = new Expression("(p&)=>q()");
		Expression quillin = new Expression("(p#q)");
		Expression spacedout = new Expression("(p => q)");
		Expression luvsawoman = new Expression("");
/*
		System.out.println("EM2 Root: " + expressingmyself2.getRoot());
		System.out.println(expressingmyself.getRoot());
		System.out.println(stillexpressing.getRoot());
		System.out.println("Should be &: " + stillexpressing.getLeft().getRoot());
		System.out.println("Should be p: " + stillexpressing.getLeft().getLeft().getRoot());
		System.out.println("Should be ~: " + youdontknowme.getRoot());
		System.out.println("Should be &: " + youdontknowme.getRight().getRoot());
		System.out.println("Should be |: " + youdontknowme.getRight().getLeft().getRoot());
		System.out.println("Should be p: " + youdontknowme.getRight().getRight().getRoot());
*/
	}	catch (IllegalLineException e) {
			System.out.println(e.getMessage());
	}






	}

}