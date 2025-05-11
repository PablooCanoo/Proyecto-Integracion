package aiss.GitHubMiner.repository;

import aiss.GitHubMiner.model.*;
import aiss.GitHubMiner.gitMinerModel.*;
import aiss.GitHubMiner.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class Transformer {

    @Autowired
    private ProjectService projectService;
    @Autowired
    private CommitService commitService;
    @Autowired
    private IssueService issueService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;

    //GET
    public GitMinerProject getProject(String owner, String repo,
                                      Integer sinceIssues, Integer maxPages ) {
        GitMinerProject project = new GitMinerProject();
        Project data = projectService.getProject(owner, repo);
        project.setId(data.getId().toString());
        project.setName(data.getName());
        project.setWebUrl(data.getHtmlUrl());
        project.setCommits(getCommits(owner, repo, maxPages));
        project.setIssues(getIssues(owner, repo, sinceIssues, maxPages));
        return project;
    }

    private List<GitMinerCommit> getCommits(String owner, String repo, Integer maxPages) {
        List<GitMinerCommit> commits = new ArrayList<>();
        List<Commit> datas = commitService.getCommits(owner, repo, maxPages);
        for (Commit data : datas) {
            GitMinerCommit commit = new GitMinerCommit();
            commit.setId(data.getSha());
            commit.setTitle(" ");
            commit.setMessage(data.getMessage());
            commit.setAuthorName(data.getName());
            commit.setAuthorEmail(data.getEmail());
            commit.setAuthoredDate(data.getDate());
            commit.setWebUrl(data.getUrl());
            commits.add(commit);
        }
        return commits;
    }

    private List<GitMinerIssue> getIssues(String owner, String repo, Integer sinceIssues, Integer maxPages) {
        List<GitMinerIssue> issues = new ArrayList<>();
        List<Issue> datas = issueService.getIssues(owner, repo, sinceIssues, maxPages);
        for (Issue data : datas) {
            GitMinerIssue issue = new GitMinerIssue();
            issue.setId(data.getId().toString());
            issue.setTitle(data.getTitle());
            issue.setDescription(data.getBody());
            issue.setState(data.getState());
            issue.setCreatedAt(data.getCreatedAt());
            issue.setUpdatedAt(data.getUpdatedAt());
            issue.setClosedAt(data.getClosedAt());
            issue.setLabels(data.getLabels().stream().map(l -> l.getName()).collect(Collectors.toList()));
            issue.setVotes(data.getReactions().getTotalCount());
            String user_url = data.getUser().getWebUrl();
            issue.setAuthor(getUser(user_url));
            issue.setComments(getComments(data.getCommentsUrl()));
            issues.add(issue);
        }
        return issues;
    }

    private GitMinerUser getUser(String url) {
        GitMinerUser user = new GitMinerUser();
        User data = userService.getUser(url);
        user.setId(data.getId().toString());
        user.setName(data.getName());
        user.setUsername(data.getUsername());
        user.setAvatarUrl(data.getAvatarUrl());
        user.setWebUrl(data.getWebUrl());
        return user;
    }

    private List<GitMinerComment> getComments(String url) {
        List<GitMinerComment> comments = new ArrayList<>();
        List<Comment> datas = commentService.getComments(url);
        for (Comment data : datas) {
            GitMinerComment comment = new GitMinerComment();
            comment.setId(data.getId().toString());
            comment.setBody(data.getBody());
            comment.setCreatedAt(data.getCreatedAt());
            comment.setUpdatedAt(data.getUpdatedAt());
            comment.setAuthor(getUser(data.getUser().getWebUrl()));
            comments.add(comment);
        }
        return comments;
    }

}