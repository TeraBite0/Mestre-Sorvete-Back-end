package grupo.terabite.terabite.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/images")
public class ImagemController {

    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestParam Integer idProduto, @RequestParam("file") MultipartFile file){
        return ResponseEntity.ok("Imagem " + file.getOriginalFilename() + " recebida para o produto " + idProduto);
    }
}
