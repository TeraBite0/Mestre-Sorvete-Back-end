package grupo.terabite.terabite.generic.filagenerica;

public class FilaObj<T> {
    private int tamanho;
    private T[] fila;

    public FilaObj(int capaciade) {
        this.fila = (T[]) new Object[capaciade];
        this.tamanho = 0;
    }

    public boolean isEmpty() {
        return tamanho == 0;
    }

    public boolean isFull() {
        return tamanho == fila.length;
    }

    public void insert(T info) {
        if (isFull()) throw new IllegalStateException();
        fila[tamanho++] = info;
    }

    public T peek() {
        return fila[0];
    }

    public T poll() {
        T primeiroElemento = peek();
        for (int i = 0; i < tamanho - 1; i++) {
            if (i + 1 == tamanho) fila[i] = null;
            fila[i] = fila[1 + i];
        }
        tamanho--;
        return primeiroElemento;
    }

    public void exibe() {
        for (int i = 0; i < tamanho; i++) {
            System.out.print(fila[i] + "  ");
        }
    }

    public int getTamanho() {
        return tamanho;
    }
}