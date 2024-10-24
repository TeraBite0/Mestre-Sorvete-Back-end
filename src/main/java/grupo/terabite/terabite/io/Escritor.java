package grupo.terabite.terabite.io;

import grupo.terabite.terabite.entity.Lote;
import grupo.terabite.terabite.entity.Produto;
import grupo.terabite.terabite.generic.listagenerica.ListaObj;
import grupo.terabite.terabite.repository.LoteRepository;
import grupo.terabite.terabite.repository.ProdutoRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
@Service
@Getter
@Setter
@RequiredArgsConstructor
public class Escritor {

    ListaObj<Lote> listEstoques = new ListaObj<>(500);
    private final LoteRepository loteRepository;

    public void escrever() {
        List<Lote> estoques = loteRepository.findAll();
        estoques.stream().forEach(estoque -> {
            listEstoques.adiciona(estoque);
        });

        try (OutputStream file = new FileOutputStream("arquivo.csv");
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(file))) {

            writer.write("Data e hora;Cidade\n");
            String pattern = "%s;%s\n";
            writer.write(pattern.formatted(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss")), "Santo André"));
            writer.write("Código do Produto;Nome do Produto;Preço (R$);Em Estoque;Categoria/Subtipo;Marca;Status produto ativo; \n");
            for (int i = 0; i < listEstoques.getTamanho(); i++) {
                writer.write("%d;%s;%.2f;%b;%s;%s;%b\n".formatted(
                        listEstoques.getElemento(i).getId(),
                        listEstoques.getElemento(i).getProduto().getNome(),
                        listEstoques.getElemento(i).getProduto().getPreco(),
                        listEstoques.getElemento(i).getProduto().getEmEstoque(),
                        listEstoques.getElemento(i).getProduto().getSubtipo().getNome(),
                        listEstoques.getElemento(i).getProduto().getMarca().getNome(),
                        listEstoques.getElemento(i).getProduto().getIsAtivo()));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Deu erro para encontrar o arquivo");
        } catch (IOException e) {
            System.out.println("Deu erro para ler o arquivo");
        }
    }
}
