package grupo.terabite.terabite.service;

import grupo.terabite.terabite.io.Escritor;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class RelatorioService {
    private final Escritor escritor;

    public ByteArrayResource gerarRelatorio() {
        String nomeArquivo = escritor.novoRelatorio();
        Path filePath = Paths.get("").resolve(nomeArquivo).normalize();

        try {
            Resource resource = new UrlResource(filePath.toUri());
            byte[] dados = Files.readAllBytes(resource.getFile().toPath());
            ByteArrayResource arquivoEmMemoria = new ByteArrayResource(dados);
            Files.delete(filePath);
            return arquivoEmMemoria;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao redirecionar arquivo para download");
        }
    }
}
