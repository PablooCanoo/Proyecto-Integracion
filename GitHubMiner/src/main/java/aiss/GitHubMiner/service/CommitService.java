package aiss.GitHubMiner.service;


import aiss.GitHubMiner.model.Commit;
import aiss.GitHubMiner.model.commitExtra.CommitContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class CommitService {

    RestTemplate restTemplate= new RestTemplate();

    @Autowired
    public CommitService(RestTemplate restTemplate) { this.restTemplate = restTemplate; }

    @Value("${github.baseuri}")
    private String baseuri;


    public List<Commit> getCommits(String owner, String repo, Integer maxPages) {
        List<Commit> commits = new ArrayList<>();
        int page = 1;
        String uri = baseuri+"repos/"+owner+ "/"+repo + "/commits"+"&page="+page;
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Commit[]> request = new HttpEntity<>(null, headers);
        ResponseEntity<Commit[]> response = restTemplate.exchange(uri, HttpMethod.GET, request, Commit[].class);
        List<Commit> commits1 = Arrays.asList(response.getBody());
        commits.addAll(commits1);
        while (page < maxPages) {
            page++;
            uri = baseuri + "repos/" + owner + "/" + repo + "/issues" + "&page=" + page;
            response = restTemplate.exchange(uri, HttpMethod.GET, request, Commit[].class);
            List<Commit> commits2 = Arrays.asList(response.getBody());
            commits.addAll(commits2);
        }
        return commits;
    }
}
