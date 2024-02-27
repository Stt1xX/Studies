package itmo.spring.meeting.back.controlllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@CrossOrigin
@RequestMapping("/")
public class MainController {

    @ResponseBody
    @GetMapping("hello")
    public String testHello(){
        return "Test Hello!";
    }
}
