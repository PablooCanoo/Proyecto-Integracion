package aiss.GitHubMiner.service;

import aiss.GitHubMiner.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class CommentService {

    RestTemplate restTemplate= new RestTemplate();

    @Autowired
    public CommentService(RestTemplate restTemplate) { this.restTemplate = restTemplate; }

    public List<Comment> getComments(String url) {
        String uri = url;
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Comment[]> request = new HttpEntity<>(null, headers);
        ResponseEntity<Comment[]> response = restTemplate.exchange(uri, HttpMethod.GET, request, Comment[].class);
        List<Comment> comments = Arrays.asList(response.getBody());
        return comments;
    }
}

