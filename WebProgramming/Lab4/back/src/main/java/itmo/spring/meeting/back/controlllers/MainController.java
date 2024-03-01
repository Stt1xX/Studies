package itmo.spring.meeting.back.controlllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import itmo.spring.meeting.back.model.entities.Attempt;
import itmo.spring.meeting.back.model.entities.User;
import itmo.spring.meeting.back.model.managers.AttemptDataManager;
import itmo.spring.meeting.back.model.managers.UserDataManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin
@RequestMapping("/")
public class MainController {

    private final AttemptDataManager attemptDataManager;
    private final UserDataManager userDataManager;

    private static final ObjectMapper objectMapper = new ObjectMapper();
    public MainController(AttemptDataManager attemptDataManager, UserDataManager userDataManager) {
        this.attemptDataManager = attemptDataManager;
        this.userDataManager = userDataManager;
    }

    @GetMapping({"main", "", "login", "signUp"})
    public String getIndexPage(){
        return "index.html";
    }

    @ResponseBody
    @PostMapping("api-sentForm")
    public String sentForm(@RequestBody String jsonAttempt) throws JsonProcessingException {
        Attempt attempt = objectMapper.readValue(jsonAttempt, Attempt.class);
        if(attempt.checkIsValid()){
            attempt.setValues();
            this.attemptDataManager.save(attempt);
        }
        else
            attempt = Attempt.ERROR_ATTEMPT;
        return objectMapper.writeValueAsString(attempt);
    }

    @ResponseBody
    @GetMapping("api-receiveAttempts")
    public String receiveAttempts() throws JsonProcessingException {
        return objectMapper.writeValueAsString(this.attemptDataManager.get());
    }

    @ResponseBody
    @PostMapping("api-signUp")
    public void signUp(@RequestBody String jsonUser) throws JsonProcessingException{
        User user = objectMapper.readValue(jsonUser, User.class);
        this.userDataManager.save(user);
    }

//
//    @ResponseBody
//    @PostMapping("api-signIn")
//    public String trySignIn(@RequestBody )
}
