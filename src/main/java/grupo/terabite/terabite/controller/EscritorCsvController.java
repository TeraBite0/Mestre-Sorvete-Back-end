package grupo.terabite.terabite.controller;

import grupo.terabite.terabite.io.Escritor;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/csv")
public class EscritorCsvController {

    private final Escritor escritor;

    @GetMapping(value = "/download", produces = "text/csv")
    public ResponseEntity downloadCSVNovo() throws IOException {
        escritor.generateMockCSV();

        Resource fileResource = new FileSystemResource(escritor.getCsvFilePath());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"relatorio.csv\"")
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(fileResource);
    }
}
