package com.mysite.sbb.question.controller;

import com.mysite.sbb.question.QuestionForm;
import com.mysite.sbb.question.domain.Question;
import com.mysite.sbb.question.service.QuestionService;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/question")
@AllArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @RequestMapping("/list")
    public String showQuestionList(@NotNull Model model) {
        List<Question> questionList = this.questionService.getList();
        model.addAttribute("questionList", questionList);

        return "questionList";
    }

    @RequestMapping("/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id) {
        Question question = this.questionService.getQuestion(id);
        model.addAttribute("question", question);

        return "questionDetail";
    }

    @GetMapping("/create")
    public String questionCreate(QuestionForm questionForm) {
        return "questionForm";
    }

    @PostMapping("/create")
    public String questionCreate(@Valid QuestionForm questionForm, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "questionForm";
        }
        this.questionService.create(questionForm.getSubject(), questionForm.getContent());
        return "redirect:/question/list";
    }
}
