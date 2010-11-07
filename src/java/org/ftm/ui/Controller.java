package org.ftm.ui;

import org.bushe.swing.event.EventBus;
import org.bushe.swing.event.EventTopicSubscriber;
import org.ftm.api.Candidate;
import org.ftm.api.DataAccessObject;
import org.ftm.api.ZipCode;

import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <font size=-1 color="#a3a3a3">Johnny Hujol</font>
 * @since Oct 31, 2010
 */
final class Controller implements EventTopicSubscriber {

    private final Main main;
    private final DataAccessObject dao;
    private final Model model;

    Controller(Main main, DataAccessObject dao, Model model) {
        this.main = main;
        this.dao = dao;
        this.model = model;

        EventBus.subscribeStrongly("zipcode", this);
        EventBus.subscribeStrongly("candidateNameSearch", this);
        EventBus.subscribeStrongly("candidateSearch", this);
        EventBus.subscribeStrongly("issueSearch", this);
        EventBus.subscribeStrongly("contributionSearch", this);
        EventBus.subscribeStrongly("visualization", this);
    }

    public void onEvent(String s, Object o) {
        if("zipcode".equals(s)) {
            // Set the zipCode in the model
            final ZipCode zipCode = new ZipCode((String) o);
            model.setZipCode(zipCode);

            final List<Candidate> candidates = new ArrayList<Candidate>();
            Candidate p = null;
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
            JOptionPane.showMessageDialog(null, "Candidate name for search " + o);
        }
        else if("candidateSearch".equals(s)) {
            main.setCandidateSearch();
        }
        else if("issueSearch".equals(s)) {
            main.setIssueSearch();
        }
        else if("contributionSearch".equals(s)) {
            main.setContributionSearch();
        }
        else if("visualization".equals(s)) {
            main.setVisualization();
        }
    }

    public void close() {
        EventBus.unsubscribe("zipcode", this);
        EventBus.unsubscribe("candidateSearch", this);
        EventBus.unsubscribe("issueSearch", this);
        EventBus.unsubscribe("contributionSearch", this);
        EventBus.unsubscribe("visualization", this);

    }
}
