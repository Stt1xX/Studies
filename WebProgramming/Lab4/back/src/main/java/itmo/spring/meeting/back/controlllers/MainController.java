package itmo.spring.meeting.back.controlllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import itmo.spring.meeting.back.model.entities.Attempt;
import itmo.spring.meeting.back.model.entities.User;
import itmo.spring.meeting.back.model.managers.AttemptDataManager;
import itmo.spring.meeting.back.model.managers.UserDataManager;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
    public String sentForm(@RequestHeader("Cookie") String cookies, @RequestBody String jsonAttempt) throws JsonProcessingException {
        Attempt attempt = objectMapper.readValue(jsonAttempt, Attempt.class);
        if(attempt.checkIsValid()){
            attempt.setValues();
            attempt.setOwner(
                    this.userDataManager.getUserByUsernameAndPassword(parseUsername(cookies), parsePassword(cookies)));
            this.attemptDataManager.save(attempt);
        }
        else
            attempt = Attempt.ERROR_ATTEMPT;
        return objectMapper.writeValueAsString(attempt);
    }

    @ResponseBody
    @GetMapping("api-receiveAttempts")
    public String receiveAttempts(@RequestHeader("Cookie") String cookies) throws JsonProcessingException {
        return objectMapper.writeValueAsString(this.attemptDataManager.getByCurrentUser
                (this.userDataManager.getUserByUsernameAndPassword(parseUsername(cookies), parsePassword(cookies))));
    }

    @ResponseBody
    @PostMapping("api-signUp")
    public void signUp(@RequestBody String jsonUser) throws JsonProcessingException{
        User user = objectMapper.readValue(jsonUser, User.class);
        if (this.userDataManager.checkSameUser(user)){
            this.userDataManager.save(user);
        } else{
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Same user");
        }
    }

    @ResponseBody
    @PostMapping("api-signIn")
    public void trySignIn(@RequestHeader("Cookie") String cookies) {
        // The qualitative search to ensure that we get the values we need and not, for example, part of the password
        String login = parseUsername(cookies), password = parsePassword(cookies);
        if(login != null && password != null){
            if (!userDataManager.authorization(new User(login, password))){
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No auth");
            }
        } else{
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No auth");
        }
    }

    public String parseUsername(String cookies){
        String[] cookiesList = cookies.split("; ");
        String login = null;
        for (String item : cookiesList) {
            if (item.matches("^Login=(.*)")) {
                login = item.replace("Login=", "");
            }
        }
        if (login == null){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Unauthorized entry");
        }
        return login;
    }

    public String parsePassword(String cookies){
        String[] cookiesList = cookies.split("; ");
        String password = null;
        for (String item : cookiesList) {
            if (item.matches("^Password=(.*)")){
                password = item.replace("Password=", "");
            }
        }
        if (password == null){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Unauthorized entry");
        }
        return password;
    }
}
