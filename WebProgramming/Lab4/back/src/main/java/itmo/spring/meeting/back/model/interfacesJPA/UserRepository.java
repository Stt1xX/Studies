package itmo.spring.meeting.back.model.interfacesJPA;

import itmo.spring.meeting.back.model.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    int countUserByUsernameAndHash(String username, byte[] hash);
    User getUserByUsernameAndHash(String username, byte[] hash);

    User getUserByUsername(String username);
    int countUserByUsernameIs(String username);
}
