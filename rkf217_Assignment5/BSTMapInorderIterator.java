// ***********************************************************************
//
// BSTMapInorderIterator: The inorder iterator for the BST Map
//
//
// ***********************************************************************
// Computer Science 102: Data Structures
// New York University, Fall 2013,
//
// Lecturers: Eric Koskinen and Daniel Schwartz-Narbonne
//
// ***********************************************************************
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;


public class BSTMapInorderIterator<K extends Comparable<K>> implements Iterator<K> {

	private BSTMapNode<K,Object> curNode;
	private BSTMap<K,Object> map;
	private Integer version;
	private Deque<BSTMapNode<K,Object>> stack = new LinkedList<BSTMapNode<K,Object>>();;



	/**
	 * Constructor that takes in a map instance to create the iterator on.
	 * @param map The map to iterate over
	 */
	public BSTMapInorderIterator(BSTMap<K,?> map){
		if(map!=null){
			this.version = map.getVersion();
			this.map = (BSTMap<K, Object>) map;
			if(map.getRoot()!=null){
				this.addLeftChain((BSTMapNode<K, Object>) map.getRoot());
			}
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
			if(!stack.isEmpty()){
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
	@SuppressWarnings("unchecked")
	@Override
	public K next() {
		if(this.versionSame()){
			if(this.hasNext()){
				BSTMapNode<K,Object> temp = stack.pop();
				curNode = temp;
				if(temp.getRight()!=null){
					this.addLeftChain(temp.getRight());	
				}
				return temp.getKey();
			}else{
				//Throw Exception if no next?
				return null;
			}
		}else{
			throw new RuntimeException("Iterator has invalid version");
		}

	}
	@Override
	public void remove() {
		//Increment original map version and ourselves since we alter it for other iterators
		map.version++;
		this.version++;
		if(this.versionSame()){
			if(curNode!=null){
				if(curNode.isLeaf()&&curNode.getParent()!=null){
					//case 2:
					if(curNode == curNode.getParent().getLeft()){
						curNode.getParent().setLeft(null);
					}else{
						curNode.getParent().setRight(null);
					}
				} else if(curNode.getParent()==null){
					map.setRoot(null);
				}else if(curNode.getLeft() == null){
					//case 3
					curNode.getRight().shiftNodeUp();
				} else if(curNode.getRight() == null){
					//case 3
					curNode.getLeft().shiftNodeUp();
				} else {
					//case 4
					//We know that there is some rightmost decendent
					BSTMapNode<K,Object> rightmost = curNode.getLeft().getRightMostNode();
					//updating in place makes it easier to handle the case of the root
					curNode.setValue(rightmost.getValue());
					curNode.setKey(rightmost.getKey());
					//now we need to deal with the rightmost node
					//and its children
					if(rightmost.isLeaf()){
						//case 4a
						if(rightmost == rightmost.getParent().getLeft()){
							rightmost.getParent().setLeft(null);
						}else{
							rightmost.getParent().setRight(null);
						}
					} else {
						//case 4b
						rightmost.getLeft().shiftNodeUp();
					}
				}


			}
		}else{
			throw new RuntimeException("Iterator has invalid version");
		}

	}
	private boolean versionSame(){
		if(this.version == this.map.getVersion() ){
			return true;
		}else{
			return false;
		}
	}
	private void addLeftChain(BSTMapNode<K,Object> N){
		while(N != null){
			stack.push(N);
			N = N.getLeft();
		}
	}


}
