package grupo.terabite.terabite.io;

import grupo.terabite.terabite.entity.Produto;
import grupo.terabite.terabite.entity.SaidaEstoque;
import grupo.terabite.terabite.repository.*;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
@RequiredArgsConstructor
public class Escritor {
    private final LoteRepository loteRepository;
    private final LoteProdutoRepository loteProdutoRepository;
    private final ProdutoRepository produtoRepository;
    private final SaidaEstoqueRepository saidaEstoqueRepository;
    private final NotificacaoRepository notificacaoRepository;
    private List<LoteRepository> loteRepositoryList;
    private List<LoteProdutoRepository> loteProdutoRepositoryList;
    private List<Produto> produtoList;
    private List<SaidaEstoque> saidaEstoqueList;
    private List<NotificacaoRepository> notificacaoRepositoryList;

    // gera um relatório com base nos registros do mês passado (lotes, saidaEstoq)
    public void gerarRelatorio() {
        InputStream file = getClass().getClassLoader().getResourceAsStream("excel/model.xlsx");
        if (file == null) erro("Modelo excel não encontrado");

        Workbook workbook = null;
        try {
            workbook = new XSSFWorkbook(file);
        } catch (IOException e) {
            erro("Não foi possivel processar o modelo excel");
        }

        escreverProdutos(workbook);

        try (FileOutputStream out = new FileOutputStream("relatorio.xlsx")) {
            workbook.write(out);
            workbook.close();
        } catch (IOException e) {
            erro("Erro ao salvar relatório");
        }
    }

    private void escreverProdutos(Workbook workbook){
        Sheet produtosSheet = workbook.getSheet("Produtos");
        List<Produto> produtos = listarProdutos();
        int rowIndex = 2;

        for (Produto p : produtos) {
            Row row = produtosSheet.createRow(rowIndex++);
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

    private List<Produto> listarProdutos() {
        return produtoRepository.findAll();
    }

    private void erro(String mensagem) {
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, mensagem);
    }
}
