package com.fd.finema.security;

import com.fd.finema.services.MailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@CrossOrigin(origins = "*")
public class SecurityController {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private PasswordEncoder encoder;
    private Pattern emailPattern = Pattern.compile("^([\\w-\\.]+){1,64}@([\\w&&[^_]]+){2,255}.[a-z]{2,}$");
    private Pattern passwordPattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=])(?=\\S+$).{6,}");

    @Autowired
    private MailSenderService mailSenderService;

    @PostMapping("/logIn")
    public UserDTO generateToken(@RequestBody AuthRequest authRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
        } catch (Exception ex) {
            throw new Exception("invalid email/password");
        }
        UserDTO user = new UserDTO();
        user.setToken(jwtUtil.generateToken(authRequest.getEmail()));
        user.setEmail(authRequest.getEmail());
        User user1 = userRepo.findByEmail(authRequest.getEmail());
        user.setAdmin(user1.getAdmin());
        return user;
    }

    @PostMapping("/signUp")
    public UserDTO signUp(@RequestBody AuthRequest authRequest) throws Exception {
        Matcher emailMatcher = emailPattern.matcher(authRequest.getEmail());
        Matcher passwordMatcher = passwordPattern.matcher(authRequest.getPassword());
        if (emailMatcher.find() && passwordMatcher.find()) {
            userRepo.saveAndFlush(new User(authRequest.getEmail(), encoder.encode(authRequest.getPassword()), false));
            return generateToken(authRequest);
        }
        throw new Exception("invalid email/password");
    }

    @GetMapping("/emailSend")
    public void sendResetEmail(@RequestParam String userEmail) throws Exception {
        User user = userRepo.findByEmail(userEmail);
        if (user == null) {
            throw new Exception("No user with such email found");
        }
        String encode = encoder.encode(String.format("%s%d%s", userEmail, new Random(100000).nextInt(), LocalDateTime.now().toString()));
        user.setPassResetToken(encode);
        mailSenderService.sendMail(userEmail, "Е-mail recovery mail",String.format("http://localhost:4200/resetPass?token=%s&email=%s",encode,userEmail));
        userRepo.saveAndFlush(user);
    }

    @PostMapping("/resetPassword")
    public void resetPassword(@RequestBody PassResetRequest resetRequest) throws Exception {
        Matcher emailMatcher = emailPattern.matcher(resetRequest.getEmail());
        Matcher passwordMatcher = passwordPattern.matcher(resetRequest.getPassword());
        if (emailMatcher.find() && passwordMatcher.find()) {
            User user = userRepo.findByEmailAndPassResetToken(resetRequest.getEmail(), resetRequest.getPassResetToken());
            if(user == null){
                throw  new Exception("No such user found");
            }
            user.setPassword(encoder.encode(resetRequest.getPassword()));
            user.setPassResetToken(null);
            userRepo.saveAndFlush(user);
        }else{
            throw new Exception("invalid email/token");
        }
    }


}