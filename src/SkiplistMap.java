// ***********************************************************************
//
// The SkiplistMap main class.  YOU NEED TO IMPLEMENT THIS CLASS
//
// ***********************************************************************
// Computer Science 102: Data Structures
// New York University, Fall 2013,
//
// Lecturers: Eric Koskinen and Daniel Schwartz-Narbonne
//
// ***********************************************************************

import java.util.ArrayList;
import java.util.Iterator;

public class SkiplistMap<K extends Comparable<K>,V> implements SortedMap<K,V> {

	//private variables
	private SkiplistMapNode<K,V> head;
	public int MAX_LEVEL;
	private double probability;
	protected Integer version = 0;

	/**
	 * Getter for the head node
	 * 
	 */
	public Integer getVersion(){
		return this.version;
	}
	/**
	 * Getter for the head node
	 * 
	 */
	public SkiplistMapNode<K,V> getHead(){
		return this.head;
	}
	/**
	 * Constructor with no parameters.
	 * 
	 */
	public SkiplistMap(){
		this.MAX_LEVEL = 6;
		this.probability = 0.5;
		SkiplistMapNode<K, V> temp = new SkiplistMapNode<K, V>(null,null,MAX_LEVEL, true);
		this.head = temp;
	}
	/**
	 * Constructor with one parameter for max height of Skiplist.
	 * 
	 * @param maxLvl The maximum level for the Skiplist (Default is 6)
	 */
	public SkiplistMap(int maxLvl){
		if(maxLvl>0){
			this.MAX_LEVEL = maxLvl;
		}else{
			this.MAX_LEVEL = 6;
		}
		this.probability = 0.5;
		SkiplistMapNode<K, V> temp = new SkiplistMapNode<K, V>(null,null,MAX_LEVEL, true);
		this.head = temp;
	}
	/**
	 * Constructor with two parameters.
	 * 
	 * @param maxLvl The maximum level for the Skiplist (Default is 6)
	 * @param prob The probability a new node will gain a level when randomly generated
	 */
	public SkiplistMap(int maxLvl,double prob){
		if(maxLvl>0 && (prob >0 && prob<1)){
			this.MAX_LEVEL = maxLvl;
			this.probability = prob;
		}else{
			this.MAX_LEVEL = 6;
			this.probability = 0.5;
		}
		SkiplistMapNode<K, V> temp = new SkiplistMapNode<K, V>(null,null,MAX_LEVEL, true);
		this.head = temp;
	}
	/**
	 * Calculates the distribution statistics.
	 * 
	 * @return A table representing the distribution of the number of steps and number of nodes reached per steps
	 * 
	 */
	@Override
	public SortedMap<Integer, Integer> calculateStats() {
		SortedMap<Integer, Integer> map = new SkiplistMap<Integer,Integer>();
		if(this.head==null){
			return null;
		}else{
			SkiplistMapNode<K, V> cur = this.head.next[0];
			ArrayList<K> listOfKeys = new ArrayList<K>();
			//Create list of keys so we can search for them
			while(cur!=null){
				listOfKeys.add(cur.getKey());
				cur=cur.next[0];
			}
			for(K keyToSearch : listOfKeys){
				Integer steps = this.getSteps(keyToSearch);
				if(steps==null){
					continue;
				}else{
					try {
						if(map.get(steps)==null){
							map.put(steps, 1);
						}else{
							Integer numNodes = map.get(steps);
							map.put(steps, (numNodes+1));
						}
					} catch (SortedMapException e) {
						e.printStackTrace();
					}
				}
			}
			return map;
		}
	}

	/**
	 * Transforms the Skiplist to a String in format "Key Value".
	 * 
	 * @return A string of nodes in the Skiplist in order from least to greatest
	 */
	@Override
	public String toString() {
		if(this.head==null){
			return "";
		}
		
		String finalString = "";
		SkiplistMapNode<K,V> cur = this.head;
		while(cur.next[0]!=null){
			finalString+=cur.next[0].toString()+"\n";
			cur = cur.next[0];
		}
		return finalString;

	}

	/**
	 * Transforms the Skiplist to a Iterator.
	 * 
	 * @return An Iterator starting at the head of the Skiplist
	 */
	@Override
	public Iterator<K> iterator() {
		SkiplistMapIterator<K> iterator = new SkiplistMapIterator<K>(this);
		return iterator;
	}

