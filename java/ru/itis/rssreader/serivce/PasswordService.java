package ru.itis.rssreader.serivce;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class PasswordService {

    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public boolean checkPassword(String password, String storedHash) {
        return BCrypt.checkpw(password, storedHash);
    }
}