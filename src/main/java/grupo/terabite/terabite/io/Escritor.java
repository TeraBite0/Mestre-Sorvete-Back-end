package grupo.terabite.terabite.io;

import grupo.terabite.terabite.entity.Lote;
import grupo.terabite.terabite.entity.LoteProduto;
import grupo.terabite.terabite.entity.Produto;
import grupo.terabite.terabite.entity.SaidaEstoque;
import grupo.terabite.terabite.repository.*;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class Escritor {
    private final LoteRepository loteRepository;
    private final ProdutoRepository produtoRepository;
    private final SaidaEstoqueRepository saidaEstoqueRepository;

    private List<Produto> produtos;
    private List<Lote> lotes;
    private List<SaidaEstoque> saidaEstoques;
    private List<LoteProduto> loteProdutos;

    // gera um relatório com base nos registros do mês passado (lotes, saidaEstoq)
    public String novoRelatorio() {
        InputStream file = getClass().getClassLoader().getResourceAsStream("excel/model.xlsx");
        if (file == null) erro("Modelo excel não encontrado");

        Workbook workbook = null;
        try {
            workbook = new XSSFWorkbook(file);
        } catch (IOException e) {
            erro("Não foi possivel processar o modelo excel");
        }

        listarDados();
        escreverDashboard(workbook);
        escreverProdutos(workbook);
        escreverLotes(workbook);
        escreverLoteProduto(workbook);
        escreverSaidaEstoque(workbook);

        String diretorio = "relatorios";
        String nomeArquivo = "novoRelatorio.xlsx";
        Path caminhoDestino = Paths.get(diretorio, nomeArquivo);
        try (FileOutputStream out = new FileOutputStream(caminhoDestino.toFile())) {
            workbook.write(out);
            workbook.close();
        } catch (IOException e) {
            erro("Erro ao salvar relatório");
        }
        return nomeArquivo;
    }

    private void escreverDashboard(Workbook workbook){
        Sheet sheet = workbook.getSheet("Dashboard");
        CellStyle dateCellStyle = estiloDataCelula(workbook);
        Row row1 = sheet.createRow(0);
        row1.createCell(1).setCellStyle(dateCellStyle);
        row1.createCell(1).setCellValue(LocalDate.now());

        Double faturamentoMensal = 0.0;
        Double qtdGastaEmLotes = 0.0;
        List<Produto> produtosSemSaida = new ArrayList<>();
        produtosSemSaida.addAll(produtos);
        for(SaidaEstoque se : saidaEstoques){
            faturamentoMensal += se.getQtdCaixasSaida() * (se.getProduto().getPreco() * se.getProduto().getQtdPorCaixas());
            produtosSemSaida.remove(se.getProduto());
        }
        for(Lote l: lotes){
            qtdGastaEmLotes += l.getValorLote();
        }
        faturamentoMensal -= qtdGastaEmLotes;
        Row row2 = sheet.createRow(1);
        row2.createCell(1).setCellValue(faturamentoMensal);
        Row row3 = sheet.createRow(2);
        row3.createCell(1).setCellValue(qtdGastaEmLotes);
        Row row4 = sheet.createRow(3);
        row4.createCell(1).setCellValue(produtosSemSaida.size());
    }

    private void escreverProdutos(Workbook workbook) {
        Sheet sheet = workbook.getSheet("Produtos");
        int rowIndex = 2;

        for (Produto p : this.produtos) {
            Row row = sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue(p.getId());
            row.createCell(1).setCellValue(p.getNome());
            row.createCell(2).setCellValue(p.getMarca().getNome());
            row.createCell(3).setCellValue(p.getSubtipo().getTipo().getNome());
            row.createCell(4).setCellValue(p.getSubtipo().getNome());
            row.createCell(5).setCellValue(p.getQtdCaixasEstoque());
            row.createCell(6).setCellValue(p.getQtdPorCaixas());
            row.createCell(7).setCellValue(p.getPreco());
            if (p.getDisponivel()) {
                row.createCell(8).setCellValue("Disponivel");
            } else {
                row.createCell(8).setCellValue("Indisponivel");
            }
            if (p.getIsAtivo()) {
                row.createCell(9).setCellValue("Ativo");
            } else {
                row.createCell(9).setCellValue("Inativo");
            }
            if (p.getTemGluten()) {
                row.createCell(10).setCellValue("Glútem");
                if (p.getTemLactose()) {
                    row.createCell(10).setCellValue("Glútem, Lactose");
                }
            } else if (p.getTemLactose()) {
                row.createCell(10).setCellValue("Lactose");
            }
        }
    }

    private void escreverLotes(Workbook workbook) {
        Sheet sheet = workbook.getSheet("Lotes");
        int rowIndex = 2;

        CellStyle dateCellStyle = estiloDataCelula(workbook);

        for (Lote l : this.lotes) {
            Row row = sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue(l.getId());
            row.createCell(1).setCellStyle(dateCellStyle);
            row.createCell(1).setCellValue(l.getDtPedido());
            row.createCell(2).setCellStyle(dateCellStyle);
            row.createCell(2).setCellValue(l.getDtEntrega());
            row.createCell(3).setCellStyle(dateCellStyle);
            row.createCell(3).setCellValue(l.getDtVencimento());
            row.createCell(4).setCellValue(l.getLoteProdutos().size());
            row.createCell(5).setCellValue(l.getFornecedor().getNome());
            row.createCell(6).setCellValue(l.getLoteProdutos().stream().mapToInt(LoteProduto::getQtdCaixasCompradas).sum());
            row.createCell(7).setCellValue(l.getValorLote());
            row.createCell(8).setCellValue(l.getStatus().getStatus());
            if (l.getObservacao() != null) {
                row.createCell(9).setCellValue(l.getObservacao());
            }
        }
    }

    private void escreverLoteProduto(Workbook workbook) {
        Sheet sheet = workbook.getSheet("Produtos por Lote");
        int rowIndex = 2;

        for (LoteProduto lp : this.loteProdutos) {
            Row row = sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue(lp.getId());
            row.createCell(1).setCellValue(lp.getLote().getId());
            row.createCell(2).setCellValue(lp.getProduto().getNome());
            row.createCell(3).setCellValue(lp.getProduto().getMarca().getNome());
            row.createCell(4).setCellValue(lp.getProduto().getSubtipo().getTipo().getNome());
            row.createCell(5).setCellValue(lp.getProduto().getSubtipo().getNome());
            row.createCell(6).setCellValue(lp.getQtdCaixasCompradas());
        }
    }

    private void escreverSaidaEstoque(Workbook workbook){
        Sheet sheet = workbook.getSheet("Baixas de Estoque");
        int rowIndex = 2;

        CellStyle dateCellStyle = estiloDataCelula(workbook);

        for(SaidaEstoque se: this.saidaEstoques){
            Row row = sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue(se.getId());
            row.createCell(1).setCellValue(se.getProduto().getNome());
            row.createCell(2).setCellValue(se.getProduto().getMarca().getNome());
            row.createCell(3).setCellValue(se.getProduto().getSubtipo().getTipo().getNome());
            row.createCell(4).setCellValue(se.getProduto().getSubtipo().getNome());
            row.createCell(5).setCellStyle(dateCellStyle);
            row.createCell(5).setCellValue(se.getDtSaida());
            row.createCell(6).setCellValue(se.getQtdCaixasSaida());
        }
    }

    private void listarDados(){
        this.produtos = listarProdutos();
        this.lotes = lisarLotes();
        this.loteProdutos = listarLoteProdutos();
        this.saidaEstoques = listarSaidaEstoques();
    }

    private List<Produto> listarProdutos() {
        return produtoRepository.findAll();
    }

    private List<Lote> lisarLotes() {
        return loteRepository.findAll();
        //LocalDate dataLotes = LocalDate.now().minusDays(LocalDate.now().getDayOfMonth());
        //return loteRepository.findByDtPedidoBefore(dataLotes);
    }

    private List<LoteProduto> listarLoteProdutos() {
        List<List<LoteProduto>> loteProdutosListList = this.lotes.stream().map(Lote::getLoteProdutos).toList();
        List<LoteProduto> loteProdutos = new ArrayList<>();
        for (List<LoteProduto> l : loteProdutosListList) {
            loteProdutos.addAll(l);
        }
        return loteProdutos;
    }

    private List<SaidaEstoque> listarSaidaEstoques(){
        return saidaEstoqueRepository.findAll();
        //LocalDate dataSaidaEstoque = LocalDate.now().minusDays(LocalDate.now().getDayOfMonth());
        //return saidaEstoqueRepository.findByDtSaidaBefore(dataSaidaEstoque);
    }

    private CellStyle estiloDataCelula(Workbook workbook){
        CreationHelper createHelper = workbook.getCreationHelper();
        CellStyle dateCellStyle = workbook.createCellStyle();
        dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/MM/yyyy"));
        return dateCellStyle;
    }

    private void erro(String mensagem) {
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, mensagem);
    }
}
