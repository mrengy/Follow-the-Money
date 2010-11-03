package org.ftm.api;

/**
 * @author <font size=-1 color="#a3a3a3">Johnny Hujol</font>
 * @since Oct 1, 2010
 */
public final class Bill {

    public enum Vote {
        YES,
        NO
    }

    private final String title;
    private final String stage;
    private final Vote vote;
    private final String issue;

    public Bill(String title, String stage, Vote vote, String issue) {
        this.title = title;
        this.stage = stage;
        this.vote = vote;
        this.issue = issue;
    }

    public String getTitle() {
        return title;
    }

    public String getStage() {
        return stage;
    }

    public Vote getVote() {
        return vote;
    }

    public String getIssue() {
        return issue;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Bill");
        sb.append("{title='").append(title).append('\'');
        sb.append(", stage='").append(stage).append('\'');
        sb.append(", vote=").append(vote);
        sb.append(", issue=").append(issue);
        sb.append('}');
        return sb.toString();
    }
}
