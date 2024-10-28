package grupo.terabite.terabite.io;

import grupo.terabite.terabite.entity.Lote;
import grupo.terabite.terabite.entity.Produto;
import grupo.terabite.terabite.entity.VendaProduto;
import grupo.terabite.terabite.generic.listagenerica.ListaObj;
import grupo.terabite.terabite.repository.LoteRepository;
import grupo.terabite.terabite.repository.ProdutoRepository;
import grupo.terabite.terabite.repository.VendaProdutoRepository;
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
    ListaObj<VendaProduto> listVendaProduto = new ListaObj<>(500);
    ListaObj<Produto> listProtudos = new ListaObj<>(500);
    private final LoteRepository loteRepository;
    private final VendaProdutoRepository vendaProdutoRepository;
    private final ProdutoRepository produtoRepository;

    public void escrever() {
        List<Lote> estoques = loteRepository.findAll();
        estoques.stream().forEach(estoque -> {
            listEstoques.adiciona(estoque);
        });

        List<VendaProduto> vendas = vendaProdutoRepository.findAll();
        vendas.stream().forEach(venda -> {
            listVendaProduto.adiciona(venda);
        });

        List<Produto> produtos = produtoRepository.findAll();
        produtos.stream().forEach(produto -> {
            listProtudos.adiciona(produto);
        });

        try (OutputStream file = new FileOutputStream("arquivo.csv");
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(file))) {

            writer.write("Data e hora;Cidade\n");
            writer.write("%s;%s\n".formatted(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss")), "Santo André"));

            writer.write("Valor total de todos os lotes;\n");
            double totalLote = 0.0;
            for (int i = 0; i < listEstoques.getTamanho(); i++) {
                totalLote += listEstoques.getElemento(i).getValorLote();
            }
            writer.write("%.2f\n".formatted(totalLote));

            writer.write("Total de venda de todos os produtos;\n");
            double totalVendido = 0.0;
            for (int i = 0; i < listVendaProduto.getTamanho(); i++) {
                totalVendido += listVendaProduto.getElemento(i).getQtdProdutosVendido() * listEstoques.getElemento(i).getProduto().getPreco();
            }
            writer.write("%.2f\n".formatted(totalVendido));

            writer.write("Código do Produto;Nome do Produto;Preço (R$);Em Estoque;Categoria/Subtipo;Marca;Status produto ativo;Valor do lote;Quantidade de lote comprado;Total de venda desse produto\n");
            for (int i = 0; i < listProtudos.getTamanho(); i++) {
                Double valorVendas = (listVendaProduto.getElemento(i) == null) ? 0.0 : listVendaProduto.getElemento(i).getQtdProdutosVendido();
                writer.write("%d;%s;%.2f;%b;%s;%s;%b;%.2f;%d;%.2f\n".formatted(
                        listProtudos.getElemento(i).getId(),
                        listProtudos.getElemento(i).getNome(),
                        listProtudos.getElemento(i).getPreco(),
                        listProtudos.getElemento(i).getEmEstoque(),
                        listProtudos.getElemento(i).getSubtipo().getNome(),
                        listProtudos.getElemento(i).getMarca().getNome(),
                        listProtudos.getElemento(i).getIsAtivo(),
                        listEstoques.getElemento(i) == null ? 0 : listEstoques.getElemento(i).getValorLote(),
                        listEstoques.getElemento(i) == null ? 0 : listEstoques.getElemento(i).getQtdProdutoComprado(),
                        (valorVendas * listProtudos.getElemento(i).getPreco())));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Deu erro para encontrar o arquivo");
        } catch (IOException e) {
            System.out.println("Deu erro para ler o arquivo");
        }
    }
}
