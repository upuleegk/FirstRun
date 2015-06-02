package cs200P4;


public class Tree {
    private TreeNode root;
    
    //empty tree
    public Tree(){
    	this.root = null;
    }
    
    // rootItem
    public Tree(TreeNode root){
    	this.root = root;
    }
    
    public boolean isEmpty(){
    	return root==null;
    }
    
    private void error(String message) throws BSTException{
    	throw new BSTException(message);
    }
    public void preorderTraverse(){
    	if (!isEmpty())
    		preorderTraverse(root,"");
    	else
    		System.out.println("root is null");
    }
    
	public void preorderTraverse(TreeNode node, String indent){
		System.out.println(indent+node.getItem());
		if(node.getLeft()!=null) preorderTraverse(node.getLeft(),indent+" ");
		if(node.getRight()!=null) preorderTraverse(node.getRight(),indent+" ");
		
	}

    // if tree empty return null
	// else evaluate the tree by postorder traversal 
	// and return its value
    public Integer postorderEval(BST symTab) throws BSTException{
    	Integer res = null;
    	if(!isEmpty()){
    		res = postorderEval(root, "", symTab);
    	}
    	return res;
    }

    private boolean isOperator(String s){
    	return (s.equals("+")||s.equals("-")||s.equals("*")||s.equals("/") );
    }
    
    private Integer Eval(String op, Integer left, Integer right){
    	if(op.equals("+"))
    		return left + right;
    	if(op.equals("-"))
    		return left - right;
    	if(op.equals("*"))
    		return left * right;
    	if(op.equals("/"))
    		return left / right;
    	else return null;
    }
    
    public Integer postorderEval(TreeNode node, String indent, BST symTab) throws BSTException{
    	String token = node.getItem();
    	if( isOperator(token)){
    		Integer left  = postorderEval(node.getLeft(), indent+" ", symTab);
    		Integer right = postorderEval(node.getRight(), indent+" ", symTab);
    		Integer eval = Eval(token,left,right);
    		return eval;
        } else {
        	if(Character.isDigit(token.charAt(0)))
        		return Integer.parseInt(token);
        	else{
        		Symbol tokVal = symTab.retrieveItem(token);
        		if(tokVal==null){
        			error("undefined identifier: " + token);
        			return 0; // not, this is to satisfy the compiler
        		}
        		else
        			return tokVal.getVal();
        		
        	}
        }
    }	

}