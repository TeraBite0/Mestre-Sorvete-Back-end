package grupo.terabite.terabite.controller;

import grupo.terabite.terabite.io.Escritor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/exportcsv")
public class CriarArquivoCsvController {

    private final Escritor escritor;

    @GetMapping
    public void gerarArquivo(){
        escritor.escrever();
    }
}
