package aiss.GitHubMiner.service;

import aiss.GitHubMiner.model.Issue;
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
public class IssueService {

    RestTemplate restTemplate;

    @Autowired
    public IssueService(RestTemplate restTemplate) { this.restTemplate = restTemplate; }

    @Value("${github.baseuri}")
    private String baseuri;

    public List<Issue> getIssues(String owner, String repo, Integer days, Integer maxPages) {
        List<Issue> issues = new ArrayList<>();
        LocalDateTime today = LocalDateTime.now();
        today = today.minusDays(days);
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        String day = today.format(formato);
        int page = 1;
        String uri = baseuri+"repos/"+owner+ "/"+repo + "/issues?since="+day+"&page="+page;
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Issue[]> request = new HttpEntity<>(null, headers);
        ResponseEntity<Issue[]> response = restTemplate.exchange(uri, HttpMethod.GET, request, Issue[].class);
        List<Issue> issues1 = Arrays.asList(response.getBody());
        issues.addAll(issues1);
        while (page < maxPages) {
            page++;
            uri = baseuri + "repos/" + owner + "/" + repo + "/issues?since=" + day + "&page=" + page;
            response = restTemplate.exchange(uri, HttpMethod.GET, request, Issue[].class);
            List<Issue> issues2 = Arrays.asList(response.getBody());
            issues.addAll(issues2);
        }
        return issues;
    }
}