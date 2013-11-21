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
	
	
	//Constructor
	public SkiplistMapIterator(SkiplistMap<K,?> map){
		if(map!=null){
			head = (SkiplistMapNode<K, Object>) map.getHead();
			this.curNode = head;
			//this.prevNode  = null;
			this.map = map;
			this.version = map.getVersion();
		}
	}
	@Override
	public boolean hasNext() {
		if(curNode.next[0]!=null && this.versionSame()){
			return true;
		}else{
			//THrow Exceptions? 
			return false;
		}
	}

	@Override
	public K next() {
		if(hasNext() && this.versionSame()){
			K key = curNode.next[0].getKey();
			if(prevNode==null){
				prevNode = head;
			}else{
				prevNode = prevNode.next[0];
				//put line belowe here set one above ot head
			}
			curNode = curNode.next[0];
			return key;
		}else{
			//Throw Excpetion?????
			return null;
		}
	}

	@Override
	public void remove() {
		//Increment original map version and ourselves since we alter it for other iterators
		map.version++;
		this.version++;
		boolean removeSuccess = false;
		if(this.versionSame() && curNode!=null&& prevNode!=null){
			//CHANGE MAX LEVEL TO CURRENT NODES LEVEL
			for(int currentLevel =0;currentLevel<=prevNode.getLevel();currentLevel++){
				if(prevNode.next[currentLevel]==curNode){
					prevNode.next[currentLevel]=curNode.next[currentLevel];
				}else{
					break;
				}
			}
			
			//curNode = curNode.next[0];
			removeSuccess = true;
		}
		System.out.println("Remove was successful = "+removeSuccess);
		
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
