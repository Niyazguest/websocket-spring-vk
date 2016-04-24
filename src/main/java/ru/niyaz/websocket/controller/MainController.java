package ru.niyaz.websocket.controller;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.context.WebContext;
import ru.niyaz.websocket.dto.UserInfo;
import ru.niyaz.websocket.service.AuthenticationService;
import ru.niyaz.websocket.service.MessageService;
import ru.niyaz.websocket.util.ThymeleafTemplateUtil;

import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.Date;

/**
 * Created by user on 18.04.2016.
 */

@Controller
public class MainController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private InitialContext initialContext;

    @Autowired
    private MessageService messageService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public void index(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.setContentType("text/html;charset=UTF-8");
            WebContext webContext = new WebContext(request, response, request.getSession().getServletContext());
            UserInfo userInfo = authenticationService.getCurrentUserInfo();
            if (userInfo != null) {
                webContext.setVariable("vk_user_id", userInfo.getUserID());
                webContext.setVariable("name", userInfo.getName() + " " + userInfo.getLastName());
                webContext.setVariable("photo_url", userInfo.getPhotoURL());
                webContext.setVariable("page_url", userInfo.getPageURL());
                webContext.setVariable("messages", messageService.getMessagesBeforeDate(new Date()));
                ThymeleafTemplateUtil.getTemplateEngine().process("index", webContext, response.getWriter());
            } else {
                ThymeleafTemplateUtil.getTemplateEngine().process("login", webContext, response.getWriter());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public void login(@RequestParam(value = "code") String code, HttpServletRequest request, HttpServletResponse response) {
        try {
            if (authenticationService.authenticateUser(code)) {
                String siteName = (String) initialContext.lookup("java:/comp/env/siteName");
                response.sendRedirect(siteName);
            } else {
                WebContext webContext = new WebContext(request, response, request.getSession().getServletContext());
                ThymeleafTemplateUtil.getTemplateEngine().process("login", webContext, response.getWriter());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}
