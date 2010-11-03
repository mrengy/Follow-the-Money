package org.ftm.ui;

import org.bushe.swing.event.EventBus;
import org.bushe.swing.event.EventTopicSubscriber;
import org.ftm.api.Candidate;
import org.ftm.api.DataAccessObject;
import org.ftm.api.ZipCode;

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

        EventBus.subscribe("zipcode", this);
        EventBus.subscribe("politicianSearch", this);
        EventBus.subscribe("issueSearch", this);
        EventBus.subscribe("contributionSearch", this);
        EventBus.subscribe("visualization", this);
    }

    public void onEvent(String s, Object o) {
        if("zipcode".equals(s)) {
            String zipCode = (String) o;
            final List<Candidate> candidates = new ArrayList<Candidate>();
            Candidate p = null;
            try {
                candidates.addAll(dao.getPoliticians(new ZipCode(zipCode)));
            }
            catch(Exception e) {
                e.printStackTrace();
                EventBus.publish("politiciansfound", null);
                return;
            }
            EventBus.publish("politiciansfound", candidates);
        }
        else if("politicianSearch".equals(s)) {
            main.setPoliticianSearch();
            EventBus.publish("zipcodeset", model.getZipCode());
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
}
