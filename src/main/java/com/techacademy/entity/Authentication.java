package com.techacademy.entity;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "authentication" )

public class Authentication {
    /** ログインユーザー名 */
    @Id
    private String loginUser;

    /** パスワード */
    private String password;

    /** 有効日付 */
    private Date validDate;

    /** ユーザID */
    // 認証エンティティとユーザーエンティティが１対１の関係を示す
    @OneToOne
    // リレーションを行う nameが結合元(認証エンティティ)の項目名、referencedColumnNameが結合先（ユーザーエンティティの項目名）
    @JoinColumn(name ="user_id", referencedColumnName="id")
    private User user;

}
