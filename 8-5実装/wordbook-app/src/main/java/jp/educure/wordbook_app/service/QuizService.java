package jp.educure.wordbook_app.service;

import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Service;


import jp.educure.wordbook_app.entity.Word;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class QuizService {
    private final WordService wordService;
    private List<Word>quizWords;
    private int currentIndex;
        
    public void startQuiz(int limit){
        List<Word>words = wordService.findAllWord(null,null,null);
        Collections.shuffle(words);
        
        if(words.size()>limit){
            this.quizWords=words.subList(0,limit);
        }else{
            this.quizWords=words;
        }
        this.currentIndex=0;
    }
        

    public Word getNextQuestion(){
        if(currentIndex >10 || currentIndex >= quizWords.size()){
            return null;
        }
        return quizWords.get(currentIndex++);
    }

    public String getAnswer(){
        if(currentIndex ==0 || quizWords == null){
            return null;
        }
        return quizWords.get(currentIndex-1).getMeaning();
    }


    public void reset(){
        quizWords = null;
        currentIndex = 0;
    }

    public boolean isFinished(){
        return currentIndex > 10 || quizWords == null || currentIndex >= quizWords.size();
    }

    public Word getCurrentWord(){
        if(quizWords == null || currentIndex == 0){
            return null;
        }
        return quizWords.get(currentIndex-1);
    }
}     
