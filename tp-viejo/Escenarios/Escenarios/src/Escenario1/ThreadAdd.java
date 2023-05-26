import finegrainedlist.*;

public class ThreadAdd extends Thread {

    public FineGrainList list;
    public int desde;
    public int hasta;
    public int salto;

    public ThreadAdd(FineGrainList list, int desde, int hasta, int salto) {
        this.list = list;
        this.desde = desde;
        this.hasta = hasta;
        this.salto = salto;
    }

    public void run(){
        int valor = desde;
        while(valor < hasta){
            list.add(valor);
            valor+=salto;
        }
    }

}
