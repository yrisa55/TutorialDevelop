package com.techacademy.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.techacademy.entity.User;

//@SpringBootTestアノテーションにより、アプリケーションの初期化が行われる+テストコードでSpring Bootのアノテーションを使用できる
@SpringBootTest
//@Extendwithはコンストラクタ・インジェクションを行うために必要
@ExtendWith(SpringExtension.class)
class UserRepositoryTest {
    private final UserRepository userRepository;

  //@Autowired使いたいクラスのインスタンス化をしてくれる
    @Autowired
    UserRepositoryTest(UserRepository repository) {
        this.userRepository = repository;
    }

    @Test
    void testFindById() {
        User user = userRepository.findById(1).get();
        assertEquals(1, user.getId());
        assertEquals("キラメキ太郎", user.getName());
    }

}
