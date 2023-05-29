package concurrentlist; 
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicMarkableReference;

final public class LockFreeList extends ConcurrentList {
    
    private class Node {
        private Object element;
        private int hashKey; 
        private AtomicMarkableReference<Node> next_mark;
        
        public Node (Object element){
            this.element = element;
            this.hashKey = element.hashCode();
            this.next_mark = new AtomicMarkableReference<LockFreeList.Node>(this, false);
        }
        public int hashKey(){
            return this.hashKey; 
        }
 
    }

    private class NodeTuple{
        private Node first;
        private Node second;

        public NodeTuple(Node first, Node second){
            this.first = first; 
            this.second = second; 
        }
    }
    

    private Node head;
    public AtomicInteger size = new AtomicInteger(0);
    
    public LockFreeList(){
        head = new Node (Integer.MIN_VALUE);
        head.next_mark = new AtomicMarkableReference<LockFreeList.Node>(
                                                new Node (Integer.MAX_VALUE), false);
    }

    @Override public int size(){
        return this.size.get();
    };

    @Override public boolean remove(Object o) {
        int key = o.hashCode();
        boolean snip;
        while (true) {
            NodeTuple predAndSucc = find(o);
            Node predecesor = predAndSucc.first; 
            Node succesor = predAndSucc.second; 
            Node current = predecesor.next_mark.getReference(); 
            if (current.hashKey() != key)
                return false;
            else {
                succesor = current.next_mark.getReference();
                snip = current.next_mark.attemptMark(succesor, true);
                if (!snip)
                    continue;
                predecesor.next_mark.compareAndSet(current, succesor, false, false);
                size.getAndDecrement();
                return true;
            }
        }
    }

    @Override public boolean add(Object o) {
        int key = o.hashCode();
        while (true){
            NodeTuple predAndSucc = find(o);
            Node predecesor = predAndSucc.first;
            
            Node current = predecesor.next_mark.getReference(); 
            if (current.hashKey() == key)
                return false;
            else{
                Node node = new Node(o);
                node.next_mark = new AtomicMarkableReference<Node>(current,false);
                if (predecesor.next_mark.compareAndSet(current, node, false, false))
                    size.getAndIncrement();
                    return true;
            }
        }
    }

    private NodeTuple find(Object o){
        Node predecesor, current, succesor;
        boolean[] marked ={false};
        boolean snip;
        int key = o.hashCode();
        tryAgain: while (true) {
            predecesor = head;
            current = predecesor.next_mark.getReference();
            while (true) {
                succesor = current.next_mark.get(marked);
                while (marked[0]){
                    snip = predecesor.next_mark.compareAndSet(current, succesor, false, false);
                    if (!snip) continue tryAgain;
                    current = succesor;
                    succesor = current.next_mark.get(marked);
                }
                if (current.hashKey() >= key){
                    NodeTuple predecesorAndSucc = new NodeTuple(predecesor, current);  
                    return predecesorAndSucc;
                }
                predecesor = current;
                current = succesor;
            }
        }
    }

    public boolean contains(Object o) {
        boolean[] marked = {false};
        int key = o.hashCode();
        Node current = head;
        while (current.hashKey() < key) {
            current = current.next_mark.getReference();
            current.next_mark.get(marked);
        }
        return (current.hashKey() == key && !marked[0]);
    }
    
    @Override public void printList(){
        Node current = this.head.next_mark.getReference();
        for(int i = 0; i< this.size(); i++){
            if(this.contains(current.element)){
                System.out.println(current.element);
            }
            current = current.next_mark.getReference();   
        }
    }

    
    @Override public boolean checkListInvariant(){
        int actual_size = 0; 
        Node current = this.head.next_mark.getReference();
        for(int i = 0; i< this.size(); i++){
            if(this.contains(current.element)){
                actual_size++;
            }
            current = current.next_mark.getReference();
            /* if(node_val + 1 != (int)current.element){
                System.out.println("falta el" + node_val);
                System.out.println(current.element);
                break;
            }
            node_val++;  */

        }
        if((actual_size != this.size())){
            System.out.println("actual " + actual_size);
            System.out.println("size " + this.size());
            this.printList();
            return false; 
        }
        //(actual_size == this.size())
        return  ((Integer)current.element == Integer.MAX_VALUE)
                && ((Integer)head.element == Integer.MIN_VALUE); 
    }
}

