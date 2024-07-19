package com.techacademy.service;

import java.util.List;
import java.util.Set; // 追加

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; //追加

import com.techacademy.entity.User;
import com.techacademy.repository.UserRepository;

@Service
// サービスではUserRepositoryリポジトリを使って、データを取得
// コンポーネントを利用するのでprivate finalで変数userRepositoryを定義
public class UserService {
    private final UserRepository userRepository;

    // UserServiceクラスのインスタンスを作成する際に、UserRepositoryのインスタンスを受け取って、userRepositoryフィールドに代入する
    public UserService(UserRepository repository) {
        this.userRepository = repository;
    }

    /** 全件を検索して返す */
    public List<User> getUserList() {
        // リポジトリのfindAllメソッドを呼び出す
        return userRepository.findAll();
    }

    /** Userを1件検索して返す */
    public User getUser(Integer id) {
        return userRepository.findById(id).get();
    }

    /** Userの登録を行なう */
    @Transactional
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    // ----- 追加:ここから---------
    /** Userの削除を行う */
    // idckは引数/主キー　削除するユーザーIDのセット　　
    //deleteUserはセット内の各IDについてリポジトリのdeleteByIdメソッドを呼び出し、該当のユーザーを削除
    @Transactional
    public void deleteUser(Set<Integer> idck) {
        for(Integer id : idck) {
            userRepository.deleteById(id);
        }
    }

    // ----- 追加:ここまで -------

}
