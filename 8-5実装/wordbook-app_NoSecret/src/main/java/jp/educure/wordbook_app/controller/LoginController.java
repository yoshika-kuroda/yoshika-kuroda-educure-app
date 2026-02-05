package jp.educure.wordbook_app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Optional;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;

import jp.educure.wordbook_app.service.UserService;
import jp.educure.wordbook_app.entity.User;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;
    @GetMapping("/")
    public String top(){
        return "top";
    }

    @GetMapping("/login/success")
    public String loginSucess (@AuthenticationPrincipal OAuth2User user,Model model){
        String email = user.getAttribute("email");
        String name = user.getAttribute("name");

        boolean isAdmin = "ysk1020svtlj@gmail.com".equals(user.getAttribute("email"));
    
        String role = "USER";
        if(isAdmin){
            role = "ADMIN";
        }
        User loginUser = userService.findOrCreate(email,name,role); 
        
        if(isAdmin){
            return "redirect:/users/list";
        }
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String home(@AuthenticationPrincipal OAuth2User user,Model model){
        if(user != null) {
            String name = user.getAttribute("name");
            model.addAttribute("name",name);
        }else{
            model.addAttribute("name","ゲスト");
        }
        return "home";
    }
}

