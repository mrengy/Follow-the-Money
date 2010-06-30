package org.ftm.impl;

import org.ftm.api.DataAccessObject;
import org.ftm.api.Issue;
import org.ftm.api.Politician;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Collection;
import java.util.List;

/**
 * This implementation is using the followthemoney.org API.
 *
 * @author <font size=-1 color="#a3a3a3">johnny_hujol@vrtx.com (Johnny Hujol)</font>
 * @since Apr 26, 2010
 */
public final class DataAccessObjectVSRest implements DataAccessObject {

    private static final String FTM_API_KEY = "1ebd4d39454987ed3d3712cacdfd9e87";
    private static final String HOST = "api.votesmart.org";
    private static final String URI_ACCESS = "http://%s/%s?key=%s%s";

    private static final String GET_CATEGORIES = String.format(
            URI_ACCESS,
            HOST,
            "Votes.getCategories",
            FTM_API_KEY,
            "&year=%s"
    );
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");

    public List<Issue> getIssues() throws Exception {
        final String xmlDoc = downloadContent(String.format(
                GET_CATEGORIES,
                2010
        ));
        final ByteArrayInputStream bis = new ByteArrayInputStream(xmlDoc.getBytes("UTF-8"));
        final Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(bis);
        final NodeList nodes = doc.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++) {
            final Node node = nodes.item(i);
            final NodeList attributes = node.getChildNodes();
            final int l = attributes.getLength();
            for (int j = 0; j < l; j++) {
                final Node att = attributes.item(j);

            }
        }
        return null;
    }

    public List<Politician> getPoliticians() throws Exception {
        throw new IllegalStateException("org.ftm.impl.DataAccessObjectVSRest.getPoliticians Not implemented");
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
        final DataAccessObjectVSRest dao = new DataAccessObjectVSRest();
        final Collection<Issue> ss = dao.getIssues();
        System.out.println(ss);
    }
}