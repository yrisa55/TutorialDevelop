package com.techacademy.controller;

//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oidcLogin;

import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.techacademy.entity.User;
import com.techacademy.service.UserService;

// @Controller このクラスがSpring MVCのコントローラーであることを示す
// @RequestMapping このクラスのすべてのメソッドのベースURLが/userである
@Controller
@RequestMapping("user")
// コントローラーではUserServiceリポジトリを使ってデータを取得や保存などを担当
// 変数serviceを定義　finalを使うので再代入不可
public class UserController {
    private final UserService service;

    // UserServiceクラスのインスタンスを依存性注入している　このserviceを使ってユーザー情報の操作を行う
    public UserController(UserService service) {
        this.service = service;
    }

    /** 一覧画面を表示 */
    @GetMapping("/list")
    public String getList(Model model) {
        // 全件検索結果をModelに登録
        model.addAttribute("userlist", service.getUserList());
        // user/list.htmlに画面遷移
        return "user/list";
    }

    /** User登録画面を表示 */
    @GetMapping("/register")
    public String getRegister(@ModelAttribute User user) {
        // User画面登録に遷移
        return "user/register";
    }

    /** User登録処理 */
    @PostMapping("/register")
    // @Validatedアノテーション Userエンティティの設定に基づいた入力チェック　結果はBindingResult resに格納
    public String postRegister(@Validated User user, BindingResult res, Model model) {
        if(res.hasErrors()) {
            // エラーあり
            return getRegister(user);
        }
        // User登録
        service.saveUser(user);
        // 一覧画面にリダイレクト
        return "redirect:/user/list";
    }

    // 課題ここのコードを修正
    /** User更新画面を表示 */
    @GetMapping("/update/{id}/")
    public String getUser(@PathVariable("id") Integer id, @ModelAttribute("user") User user, Model model) {
            // idがnullではない：一覧画面から遷移。Modelにはサービスから取得したUserをセットする
            if(id != null) {
                model.addAttribute("user", service.getUser(id));
            } else {
            // idがnull：postUser()から遷移。ModelにはpostUser()から渡された引数のuserをセットする
                model.addAttribute("user", user);
            }
            // User更新画面に遷移
            return "user/update";
        }

        /** User更新処理 */
        @PostMapping("/update/{id}/")
        // 入力された内容がuserにはいる→getUserに渡す
        public String postUser(@PathVariable("id") Integer id, @Validated @ModelAttribute User user, BindingResult res, Model model) {
            if (res.hasErrors()) {
                // エラーあり、idにnullを設定してgetUser()メソッドを呼び出し　誤った入力内容を再表示させるためにuserを渡す
                return getUser(null, user, model);
            }
            // User登録
            service.saveUser(user);
            // 一覧画面にリダイレクト
            return "redirect:/user/list";
        }

        /** User削除処理 */
        @PostMapping(path="list", params="deleteRun")
        public String deleteRun(@RequestParam(name="idck") Set<Integer> idck, Model model) {

            // Userを一括削除
            service.deleteUser(idck);
            //  一覧画面にリダイレクト
            return "redirect:/user/list";
        }

}
