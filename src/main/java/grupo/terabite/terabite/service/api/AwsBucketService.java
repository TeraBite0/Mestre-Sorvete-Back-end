package grupo.terabite.terabite.service.api;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import grupo.terabite.terabite.entity.Produto;
import grupo.terabite.terabite.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AwsBucketService {

    // final private AmazonS3 client;

    final private ProdutoRepository produtoRepository;

//    @Value("${app.s3.bucket}")
//    final private String bucketName;

    public String salvarImagem(Integer idProduto, MultipartFile arquivo){
        Produto produto = produtoRepository.findById(idProduto).orElse(null);

        if(produto == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado");

        String nomeOriginalArquivo = arquivo.getOriginalFilename();
        String tipoArquivo = nomeOriginalArquivo.substring(nomeOriginalArquivo.indexOf("."));
        String nomeArquivo = "PRODUTO_" + String.format("%06", idProduto) + tipoArquivo;
        Set<String> tiposArquivosPermitidos = Set.of(
                ".png",
                ".jpg",
                ".jpeg"
        );

        if(!tiposArquivosPermitidos.contains(nomeOriginalArquivo.substring(nomeOriginalArquivo.indexOf(".")))){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "O tipo de arquivo passado não é permitido");
        }

        ObjectMetadata metaData = new ObjectMetadata();
        metaData.setContentLength(arquivo.getSize());

//        try {
//            client.putObject(new PutObjectRequest(bucketName, nomeArquivo, arquivo.getInputStream(), metaData));
//        } catch (IOException e) {
//            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao realizar upload da imagem no S3");
//        }

        produto.setTipoImagem(tipoArquivo);
        produtoRepository.save(produto);

        return gerarUrlImagem(nomeArquivo);
    }

    private String gerarUrlImagem(String fileName){
        // define a expiração da Url para 1 hora
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY, 1);
        Date expirationDate = calendar.getTime();

//        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, fileName)
//                .withMethod(HttpMethod.GET)
//                .withExpiration(expirationDate);

        //return client.generatePresignedUrl(generatePresignedUrlRequest).toString();
        return null;
    }

    public String imagemProduto(Integer idProduto, String tipoArquivo){
        return tipoArquivo == null? null : gerarUrlImagem("PRODUTO_" + String.format("%06", idProduto) + tipoArquivo);
    }
}
