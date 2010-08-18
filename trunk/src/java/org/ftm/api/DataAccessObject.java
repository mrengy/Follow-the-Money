package org.ftm.api;

import java.util.List;

/**
 * @author <font size=-1 color="#a3a3a3">johnny.hujol@gmail.com (Johnny Hujol)</font>
 * @since Jun 21, 2010
 */
public interface DataAccessObject {
    //    Collection<Politician> getCandidates() throws Exception;
    //
    //    Collection<Contributor> getContributorsUsingCandidateId(int id) throws Exception;

    List<Issue> getIssues() throws Exception;

    List<Politician> getPoliticians() throws Exception;

    List<Politician> getPoliticians(ZipCode zipCode) throws Exception;
}
