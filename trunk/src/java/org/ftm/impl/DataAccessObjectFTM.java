package org.ftm.impl;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * This implementation is using the followthemoney.org API.
 *
 * @author <font size=-1 color="#a3a3a3">johnny_hujol@vrtx.com (Johnny Hujol)</font>
 * @since Apr 26, 2010
 */
public final class DataAccessObjectFTM { // implements DataAccessObject {

    private static final String FTM_API_KEY = "d7d55c6207bd8aff6846ec347c74fc1a";
    private static final String HOST = "api.followthemoney.org";
    private static final String URI_ACCESS = "http://%s/%s?key=%s%s";

    private static final String GET_CANDIDATE_REQUEST =
            String.format(
                    URI_ACCESS,
                    HOST,
                    "candidates.list.php",
                    FTM_API_KEY,
                    ""
            );

    private static final String GET_CONTRIBUTORS_REQUEST = String.format(
            URI_ACCESS,
            HOST,
            "candidates.top_contributors.php",
            FTM_API_KEY,
            "&imsp_candidate_id=%s"
    );
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");

    public String getCandidates() throws Exception {
        return downloadContent(GET_CANDIDATE_REQUEST);
    }

    public String getContributorsUsingCandidateId(int id) throws Exception {
        return downloadContent(String.format(
                GET_CONTRIBUTORS_REQUEST,
                id
        ));
    }

    private static String downloadContent(String uri) throws IOException {
        final StringBuilder sb = new StringBuilder();
        BufferedReader bufIn = null;
        InputStream in = null;
        try {
            final URL url = new URL(uri);
            in = url.openStream();
            bufIn = new BufferedReader(new InputStreamReader(new BufferedInputStream(in)));
            String s = bufIn.readLine();
            while (null != s) {
                sb.append(s);
                sb.append(LINE_SEPARATOR);
                s = bufIn.readLine();
            }
        } finally {
            if (null != in) {
                in.close();
            }
            if (null != bufIn) {
                bufIn.close();
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) throws Exception {
        final DataAccessObjectFTM dao = new DataAccessObjectFTM();
        final String candidates = dao.getCandidates();
        System.out.println(candidates);
        final String ss = dao.getContributorsUsingCandidateId(11);
        System.out.println(ss);
    }
}
