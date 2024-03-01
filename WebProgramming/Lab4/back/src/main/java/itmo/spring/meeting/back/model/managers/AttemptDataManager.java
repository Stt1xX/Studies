package itmo.spring.meeting.back.model.managers;

import itmo.spring.meeting.back.model.entities.Attempt;
import itmo.spring.meeting.back.model.interfacesJPA.AttemptRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;

import java.util.Collections;
import java.util.List;

@Component
@ApplicationScope
public class AttemptDataManager {
    private final AttemptRepository attemptRepository;

    public AttemptDataManager(AttemptRepository attemptRepository) {
        this.attemptRepository = attemptRepository;
    }

    public void save(Attempt attempt){
        this.attemptRepository.save(attempt);
    }

    public List<Attempt> get(){
        List<Attempt> list = (List<Attempt>) this.attemptRepository.findAll();
        Collections.reverse(list);
        return list;
    }
}
