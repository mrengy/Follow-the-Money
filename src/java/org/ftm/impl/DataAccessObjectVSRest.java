package org.ftm.impl;

import org.ftm.api.DataAccessObject;
import org.ftm.api.Issue;
import org.ftm.api.Politician;
import org.ftm.api.ZipCode;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * This implementation is using the followthemoney.org API.
 *
 * @author <font size=-1 color="#a3a3a3">johnny.hujol@gmail.com (Johnny Hujol)</font>
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

    private static final String GET_CANDIDATES = String.format(
            URI_ACCESS,
            HOST,
            "Officials.getStatewide",
            FTM_API_KEY,
            ""
    );

    private static final String GET_CANDIDATES_BY_ZIP_CODE = String.format(
            URI_ACCESS,
            HOST,
            "Officials.getByZip",
            FTM_API_KEY,
            ""
    );

    public List<Politician> getPoliticians() throws Exception {
        final String xmlDoc = downloadContent(GET_CANDIDATES);

        return getPoliticians(xmlDoc);
    }

    public List<Politician> getPoliticians(ZipCode zipCode) throws Exception {
        final String xmlDoc = downloadContent(GET_CANDIDATES_BY_ZIP_CODE + "&zip5=" + zipCode.getZip5());

        return getPoliticians(xmlDoc);
    }

    private List<Politician> getPoliticians(String xmlDoc) throws SAXException, IOException, ParserConfigurationException {
        final ByteArrayInputStream bis = new ByteArrayInputStream(xmlDoc.getBytes("UTF-8"));
        //        FileInputStream bis = new FileInputStream(new File("/Users/hujol/Projects/followthemoney/sf/resources", "pvs-api-candidates.xml"));
        final Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(bis);
        final NodeList nodes = doc.getChildNodes();
        final List<Politician> politicians = new ArrayList<Politician>(8);
        for (int i = 0; i < nodes.getLength(); i++) {
            final Node node = nodes.item(i);
            final NodeList attributes = node.getChildNodes();
            final int l = attributes.getLength();
            for (int j = 0; j < l; j++) {
                final Node att = attributes.item(j);
                String nodeName = att.getNodeName().toLowerCase();
                if ("candidate".equals(nodeName)) {
                    NodeList candidatesAttributes = att.getChildNodes();
                    String firstName = null;
                    String lastName = null;
                    for (int k = 0; k < candidatesAttributes.getLength(); k++) {
                        Node node1 = candidatesAttributes.item(k);
                        String name = node1.getNodeName();
                        String content = node1.getTextContent();
                        if ("firstName".equalsIgnoreCase(name)) {
                            firstName = content;
                        } else if ("lastName".equalsIgnoreCase(name)) {
                            lastName = content;
                        }
                    }
                    politicians.add(new Politician(lastName + " " + firstName));
                }
            }
        }
        return politicians;
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
        final DataAccessObject dao = new DataAccessObjectVSRest();

        final List<Politician> ss = dao.getPoliticians(new ZipCode(02143));
        //        final List<Politician> ss = dao.getPoliticians();

        for (Politician s : ss) {
            System.out.println(s.getName());
        }
    }
}