package ru.niyaz.websocket.service;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.niyaz.websocket.authentication.AuthenticationToken;
import ru.niyaz.websocket.dto.UserInfo;
import ru.niyaz.websocket.entity.Author;
import ru.niyaz.websocket.repository.AuthorsRepository;

import javax.naming.InitialContext;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by user on 20.04.2016.
 */

@Service
public class AuthenticationService {

    @Autowired
    private AuthorsRepository authorsRepository;

    @Autowired
    private InitialContext initialContext;

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean authenticateUser(String code) {
        try {
            String siteName = (String) initialContext.lookup("java:/comp/env/siteName");
            CloseableHttpClient httpClient = HttpClients.createDefault();
            URI uri = new URIBuilder("https://oauth.vk.com/access_token")
                    .setParameter("client_id", "5422686")
                    .setParameter("client_secret", "D8hyl9P3xHAHYh9kAeJE")
                    .setParameter("redirect_uri", siteName + "/login")
                    .setParameter("code", code)
                    .build();
            HttpGet httpGet = new HttpGet(uri);
            CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
            BufferedReader bf = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
            String json = bf.readLine();
            JSONObject jsonObject = new JSONObject(json);

            if (!jsonObject.has("error")) {
                Integer userID = jsonObject.getInt("user_id");
                String accessToken = jsonObject.getString("access_token");
                JSONObject userInfo = getUserInfo(httpClient, userID, accessToken);

                if (!userInfo.has("error")) {
                    String firstName = userInfo.getString("first_name");
                    String lastName = userInfo.getString("last_name");
                    String photo50URL = userInfo.getString("photo_50");
                    String pageURL = "https://vk.com/" + userInfo.getString("domain");
                    UserInfo principal = new UserInfo(firstName, lastName, photo50URL, userID, pageURL);
                    Long authorID = saveAuthor(principal);
                    Authentication authentication = new AuthenticationToken(authorID, principal, getUserAutorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Long saveAuthor(UserInfo userInfo) {
        Author author = new Author(userInfo.getUserID(), userInfo.getName(), userInfo.getLastName(), userInfo.getPageURL(), userInfo.getPhotoURL());
        return authorsRepository.saveAuthor(author);
    }

    public UserInfo getCurrentUserInfo() {
        if(SecurityContextHolder.getContext().getAuthentication()!=null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER"))) {
                return (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public Long getCurrentUserID() {
        AuthenticationToken authentication = (AuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        return authentication.getUserID();
    }

    private JSONObject getUserInfo(CloseableHttpClient httpClient, Integer userID, String accessToken) throws Exception {
        URI uri = new URIBuilder("https://api.vk.com/method/users.get")
                .setParameter("user_ids", userID.toString())
                .setParameter("fields", "photo_50,domain")
                .setParameter("access_token", accessToken)
                .build();
        HttpGet httpGet = new HttpGet(uri);
        CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
        BufferedReader bf = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
        String json = bf.readLine();
        JSONObject jsonObject = new JSONObject(new String(json.getBytes(), "UTF-8"));
        return (JSONObject) ((JSONArray) jsonObject.get("response")).get(0);
    }

    private Collection<? extends GrantedAuthority> getUserAutorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        return authorities;
    }

}
