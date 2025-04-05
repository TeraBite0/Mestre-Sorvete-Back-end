package grupo.terabite.terabite.controller;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@Controller
@RequestMapping("/images")
public class ImagemController {

    @Autowired
    private AmazonS3 client;

    @Value("${app.s3.bucket}")
    private String bucketName;

    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestParam Integer idProduto, @RequestParam("file") MultipartFile file) {
        String originalFileName = file.getOriginalFilename();

        // gera o nome unico do arquivo no padrão > "produto{id}.{type}" <
        String fileName = "produto" + idProduto + originalFileName.substring(originalFileName.indexOf("."));

        ObjectMetadata metaData = new ObjectMetadata();
        metaData.setContentLength(file.getSize());

        try {
            PutObjectResult putObjectResult = client.putObject(new PutObjectRequest(bucketName, fileName, file.getInputStream(), metaData));
            return ResponseEntity.ok("Imagem " + originalFileName + " recebida para o produto " + idProduto);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao realizar upload da imagem no S3");
        }
    }
}
