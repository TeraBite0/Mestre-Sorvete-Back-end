package grupo.terabite.terabite.generic;

import java.util.List;

public interface IOrdenadorGeneric<T extends Comparable<T>> {

    void quickSort(List<T> list, int indInicio, int indFim);
    T buscaBinaria(List<T> list, Object indBusca);
}
