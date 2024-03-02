package itmo.spring.meeting.back.model.managers;

import itmo.spring.meeting.back.model.HashManager;
import itmo.spring.meeting.back.model.entities.User;
import itmo.spring.meeting.back.model.interfacesJPA.UserRepository;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;

import java.security.NoSuchAlgorithmException;

@Component
@ApplicationScope
public class UserDataManager {

    final UserRepository userRepository;

    public UserDataManager(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void save(User user){
        this.userRepository.save(user);
    }

    public boolean authorization(String username, String password) throws NoSuchAlgorithmException {
        User user = this.userRepository.getUserByUsername(username);
        if (user == null) return false;
        byte[] hash = HashManager.getHashFromPassword(password, user.getSalt());
        return this.userRepository.countUserByUsernameAndHash(user.getUsername(), hash) == 1;
    }

    public boolean checkSameUser(User user){
        return this.userRepository.countUserByUsernameIs(user.getUsername()) == 0;
    }

    public User getUserByUsernameAndPassword(String username, String password) throws NoSuchAlgorithmException {
        User user = this.userRepository.getUserByUsername(username);
        byte[] hash = HashManager.getHashFromPassword(password, user.getSalt());
        return this.userRepository.getUserByUsernameAndHash(user.getUsername(), hash);
    }

}
