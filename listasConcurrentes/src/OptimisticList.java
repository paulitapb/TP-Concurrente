import java.util.concurrent.locks.ReentrantLock;

public class OptimisticList {
    public class Node {
        public Object element;
        public int hashKey;
        public Node next;
        public ReentrantLock lock = new ReentrantLock();
        public Node (Object element){
            this.element = element;
            this.hashKey = element.hashCode();
        }
        public int hashKey(){
            return this.hashKey; 
        }
    }

    private Node head;
    public OptimisticList(){
        head = new Node (Integer.MIN_VALUE);
        head.next = new Node (Integer.MAX_VALUE);
    }


    private boolean validate(Node pred, Node curr){
        Node aux = head;
        while (aux.hashKey() <= pred.hashKey()) {
        if (aux == pred) //accessible
        return pred.next == curr; //adyacentes
        aux = aux.next;
        }
        return false;
       }

     
}
