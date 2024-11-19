package grupo.terabite.terabite.io;

import com.opencsv.CSVWriter;
import grupo.terabite.terabite.entity.Lote;
import grupo.terabite.terabite.entity.Produto;
import grupo.terabite.terabite.entity.VendaProduto;
import grupo.terabite.terabite.generic.listagenerica.ListaObj;
import grupo.terabite.terabite.repository.LoteRepository;
import grupo.terabite.terabite.repository.ProdutoRepository;
import grupo.terabite.terabite.repository.VendaProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class Escritor {

    ListaObj<Lote> listEstoques = new ListaObj<>(500);
    ListaObj<VendaProduto> listVendaProduto = new ListaObj<>(500);
    ListaObj<Produto> listProtudos = new ListaObj<>(500);
    private final LoteRepository loteRepository;
    private final VendaProdutoRepository vendaProdutoRepository;
    private final ProdutoRepository produtoRepository;

    private static final String CSV_FILE_PATH = "relatorio.csv";

    public void generateMockCSV() throws IOException {
        loteRepository.findAll().stream().forEach(estoque -> {listEstoques.adiciona(estoque);});
        vendaProdutoRepository.findAll().stream().forEach(venda -> {listVendaProduto.adiciona(venda);});
        produtoRepository.findAll().stream().forEach(produto -> {listProtudos.adiciona(produto);});

        try (OutputStream file = new FileOutputStream(CSV_FILE_PATH);
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(file))) {
            writer.write("Data e hora;Cidade\n");
            writer.write("%s;%s\n".formatted(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss")), "Santo André"));

            double totalLote = 0.0;
            double totalVendido = 0.0;

            for (int i = 0; i < listEstoques.getTamanho(); i++) totalLote += listEstoques.getElemento(i).getValorLote();
            for (int i = 0; i < listVendaProduto.getTamanho(); i++) totalVendido += listVendaProduto.getElemento(i).getQtdProdutosVendido() * listEstoques.getElemento(i).getProduto().getPreco();

            writer.write("Valor total de todos os lotes;\n");
            writer.write("%.2f\n".formatted(totalLote));
            writer.write("Total de venda de todos os produtos;\n");
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

    public String getCsvFilePath() {
        return CSV_FILE_PATH;
    }
}
