package grupo.terabite.terabite.io;

import org.springframework.stereotype.Component;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Component
public class Escritor {

    public void escrever(){
        try(OutputStream file = new FileOutputStream("arquivo.csv");
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(file))){

            writer.write("Data;Cidade;Hora\n");
            String pattern = "%s;%s;%s\n";
            writer.write(pattern.formatted(LocalDate.now(), "Santo André", LocalTime.now().toString()));
        } catch(FileNotFoundException e){
            System.out.println("Deu erro para encontrar o arquivo");
        } catch (IOException e){
            System.out.println("Deu erro para ler o arquivo");
        }
    }
}
