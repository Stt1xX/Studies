package itmo.spring.meeting.back.model.interfacesJPA;

import itmo.spring.meeting.back.model.entities.Attempt;
import org.springframework.data.repository.CrudRepository;

public interface AttemptRepository extends CrudRepository<Attempt, Long> {
}
