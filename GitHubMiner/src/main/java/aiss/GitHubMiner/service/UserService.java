package aiss.GitHubMiner.service;

import aiss.GitHubMiner.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Service
public class UserService {

    RestTemplate restTemplate= new RestTemplate();

    @Autowired
    public UserService(RestTemplate restTemplate) { this.restTemplate = restTemplate; }

    final String baseUri = "https://api.github.com/";

    public User getUser(String id) {
        String uri = baseUri + id;
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<User> request = new HttpEntity<>(null, headers);
        ResponseEntity<User> response = restTemplate.exchange(uri, HttpMethod.GET, request, User.class);
        return response.getBody();
    }

}
