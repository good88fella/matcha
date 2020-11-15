package ru.school21.matcha.service;

import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.school21.matcha.entity.Role;
import ru.school21.matcha.entity.User;
import ru.school21.matcha.entity.VerificationToken;
import ru.school21.matcha.exception.UserAlreadyExistException;
import ru.school21.matcha.repository.UserRepository;
import ru.school21.matcha.repository.VerificationTokenRepository;

import javax.servlet.http.HttpSession;
import java.security.InvalidParameterException;
import java.util.Collections;

@Service
public class UserAccountServiceImpl implements UserAccountService {

    @Value("${server.url}")
    private String url;

    private final UserRepository userRepository;
    private final VerificationTokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailSenderService emailSenderService;

    public UserAccountServiceImpl(UserRepository userRepository, VerificationTokenRepository tokenRepository,
                                  PasswordEncoder passwordEncoder, EmailSenderService emailSenderService) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailSenderService = emailSenderService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }

    @Transactional
    @Override
    public User registerNewUserAccount(User user) throws UserAlreadyExistException {
        if (userNameExist(user.getUsername())) {
            throw new UserAlreadyExistException("Account already exist with that user name: " + user.getUsername());
        }
        if (emailExist(user.getEmail())) {
            throw new UserAlreadyExistException("Account already exist with that email address: " + user.getEmail());
        }
        if (!user.getPassword().equals(user.getMatchingPassword())) {
            throw new InvalidParameterException("Passwords don't match");
        }
        correctUserParams(user);
        User saved = userRepository.save(user);
        sendEmail(saved);
        return saved;
    }

    private boolean userNameExist(String username) {
        return userRepository.findByUsername(username) != null;
    }

    private boolean emailExist(String email) {
        return userRepository.findByEmail(email) != null;
    }

    private void correctUserParams(User user) {
        user.setUsername(user.getUsername().toLowerCase());
        user.setEmail(user.getEmail().toLowerCase());
        user.setAuthorities(Collections.singleton(new Role("ROLE_USER")));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    }

    private void sendEmail(User user) {
        VerificationToken token = new VerificationToken(user);
        tokenRepository.save(token);
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Complete Registration!");
        mailMessage.setFrom("matcha21@gmail.com");
        mailMessage.setText("To confirm your account, please click here : "
                + url + "/account/confirm?token=" + token.getToken());
        emailSenderService.sendEmail(mailMessage);
    }

    @Override
    public String verifyAccountByEmail(String token) {
        VerificationToken verificationToken = tokenRepository.findByToken(token);
        if (token != null) {
            User user = userRepository.findByEmail(verificationToken.getUser().getEmail());
            user.setEnabled(true);
            userRepository.save(user);
            return user.getEmail();
        }
        return null;
    }

    @Override
    public void changeEmail(String newEmail, String password) {
        User user = getUserFromSession();
        if (passwordEncoder.encode(password).equals(user.getPassword())) {
            user.setEmail(newEmail);
            user.setEnabled(false);
            tokenRepository.deleteByUser(user);
            userRepository.save(user);
            sendEmail(user);
        } else {
            throw new InvalidParameterException("Invalid password");
        }
    }

    @Override
    public void changePassword(String password, String newPassword) {
        User user = getUserFromSession();
        if (passwordEncoder.encode(password).equals(user.getPassword())) {
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
        } else {
            throw new InvalidParameterException("Invalid password");
        }
    }

    @Override
    public void sendPasswordRecoveryCode(HttpSession session) {
        String code = RandomString.make(4).toUpperCase();
        session.setAttribute("code", code);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(getUserFromSession().getEmail());
        mailMessage.setSubject("Code for restore password");
        mailMessage.setFrom("matcha21@gmail.com");
        mailMessage.setText("To restore password insert code: " + code + " in form");
        emailSenderService.sendEmail(mailMessage);
    }

    @Override
    public void restorePassword(String code, String newPassword, HttpSession session) {
        String sessionCode = (String) session.getAttribute("code");
        if (code.equals(sessionCode)) {
            session.removeAttribute("code");
            User user = getUserFromSession();
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
        } else {
            throw new InvalidParameterException("Invalid code");
        }
    }

    private User getUserFromSession() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = (String) auth.getPrincipal();
        return userRepository.findByUsername(username);
    }
}
