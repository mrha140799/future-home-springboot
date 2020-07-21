package vn.edu.hnue.backend.service.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import vn.edu.hnue.backend.model.dto.UserDTO;
import vn.edu.hnue.backend.repository.VerificationTokenRepository;
import vn.edu.hnue.backend.service.email.EmailSenderService;

import java.util.concurrent.ExecutionException;

@Service
public class AsyncService {
    private static Logger log = LoggerFactory.getLogger(AsyncService.class);

    @Autowired
    private EmailSenderService emailSenderService;
    @Autowired
    private VerificationTokenRepository verificationTokenRepository;
    @Value("${url.path}")
    private String urlPath;
    @Async("asyncEmailExecutor")
    public void waitMilliSecondToSendEmail(UserDTO userRegisterFormRequest, long ms) throws InterruptedException, ExecutionException {
        log.info("Send email starts");
        Thread.sleep(ms);
        String text = "Hello "+ userRegisterFormRequest.getFullName()+"!\nTo confirm your account, please click here : \n"+urlPath+"/v1/auth/confirm-email/token=" + verificationTokenRepository.findByAccount_Email(userRegisterFormRequest.getEmail()).getToken()+"\n\nThank you!";
        log.info(emailSenderService.sendEmail(userRegisterFormRequest.getEmail(), "Confirm Register!", text).get());
        log.info("Send email  completed");
    }

}
