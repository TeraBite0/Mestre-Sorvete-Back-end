package grupo.terabite.terabite.controller;

import grupo.terabite.terabite.dto.requisition.NotificacaoRequisitionDTO;
import grupo.terabite.terabite.dto.mapper.NotificacaoMapper;
import grupo.terabite.terabite.dto.response.NotificacaoResponseDTO;
import grupo.terabite.terabite.entity.Notificacao;
import grupo.terabite.terabite.service.NotificacaoService;
import grupo.terabite.terabite.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notificacoes")
public class NotificacaoController {

    private final NotificacaoService service;
    private final ProdutoService produtoService;

    @Operation(summary = "Lista todos alertas de notificação pendentes", description = "Retorna a lista de alertas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação bem-sucedida, Alertas de produtos listados"),
            @ApiResponse(responseCode = "204", description = "Operação bem-sucedida, Sem alertas de produtos"),
            @ApiResponse(responseCode = "401", description = "Erro de requisição, Não autorizado")
    })
    @GetMapping
    public ResponseEntity<List<NotificacaoResponseDTO>> listarNotificacoes() {
        List<Notificacao> notificacoes = service.listarNotificacoes();
        return ResponseEntity.ok(notificacoes.stream().map(NotificacaoMapper::toResponseNotificacaoDto).toList());
    }

    @Operation(summary = "Cria um alerta de notificacao", description = "Retorna o alerta de notificação criado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Alerta criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos")
    })
    @PostMapping
    public ResponseEntity<NotificacaoResponseDTO> criarNotificacao(@RequestBody @Valid NotificacaoRequisitionDTO novaNotificacao) {
        return ResponseEntity.created(null).body(NotificacaoMapper.toResponseNotificacaoDto(
                service.criarNotificacao(
                        NotificacaoMapper.toCreateNotificacaoDto(novaNotificacao, produtoService))));
    }
}
