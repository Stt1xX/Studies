package itmo.spring.meeting.back.controlllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import itmo.spring.meeting.back.Model.Attempt;
import itmo.spring.meeting.back.Model.DataBaseManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin
@RequestMapping("/")
public class MainController {

    private final DataBaseManager dataBaseManager;

    public MainController(DataBaseManager dataBaseManager) {
        this.dataBaseManager = dataBaseManager;
    }

    @ResponseBody
    @PostMapping("sentForm")
    public String sentForm(@RequestBody String jsonAttempt) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Attempt attempt = objectMapper.readValue(jsonAttempt, Attempt.class);
        if(attempt.checkIsValid()){
            attempt.setValues();
            this.dataBaseManager.save(attempt);
        }
        else
            attempt = Attempt.ERROR_ATTEMPT;
        return objectMapper.writeValueAsString(attempt);
    }

    @ResponseBody
    @GetMapping("receiveAttempts")
    public String receiveAttempts() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(this.dataBaseManager.get());
    }
}
