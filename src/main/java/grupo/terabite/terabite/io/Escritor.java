package grupo.terabite.terabite.io;

import grupo.terabite.terabite.entity.Lote;
import grupo.terabite.terabite.entity.Produto;
import grupo.terabite.terabite.entity.Venda;
import grupo.terabite.terabite.entity.VendaProduto;
import grupo.terabite.terabite.generic.filagenerica.FilaObj;
import grupo.terabite.terabite.generic.listagenerica.ListaObj;
import grupo.terabite.terabite.generic.pilhagenerica.PilhaObj;
import grupo.terabite.terabite.repository.LoteRepository;
import grupo.terabite.terabite.repository.ProdutoRepository;
import grupo.terabite.terabite.repository.VendaProdutoRepository;
import grupo.terabite.terabite.repository.VendaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class Escritor {

    ListaObj<Lote> listEstoques = new ListaObj<>(500);
    ListaObj<VendaProduto> listVendaProdutos = new ListaObj<>(500);
    ListaObj<Produto> listProtudos = new ListaObj<>(500);
    ListaObj<Venda> listVendas = new ListaObj<>(500);
    private final LoteRepository loteRepository;
    private final VendaProdutoRepository vendaProdutoRepository;
    private final VendaRepository vendaRepository;
    private final ProdutoRepository produtoRepository;

    private static final String CSV_FILE_PATH = "relatorio.csv";

    public void generateMockCSV() throws IOException {
        converterListas();

        try (OutputStream file = new FileOutputStream(CSV_FILE_PATH);
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(file))) {

            writer.write("Data e hora;Cidade\n");
            writer.write("%s;%s\n".formatted(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss")), "Santo André"));

            Double valorTotal = 0.0;
            writer.write("Valor total de todos os lotes;Total de venda de todos os produtos\n");
            writer.write("%.2f;%.2f\n".formatted(calcularTotalDeLotes(valorTotal), calcularTotalVendido(valorTotal)));


            writer.write("Vendas de Hoje\n");
            Double totalDeVendasDodia = 0.0;
            writer.write("Data das vendas;Nome do produto;Valor unitário;Quantidade comprada;Valor total das vendas de cada produto\n");
            for (int i = 0; i < listVendas.getTamanho(); i++) {
                LocalDateTime data = listVendas.getElemento(i).getDataCompra();
                if(data.toLocalDate().equals(LocalDate.now())){
                    for (int j = 0; j < listVendaProdutos.getTamanho(); j++) {
                        if(listVendaProdutos.getElemento(j).getVenda().getDataCompra().equals(listVendas.getElemento(i).getDataCompra())){
                            writer.write("%s;%s;%.2f;%s;%.2f\n".formatted(
                                    data,
                                    listVendaProdutos.getElemento(j).getProduto().getNome(),
                                    listVendaProdutos.getElemento(j).getProduto().getPreco(),
                                    listVendaProdutos.getElemento(j).getQtdProdutosVendido(),
                                    listVendaProdutos.getElemento(j).getQtdProdutosVendido() * listVendaProdutos.getElemento(j).getProduto().getPreco()));
                            totalDeVendasDodia += listVendaProdutos.getElemento(j).getQtdProdutosVendido() * listVendaProdutos.getElemento(j).getProduto().getPreco();
                        }
                    }
                }
            }

            writer.write("Total de venda do dia atual\n");
            writer.write("%.2f\n".formatted(totalDeVendasDodia));

            writer.write("Historico de todos os produtos cadastrados\n");
            writer.write("Código do Produto;Nome do Produto;Preço (R$);Em Estoque;Categoria/Subtipo;Marca;Status produto ativo;Valor do lote;Quantidade de lote comprado;Total de venda desse produto\n");
            for (int i = 0; i < listProtudos.getTamanho(); i++) {
                Double valorVendas = (listVendaProdutos.getElemento(i) == null) ? 0.0 : listVendaProdutos.getElemento(i).getQtdProdutosVendido();
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

    public void converterListas(){
        loteRepository.findAll().stream().forEach(estoque -> {listEstoques.adiciona(estoque);});
        vendaProdutoRepository.findAll().stream().forEach(venda -> {listVendaProdutos.adiciona(venda);});
        vendaRepository.findAll().stream().forEach(venda -> {listVendas.adiciona(venda);});
        produtoRepository.findAll().stream().forEach(produto -> {listProtudos.adiciona(produto);});
    }

    public Double calcularTotalDeLotes(Double soma){
        PilhaObj<Double> pilha = new PilhaObj<>(listEstoques.getTamanho());

        for (int i = 0; i < listEstoques.getTamanho(); i++) pilha.push(listEstoques.getElemento(i).getValorLote());
        for (int i = 0; i < pilha.getTopo(); i++) soma += pilha.pop();

        return soma;
    }

    public Double calcularTotalVendido(Double soma){
        FilaObj<Double> fila = new FilaObj<>(listVendaProdutos.getTamanho());

        for (int i = 0; i < listVendaProdutos.getTamanho(); i++){
            fila.insert(listVendaProdutos.getElemento(i).getQtdProdutosVendido() * listEstoques.getElemento(i).getProduto().getPreco());
        }
        for (int i = 0; i < fila.getTamanho(); i++) soma += fila.poll();

        return soma;
    }

    public String getCsvFilePath() {
        return CSV_FILE_PATH;
    }
}
