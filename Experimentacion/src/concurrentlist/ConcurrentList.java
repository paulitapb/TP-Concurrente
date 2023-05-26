
package concurrentlist; 

abstract public class ConcurrentList {
    
    //Uses subclass contructor for initalization
    
    public abstract boolean add(Object o);
    public abstract boolean remove(Object o);
    public abstract void printList(); 
}
