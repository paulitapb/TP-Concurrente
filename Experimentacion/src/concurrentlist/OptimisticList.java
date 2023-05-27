package concurrentlist; 
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

final public class OptimisticList extends ConcurrentList{
    
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
    public OptimisticList(){
        head = new Node (Integer.MIN_VALUE);
        head.next = new Node (Integer.MAX_VALUE);
    }

    @Override public int size(){
        return this.size.get();
    };

    @Override public boolean remove(Object o) {
        int key = o.hashCode();
        while (true) { //volver a empezar
            Node predecesor = head;
            Node current = head.next;
            while (current.hashKey() < key) {
                predecesor = current;
                current = current.next;
            }
            predecesor.lock.lock(); 
            current.lock.lock();

            try {
                if (validate(predecesor,current)) {
                    if (current.hashKey() == key) {
                        predecesor.next = current.next;
                        size.getAndDecrement();
                        return true;
                    } 
                    else
                        return false;
                }
            }
            finally {
                predecesor.lock.unlock(); 
                current.lock.unlock();
            }
        }
    }

    @Override public boolean add(Object o) {
        int key = o.hashCode();
        while (true) { //volver a empezar
            Node predecesor = head;
            Node current = head.next;
            while (current.hashKey() < key) {
                predecesor = current;
                current = current.next;
            }
            
            predecesor.lock.lock(); 
            current.lock.lock();
            
            try {
                if (validate(predecesor,current)) {
                    if (current.hashKey() == key){
                        return false;
                    }
                    else {
                        Node node = new Node (o);
                        node.next = current;
                        predecesor.next = node;
                        size.getAndIncrement();
                        return true;
                    }
                }
            } 
            finally {
                predecesor.lock.unlock(); 
                current.lock.unlock();
            }
        }
    }


    private boolean validate(Node predecesor, Node current){
        Node aux = head;
        while (aux.hashKey() <= predecesor.hashKey()) {
            if (aux == predecesor)
                return predecesor.next == current; 
            aux = aux.next;
        }
        return false;
    }

    @Override public void printList(){
        Node current = this.head.next();
        for(int i = 0; i< this.size.get(); i++){
            System.out.println(current.element);
            current = current.next;
        }
    }

    @Override public boolean checkListInvariant(){
        int actual_size = 0; 
        Node current = this.head.next();
        int last_hash = current.hashKey(); 
            
        for(int i = 0; i< this.size(); i++){
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


    

