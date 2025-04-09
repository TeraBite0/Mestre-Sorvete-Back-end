package grupo.terabite.terabite.controller;

import grupo.terabite.terabite.service.RelatorioService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.YearMonth;

@RestController
@RequiredArgsConstructor
@RequestMapping("/relatorio")
public class RelatorioController {
    private final RelatorioService relatorioService;

    @GetMapping
    public ResponseEntity<Resource> gerarRelatorio() {
        ByteArrayResource resource = relatorioService.gerarRelatorio();
        String nomeArquivo = "relatorio-" + YearMonth.now().minusMonths(1) + ".xlsx";

        if (resource.exists()) {
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + nomeArquivo + "\"")
                    .body(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
