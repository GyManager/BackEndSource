package org.gymanager.handler.implementation;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gymanager.handler.specification.RecoveryServiceHandler;
import org.gymanager.model.client.UsuarioRecovery;
import org.gymanager.service.specification.UsuarioService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecoveryServiceHandlerImpl implements RecoveryServiceHandler {

    private static final String ASUNTO = "GyManager reinicio de contraseña";

    @Value("${email-google-remitente}")
    private String remitente;

    @Value("${email-google-password}")
    private String password;

    @NonNull
    private UsuarioService usuarioService;

    @Override
    @Transactional
    public void resetPasswordUsuarioByMail(UsuarioRecovery usuarioRecovery) {
        var usuario = usuarioService.getUsuarioEntityByMail(usuarioRecovery.getMail());
        var nuevoPassword = (int) (Math.random() * 1_000_000);

        usuarioService.resetPasswordUsuarioById(usuario.getIdUsuario(), Integer.toString(nuevoPassword));

        var destinatario = usuario.getMail();
        var cuerpo = "Su nueva contraseña provisoria es: " + Integer.toString(nuevoPassword);

        var props = System.getProperties();
        props.put("mail.smtp.host", "smtp.gmail.com");  //El servidor SMTP de Google
        props.put("mail.smtp.user", remitente);
        props.put("mail.smtp.clave", password);    //La clave de la cuenta
        props.put("mail.smtp.auth", "true");    //Usar autenticación mediante usuario y clave
        props.put("mail.smtp.starttls.enable", "true"); //Para conectar de manera segura al servidor SMTP
        props.put("mail.smtp.port", "587"); //El puerto SMTP seguro de Google

        var session = Session.getDefaultInstance(props);
        var message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(remitente));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));   //Se podrían añadir varios de la misma manera
            message.setSubject(ASUNTO);
            message.setText(cuerpo);
            Transport transport = session.getTransport("smtp");
            transport.connect("smtp.gmail.com", remitente, password);
            transport.
                    sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (MessagingException me) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
