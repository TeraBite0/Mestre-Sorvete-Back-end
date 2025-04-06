package grupo.terabite.terabite.service.api;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

public class AwsBucketService {

    @Autowired
    private AmazonS3 client;

    @Value("${app.s3.bucket}")
    private String bucketName;

    public String salvarImagem(Integer idProduto, MultipartFile arquivo){
        String nomeOriginalArquivo = arquivo.getOriginalFilename();
        String nomeArquivo = "produto" + idProduto + nomeOriginalArquivo.substring(nomeOriginalArquivo.indexOf("."));
        Set<String> tiposArquivosPermitidos = Set.of(
                ".png",
                ".jpg",
                ".jpeg"
        );

        if(!tiposArquivosPermitidos.contains(nomeOriginalArquivo.substring(nomeOriginalArquivo.indexOf(".")))){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O tipo de arquivo passado não é permitido");
        }

        ObjectMetadata metaData = new ObjectMetadata();
        metaData.setContentLength(arquivo.getSize());

        try {
            PutObjectResult putObjectResult = client.putObject(new PutObjectRequest(bucketName, nomeArquivo, arquivo.getInputStream(), metaData));
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao realizar upload da imagem no S3");
        }

        return gerarUrlImagem(nomeArquivo);
    }

    private String gerarUrlImagem(String fileName){
        // define a expiração da Url para 1 hora
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY, 1);
        Date expirationDate = calendar.getTime();

        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, fileName)
                .withMethod(HttpMethod.GET)
                .withExpiration(expirationDate);

        return client.generatePresignedUrl(generatePresignedUrlRequest).toString();
    }
}
