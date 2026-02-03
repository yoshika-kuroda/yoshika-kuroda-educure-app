package jp.educure.wordbook_app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

import jp.educure.wordbook_app.service.QuizService;
import jp.educure.wordbook_app.entity.Word;

@Controller
@AllArgsConstructor
@RequestMapping("/quiz")
public class QuizController {
    private final QuizService quizService;
    
    @GetMapping("")
    public String quizTop(){
        quizService.reset();
        return "quiz/quiz-top";
    }
    
    @GetMapping("/start")
    public String startQuiz(Model model){
        quizService.startQuiz(10);
        Word word = quizService.getNextQuestion();
        if(word == null){
            model.addAttribute("question","登録されている単語がありません");
            model.addAttribute("mode","finish");
        }else{
            model.addAttribute("question",word.getWord());
            model.addAttribute("mode","question");
        }    
        return "quiz/question";
    }

    @GetMapping("/answer")
    public String getAnswer(Model model){
        Word word = quizService.getCurrentWord();
        model.addAttribute("question",word.getWord());
        model.addAttribute("answer",word.getMeaning());
        model.addAttribute("mode", "answer");
    
        return "quiz/question";
    }

    @GetMapping("/next")
    public String getNextQuestion(Model model){
        Word word = quizService.getNextQuestion();
        if(word == null){
            model.addAttribute("question","出題終了!");
            model.addAttribute("mode","finish");
        }else{
            model.addAttribute("question",word.getWord());
            model.addAttribute("mode","question");
        }    
        return "quiz/question";
    }        
}
