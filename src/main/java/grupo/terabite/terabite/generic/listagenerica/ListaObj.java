package grupo.terabite.terabite.generic.listagenerica;

import java.util.Arrays;

public class ListaObj<T> {

    private T[] vetor;
    private int nroElem;

    public ListaObj(int tam) {
        this.vetor = (T[]) new Object[tam];
        this.nroElem = 0;
    }

    public void adiciona(T elemento) {
        if (nroElem >= vetor.length) throw new IllegalStateException();
        vetor[nroElem++] = elemento;
    }

    public int busca(T elemento) {
        for (int i = 0; i < nroElem; i++) {
            if (elemento.equals(vetor[i])) return i;
        }
        return -1;
    }

    public int getTamanho() {
        return nroElem;
    }

    public T getElemento(int indice) {
        if (indice < 0 || indice >= nroElem) return null;
        return vetor[indice];
    }

    public void setElemento(int indice, T elemento) {
        if (indice >= 0 && indice < nroElem) {
            vetor[indice] = elemento;
        }
    }

    public void limpar() {
        nroElem = 0;
        vetor = (T[]) new Object[vetor.length];
        System.out.println(Arrays.toString(vetor));
    }

    public boolean removePeloIndice(int indiceRemovido) {
        if (indiceRemovido >= nroElem || indiceRemovido < 0) return false;
        for (int i = indiceRemovido; i < nroElem - 1; i++) {
            vetor[i] = vetor[i + 1];
        }
        nroElem--;
        return true;
    }

    public boolean removeElemento(T elemento) {
        return removePeloIndice(busca(elemento));
    }

    public void exibe() {
        for (int i = 0; i < nroElem; i++) System.out.printf(vetor[i] + " ");
    }

    public boolean substitui(T valorAntigo, T valorNovo) {
        if (busca(valorAntigo) == -1) System.out.println("Valor não encontrado");
        vetor[busca(valorAntigo)] = valorNovo;
        return true;
    }

    public int contaOcorrencia(T valorRecebido) {
        int ocorrencia = 0;
        for (int i = 0; i < nroElem; i++) {
            if (vetor[i] == valorRecebido) {
                ocorrencia++;
            }
        }
        return ocorrencia;
    }

    public boolean adicionarNoInicio(T valorAdicionar) {
        if (nroElem == vetor.length) System.out.println("Lista cheia");
        else {
            nroElem++;
            for (int i = nroElem; i > 0; i--) {
                vetor[i] = vetor[i - 1];
            }
        }
        vetor[0] = valorAdicionar;
        return true;
    }

    public int getNroElem() {
        return nroElem;
    }

    public T[] getVetor() {
        return vetor;
    }
}
