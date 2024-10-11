package grupo.terabite.terabite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import grupo.terabite.terabite.service.AzureService;

@RestController
@RequestMapping("/azure")
public class AzureController {

    @Autowired
    AzureService azureService;

    @GetMapping
    public ResponseEntity<String> gerarTokenSas(){
        return ResponseEntity.ok(azureService.gerarTokenSAS());
    }
}
