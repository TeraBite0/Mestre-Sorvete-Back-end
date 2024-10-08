package grupo.terabite.terabite.controller;

import grupo.terabite.terabite.entity.Notificacao;
import grupo.terabite.terabite.service.NotificacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notificacoes")
public class NotificacaoController {

    @Autowired
    private NotificacaoService service;

    @Operation(summary = "Lista todos alertas de notificação pendentes", description = "Retorna a lista de alertas")
    @GetMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação bem-sucedida, Alertas de produtos listados"),
            @ApiResponse(responseCode = "204", description = "Operação bem-sucedida, Sem alertas de produtos"),
            @ApiResponse(responseCode = "401", description = "Erro de requisição, Não autorizado")
    })
    public ResponseEntity<List<Notificacao>> listarNotificacoes() {
        return ResponseEntity.ok(service.listarNotificacoes());
    }

    @Operation(summary = "Cria um alerta de notificacao", description = "Retorna o alerta de notificação criado")
    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Alerta criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos")
    })
    public ResponseEntity<Notificacao> criarNotificacao(@RequestBody Notificacao novaNotificacao) {
        return ResponseEntity.ok(service.criarNotificacao(novaNotificacao));
    }
}
