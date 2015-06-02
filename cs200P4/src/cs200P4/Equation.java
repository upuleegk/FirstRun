package cs200P4;


public class Equation {

	private boolean debug;

	private String nextToken;	
	private TokenIter itTokens;
	
	Tree exprTree;
    
	public Equation(TokenIter iter, boolean debug){
		itTokens = iter;
		this.debug = debug;
		nextTok("");
	}

	private void nextTok(String indent){
		if(itTokens.hasNext())
			nextToken = itTokens.next();
		else 
			nextToken = null;
		if(debug)
			System.out.println(indent+"next token: " + nextToken);
	}

	private void error(String errMess) throws ParseException{
		throw new ParseException(errMess);

	}

	// line parses a line: LHS "=" expr
	// lhs = identifier
	// and stores an IdVal (identifier + value) in the symbol table
	public Symbol line(BST symbolTable) throws BSTException, ParseException{
		TreeNode root;
		exprTree = null;
                Symbol lhsVal = null; // Symbol to be returned
		if(nextToken==null)
			return null;
		else {
			if(debug)
				System.out.println("line");				
			String lhs = nextToken;
			if(!Character.isLetter(lhs.charAt(0)))
				error("Identifier expected");
			nextTok("");
			if(nextToken ==null)
				error("unexpected end of line");
			else if(!nextToken.equals("="))
				error("= expected");
			nextTok("");
			root = expr("");
			exprTree = new Tree(root);
			Integer val = exprTree.postorderEval(symbolTable);
			lhsVal = new Symbol(lhs,val);
			symbolTable.insertItem(lhsVal);
			if(debug)
				System.out.println("retrieving " + lhs + ": " + symbolTable.retrieveItem(lhs));
			if(nextToken!=null){
				error("end of line expected");
				return null;//not
			} else 
				return lhsVal;
		}
	}
	// check whether nextToken is an additive operator
	private boolean addOp(String indent){
		boolean res = nextToken.equals("+") || nextToken.equals("-");
		if(debug)
			System.out.println(indent+"addOp: " + res);
		return res;
	}
	
	// expr = term ( ("+" | "-") term )*
	private TreeNode expr(String indent) throws ParseException{
		if(debug)
			System.out.println(indent+"expr");
		TreeNode left = term(indent+" ");
		while(nextToken != null && addOp(indent+" ") ){
			    String op = nextToken;
				nextTok(indent+" ");
				TreeNode right = term(indent+" ");
				left = new TreeNode(op,left, right);
		}
		return left;
	}

	
	// check whether nextToken is an multiplicative operator
	private boolean mulOp(String indent){
		boolean res = nextToken.equals("*") || nextToken.equals("/");
		if(debug)
			System.out.println(indent+"mulOp: " + res);
		return res;
	}
	
	// term = factor ( ("*" | "/") factor )*
	private TreeNode term(String indent) throws ParseException{
		if(debug)
			System.out.println(indent+"term");
		TreeNode left = factor(indent+" ");
		while(nextToken!=null && mulOp(indent+" ") ){
			String op = nextToken;
			nextTok(indent+" ");
			TreeNode right = factor(indent+" ");
			left = new TreeNode(op, left, right);
		}
		return left;
	}

	// factor = id | number | "(" expr ")"
	private TreeNode factor(String indent) throws ParseException{
		if(debug)
			System.out.println(indent + "factor");
		if(nextToken==null){
			error("Unexpected end of line");
			return null; //not  
		}
		else if(nextToken.equals("(")){
			nextTok(indent+" ");
			TreeNode E = expr(indent+" ");
			if(nextToken.equals(")") )
				nextTok(indent+" ");
			else
				error(") expected");
			return E;
		}
		else if(Character.isDigit(nextToken.charAt(0))){
	    		TreeNode num = number(indent+" ");
	    		nextTok(indent+" ");
	    		return num;
		}
		else if(Character.isLetter(nextToken.charAt(0))){
				TreeNode id = identifier(indent+" ");
				nextTok(indent+" ");
				return id;
		}
		else{
			error("Unexpected token: " + nextToken);
			return null;
		}
	}
	
	
	private TreeNode identifier(String indent) throws ParseException{
		if(debug)
			System.out.println(indent + "identifier");
		return new TreeNode(nextToken);

	}
	
	private TreeNode number(String indent) throws ParseException{
		if(debug)
			System.out.println(indent + "number");
		return new TreeNode(nextToken);

	}
}