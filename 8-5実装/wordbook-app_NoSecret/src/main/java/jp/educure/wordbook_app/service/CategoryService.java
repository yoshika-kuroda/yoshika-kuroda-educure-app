package jp.educure.wordbook_app.service;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.util.List;

import jp.educure.wordbook_app.mapper.CategoryMapper;
import jp.educure.wordbook_app.entity.Category;
import jp.educure.wordbook_app.mapper.WordMapper;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryMapper categoryMapper;
    private final WordMapper wordMapper;
    public List<Category> findAllCategory(){
        return categoryMapper.findAllCategory();
    }

    public List <String> findCategoryNamesByWordId(Long WordId){
        return categoryMapper.findCategoryNames(WordId);
    }

    public Category findByIdCategory(Long id){
        return categoryMapper.findByIdCategory(id);
    }

    public void create(Category category){
        categoryMapper.insert(category);
    }

    public void update(Category category){
        categoryMapper.update(category);
    }

    public void delete(Long id){
        categoryMapper.delete(id);
    }
}
