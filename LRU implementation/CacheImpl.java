public class CacheImpl<K,V> implements Cache<K,V> {
	
	LinearHashTable<K,V> LH; // LinearHashTable class snapshot
	int successes; // counter for the successful searches
	int searches; // counter for the total number of searches
	V returnedData; // V (value) variable, holding some V data
	
	/** 
	 * Cache constructor that also creates a Linear Hash Table with the same size
	 * @param cacheSize indicates the cache size
	 */
	public CacheImpl(int cacheSize) {
		LH = new LinearHashTable<K,V>(cacheSize);
		successes = 0;
		searches = 0;
	}
	
	public V lookUp(K key) {
		++searches;
		 returnedData = LH.LookUp(key);
		 if (returnedData!=null){
			 ++successes;
		 }
		 return returnedData;
	}
	
	public void store(K key, V value) {
		LH.Store(key, value);	
	}
	
	public double getHitRatio() {
		return (double) getHits() / getNumberOfLookUps();
	}
	
	public long getHits() {
		return successes;
	}
	
	public long getMisses() {
		return searches - successes;
	}
	
	public long getNumberOfLookUps() {
		return searches;
	}
	
}