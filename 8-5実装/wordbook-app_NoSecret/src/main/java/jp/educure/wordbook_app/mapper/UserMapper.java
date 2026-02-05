package jp.educure.wordbook_app.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;

import jp.educure.wordbook_app.entity.User;

@Mapper
public interface UserMapper {
    List<User> findAllUser();
    User findByIdUser(Long id);
    void insert(User user);
    User findByEmail(String email);
    void delete(Long id);
}
