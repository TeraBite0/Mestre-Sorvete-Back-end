package grupo.terabite.terabite.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.Properties;
import java.util.Scanner;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailApiService {
    final String username = System.getenv("EMAIL_BENEFICIARIO");
    final String password = System.getenv("SENHA_APP_EMAIL");
    final Properties props;

    final Session session;

    public EmailApiService() {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");  // Servidor SMTP
        properties.put("mail.smtp.port", "587");             // Porta TLS
        properties.put("mail.smtp.auth", "true");            // Requer autenticação
        properties.put("mail.smtp.starttls.enable", "true"); // Habilitar TLS
        this.props = properties;

        this.session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
    }

    public void enviarEmail(String destinatario,String assunto, String body){
        Message email = construirEmail(destinatario, assunto, body);

        try{
            Transport.send(email);
        } catch ( Exception e){
            throw new RuntimeException("Erro ao enviar email.", e);
        }
    }

    public Message construirEmail(String destinatario,String assunto , String bodyText){
        Message message = new MimeMessage(session);
        String headerPath = "email/header.png";
        String footerPath = "email/footer.png";

        try{
            message.setFrom(new InternetAddress(username));

            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));

            message.setSubject(assunto);

            MimeBodyPart header = new MimeBodyPart();
            InputStream imageStreamHeader = EmailApiService.class.getClassLoader().getResourceAsStream(headerPath);

            MimeBodyPart footer = new MimeBodyPart();
            InputStream imageStreamFooter = EmailApiService.class.getClassLoader().getResourceAsStream(footerPath);

            if(imageStreamHeader == null || imageStreamFooter == null){
                throw new FileNotFoundException("Imagem não encontrada no classpath.");
            }

            File headerFile = Files.createTempFile("header", ".png").toFile();
            File footerFile = Files.createTempFile("footer", ".png").toFile();

            Files.copy(imageStreamHeader, headerFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            Files.copy(imageStreamFooter, footerFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

            header.attachFile(headerFile);
            header.setContentID("<header>");
            header.setDisposition(MimeBodyPart.INLINE);

            footer.attachFile(footerFile);
            footer.setContentID("<footer>");
            footer.setDisposition(MimeBodyPart.INLINE);

            MimeBodyPart body = new MimeBodyPart();
            body.setContent(
                    String.format("<img src='cid:header'>" +
                            "<br<br>> %s <br><br>" +
                            "<img src='cid:footer'>", bodyText),
                    "text/html"
            );

            MimeBodyPart text = new MimeBodyPart();
            text.setText(bodyText, "utf-8");

            Multipart fullBody = new MimeMultipart("alternative");
            fullBody.addBodyPart(text);
            fullBody.addBodyPart(header);
            fullBody.addBodyPart(footer);
            fullBody.addBodyPart(body);

            message.setContent(fullBody);

            // Excluindo arquivos temporarios
            headerFile.deleteOnExit();
            footerFile.deleteOnExit();

        } catch (Exception e){
            throw new RuntimeException("Erro na criação do conteúdo do email",e);
        }

        System.out.println("Email enviado para (" + destinatario + ")");
        return message;
    }


    public static void main(String[] args) {
        String seuEmail = "";
        if(seuEmail.equals("")){
            Scanner sc = new Scanner(System.in);
            System.out.println("Digite o email do destinatário:");
            seuEmail = sc.nextLine();
        }

        EmailApiService service = new EmailApiService();
        String body = "<br>Teste<br><br>Teste de email concluido com sucesso.<br>";
        service.enviarEmail(seuEmail,"Teste" + LocalDateTime.now(), body);
    }
}
