// ***********************************************************************
//
// SkiplistMapIterator : The iterator for the Skiplist based Map
//
// ***********************************************************************
// Computer Science 102: Data Structures
// New York University, Fall 2013,
//
// Lecturers: Eric Koskinen and Daniel Schwartz-Narbonne
//
// ***********************************************************************

import java.util.Iterator;

public class SkiplistMapIterator<K extends Comparable<K>> implements Iterator<K> {


	private SkiplistMapNode<K,Object> curNode;
	private SkiplistMapNode<K,Object> prevNode;
	private SkiplistMapNode<K,Object> head;
	private Integer version;
	private SkiplistMap<K, ?> map;


	/**
	 * Constructor that takes in a map instance to iterate over. 
	 * @param map The map to create the iterator on
	 */
	public SkiplistMapIterator(SkiplistMap<K,?> map){
		if(map!=null){
			head = (SkiplistMapNode<K, Object>) map.getHead();
			this.curNode = head;
			this.map = map;
			this.version = map.getVersion();
		}
	}
	/**
	 * Determines if there is another element to return.
	 * 
	 * @return whether there is another element left to iterate over
	 */
	@Override
	public boolean hasNext() {
		if(this.versionSame()){
			if(curNode.next[0]!=null){
				return true;
			}else{

				return false;
			}
		}else{
			throw new RuntimeException("Iterator has invalid version");
		}
	}
	/**
	 * Gets the next element in SkiplistMap
	 * 
	 * @return key of next element
	 */
	@Override
	public K next(){
		if(this.versionSame()){


			if(hasNext()){
				K key = curNode.next[0].getKey();
				if(prevNode==null){
					prevNode = head;
				}else if(prevNode.next[0]!=curNode.next[0]){
					prevNode = prevNode.next[0];
				}
				curNode = curNode.next[0];
				return key;
			}else{
				return null;
			}
		}else{
			throw new RuntimeException("Iterator has invalid version");
		}
	}

	/**
	 * Removes the last element returned by the next() function call.
	 * 
	 */
	@Override
	public void remove() {
		//Increment original map version and ourselves since we alter it for other iterators
		int tempVer = map.getVersion()+1;
		map.setVersion(tempVer);
		this.version++;
		boolean removeSuccess = false;
		if(this.versionSame()){
			if(curNode!=null&& prevNode!=null){

				for(int currentLevel =0;currentLevel<=prevNode.getLevel();currentLevel++){
					if(prevNode.next[currentLevel]==curNode){
						prevNode.next[currentLevel]=curNode.next[currentLevel];
					}else{
						break;
					}
				}

				removeSuccess = true;
			}
			System.out.println("Remove was successful = "+removeSuccess);

		}else{
			throw new RuntimeException("Iterator has invalid version");
		}
	}

	private boolean versionSame(){
		if(this.version == this.map.getVersion() ){
			return true;
		}else{
			System.out.println("Version of original map has changed. New Iterator needed.");
			return false;
		}
	}

}
