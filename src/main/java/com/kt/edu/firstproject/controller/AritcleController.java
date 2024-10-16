package com.kt.edu.firstproject.controller;

import com.kt.edu.firstproject.dto.ArticleForm;
import com.kt.edu.firstproject.entity.Article;
import com.kt.edu.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@Slf4j // logging을 위한 annotation
public class AritcleController {
    @Autowired  // 스프링부트가 미리 생성해놓은 객체를 가져다가 자동 연결!
    private ArticleRepository articleRepository;


    @GetMapping("/articles/new")
    public String newArticleForm() {
        return "articles/new";
    }

    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form){ // DTO
        log.info(form.toString());
        //System.out.println(form.toString()); // --> logging 기능(블랙박스)으로 대체

        // 1. DTO를 Entity로 변환
        Article article = form.toEntity();
        log.info(article.toString());
        //System.out.println(form.toString());


        // 2. Repository에게 Entity를 DB에 저장하도록 함.
        Article saved = articleRepository.save(article);
        log.info(saved.toString());
        //System.out.println(saved.toString());

        return "redirect:/articles/" + saved.getId();
    }

//    @PostMapping("/articles/{id}/update")
//    public String update(@PathVariable Long id, ArticleForm form){
//        log.info(form.toString());
//        Article target = articleRepository.findById(id).orElse(null);
//        if (target != null) {
//            target
//        }
//    }


    @GetMapping("/articles/{id}")
    public String show(@PathVariable Long id, Model model){
        log.info("id = " + id);

        // 1. id로 데이터를 가져옴.
        Article articleEntity = articleRepository.findById(id).orElse(null);//findbyid 함수는 Optional<Article> 형식임.
        // 2. 가져온 데이터를 모델에 등록!
        model.addAttribute("article", articleEntity);
        // 3. 보여줄 페이지를 설정!
        return "/articles/show";
    }

    @GetMapping("/articles")
    public String index(Model model) {
        // 1. 모든 article 가져오기
        List<Article> articleEntityList = articleRepository.findAll(); // List는 ArrayList의 상위 개념 --> 호환 가능.
        // 2. 가져온 article 묶음을 뷰로 전달.
        model.addAttribute("articleList", articleEntityList);
        // 3. 뷰 페이지 결정.
        return "articles/index";
    }

    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable Long id, Model model){
        // 수정할 데이터를 가져오기
        Article articleEntity = articleRepository.findById(id).orElse(null);

        // 모델에 데이터 등록
        model.addAttribute("article", articleEntity);

        return "articles/edit";
    }

    @GetMapping("articles/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr) {
        log.info("삭제 요청이 들어왔습니다.");
        // 1. 삭제 대상을 가져온다.
        Article target = articleRepository.findById(id).orElse(null);
        // 2. 대상을 삭제한다.
        if (target != null) {
            articleRepository.delete(target);
            rttr.addFlashAttribute("msg", "삭제가 완료되었습니다!");
        }
        // 3. 결과 페이지로 이동.
        return "redirect:/articles";
    }
}
