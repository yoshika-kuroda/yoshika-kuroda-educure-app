package jp.educure.wordbook_app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import org.springframework.web.multipart.MultipartFile;
import java.util.ArrayList;

import jp.educure.wordbook_app.mapper.CategoryMapper;
import jp.educure.wordbook_app.mapper.WordMapper;
import jp.educure.wordbook_app.entity.Word;
import jp.educure.wordbook_app.entity.Category;


@Service
@RequiredArgsConstructor
public class WordService {
    private final WordMapper wordMapper;
    private final CategoryMapper categoryMapper;

    public List<Word>findAllWord(String keyword,Long categoryId,String sort){
        List<Word>words = wordMapper.findAllWord(keyword,categoryId,sort);
        for(Word word : words){
            List <String> categoryList = categoryMapper.findCategoryNamesByWordId(word.getWordId());
            word.setCategoryNames(String.join(",",categoryList));
        }
        return words;
    }

    public Word findByIdWord(Long wordId){
        Word word = wordMapper.findByIdWord(wordId);
        if(word == null){
            return null;
        }
        List <Long> categoryIds = wordMapper.findCategoryIdsByWordId(wordId);
        word.setCategoryIds(categoryIds);
        List <String> categoryNames = categoryMapper.findCategoryNamesByWordId(wordId);
        word.setCategoryNames(String.join(",",categoryNames));
        
        return word;
    }

    public void create (Word word){
        wordMapper.insert(word);
        if (word.getCategoryIds() != null) {
            for(Long categoryId : word.getCategoryIds()){
                wordMapper.insertWordCategory(word.getWordId(),categoryId);
            }  
        }
    }

    public void update(Word word){
        wordMapper.update(word);
        wordMapper.deleteWordCategoriesByWordId(word.getWordId());
        if(word.getCategoryIds() !=  null){
            for(Long categoryId : word.getCategoryIds()){
                wordMapper.insertWordCategory(word.getWordId(),categoryId);
            }
        }
    }

    public void delete(Long id){
        wordMapper.deleteWordCategoriesByWordId(id);
        wordMapper.delete(id);
    } 
    
    public void importCsv(MultipartFile csvFile) throws Exception{
        try(
            BufferedReader br = new BufferedReader(new InputStreamReader(csvFile.getInputStream(),StandardCharsets.UTF_8))
           ){
            String line;
            boolean isFirst = true;
            while((line = br.readLine())!= null){
                if(isFirst){
                    line = line.replace("\uFEFF", "");
                    isFirst = false;
                    continue;
                }
                String[]data = line.split(",");
                if(data.length < 2) continue;

                Word word = new Word();
                word.setWord(data[0].trim());
                word.setMeaning(data[1].trim());
                word.setCreatedAt(LocalDateTime.now());
                word.setUpdatedAt(LocalDateTime.now());

                wordMapper.insert(word);

                if(data.length >= 3 && !data[2].trim().isEmpty()){
                    String[]categories = data[2].split("\\|");
                    for(String categoryName : categories){
                        categoryName = categoryName.trim();

                        Long categoryId = categoryMapper.findIdByCategoryName(categoryName);
                
                        if(categoryId == null){
                            Category category = new Category();
                            category.setCategoryName(categoryName);
                            categoryMapper.insertCategoryName(category);
                            categoryId = category.getCategoryId();
                        }
                        wordMapper.insertWordCategory(word.getWordId(),categoryId);
                    }
                }
            }
        }
    }
}               

