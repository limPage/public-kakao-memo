package dev.alllim.demo2.services;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import javax.servlet.http.HttpSession;
import dev.alllim.demo2.common.Const;
import dev.alllim.demo2.transformer.Trans;
import com.google.gson.JsonParser;



@RequiredArgsConstructor
@Service
public class KakaoService {

    private final HttpSession httpSession;

    @Autowired
    public HttpCallService httpCallService;


    @Value("2cfb634876b2550cd738d54b5998a512")
    private String REST_API_KEY;

    @Value("http://localhost:8080/login-callback")
    private String REDIRECT_URI;

    @Value("https://kauth.kakao.com/oauth/authorize")
    private String AUTHORIZE_URI;

    @Value("https://kauth.kakao.com/oauth/token")
    public String TOKEN_URI;

    @Value("oRCTUgAjyUAKk6xrf4Q05vtk3KMVkeFb")
    private String CLIENT_SECRET;

    @Value("https://kapi.kakao.com")
    private String KAKAO_API_HOST;


    public RedirectView goKakaoOAuth() {
        return goKakaoOAuth("");
    }

    public RedirectView goKakaoOAuth(String scope) {

        String uri = AUTHORIZE_URI+"?redirect_uri="+REDIRECT_URI+"&response_type=code&client_id="+REST_API_KEY;

        if(!scope.isEmpty()) uri += "&scope="+scope;
        return new RedirectView(uri);
    }

    public ModelAndView loginCallback(String code) {
        String param = "grant_type=authorization_code&client_id="+REST_API_KEY+"&redirect_uri="+REDIRECT_URI+"&code="+code;
        String rtn = httpCallService.Call(Const.POST, TOKEN_URI, Const.EMPTY, param);
        httpSession.setAttribute("token", Trans.token(rtn, new JsonParser()));

        ModelAndView modelAndView = new ModelAndView("member/kakao");
        modelAndView.addObject("user",1);

        return modelAndView;
    }

    public String getProfile() {
        String uri = KAKAO_API_HOST + "/v2/user/me";
        return httpCallService.CallwithToken(Const.GET, uri, httpSession.getAttribute("token").toString());
    }

    public String getFriends() {
        String uri = KAKAO_API_HOST + "/v1/api/talk/friends";
        return httpCallService.CallwithToken(Const.GET, uri, httpSession.getAttribute("token").toString());
    }

    public String message( String text ) {
        String uri = KAKAO_API_HOST + "/v2/api/talk/memo/default/send";
        return httpCallService.CallwithToken(Const.POST, uri, httpSession.getAttribute("token").toString(), Trans.postText(text));
    }

    public ModelAndView logOut() {
        httpSession.removeAttribute("token");
        ModelAndView modelAndView = new ModelAndView("redirect:/");

        return modelAndView;
    }
}