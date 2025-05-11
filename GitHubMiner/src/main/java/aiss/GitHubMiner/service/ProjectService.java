package aiss.GitHubMiner.service;

import aiss.GitHubMiner.model.Project;
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
public class ProjectService {

    RestTemplate restTemplate= new RestTemplate();

    @Autowired
    public ProjectService(RestTemplate restTemplate) { this.restTemplate = restTemplate; }

    final String baseUri = "https://api.github.com/";

    public Project getProject(String workspace, String repo) {
        String uri = baseUri + "repos/" + workspace + "/" + repo;
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Project> request = new HttpEntity<>(null, headers);
        ResponseEntity<Project> response = restTemplate.exchange(uri, HttpMethod.GET, request, Project.class);
        return response.getBody();
    }

}
