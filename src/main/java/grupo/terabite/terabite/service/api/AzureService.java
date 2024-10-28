package grupo.terabite.terabite.service.api;

import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.sas.BlobSasPermission;
import com.azure.storage.blob.sas.BlobServiceSasSignatureValues;
import com.azure.storage.common.StorageSharedKeyCredential;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Service
public class AzureService {

    public String gerarTokenSAS() {
        String accountKey = System.getenv("AZURE_ACCOUNT_KEY");

        String accountName = "terabite";
        String containerName = "terabite-container";

        StorageSharedKeyCredential credential = new StorageSharedKeyCredential(accountName, accountKey);

        BlobSasPermission permission = new BlobSasPermission()
                .setReadPermission(true)
                .setWritePermission(true);

        OffsetDateTime expiryTime = OffsetDateTime.now(ZoneOffset.UTC).plusHours(1);


        BlobServiceSasSignatureValues values = new BlobServiceSasSignatureValues(expiryTime, permission);

        // Gera a URL SAS
        BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
                .endpoint("https://" + accountName + ".blob.core.windows.net")
                .credential(credential)
                .buildClient();

        return blobServiceClient.getBlobContainerClient(containerName)
                .generateSas(values);
    }

    public static void main(String[] args) {
        //RODAR PARA TESTE EU GERAMENTO MANUAL DO TOKEN SAS
        System.out.println(new AzureService().gerarTokenSAS());
    }
}
