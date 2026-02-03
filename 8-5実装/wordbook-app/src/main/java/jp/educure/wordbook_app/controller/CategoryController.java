package jp.educure.wordbook_app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

import jp.educure.wordbook_app.entity.Category;
import jp.educure.wordbook_app.service.CategoryService;

@Controller
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public String list (Model model){
        model.addAttribute("categories",categoryService.findAllCategory());
        return "categories/list";
    }

    @GetMapping("/{id}/detail")
    public String detail(@PathVariable("id")Long id,RedirectAttributes attributes,Model model){
        Category category = categoryService.findByIdCategory(id);
        if(category == null) {
            model.addAttribute("category", categoryService.findByIdCategory(id));
            return "categories/detail";
        }else{
            attributes.addAttribute("errorMassage","登録されているカテゴリがありません");
            return "redirect:/categories/list";
        }
    }

    @GetMapping("/form")
    public String newCategory(Model model){
        model.addAttribute("category",new Category());
        model.addAttribute("mode", "create");
        return "categories/form";
    }

    @PostMapping("/save")
    public String create(@Valid @ModelAttribute ("category")Category category,BindingResult result,RedirectAttributes attributes,Model model){
        if(result.hasErrors()){
            return "categories/form";
        }
        categoryService.create(category);
        attributes.addFlashAttribute("message","登録しました");
        return "redirect:/categories";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id")Long id,Model model){
        model.addAttribute("category",categoryService.findByIdCategory(id));
        model.addAttribute("mode", "edit");
        return "categories/form";
    }

    @PostMapping("/{id}")
    public String update(@Valid @ModelAttribute ("category")Category category,BindingResult result,RedirectAttributes attributes,Model model){
        if(result.hasErrors()){
            return "categories/form";
        }
        categoryService.update(category);
        attributes.addFlashAttribute("message","更新しました");
        return "redirect:/categories";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id")Long id,RedirectAttributes attributes){
        categoryService.delete(id);
        attributes.addFlashAttribute("message","削除しました");
        return "redirect:/categories";
    }
}
