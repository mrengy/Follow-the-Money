package org.ftm.api;

import java.util.List;

/**
 * @author <font size=-1 color="#a3a3a3">johnny_hujol@vrtx.com (Johnny Hujol)</font>
 * @since Jun 21, 2010
 */
public interface DataAccessObject {
    //    Collection<Candidate> getCandidates() throws Exception;
    //
    //    Collection<Contributor> getContributorsUsingCandidateId(int id) throws Exception;

    List<Issue> getIssues() throws Exception;
}
