
package concurrentlist; 

abstract public class ConcurrentList {
    
    //Uses subclass contructor for initalization
    
    public abstract boolean add(Object o);
    public abstract boolean remove(Object o);
    public abstract int size();
    public abstract void printList(); 
    public abstract boolean checkListInvariant(); 
}
