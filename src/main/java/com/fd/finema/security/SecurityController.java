package com.fd.finema.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/logIn")
    public UserDTO generateToken(@RequestBody AuthRequest authRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
        } catch (Exception ex) {
            throw new Exception("inavalid email/password");
        }
        UserDTO user  = new UserDTO();
        user.setToken(jwtUtil.generateToken(authRequest.getEmail()));
        user.setEmail(authRequest.getEmail());
        User user1 = userRepo.findByEmail(authRequest.getEmail());
        user.setAdmin(user1.getAdmin());
        return user;
    }

    @PostMapping("/signUp")
    public UserDTO	 signUp(@RequestBody AuthRequest authRequest) throws Exception {
        Matcher emailMatcher = emailPattern.matcher(authRequest.getEmail());
        Matcher passwordMatcher = passwordPattern.matcher(authRequest.getPassword());
        if(emailMatcher.find() && passwordMatcher.find()) {
            userRepo.saveAndFlush(new User(authRequest.getEmail(), encoder.encode(authRequest.getPassword()),false));
            return generateToken(authRequest);
        }
        throw new Exception("invalid email/password");
    }
}