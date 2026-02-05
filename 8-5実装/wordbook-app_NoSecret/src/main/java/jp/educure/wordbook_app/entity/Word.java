package jp.educure.wordbook_app.entity;

import java.time.LocalDateTime;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Word {
    private Long wordId;
    private Long userId;
    @NotBlank(message = "単語を入力してください")
    private String word;
    @NotBlank(message = "意味を入力してください")
    private String meaning;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private List<Long> categoryIds;
    private String categoryNames;
}
