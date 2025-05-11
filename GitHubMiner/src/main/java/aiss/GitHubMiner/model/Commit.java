package aiss.GitHubMiner.model;


import aiss.GitHubMiner.model.commitExtra.Author__1;
import aiss.GitHubMiner.model.commitExtra.CommitContent;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Commit {

    @JsonProperty("sha")
    private String sha;
    @JsonProperty("author")
    private Author__1 author;
    @JsonProperty("commit")
    private CommitContent commitContent;
    @JsonProperty("url")
    private String url;


    @JsonProperty("author")
    public Author__1 getAuthor() {
        return author;
    }

    @JsonProperty("author")
    public void setAuthor(Author__1 author) {
        this.author = author;
    }

    @JsonProperty("url")
    public String getUrl() {
        return url;
    }

    @JsonProperty("url")
    public void setUrl(String url) {
        this.url = url;
    }

    @JsonProperty("commitContent")
    public CommitContent getCommitContent() {
        return commitContent;
    }

    @JsonProperty("commitContent")
    public void setCommitContent(CommitContent commitContent) {
        this.commitContent = commitContent;
    }

    public String getTitle() {
        return " ";
    }

    @JsonProperty("sha")
    public void setSha(String sha) {
        this.sha = sha;
    }

    @JsonProperty("sha")
    public String getSha() {
        return sha;
    }

    public String getName(){return this.author.getAuthorName();}
    public String getEmail(){return this.author.getEmail();}
    public String getDate(){return this.author.getDate();}
    public String getMessage(){return this.commitContent.getMessage();}

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Commit.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("author");
        sb.append('=');
        sb.append(((this.author == null)?"<null>":this.author));
        sb.append(',');
        sb.append("sha");
        sb.append('=');
        sb.append(((this.sha == null)?"<null>":this.sha));
        sb.append(',');
        sb.append("url");
        sb.append('=');
        sb.append(((this.url == null)?"<null>":this.url));
        sb.append(',');
        sb.append("commit");
        sb.append('=');
        sb.append(((this.commitContent == null)?"<null>":this.commitContent));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}
