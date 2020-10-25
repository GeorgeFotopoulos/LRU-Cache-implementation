public class MaxHeap<K,V> {
	
	public CacheSlice<K,V>[] Heap;
	public int size; // heap's current size
	private int capacity; // heap's maximum size
	private static final int HEAD = 1;

	@SuppressWarnings("unchecked")
	/**
	 *  Constructor of a max heap, sets the current size to zero, 
	 *  the first node as null and creates a CacheSlice object.
	 * @param capacity indicates its maximum size
	 */
	public MaxHeap(int capacity) {
		this.capacity = capacity;
		this.size = 0;
		this.Heap = (CacheSlice<K,V>[]) new CacheSlice[this.capacity + 1];
		Heap[0] = null;
	}
	
	// Returns the heap's capacity
	public int getCap() {
		return this.capacity;
	}
	
	// Checks if the heap is full of items, returns true if yes, false if not
	private boolean isFull() {
		return size == capacity;
	}
	
	// Returns the parent node of the one given as a parameter
	private int parent(int pos) {
		return pos / 2;
	}
	
	// This method swaps the positions of the two nodes given as parameters
	private void swap(int fpos, int spos) {
		CacheSlice<K,V> tmp = Heap[fpos];
		Heap[fpos] = Heap[spos];
		Heap[spos] = tmp;
	}
	
	// This method inserts the object given as parameter in the MaxHeap
	public int insert(CacheSlice<K,V> element) {
		if(isFull()){
			int newIndex = replace(HEAD, element);
			int current = size;
			current = parent(current);
			return newIndex;
		}
		++size;
		element.setIndex(size);
		Heap[size] = element;
		int current = size;
		current = parent(current);
		return size;
	}
	
	/**
	 *  This method swaps the object at position given as parameter (pos) with its left or right child, 
	 *  repeatedly, until it reaches the correct spot, depending on its time value
	 * @param pos The position where the object is first found
	 */
	public void sink(int pos) {
		int left = 2 * pos;
		int right = left + 1;
		int max = left;
		while(left <= size){
			if(right <= size) {
				if(Heap[left].getTime() < Heap[right].getTime()) max = right;
			}
			swap(pos, max);
			Heap[pos].setIndex(pos);
			pos = max;
			left = 2 * pos;
			right = left + 1;
			max = left;
			}
		Heap[pos].setIndex(pos);
	}
	
	/**
	 * Variation of sink() method
	 * @param pos The position where the object is first found
	 * @param newElement The object which is going to replace the object found on the head node
	 * @return The position of the MaxHeap where the object was placed
	 */
	private int replace(int pos, CacheSlice<K,V> newElement) {
		int left = 2 * pos;
		int right = left + 1;
		int max = left;
		while(left <= size){
			if(right <= size) {
				if(Heap[left].getTime() < Heap[right].getTime()) max = right;
			}
			swap(pos, max);
			Heap[pos].setIndex(pos);
			pos = max;
			left = 2 * pos;
			right = left + 1;
			max = left;
		}
		newElement.setIndex(pos);
		Heap[pos] = newElement;
		return pos;
	}
	
}