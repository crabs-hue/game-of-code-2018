package lu.arhs.cube.gameofcode.projectcodeundefined.controllers;

import lu.arhs.cube.gameofcode.projectcodeundefined.models.ValueBean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

    @RequestMapping("/user")
    public ValueBean getConnectedUserName() {
        return new ValueBean("game-of-code-2018");
    }

}
