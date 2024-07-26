package com.techacademy.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

//import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.techacademy.entity.User;


@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class UserControllerTest {
    private MockMvc mockMvc;

    private final WebApplicationContext webApplicationContext;

    UserControllerTest(WebApplicationContext context) {
        this.webApplicationContext = context;
    }

    // 各テストの前にこの処理が実行される
    @BeforeEach
    void beforeEach() {
        // Spring Securityを有効にする
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity()).build();
    }

    @Test
    @DisplayName("User更新画面") // ()内はJUnitビューに表示されるテスト名
    @WithMockUser
    void testGetUser() throws Exception {
        // HTTPリクエストに対するレスポンスの検証
        MvcResult result = mockMvc.perform(get("/user/update/1/")) // URLにアクセス
                .andExpect(status().isOk()) // ステータスを確認
                .andExpect(model().attributeExists("user")) // Modelの内容を確認
                .andExpect(model().hasNoErrors()) // Modelのエラー有無の確認
                .andExpect(view().name("user/update")) // viewの確認
                .andReturn(); // 内容の取得

        // userの検証
        // Modelからuserを取り出す
        User user = (User)result.getModelAndView().getModel().get("user");
        assertEquals(1, user.getId());
        assertEquals("キラメキ太郎", user.getName());
    }

    @Test
    @DisplayName("Userリスト画面")
    @WithMockUser
    void  testGetList() throws Exception {

          // 以下のaddは不要
          //ArrayList<String> userList = new ArrayList<>();
          //userList.add("キラメキ太郎");
          //userList.add("キラメキ次郎");
          //userList.add("キラメキ花子");

          MvcResult result =  mockMvc.perform(get("/user/list")) // URLにアクセス
                .andExpect(status().isOk()) // ステータスを確認
                .andExpect(model().attributeExists("userlist")) // Modelの内容を確認
                .andExpect(model().hasNoErrors()) // Modelのエラー有無の確認
                .andExpect(view().name("user/list")) // viewの確認
                .andReturn(); // 内容の取得

          // 件数が3件であること
          List<User> resultList = (List<User>)result.getModelAndView().getModel().get("userlist");
          assertEquals(3, resultList.size());

          // userListから1件ずつ取り出し、idとnameを検証する
          User userList = (User) resultList.get(0);
          assertEquals(1, userList.getId());
          assertEquals("キラメキ太郎", userList.getName());

          User userList2 = (User) resultList.get(1);
          assertEquals(2, userList2.getId());
          assertEquals("キラメキ次郎", userList2.getName());

          User userList3 = (User) resultList.get(2);
          assertEquals(3, userList3.getId());
          assertEquals("キラメキ花子", userList3.getName());

          }
    }

