package jp.educure.wordbook_app;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("jp.educure.wordbook_app.mapper")
@SpringBootApplication
public class WordbookAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(WordbookAppApplication.class, args);
	}

}
