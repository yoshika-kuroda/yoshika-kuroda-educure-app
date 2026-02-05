package jp.educure.wordbook_app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import jp.educure.wordbook_app.entity.Category;

@Mapper
public interface CategoryMapper {
    
    List<Category> findAllCategory();
    List<String> findCategoryNames(Long WordId);
    Category findByIdCategory(Long id);
    void insert(Category category);
    void update(Category category);
    void delete(Long id);
    List<String> findCategoryNamesByWordId(Long wordId);
    Long findIdByCategoryName(String categoryName);
    void insertCategoryName(Category category);
}
