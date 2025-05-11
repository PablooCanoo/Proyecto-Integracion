package aiss.GitHubMiner.controller;

import aiss.GitHubMiner.gitMinerModel.GitMinerProject;
import aiss.GitHubMiner.repository.Transformer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/github")
@JsonIgnoreProperties (ignoreUnknown = true)
public class Controller {

    private Transformer transformer=new Transformer();
    private RestTemplate restTemplate=new RestTemplate();

    @Autowired
    public Controller(Transformer transformer) { this.transformer = transformer;
        this.restTemplate = new RestTemplate();}

    //GET http://localhost:8082/github/owner/repo
    @GetMapping("/{owner}/{repo}")
    public GitMinerProject getProject(@PathVariable String owner, @PathVariable String repo,
                                      @RequestParam(defaultValue = "20") int nIssues,
                                      @RequestParam(defaultValue = "2") int maxPages) {
        return transformer.getProject(owner, repo, nIssues, maxPages);
    }

    //POST http://localhost:8082/github/owner/repo
    @PostMapping("/{owner}/{repo}")
    public GitMinerProject create(@PathVariable String owner, @PathVariable String repo,
                                       @RequestParam(defaultValue = "20") int nIssues,
                                       @RequestParam(defaultValue = "2") int maxPages) {
        GitMinerProject project= transformer.getProject(owner, repo, nIssues, maxPages);
        HttpEntity<GitMinerProject> request = new HttpEntity<>(project);
        String gitHubMinerUri = "http://localhost:8082/github/"+owner+"/"+repo;
        ResponseEntity<GitMinerProject> response = restTemplate.exchange(gitHubMinerUri, HttpMethod.POST, request, GitMinerProject.class);
        return response.getBody();
    }

}