	/**
	 * Places an element into the Skiplist if it doesn't already exists and replaces key/value pair if it does exist.
	 * 
	 * @throws SortedMapException If a key or value input is null
	 * @param key A sortable key that cannot be null
	 * @param value A value that is paired with the provided key that cannot be null and is not unique
	 * 
	 */
	@Override
	public void put(K key, V value) throws SortedMapException {
		if(key==null || value==null){
			throw new SortedMapException("Cannot put null values into the map!");
		}else{
			version++;
			boolean keyExists = false;
			//generate random level for new node and create node
			int randLevel = this.getRandomLevel();
			//System.out.println("Random Level : "+randLevel + " for "+key +" "+ value);
			SkiplistMapNode<K, V> nodeToAdd = new SkiplistMapNode<K, V>(key,value,randLevel);

			//set current node to the head to begin iteration
			SkiplistMapNode<K,V> cur = this.head;

			//iterate through the levels
			for(int currentLevel= MAX_LEVEL-1;currentLevel>=0;currentLevel--){
				//Check if we are at the end of the list
				while(cur.next[currentLevel]!=null){ 

					//check if the next node's key is equal to the key we want to add
					if(cur.next[currentLevel].getKey().equals(key)){
						//if it is, we turn boolean keyExists to true and break out of while loop
						keyExists = true;
						break;
						//check if the next node's key is greater than the key we want, 
						//if it is, we break to go down a level
					}else if(cur.next[currentLevel].getKey().compareTo(key)>0){
						break;
						//if the doesn't exist and the next node's key is less than the key we want to add,
						//we go to the next node. 
					}else{
						cur = cur.next[currentLevel];
					}

				}

				if(keyExists && currentLevel>randLevel){
					//This logic just removes the node if its above the new node's height
					cur.next[currentLevel] = cur.next[currentLevel].next[currentLevel];
				}else if(keyExists && currentLevel<=randLevel){
					//if the key exists, but we already started adding at a height above the existing key,
					//we delete the existing key and add our key to the skiplist
					nodeToAdd.next[currentLevel] = cur.next[currentLevel].next[currentLevel];
					cur.next[currentLevel] = nodeToAdd;
				}else if(keyExists==false && currentLevel<=randLevel){
					//set new node's next to the current nodes next and then set the curren't node next to our new node
					nodeToAdd.next[currentLevel] = cur.next[currentLevel];
					cur.next[currentLevel] = nodeToAdd;
				}

			}

		}

	}


	/**
	 * Gets and returns the corresponding value of the key inputed.
	 * 
	 * @throws SortedMapException if key input is null
	 * @param key The key that will be searched for, cannot be null
	 * @return the corresponding of the key inputed if it exists or null if the key doesn't exist in the map
	 */
	@Override
	public V get(K key) throws SortedMapException {
		if(key==null){
			throw new SortedMapException("Cannot get null keys!");
		}else{
			//set current node to the head to begin iteration
			SkiplistMapNode<K,V> curNode = this.head;

			for(int currentLevel =MAX_LEVEL-1;currentLevel>=0;currentLevel--){
				//Case 1: We are at the end of the list
				while(curNode.next[currentLevel]!=null){

					//Case 2: We find the node we are looking for
					if(curNode.next[currentLevel].getKey().equals(key)){
						return curNode.next[currentLevel].getValue();

						//Case 3: Node in front of us is greater than the key we 
						//        are looking for and we need to drop down a level.
					}else if(curNode.next[currentLevel].getKey().compareTo(key) > 0){
						if(currentLevel==0){
							return null;
						}else{
							break;
						}
					}else{
						//Otherwise if none of the cases are met, we move to the next
						//node on the the same level.
						curNode = curNode.next[currentLevel];
					}

				}
			}

			//If we get to end and don't find it we return null
			return null;
		}
	}

	/**
	 * Attempts to removes the key/value node of the key inputed.
	 * 
	 * @throws SortedMapException if the key inputed is null
	 * @param key the unique key to be removed
	 * @return true if the key was removed or false if it wasn't removed
	 */
	@Override
	public boolean remove(K key) throws SortedMapException {
		if(key==null){
			throw new SortedMapException("Cannot remove null keys!");
		}else{
			version++;
			SkiplistMapNode<K,V> cur = this.head;
			boolean wasRemoved = false;

			for(int i = MAX_LEVEL-1;i>=0;i--){

				//Check if we are at the second to last node in the list 
				while(cur.next[i]!=null){ 

					if(cur.next[i].getKey().equals(key)){
						cur.next[i] = cur.next[i].next[i];
						//Only change wasRemoved to true when we know we removed on the the last level
						if(i==0){
							wasRemoved = true;
						}
						break;
					}else if(cur.next[i].getKey().compareTo(key)>0){
						break;
						
					}else{
						cur = cur.next[i];
					}
				}
			}

			return wasRemoved;

		}


	}

	//Helper function to generate a random level for new nodes
	private int getRandomLevel(){
		int level =0;
		while(level<MAX_LEVEL && Math.random() < this.probability){
			level++;
		}
		return level;

	}

	//Helper function for calculateStats() to get the number of steps for a key 
	private Integer getSteps(K key){
		if(key==null){
			return null;
		}else{
			SkiplistMapNode<K,V> cur = this.head;
			int steps = 1;
			for(int currentLevel =MAX_LEVEL-1;currentLevel>=0;currentLevel--){
				//check if we are at end of list
				while(cur.next[currentLevel]!=null){
					//check if the next node is equal to what were looking for and return # of steps				
					if(cur.next[currentLevel].getKey().equals(key)){
						return steps;
						//Check if the next node is greater than what key were looking for and go down a level
					}else if(cur.next[currentLevel].getKey().compareTo(key) > 0){
						//Check if we are at the bottom level and if we are return null, else go down a level
						if(currentLevel==0){
							return null;
						}else{
							break;
						}
					}else{
						steps++;
						cur = cur.next[currentLevel];
					}

				}

			}
			return null;
		}
	}

}
