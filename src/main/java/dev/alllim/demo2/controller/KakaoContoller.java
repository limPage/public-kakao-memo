package dev.alllim.demo2.controller;


import dev.alllim.demo2.services.KakaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


@RestController(value = "/")
public class KakaoContoller {
    private int user= 0;

    @GetMapping(value = "/")
    public ModelAndView getIndex(){
        ModelAndView modelAndView =new ModelAndView("kakao/index");
        modelAndView.addObject("user",user);
        LocalTime date = LocalTime.now();
        String formatDate= date.format(DateTimeFormatter.ofPattern("a HH:mm"));
        modelAndView.addObject("formatDate",formatDate);
        return modelAndView;
    }
    @Autowired
    public KakaoService kakaoService;

    @RequestMapping("/login")
    public RedirectView goKakaoOAuth() {
        return kakaoService.goKakaoOAuth();
    }

    @RequestMapping("/login-callback")
    public ModelAndView loginCallback(@RequestParam("code") String code) {
        user =1;
        return kakaoService.loginCallback(code);
    }

    @RequestMapping("/profile")
    public String getProfile() {
        return kakaoService.getProfile();
    }

    @RequestMapping("/authorize")
    public RedirectView goKakaoOAuth(@RequestParam("scope") String scope) {
        return kakaoService.goKakaoOAuth(scope);
    }

    @RequestMapping("/friends")
    public String getFriends() {
        return kakaoService.getFriends();
    }

    @RequestMapping("/message")
    public String message(@RequestParam(value = "text", required = false)String text) {
        return kakaoService.message(text);
    }
    @GetMapping(value = "logout")
    public ModelAndView getLogout(){
        user=0;
        return this.kakaoService.logOut();
    }
}

