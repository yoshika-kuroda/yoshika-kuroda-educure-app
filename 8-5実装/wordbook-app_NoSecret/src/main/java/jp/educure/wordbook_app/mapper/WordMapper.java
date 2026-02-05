package jp.educure.wordbook_app.mapper;

import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;

import jp.educure.wordbook_app.entity.Word;

@Mapper
public interface WordMapper {
    
    List<Word> findAllWord(@Param("keyword") String keyword,
        @Param("categoryId") Long categoryId,
        @Param("sort") String sort);
    Word findByIdWord(Long id);
    void insert(Word word);
    void update(Word word);
    void delete(Long id);

    void insertWordCategory(@Param("wordId") Long wordId,@Param("categoryId") Long categoryId);
    void deleteWordCategoriesByWordId(Long wordId);
    List<Long> findCategoryIdsByWordId(Long wordId);
}
