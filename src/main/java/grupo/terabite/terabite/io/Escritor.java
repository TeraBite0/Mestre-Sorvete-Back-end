package grupo.terabite.terabite.io;

import grupo.terabite.terabite.entity.Produto;
import grupo.terabite.terabite.generic.listagenerica.ListaObj;
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

    ListaObj<Produto> list = new ListaObj<>(500);
    private final ProdutoRepository produtoRepository;

    public void escrever() {
        List<Produto> produtos = produtoRepository.findAll();
        produtos.stream().forEach(produto -> {
            list.adiciona(produto);
        });
        try (OutputStream file = new FileOutputStream("arquivo.csv");
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(file))) {

            writer.write("Data e hora;Cidade\n");
            String pattern = "%s;%s\n";
            writer.write(pattern.formatted(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss")), "Santo André"));
            writer.write("Código do Produto;Nome do Produto;Preço (R$);Em Estoque;Categoria/Subtipo;Marca;Status produto ativo\n");
            for (int i = 0; i < list.getTamanho(); i++) {
                writer.write("%d;%s;%.2f;%b;%s;%s;%b\n".formatted(
                        list.getElemento(i).getId(),
                        list.getElemento(i).getNome(),
                        list.getElemento(i).getPreco(),
                        list.getElemento(i).getEmEstoque(),
                        list.getElemento(i).getSubtipo().getNome(),
                        list.getElemento(i).getMarca().getNome(),
                        list.getElemento(i).getIsAtivo()));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Deu erro para encontrar o arquivo");
        } catch (IOException e) {
            System.out.println("Deu erro para ler o arquivo");
        }
    }
}
