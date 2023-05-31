package concurrentlist;  
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

final public class FineGrainList extends ConcurrentList{
    
    private class Node {
        private Object element;
        private int hashKey;
        private Node next;
        public ReentrantLock lock = new ReentrantLock();
        
        public Node (Object element){
            this.element = element;
            this.hashKey = element.hashCode();
        }
        public int hashKey(){
            return this.hashKey; 
        }
        public Node next(){
            return this.next;
        }
    }

    private Node head;
    public AtomicInteger size = new AtomicInteger(0);
    
    
    public FineGrainList(){
        head = new Node (Integer.MIN_VALUE);
        head.next = new Node (Integer.MAX_VALUE);
    }

    @Override public int size(){
        return this.size.get();
    };

    @Override public boolean remove(Object o) {
        Node predecesor, current;
        int key = o.hashCode();
        head.lock.lock();
        predecesor = head;
        try {
            predecesor = head;
            current = predecesor.next;
            current.lock.lock();
            try{
                while (current.hashKey() < key){
                    predecesor.lock.unlock();
                    predecesor = current;
                    current = current.next;
                    current.lock.lock();
                }
                if (key == current.hashKey()) {
                    predecesor.next = current.next;
                    size.getAndDecrement();
                    return true;
                }
                    return false;
            }
            finally {current.lock.unlock();}
        } 
        finally {predecesor.lock.unlock();}
    }

    @Override public boolean add(Object o) {
        Node predecesor, current;
        int key = o.hashCode();
        head.lock.lock();
        predecesor = head;
        try {
            predecesor = head;
            current = predecesor.next;
            current.lock.lock();
            try{
                while (current.hashKey() < key){
                    predecesor.lock.unlock();
                    predecesor = current;
                    current = current.next;
                    current.lock.lock();
                }
                if (key == current.hashKey())
                    return false;
                Node node = new Node (o);
                node.next = current;
                predecesor.next = node;
                size.getAndIncrement();
                return true;
            } finally {current.lock.unlock();}
        } finally {predecesor.lock.unlock();}
    }

    @Override public void printList(){
        Node current = this.head.next();
        for(int i = 0; i< this.size(); i++){
            System.out.println(current.element);
            current = current.next;
        }
    }

    @Override public boolean checkListInvariant(){
        int actual_size = 0; 
        Node current = this.head.next();
        int last_hash = current.hashKey(); 
        
        while((Integer)current.element != Integer.MAX_VALUE){
            current = current.next;
            int curr_hash = current.hashKey();

            if(last_hash > curr_hash){
                return false; 
            }
            last_hash = curr_hash;
            actual_size++; 
        }
        return (actual_size == this.size()) && ((Integer)current.element == Integer.MAX_VALUE)
                && ((Integer)head.element == Integer.MIN_VALUE);  
    }
     
}
