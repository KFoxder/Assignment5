// ***********************************************************************
//
// The BSTMap main class.  YOU NEED TO IMPLEMENT THIS CLASS
//
// ***********************************************************************
// Computer Science 102: Data Structures
// New York University, Fall 2013,
//
// Lecturers: Eric Koskinen and Daniel Schwartz-Narbonne
//
// ***********************************************************************

import java.util.Iterator;
import java.util.Queue;
import java.util.LinkedList;


public class BSTMap<K extends Comparable<K>,V> implements SortedMap<K,V> {


	private BSTMapNode<K,V> root;
	private VisitorOrder VisitorOrder;
	protected Integer version = 0;

	//enums
    enum VisitorOrder{
        PREORDER,
        POSTORDER,
        INORDER,
        BFS
    }

	/**
	 * Constructor for BSTMap, VisitorOrder defaults to INORDER.
	 * 
	 */
	public BSTMap(){
		this.root = null;
		VisitorOrder = VisitorOrder.INORDER;
	}
	//Getter
	public BSTMapNode<K,V> getRoot(){
		return this.root;
	}
	public Integer getVersion(){
		return this.version;
	}
	//Setter
	public void setRoot(BSTMapNode<K,V> node){
		this.root = node;
	}
	
	/**
	 * Calculates the distribution statistics and returns blank table if the map is empty.
	 * 
	 * @return A table representing the distribution of the number of steps and number of nodes reached per steps.
	 * 
	 */
	@Override
	public SortedMap<Integer, Integer> calculateStats() {
		
		//Create our Map to calc the stats
		SortedMap<Integer, Integer> map = new BSTMap<Integer,Integer>();

		Queue<BSTMapNode<K,V>> q = new LinkedList<BSTMapNode<K,V>>();
		BSTMapNode<K,V> item;
	 	if( this.root != null)
	 	{
	 		root.level = 1;
	 		q.add(root);
	 		while(!q.isEmpty())
	 		{	
	 			item = q.remove();
	 			int tempLevel = item.level;
	 			try {
	 				if(map.get(tempLevel)==null){
	 					map.put(tempLevel, 1);
	 				}else{
	 					int count = map.get(tempLevel);
	 					map.put(tempLevel, (count+1));
	 				}	
				} catch (SortedMapException e) {			
					e.printStackTrace();
				}
	 			if(item != null)
	 			{
	 				if(item.getLeft() != null)
	 				{
	 					item.getLeft().level = tempLevel+1;
	 					q.add(item.getLeft());
	 				}
	 				if(item.getRight() != null)
	 				{
	 					item.getRight().level = tempLevel+1;
	 					q.add(item.getRight());
	 				}
	 			}
	 		}
	 	}else{
	 		return map;
	 	}
	 	return map;
	}
	/**
	 * Transforms the BSTMap to a ordered String in the format "Key Value".
	 * 
	 * @return A string of nodes in the BSTMap in order from least to greatest
	 */
	@Override
	public String toString() {
		if(this.root==null){
			return "";
		}else{
			String finalString ="";
			LinkedList<String> s = this.root.getOrderedList( new LinkedList<String>());
			for(String x : s){
				finalString+=x;
			}
			return finalString;
		}
	}
	/**
	 * Transforms the BSTMap into an Iterator ordered by the specified setIteratorDefault() or constructor (Defaults to INORDER).
	 * @see setIteratorDefault()
	 * @return An iterator based on the visit order specified (Default INORDER)
	 */
	@Override
	public Iterator<K> iterator() {
		Iterator<K> iter;
		if(this.VisitorOrder==VisitorOrder.INORDER){
			iter = new BSTMapInorderIterator<K>(this);
		}else if(this.VisitorOrder==VisitorOrder.PREORDER){
			iter = new BSTMapPreorderIterator<K>(this);
		}else if(this.VisitorOrder==VisitorOrder.POSTORDER){
			iter = new BSTMapPostorderIterator<K>(this);
		}else{
			iter = new BSTMapBFSIterator<K>(this);
		}
		return iter;
	}

	/**
	 * Places an element into the BSTMap if it doesn't already exists and replaces key/value pairs if the key does exist.
	 * 
	 * @throws SortedMapException If a key or value input is null
	 * @param key A sortable key that cannot be null
	 * @param value A value that is paired with the provided key that cannot be null and is not unique
	 * 
	 */
	@Override
	public void put(K key, V value) throws SortedMapException {
		if(key==null || value == null){
			throw new SortedMapException("Cannot put null values into the map.put() function!");
		}else{
			version++;
			if(this.root == null){
				BSTMapNode<K,V> temp = new BSTMapNode<K,V>(key,value);
				this.root = temp;
				this.root.level = 1;
			}else{
				this.root.put(key,value,2);	
			}

		}

	}

	/**
	 * Gets and returns the corresponding value of the key inputed.
	 * 
	 * @throws SortedMapException if key input is null
	 * @param key The key that will be searched for, cannot be null
	 * @return the corresponding value of the key inputed if the key exists or null if the key doesn't exist in the map
	 */
	@Override
	public V get(K key) throws SortedMapException {
		if(key==null){
			throw new SortedMapException("Cannot pass null keys into the get() function!");
		}else{
			if(this.root == null){
				return null;	
				
			}else{
				return this.root.get(key);
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
	@Override
	public boolean remove(K key) throws SortedMapException {
		version++;
		if(key==null){
			throw new SortedMapException("Cannot pass null keys into the remove() function!");
		}else{
			if(this.root!=null){
				if(key.compareTo(this.root.getKey())==0&&this.root.getLeft()==null&&this.root.getRight()==null){
					this.root=null;
					return true;
				}else{
					return this.root.remove(key);
				}
			}else{
				return false;
			}
		}
	}
	/**
	 * A setter method to specify the order that the map Iterator will visit nodes. 
	 * 
	 * @param v The order in which the Iterator will visit nodes in the map 
	 * @see VisitorOrder
	 */
	public void setIteratorDefault(VisitorOrder v){
	        if(v== VisitorOrder.INORDER || v== VisitorOrder.POSTORDER || v== VisitorOrder.PREORDER || v== VisitorOrder.BFS){
	        	this.VisitorOrder = v;
	        	System.out.println("VISITOR ORDER CHANGED");
	        }
	    }






}
