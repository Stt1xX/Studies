package itmo.spring.meeting.back.model.interfacesJPA;

import itmo.spring.meeting.back.model.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    int countUserByUsernameIsAndPasswordIs(String username, String password);

    int countUserByUsernameIs(String username);
}
