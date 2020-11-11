package ru.school21.matcha.controller;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.school21.matcha.dto.Response;
import ru.school21.matcha.entity.User;
import ru.school21.matcha.exception.UserAlreadyExistException;
import ru.school21.matcha.service.UserAccountService;

import javax.mail.AuthenticationFailedException;
import javax.validation.Valid;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/account")
public class UserAccountController {

    private final UserAccountService service;

    public UserAccountController(UserAccountService service) {
        this.service = service;
    }

    @PostMapping("/registration")
    public Response registerNewUserAccount(@RequestBody @Valid User user) throws UserAlreadyExistException {
        User registered = service.registerNewUserAccount(user);
        return new Response(Collections.singletonList("User: " + registered.getUsername() + " registered successfully"), true);
    }

    @RequestMapping(value="/confirm", method= {RequestMethod.GET, RequestMethod.POST})
    public Response confirmUserAccount(@RequestParam("token") String token) {
        String email = service.verifyAccountByEmail(token);
        if (email != null) {
            return new Response(Collections.singletonList("Email address: " + email + " confirm"), true);
        } else {
            return new Response(Collections.singletonList("The link is invalid or broken!"), false);
        }
    }

    @PostMapping("/changepassword")
    public Response changePassword(@RequestParam("password") String password, @RequestParam("newPassword") String newPassword) {
        service.changePassword(password, newPassword);
        return new Response(Collections.singletonList("Password changed"), true);
    }

    @PostMapping("/changeemail")
    public Response changeEmail(@RequestParam("newEmail") String newEmail, @RequestParam("password") String password) {
        service.changePassword(newEmail, password);
        return new Response(Collections.singletonList("Email address changed"), true);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, UserAlreadyExistException.class, InvalidParameterException.class,
            AuthenticationFailedException.class, UsernameNotFoundException.class})
    public Response handleException(Exception e) {
        List<String> messages = new ArrayList<>();
        if (e instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException methodException = (MethodArgumentNotValidException) e;
            BindingResult bindingResult = methodException.getBindingResult();
            bindingResult.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage)
                    .forEach(messages::add);
            bindingResult.getGlobalErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .forEach(messages::add);
        } else {
            messages.add(e.getMessage());
        }
        return new Response(messages, false);
    }
}
