// ***********************************************************************
//
// The SkiplistMap node class. 
//
// ***********************************************************************
// Computer Science 102: Data Structures
// New York University, Fall 2013,
//
// Lecturers: Eric Koskinen and Daniel Schwartz-Narbonne
//
// ***********************************************************************

public class SkiplistMapNode<K extends Comparable<K>,V> {

	private K key;
	private V value;
	private Integer level;
	public SkiplistMapNode<K,V>[] next;

	
	//GETTERS 
	/**
	 * Getter for node's level.
	 * @return level of the node
	 */
	public Integer getLevel(){
		return this.level;
	}
	/**
	 * Getter for node's key .
	 * @return key of the node
	 */
	public K getKey(){
		return this.key;
	}
	/**
	 * Getter for node's value.
	 * @return value of the node
	 */
	public V getValue(){
		return this.value;
	}
	/**
	 * Setter for node's key.
	 * @param tempKey cannot be null
	 */
	public void setKey(K tempKey){
		if(tempKey!=null){
			this.key = tempKey;
		}
	}
	/**
	 * Setter for node's value.
	 * @param tempValue cannot be null
	 */
	public void setValue(V tempValue){
		if(tempValue!=null){
			this.value = tempValue;
		}
	}
	/**
	 * Setter for node's level.
	 * @param lev has to be equal or greater than 0
	 */
	public void setLevel(int lev){
		if(lev>=0){
			this.level = lev;
		}
	}
	
	/**
	 * Constructor for SkiplistMapNode.
	 * 
	 * @param newKey key for node, cannot be null
	 * @param newValue key for node, cannot be null
	 * @param newLevel height for node, cannot be null
	 */
	public SkiplistMapNode(K newKey, V newValue, Integer newLevel){
		if(newKey !=null && newValue!=null && newLevel!=null){
			this.value = newValue;
			this.key = newKey;
			this.level = newLevel;
			//We used +1 because we can get 0 passed in
			this.next = (SkiplistMapNode<K,V>[]) new SkiplistMapNode[newLevel+1];
		}
	}
	/**
	 * Constructor for SkiplistMapNode head node.
	 * 
	 * @param newKey key for node, cannot be null
	 * @param newValue key for node, cannot be null
	 * @param newLevel height for node, cannot be null
	 * @param isHead true if the node is going to be the head of the SkiplistMap
	 */
	public SkiplistMapNode(K tempKey, V tempValue, Integer levels, boolean isHead){
		if(isHead==true){
			this.value = tempValue;
			this.key = tempKey;
			this.level = levels;
			//We used +1 because we can get 0 passed in
			this.next = new SkiplistMapNode[levels+1];
		}else{
			if(tempKey !=null && tempValue!=null && levels!=null){
				this.value = tempValue;
				this.key = tempKey;
				this.level = levels;
				this.next = new SkiplistMapNode[levels+1];
			}

		}
	}
	/**
	 * Transforms node into a string.
	 * 
	 * @return a string in the form of "Key Value"
	 */
	@Override
	public String toString() {
		String finalString = "";
		finalString = this.key +" " +this.value;
		return finalString;
	}


}
