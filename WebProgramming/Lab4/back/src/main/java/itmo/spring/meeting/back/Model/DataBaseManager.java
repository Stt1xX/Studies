package itmo.spring.meeting.back.Model;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@ApplicationScope
public class DataBaseManager{
    private final AttemptRepository repository;

    public DataBaseManager(AttemptRepository repository) {
        this.repository = repository;
    }

    public void save(Attempt attempt){
        this.repository.save(attempt);
    }

    public List<Attempt> get(){
        List<Attempt> list = (List<Attempt>) this.repository.findAll();
        Collections.reverse(list);
        return list;
    }
}
