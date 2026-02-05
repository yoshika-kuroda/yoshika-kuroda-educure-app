package jp.educure.wordbook_app.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import jakarta.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class Category {
    private Long categoryId;
    @NotNull(message = "カテゴリ名を入力してください")
    private String categoryName;
}
