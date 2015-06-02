package cs200P4;


public class TreeNode {
	
	private String item;
	private TreeNode left;
	private TreeNode right;
	
	public TreeNode(String item){
		this.item = item;
		left = null;
		right = null;
	}

	public TreeNode(String item, TreeNode left, TreeNode right){
		this.item = item;
		this.left = left;
		this.right = right;
	}
	
	String getItem(){
		return item;
	}
	
	TreeNode getLeft(){
		return left;
	}
	
	TreeNode getRight(){
		return right;
	}
	
	public String toString(){
		if(left==null && right == null)
			return "leaf: " + item;
		else
			return "internal: " + item;
		
	}
	
}