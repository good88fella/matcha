package ru.school21.matcha.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.school21.matcha.entity.User;
import ru.school21.matcha.exception.UserAlreadyExistException;

public interface UserAccountService extends UserDetailsService {

    User registerNewUserAccount(User user) throws UserAlreadyExistException;

    String verifyAccountByEmail(String token);

    void changeEmail(String newEmail, String password);

    void changePassword(String password, String newPassword);
}
