package org.ftm.ui;

import org.bushe.swing.event.EventBus;
import org.bushe.swing.event.EventTopicSubscriber;
import org.ftm.api.Candidate;
import org.ftm.api.Contribution;
import org.ftm.api.Contributor;
import org.ftm.api.DataAccessObject;
import org.ftm.api.Issue;
import org.ftm.api.ZipCode;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author <font size=-1 color="#a3a3a3">Johnny Hujol</font>
 * @since Oct 31, 2010
 */
final class Controller implements EventTopicSubscriber {

    private final FollowTheMoney followTheMoney;
    private final DataAccessObject dao;
    private final Model model;

    Controller(FollowTheMoney followTheMoney, DataAccessObject dao, Model model) {
        this.followTheMoney = followTheMoney;
        this.dao = dao;
        this.model = model;

        EventBus.subscribeStrongly("zipcode", this);
        EventBus.subscribeStrongly("candidateNameSearch", this);
        EventBus.subscribeStrongly("candidateSearch", this);
        EventBus.subscribeStrongly("issueSearch", this);
        EventBus.subscribeStrongly("contributionSearch", this);
        EventBus.subscribeStrongly("visualization", this);
        EventBus.subscribeStrongly("getissues", this);

        EventBus.subscribeStrongly("candidateselected", this);
    }

    public void onEvent(final String s, final Object o) {
        Runnable runnable = new Runnable() {
            public void run() {
                if("zipcode".equals(s)) {
                    // Set the zipCode in the model
                    final ZipCode zipCode = new ZipCode((String) o);
                    model.setZipCode(zipCode);

                    final List<Candidate> candidates = new ArrayList<Candidate>();
                    try {
                        candidates.addAll(dao.getCandidates(zipCode));
                    }
                    catch(Exception e) {
                        e.printStackTrace();
                        EventBus.publish("candidatesfound", null);
                        return;
                    }
                    EventBus.publish("candidatesfound", candidates);
                }
                else if("candidateNameSearch".equals(s)) {
                    final List<Candidate> candidates = new ArrayList<Candidate>();
                    try {
                        candidates.addAll(dao.getCandidates(o.toString()));
                    }
                    catch(Exception e) {
                        e.printStackTrace();
                        EventBus.publish("candidatesfound", null);
                        return;
                    }
                    EventBus.publish("candidatesfound", candidates);
                }
                else if("getissues".equals(s)) {
                    final List<Issue> issues = new ArrayList<Issue>();
                    try {
                        issues.addAll(dao.getIssues());
                    }
                    catch(Exception e) {
                        e.printStackTrace();
                        EventBus.publish("issuesfound", null);
                        return;
                    }
                    EventBus.publish("issuesfound", issues);
                }
                else if("candidateSearch".equals(s)) {
                    followTheMoney.setCandidateSearch();
                }
                else if("issueSearch".equals(s)) {
                    followTheMoney.setIssueSearch();
                }
                else if("contributionSearch".equals(s)) {
                    followTheMoney.setContributionSearch();
                }
                else if("visualization".equals(s)) {
                    try {
                        followTheMoney.setVisualization();
                    }
                    catch(Exception e) {
                        e.printStackTrace();
                        return;
                    }
                }
                else if("candidateselected".equals(s)) {
                    if(!(o instanceof Candidate)) {
                        printTypeError(o, Candidate.class);
                        return;
                    }

                    // Set the candidate in the model
                    Model.getSingleton().setCandidateSelected((Candidate) o);
                    final Candidate candidate = (Candidate) o;
                    final List<Contributor> contributors = new ArrayList<Contributor>();
                    try {
                        final Collection<Contribution> cc = dao.getContributions(candidate, 2008);
                        for(Contribution contribution : cc) {
                            final Contributor contributor = contribution.getContributorName();
                            if(!contributors.contains(contributor)) {
                                contributors.add(contributor);
                            }
                        }
                    }
                    catch(Exception e) {
                        e.printStackTrace();
                        EventBus.publish("contributorsfound", null);
                        return;
                    }
                    EventBus.publish("contributorsfound", contributors);
                }
                else if("contributorselected".equals(s)) {
                    if(!(o instanceof Contributor)) {
                        printTypeError(o, Contributor.class);
                        return;
                    }

                    Model.getSingleton().setContributorSelected((Contributor) o);
                }
                else if("issueselected".equals(s)) {
                    if(!(o instanceof Issue)) {
                        printTypeError(o, Issue.class);
                        return;
                    }

                    Model.getSingleton().setIssueSelected((Issue) o);
                }
            }
        };
        new Thread(runnable).start();
    }

    private static void printTypeError(Object o, Class typeExpected) {
        System.err.println(String.format(
            "The passed object must be of type %s.%nFound type %s.",
            typeExpected,
            null == o ? null : o.getClass()
        ));
    }

    public void close() {
        EventBus.unsubscribe("zipcode", this);
        EventBus.unsubscribe("candidateNameSearch", this);
        EventBus.unsubscribe("candidateSearch", this);
        EventBus.unsubscribe("issueSearch", this);
        EventBus.unsubscribe("contributionSearch", this);
        EventBus.unsubscribe("visualization", this);

        EventBus.unsubscribe("candidateselected", this);
    }
}
