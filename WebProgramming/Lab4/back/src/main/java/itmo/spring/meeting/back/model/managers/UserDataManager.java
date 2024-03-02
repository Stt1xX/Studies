package itmo.spring.meeting.back.model.managers;

import itmo.spring.meeting.back.model.entities.User;
import itmo.spring.meeting.back.model.interfacesJPA.UserRepository;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;

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

    public boolean authorization(User user){
        return this.userRepository.countUserByUsernameIsAndPasswordIs(user.getUsername(), user.getPassword()) == 1;
    }

    public boolean checkSameUser(User user){
        return this.userRepository.countUserByUsernameIs(user.getUsername()) == 0;
    }

}
