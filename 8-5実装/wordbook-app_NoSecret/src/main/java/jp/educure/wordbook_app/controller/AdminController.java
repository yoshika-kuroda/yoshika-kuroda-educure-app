package jp.educure.wordbook_app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jp.educure.wordbook_app.service.UserService;
import jp.educure.wordbook_app.entity.User;


@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class AdminController {
    private final UserService userService;
    @GetMapping("/list")
    public String list (@AuthenticationPrincipal OAuth2User user,Model model){
        String email = user.getAttribute("email");
        if (!email.equals("ysk1020svtlj@gmail.com")) {
            return "redirect:/home";  
        }else{
            model.addAttribute("users",userService.findAllUser());
            return "users/list";
        }    
    }

    @GetMapping("/{id}/detail")
    public String detail(@PathVariable("id")Long id,Model model,RedirectAttributes attributes){
        User user = userService.findByIdUser(id);
        if(user != null) {
            model.addAttribute("user",userService.findByIdUser(id));
            return "users/detail";
        }else{
            attributes.addAttribute("errorMassage","登録されているユーザーがいません");
            return "redirect:/users/list";
        }
    }    

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id")Long id,RedirectAttributes attributes){
        userService.delete(id);
        attributes.addFlashAttribute("message","削除しました");
        return "redirect:/users/list";
    }
}
