package ru.itis.rssreader.serivce;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.rssreader.entity.User;
import ru.itis.rssreader.exception.AuthException;
import ru.itis.rssreader.repository.UserRepository;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordService passwordService;

    @Autowired
    public UserService(UserRepository userRepository, PasswordService passwordService) {
        this.userRepository = userRepository;
        this.passwordService = passwordService;
    }

    public void register(String username, String password) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new AuthException("Пользователь с таким именем уже существует");
        }

        User user = User.builder()
                .username(username)
                .passwordHash(passwordService.hashPassword(password))
                .build();

        userRepository.save(user);

    }

    public User login(String username, String password) {

        Optional<User> userOpt = userRepository.findByUsername(username);

        if (userOpt.isEmpty()) {
            throw new  AuthException("Пользователь не найден");
        }

        if ( passwordService.checkPassword(password, userOpt.get().getPasswordHash())) {
            return userOpt.get();

        } else {
            throw new AuthException("Неверный пароль");
        }
    }
}