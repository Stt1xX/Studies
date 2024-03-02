package itmo.spring.meeting.back.model.interfacesJPA;

import itmo.spring.meeting.back.model.entities.Attempt;
import itmo.spring.meeting.back.model.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AttemptRepository extends CrudRepository<Attempt, Long> {
    List<Attempt> findAllByOwner(User owner);
}
