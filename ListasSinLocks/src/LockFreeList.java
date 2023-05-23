import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.atomic.AtomicMarkableReference;

public class LockFreeList {
    
    private class Node {
        private Object element;
        private int hashKey;
        private Node next;
        private AtomicMarkableReference<Node> mark;
        public ReentrantLock lock = new ReentrantLock();
        
        
        public Node (Object element){
            this.element = element;
            this.hashKey = element.hashCode();
            this.mark = new AtomicMarkableReference<Node>(element, false);
        }
        public int hashKey(){
            return this.hashKey; 
        }
        public Node next(){
            return this.next;
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
        head.next = new Node (Integer.MAX_VALUE);
    }

    public boolean remove(Object o) {
        int key = o.hashCode();
        boolean snip;
        while (true) {
            NodeTuple predAndSucc = find(o);
            Node predecesor = predAndSucc.first; 
            Node succesor = predAndSucc.second; 
            Node current = predecesor.next; 
            if (current.hashKey != key)
                return false;
            else {
                succesor = current.next.getReference();
                snip = current.next.attemptMark(succesor, true);
                if (!snip)
                    continue;
                predecesor.next.compareAndSet(current, succesor, false, false);
                return true;
            }
        }
    }

    public boolean add(Object o) {
        int key = o.hashCode();
        while (true){
            NodeTuple predAndSucc = find(o);
            Node predecesor = predAndSucc.first;
            Node succesor = predAndSucc.second;
            Node current = predecesor.next; 
            if (current.hashKey == key)
                return false;
            else{
                Node node = new Node(o);
                node.next = new AtomicMarkableReference(current,false);
                if (predecesor.next.compareAndSet(current, node, false, false))
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
            predecesor = head.getReference();
            current = predecesor.next.getReference();
            while (true) {
                succesor = current.next.get(marked);
                while (marked[0]){
                    snip = predecesor.next.compareAndSet(current, succesor, false, false);
                    if (!snip) continue tryAgain;
                    current = succesor;
                    succesor = current.next.get(marked);
                }
                if (current.hashKey >= key){
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
        while (current.key < key) {
            current = current.next;
            Node succesor = current.next.get(marked);
        }
        return (current.hashKey == key && !marked[0]);
    }

    public void printList(){
        Node current = this.head.next();
        for(int i = 0; i< this.size.get(); i++){
            System.out.println(current.element);
            current = current.next;
        }
    }
}

