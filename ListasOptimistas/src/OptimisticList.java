import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class OptimisticList {
    
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

    public boolean remove(Object o) {
        int key = o.hashCode();
        while (true) { //volver a empezar
            Node predecesor = head;
            Node current = head.next;
            while (current.hashKey < key) {
                predecesor = current;
                current = current.next;
            }
            predecesor.lock.lock(); 
            current.lock.lock();

            try {
                if (validate(predecesor,current)) {
                    if (current.hashKey == key) {
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

    public boolean add(Object o) {
        int key = o.hashCode();
        while (true) { //volver a empezar
            Node predecesor = head;
            Node current = head.next;
            while (current.hashKey < key) {
                predecesor = current;
                current = current.next;
            }
            
            predecesor.lock.lock(); 
            current.lock.lock();
            
            try {
                if (validate(predecesor,current)) {
                    if (current.hashKey == key){
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

    public void printList(){
        Node current = this.head.next();
        for(int i = 0; i< this.size.get(); i++){
            System.out.println(current.element);
            current = current.next;
        }
    }
}


    

