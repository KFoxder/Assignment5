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

	 private void addLeftChain(BSTMapNode<K,Object> N){
	      while(N != null){
              stack.push(N);
              N = N.getLeft();
          }
	 }
	 
	//Constructor
	public BSTMapInorderIterator(BSTMap<K,?> map){
		if(map!=null){
			this.version = map.getVersion();
			this.map = (BSTMap<K, Object>) map;
			if(map.getRoot()!=null){
				this.addLeftChain((BSTMapNode<K, Object>) map.getRoot());
			}
		}
	}
	@Override
	public boolean hasNext() {
		if(!stack.isEmpty() && this.versionSame()){
			return true;
		}else{
			return false;
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public K next() {
		if(this.hasNext() && this.versionSame()){
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
		
	}
	@Override
	public void remove() {
		//Increment original map version and ourselves since we alter it for other iterators
		map.version++;
		this.version++;
		
		if(this.versionSame()&&curNode!=null){
			if(curNode.isLeaf()&&curNode.getParent()!=null){
				//case 2:
				if(curNode == curNode.getParent().getLeft()){
					curNode.getParent().setLeft(null);
				}else{
					curNode.getParent().setRight(null);
				}
			} else if(curNode.isLeaf()&&curNode.getParent()==null){
				map.setRoot(null);
			}else if(curNode.getLeft() == null){
				//case 3
				this.shiftNodeUp(curNode.getRight());
			} else if(curNode.getRight() == null){
				//case 3
				this.shiftNodeUp(curNode.getLeft());
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
			
			
		}else{
			//Throw exception if remove on empty iterator?
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
	private void shiftNodeUp(BSTMapNode <K,Object> curNode)
	{
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


}
