import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Queue;

// ***********************************************************************
//
// The BSTMap node class.  YOU NEED TO IMPLEMENT THIS CLASS
//
// ***********************************************************************
// Computer Science 102: Data Structures
// New York University, Fall 2013,
//
// Lecturers: Eric Koskinen and Daniel Schwartz-Narbonne
//
// ***********************************************************************


public class BSTMapNode<K extends Comparable<K>,V> {

	private V value;
	private K key;
	private BSTMapNode<K,V> left;
	private BSTMapNode<K,V> right;
	private BSTMapNode<K,V> parent;
	public int level;

	//GETTERS
	/**
	 * Getter for the left leaf node pointer.
	 * @return left leaf node if it exists or null
	 */
	public BSTMapNode<K,V> getLeft(){
		return this.left;
	}
	/**
	 * Getter for the right leaf node pointer.
	 * @return right leaf node if it exists or null
	 */
	public BSTMapNode<K,V> getRight(){
		return this.right;
	}
	/**
	 * Getter for the parent node pointer.
	 * @return parent node if it exists or null if its the head of the map
	 */
	public BSTMapNode<K,V> getParent(){
		return this.parent;
	}
	/**
	 * Getter for key of the node.
	 * @return key for the node
	 */
	public K getKey(){
		return this.key;
	}
	/**
	 * Getter for value of the node.
	 * @return value for the node
	 */
	public V getValue(){
		return this.value;
	}
	//SETTERS
	/**
	 * Sets the node's parent reference to the input.
	 * @param p parent node for the node
	 */
	public void setParent(BSTMapNode<K,V> p){
		this.parent = p;
	}
	/**
	 * Sets the node's left leaf node reference.
	 * @param l left leaf node
	 */
	public void setLeft(BSTMapNode<K,V> l){
		this.left = l;
	}
	/**
	 * Sets the node's right leaf node reference.
	 * @param l right leaf node
	 */
	public void setRight(BSTMapNode<K,V> r){
		this.right = r;
	}
	/**
	 * Sets the node's value.
	 * @param val new value for the node, cannot be null
	 */
	public void setValue(V val){
		if(val!=null){
			this.value = val;
		}
	}
	public void setKey(K key){
		if(key!=null){
			this.key = key;
		}
	}

	//Constructors
	public BSTMapNode(K key, V value,BSTMapNode<K,V> left,BSTMapNode<K,V> right,BSTMapNode<K,V> parent){ 
		if(key!=null && value!=null){
			this.key = key;
			this.value = value;
			this.left = left;
			this.right = right;
			this.parent = parent;
		}

	}
	public BSTMapNode(K key, V value){
		if(key!=null && value!=null){
			this.key = key;
			this.value = value;
		}
	}
	/**
	 * Transforms node into a string.
	 * @return the node transformed to a string in the format "Key Value"
	 */
	@Override
	public String toString() {
		return key+" "+value;
	}
	/**
	 * Checks if the node is a leaf (if its left leaf and right leaf pointers a null).
	 * 
	 * @return true if the node has not leafs or false if if the node does have children leafs
	 */
	public boolean isLeaf(){
		return (left == null) && (right == null);
	}
	/**
	 * Checks if the node is a root (if its left parent pointer is null).
	 * 
	 * @return true if the node has no parent or false if if the node does have a parent reference pointer
	 */
	public boolean isRoot(){
		return parent == null;
	}
	/**
	 * Places an element into the BSTMap if it doesn't already exists and replaces key/value pairs if the key does exist.
	 * 
	 * @param key A sortable key that cannot be null
	 * @param value A value that is paired with the provided key that cannot be null and is not unique
	 * @param level The level that a node resides in the tree
	 * @return true if the node is added succesfully or false if it isn't added
	 */
	public boolean put(K key, V value, int level){
		if(key==null||value==null){
			return false;
		}else{
			int ct = key.compareTo(this.getKey());

			if(ct == 0) {
				this.setValue(value);
				return true;
			} else if (ct > 0){
				if (this.getRight() == null){
					BSTMapNode<K,V> temp = new BSTMapNode<K,V>(key,value);
					temp.level = level;
					temp.setParent(this);
					this.setRight(temp);
					return true;
				} else {
					return this.getRight().put(key, value, (level+1));
				}
			} else {
				if (this.getLeft() == null){
					BSTMapNode<K,V> temp = new BSTMapNode<K,V>(key,value);
					temp.level = level;
					temp.setParent(this);
					this.setLeft(temp);
					return true;
				} else {
					return this.getLeft().put(key, value, (level+1));
				}            
			}
		}
	}
	/**
	 * Gets and returns the corresponding value of the key inputed.
	 *
	 * @param key The key that will be searched for, cannot be null
	 * @return the corresponding value of the key inputed if the key exists or null if the key doesn't exist in the map
	 */
	public V get(K key){
		if(key==null){
			return null;
		}else{
			int ct = this.getKey().compareTo(key);

			if(ct == 0) {
				return this.getValue();
			} else if (ct > 0){
				if(this.getLeft()!=null){
					return this.getLeft().get(key);
				}else{
					return null;
				}

			} else {
				if(this.getRight()!=null){
					return this.getRight().get(key);
				}else{
					return null;
				}
			}
		}
	}

