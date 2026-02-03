package jp.educure.wordbook_app.controller;

import org.springframework.stereotype.Controller;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class LogoutController {
    @GetMapping("/logout/google")
    public String logout(){
        return "redirect:https://accounts.google.com/Logout"
            + "?continue=https://appengine.google.com/_ah/logout"
            + "%3Fcontinue=http://localhost:8080/";
    }
}
