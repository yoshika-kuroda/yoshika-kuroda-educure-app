package jp.educure.wordbook_app.controller;

import org.springframework.stereotype.Controller;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ui.Model;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import jp.educure.wordbook_app.entity.Word;
import jp.educure.wordbook_app.service.CategoryService;
import jp.educure.wordbook_app.service.WordService;
import jp.educure.wordbook_app.entity.Category;




@Controller
@RequiredArgsConstructor
@RequestMapping("/words")
public class WordController {

    private final CategoryService categoryService;
    private final WordService wordService;
    @GetMapping
    public String list(
        @RequestParam(name = "keyword",required = false) String keyword,
        @RequestParam(name = "categoryId",required = false) Long categoryId,
        @RequestParam(name = "sort",required = false,defaultValue = "created")String sort, 
        Model model){
        List <Word> words = wordService.findAllWord(keyword,categoryId,sort);
        model.addAttribute("words", words);
        model.addAttribute("keyword", keyword);
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("sort", sort);

        model.addAttribute("categories",categoryService.findAllCategory());
        return "words/list";
    }

    @GetMapping("/{id}/detail")
    public String detail(@PathVariable("id")Long id,Model model,RedirectAttributes attributes){
        Word word = wordService.findByIdWord(id);
        if(word == null) {
            attributes.addAttribute("errorMessage","登録されている単語がありません");
            return "redirect:/words";
        }
            model.addAttribute("word", wordService.findByIdWord(id));
            return "words/detail";
           
    }

    @GetMapping("/form")
    public String newWord(Model model){
        model.addAttribute("word",new Word());
        model.addAttribute("mode", "create");
        model.addAttribute("categories", categoryService.findAllCategory());
        return "words/form";
    }

    @PostMapping("/save")
    public String create(@Valid @ModelAttribute ("word")Word word,BindingResult result,RedirectAttributes attributes,Model model){
        if(result.hasErrors()){
            return "words/form";
        }
        wordService.create(word);
        attributes.addFlashAttribute("message","登録しました");
        return "redirect:/words";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id")Long id,Model model){
        Word word = wordService.findByIdWord(id);
        List <Category> categories = categoryService.findAllCategory();
        model.addAttribute("word",word);
        model.addAttribute("categories", categories);
        model.addAttribute("mode", "edit");
        return "words/form";
    }

    @PostMapping("/{id}")
    public String update(@Valid @ModelAttribute ("word")Word word,BindingResult result,RedirectAttributes attributes,Model model){
        if(result.hasErrors()){
            return "words/form";
        }
        wordService.update(word);
        attributes.addFlashAttribute("message","更新しました");
        return "redirect:/words";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id")Long id,RedirectAttributes attributes){
        wordService.delete(id);
        attributes.addFlashAttribute("message","削除しました");
        return "redirect:/words";
    }

    @PostMapping("/import")
    public String importCsv(@RequestParam("csvFile") MultipartFile csvFile,RedirectAttributes attributes){ 
        if(csvFile.isEmpty()) {
            attributes.addFlashAttribute("errorMessage","ファイルを選択してください");
            return "redirect:/words"; 
        }
        try {
            wordService.importCsv(csvFile);
            attributes.addFlashAttribute("message","インポートしました");
        } catch (Exception e) {
            attributes.addFlashAttribute("errorMessage","インポートに失敗しました");
            e.printStackTrace();
        }
        return "redirect:/words";
    }

    @GetMapping("/export")
    public void exportCsv(HttpServletResponse response,RedirectAttributes attributes)throws IOException {
        response.setContentType("text/csv;charset=UTF-8");
        response.setHeader("Content-Disposition","attachment;filename=words.csv");
        response.setCharacterEncoding("UTF-8");

        List<Word> words = wordService.findAllWord(null,null,"created");

        /*response.getOutputStream().write(0xEF);
        response.getOutputStream().write(0xBB);
        response.getOutputStream().write(0xBF);*/
        
        try(PrintWriter writer = response.getWriter()){
            writer.write('\uFEFF');

            writer.println("単語,意味,カデゴリ");

            for(Word word : words){
                String categories = word.getCategoryNames() != null
                       ? word.getCategoryNames().replace(",", "|")
                       : "";
                writer.printf("%s,%s,%s%n",
                        word.getWord(),
                        word.getMeaning(),
                        categories);
            }
        }catch(IOException e){
            attributes.addFlashAttribute("errorMessage","エクスポートに失敗しました");
        }
    }
    private String escapeCsv(String value){
        if(value == null) return "";
        if(value.contains(",")||value.contains("\"")||value.contains("\n")) {
            return "\""+value + "\"";   
        }
        return value;
    }
}
