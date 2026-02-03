package jp.educure.wordbook_app.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import lombok.RequiredArgsConstructor;

import jp.educure.wordbook_app.mapper.UserMapper;
import jp.educure.wordbook_app.entity.User;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper userMapper;

    public List<User>findAllUser(){
        return userMapper.findAllUser();
    }

    public User findByIdUser(Long id) {
        return userMapper.findByIdUser(id); 
    }

    @Transactional
    public User findOrCreate(String email,String userName,String role) {
        User user = userMapper.findByEmail(email);
        if(user == null) {
            user = new User();
            user.setEmail(email);
            user.setUserName(userName);
            user.setRole(role);

            userMapper.insert(user);
        }
        return user;
        
    }
    public void delete(Long id) {
        userMapper.delete(id);
    }    
}
