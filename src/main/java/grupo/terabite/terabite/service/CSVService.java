package grupo.terabite.terabite.service;

import com.opencsv.CSVWriter;
import org.springframework.stereotype.Service;
import java.io.FileWriter;
import java.io.IOException;

@Service
public class CSVService {

    private static final String CSV_FILE_PATH = "dados_mockados.csv";

    public void generateMockCSV() throws IOException {
        try (CSVWriter writer = new CSVWriter(new FileWriter(CSV_FILE_PATH))) {
            // Cabeçalho do CSV
            String[] header = { "ID", "Nome", "Idade", "Cidade" };
            writer.writeNext(header);

            // Dados mockados
            String[] record1 = { "1", "João Silva", "30", "São Paulo" };
            String[] record2 = { "2", "Maria Oliveira", "25", "Rio de Janeiro" };
            String[] record3 = { "3", "Carlos Souza", "28", "Belo Horizonte" };

            // Escrevendo os dados no CSV
            writer.writeNext(record1);
            writer.writeNext(record2);
            writer.writeNext(record3);
        }
    }

    public String getCsvFilePath() {
        return CSV_FILE_PATH;
    }
}
