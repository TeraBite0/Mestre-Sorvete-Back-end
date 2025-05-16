package grupo.terabite.terabite.controller;

import grupo.terabite.terabite.service.api.AwsBucketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/images")
public class ImagemController {

    //@Autowired
    //AwsBucketService awsBucketService;

    @PostMapping("/produto/upload")
    public ResponseEntity<String> upload(@RequestParam Integer idProduto, @RequestParam("file") MultipartFile arquivo){
        if(arquivo == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Uma imagem é necessária para o upload");
        return null;
        //return ResponseEntity.ok(awsBucketService.salvarImagem(idProduto, arquivo));
    }
}
