package jp.educure.wordbook_app.entity;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class User {
    private Long userId;
    private String userName;
    private String email;
    private String role;
    private LocalDateTime createdAt;
}
