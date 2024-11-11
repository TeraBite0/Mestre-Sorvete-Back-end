package grupo.terabite.terabite.generic.pilhagenerica;

public class PilhaObj<T> {

    private int topo;
    private T[] vetor;

    public PilhaObj(int capacidade) {
        this.topo = -1;
        this.vetor = (T[]) new Object[capacidade];
    }

    public boolean isEmpty() {
        return topo == -1;
    }

    public boolean isFull() {
        return topo == vetor.length - 1;
    }

    public void push(T info) {
        if (isFull()) throw new IllegalStateException();
        vetor[++topo] = info;
    }

    public T pop() {
        if (isEmpty()) return null;
        return vetor[topo--];
    }

    public T peek() {
        if (isEmpty()) return null;
        return vetor[topo];
    }

    public void exibir() {
        if (isEmpty()) System.out.println("Pilha vazia");
        for (int i = vetor.length - 1; i > -1; i--) System.out.print(vetor[i]);
    }

    public int getTopo() {
        return topo;
    }

    public void setTopo(int topo) {
        this.topo = topo;
    }

    public T[] getVetor() {
        return vetor;
    }

    public void setVetor(T[] vetor) {
        this.vetor = vetor;
    }
}