	/**
	 * Removes the node of the corresponding key inputed from the map. 
	 * 
	 * @throws SortedMapException if the key inputed is null
	 * @param key the unique key to be removed
	 * @return true if the key was removed or false if it wasn't removed
	 */
	public boolean remove(K key){

		int ct = this.getKey().compareTo(key);

		if (ct==0){
			//we found the element, now to remove it!
			if(this.isLeaf()){
				//check if its the root and only node in tree
				if(this == this.getParent().getLeft()){
					this.getParent().setLeft(null);
					return true;
				}else{
					this.getParent().setRight(null);
					return true;
				}
			} else if(this.getLeft() == null){
				//case 3
				this.getRight().shiftNodeUp();
				return true;
			} else if(this.getRight() == null){
				//case 3
				this.getLeft().shiftNodeUp();
				return true;
			} else {
				//case 4
				//We know that there is some rightmost decendent
				BSTMapNode<K,V> rightmost = this.getLeft().getRightMostNode();
				//updating in place makes it easier to handle the case of the root
				this.setValue(rightmost.getValue());
				this.key = rightmost.getKey();
				//now we need to deal with the rightmost node
				//and its children
				if(rightmost.isLeaf()){
					//case 4a
					if(rightmost == rightmost.getParent().getLeft()){
						rightmost.getParent().setLeft(null);
						return true;
					}else{
						rightmost.getParent().setRight(null);
						return true;
					}
				} else {
					//case 4b
					rightmost.getLeft().shiftNodeUp();
					return true;
				}
			}

		}else if(ct>0){
			if(this.getLeft()!=null){
				return this.getLeft().remove(key);
			}else{
				return false;
			}

		}else{
			if(this.getRight()!=null){
				return this.getRight().remove(key);
			}else{
				return false;
			}
		}

	}
	/**
	 * Transforms the BSTMap into an ordered list of Strings
	 * 
	 * @param s a blank LinkedList to 
	 * @return
	 */
	protected LinkedList<String> getOrderedList(LinkedList<String> s){
		if (getLeft()!=null){
			getLeft().getOrderedList(s);
		}
		s.add(this.toString()+"\n");
		if (getRight()!=null){
			getRight().getOrderedList(s); 
		}
		return s;


	}

	//HELPER FUNCTIONS

	//Helper function to shift a node up
	protected void shiftNodeUp()
	{
		BSTMapNode <K,V> curNode = this;
		if(curNode.getParent() != null && curNode.getParent().hasOneChild()){
			//Move the keys and values and the children
			curNode.getParent().setValue(curNode.getValue());
			curNode.getParent().setKey(curNode.getKey());
			curNode.getParent().setLeft(curNode.getLeft());
			curNode.getParent().setRight(curNode.getRight());
			//patch up the parents
			//of the subtrees
			if(curNode.getParent().getLeft() != null){
				curNode.getParent().getLeft().setParent(curNode.getParent());
			}
			if(curNode.getParent().getRight() != null){
				curNode.getParent().getRight().setParent(curNode.getParent());
			}
		}
	}
	//Helper function to check if a node has one child
	protected boolean hasOneChild(){
		BSTMapNode<K,V> left = this.getLeft();
		BSTMapNode<K,V> right = this.getRight();
		if(left!=null && right==null){
			return true;
		}else if(left==null && right!=null){
			return true;
		}else{
			return false;
		}

	}
	//Helper function to get the right most node 
	protected BSTMapNode<K,V> getRightMostNode(){
		if(this.getRight()==null){
			return this;
		}else{
			return this.getRight().getRightMostNode();
		}
	}
	//Helper function to get the left most node 
	protected BSTMapNode<K,V> getLeftMostNode(){
		if(this.getLeft()==null){
			return this;
		}else{
			return this.getLeft().getLeftMostNode();
		}
	}

	protected LinkedList<BSTMapNode<K, Object>> getPreorderedListNode(LinkedList<BSTMapNode<K, Object>> linkedList){
		linkedList.add((BSTMapNode<K, Object>) this);
		if (getLeft()!=null){
			getLeft().getPreorderedListNode(linkedList);
		}
		if (getRight()!=null){
			getRight().getPreorderedListNode(linkedList); 
		}
		return linkedList;


	}
	protected LinkedList<BSTMapNode<K, Object>> getPostorderedListNode(LinkedList<BSTMapNode<K, Object>> linkedList){

		if (getLeft()!=null){
			getLeft().getPostorderedListNode(linkedList);
		}
		if (getRight()!=null){
			getRight().getPostorderedListNode(linkedList); 
		}
		linkedList.add((BSTMapNode<K, Object>) this);
		return linkedList;


	}
	protected LinkedList<BSTMapNode<K, Object>> getBFSorderedListNode(LinkedList<BSTMapNode<K, Object>> linkedList){
		Queue<BSTMapNode<K,V>> q = new LinkedList<BSTMapNode<K,V>>();
		BSTMapNode<K,V> item;
		q.add(this);
		while(!q.isEmpty())
		{
			item = q.remove();
			if(item != null)
			{
				linkedList.add((BSTMapNode<K, Object>) item);
				if(item.getLeft() != null)
				{
					q.add(item.getLeft());
				}
				if(item.getRight() != null)
				{
					q.add(item.getRight());
				}
			}
		}

		return linkedList;


	}
}
