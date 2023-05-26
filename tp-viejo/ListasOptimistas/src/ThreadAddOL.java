public class ThreadAddOL extends Thread {

    public OptimisticList list;
    public ThreadAddOL(OptimisticList list) {
        this.list = list;
    }
    
    public void run(){
        list.add(1);
        list.add(2);
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(6);
        list.add(7);
    }
}
