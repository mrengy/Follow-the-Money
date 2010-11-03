package org.ftm.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.ftm.api.Bill;
import org.ftm.api.Candidate;
import org.ftm.api.Contribution;
import org.ftm.api.Contributor;
import org.ftm.api.DataAccessObject;
import org.ftm.api.Issue;
import org.ftm.api.ZipCode;
import org.ftm.util.Filter;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This implementation is multiple data source APIs.
 *
 * @author <font size=-1 color="#a3a3a3">johnny.hujol@gmail.com (Johnny Hujol)</font>
 * @since Apr 26, 2010
 */
public final class SimpleDataAccessObject implements DataAccessObject {

    private static final String VS_API_KEY = "1ebd4d39454987ed3d3712cacdfd9e87";
    private static final String HOST = "api.votesmart.org";
    private static final String URI_ACCESS = "http://%s/%s?key=%s%s";

    private static final String GET_CATEGORIES = String.format(
        URI_ACCESS,
        HOST,
        "Votes.getCategories",
        VS_API_KEY,
        "&year=%s"
    );
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");

    public Collection<Contribution> getContributions(final String politicianName) throws Exception {
        //        final Reader reader = new StringReader(downloadContent(
        //                "http://transparencydata.com/api/1.0/contributions.json?apikey=160f59b8c6ea40cca6ed1c709179d647&contributor_state=md|va" +
        //                        "&recipient_ft=" + politicianName.toLowerCase() + "&amount=>|1000&per_page=100000&cycle=2008"));

        final Reader reader = new FileReader(new File("resources/contributions.json"));

        Filter<Contribution> filter;
        if(null == politicianName || 0 == politicianName.trim().length()) {
            filter = new Filter<Contribution>() {
                public boolean accept(Contribution c) {
                    return true;
                }
            };
        }
        else {
            filter = new Filter<Contribution>() {
                public boolean accept(Contribution c) {
                    return c.getRecipientName().getLastName().toLowerCase().contains(politicianName.toLowerCase());
                }
            };
        }
        return loadContribution(reader, filter);
    }

    private static Collection<Contribution> loadContribution(Reader reader, Filter<Contribution> filter) throws IOException {
        final GsonBuilder gsonBuilder = new GsonBuilder();
        final Map<String, String> categoryOrderToIndustryName = getCategoryOrderToIndustryName();
        gsonBuilder.registerTypeAdapter(Contribution.class, new ContributionDeserializer(categoryOrderToIndustryName));
        final Gson gson = gsonBuilder.create();
        final Type collectionType = new TypeToken<Collection<Contribution>>() {
        }.getType();
        final Collection<Contribution> tmp = gson.fromJson(reader, collectionType);
        final Collection<Contribution> contributions = new ArrayList<Contribution>(8);
        for(Contribution contribution : tmp) {
            if(null != contribution && filter.accept(contribution)) {
                contributions.add(contribution);
            }
        }
        return contributions;
    }

    private static Map<String, String> getCategoryOrderToIndustryName() throws IOException {
        InputStream is = SimpleDataAccessObject.class.getResourceAsStream("/resources/catcodes-20100402.csv");
        StringBuilder sb = new StringBuilder();

        //        @SuppressWarnings("unchecked")
        //        final List<String> rawData = FileUtils.readLines(new File("resources/catcodes-20100402.csv"));
        BufferedReader bufIn = null;
        final List<String> rawData;
        try {
            bufIn = new BufferedReader(new InputStreamReader(is));
            String s1 = bufIn.readLine();
            rawData = new ArrayList<String>(16);
            while(null != s1) {
                rawData.add(s1);
                s1 = bufIn.readLine();
            }
        }
        finally {
            if(null != is) {
                is.close();
            }
            if(null != bufIn) {
                bufIn.close();
            }
        }

        final Map<String, String> categoryOrderToIndustryName = new HashMap<String, String>(rawData.size());
        for(String s : rawData) {
            if(s.startsWith("//") || s.startsWith("#")) {
                continue;
            }

            final String[] cols = s.split(",");
            final String catOrder = cols[4];
            if(categoryOrderToIndustryName.containsKey(catOrder)) {
                continue;
            }

            categoryOrderToIndustryName.put(catOrder, cols[3]);
        }
        return categoryOrderToIndustryName;
    }

    private static final class ContributionDeserializer implements JsonDeserializer<Contribution> {
        private Map<String, String> categoryOrderToIndustryName;

        private ContributionDeserializer(Map<String, String> categoryOrderToIndustryName) {
            this.categoryOrderToIndustryName = categoryOrderToIndustryName;
        }

        public Contribution deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            final JsonObject object = jsonElement.getAsJsonObject();
            final JsonElement el = object.get("contributor_category_order");
            final String categoryOrder = null == el ? "n/a" : el.getAsString();
            String industryCategory = categoryOrderToIndustryName.get(categoryOrder);
            if(null == industryCategory) {
                industryCategory = "n/a";
            }
            final String recipient = object.get("recipient_name").getAsString();
            final String recipientType = object.get("recipient_type").getAsString();
            if(!"P".equals(recipientType)) {
                return null;
            }

            final float amount = object.get("amount").getAsFloat();
            String[] strings = recipient.split(",");
            if(1 == strings.length) {
                strings = recipient.split(" ");
            }

            Date date = null;
            try {
                final JsonElement jsonElement1 = object.get("date");
                if(!jsonElement1.isJsonNull()) {
                    date = new SimpleDateFormat("yyyy-MM-dd").parse(jsonElement1.getAsString());
                }
            }
            catch(ParseException e) {
                e.printStackTrace();
                date = null;
            }
            return new Contribution(
                new Contributor(industryCategory),
                amount,
                new Candidate(1 < strings.length ? StringUtils.join(strings, " ", 1, strings.length - 1) : "n/a", strings[0]),
                date
            );
        }
    }

    public List<Issue> getIssues() throws Exception {
        //        final String xmlDoc = downloadContent(String.format(
        //                GET_CATEGORIES,
        //                2010
        //        ));

        final String xmlDoc = FileUtils.readFileToString(new File("/Users/hujol/Projects/followthemoney/sf/resources/categories.xml"));

        final InputStream bis = new ByteArrayInputStream(xmlDoc.getBytes("UTF-8"));
        final Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(bis);
        final NodeList nodes = doc.getChildNodes();
        final List<Issue> issues = new ArrayList<Issue>(8);
        for(int i = 0; i < nodes.getLength(); i++) {
            final Node node = nodes.item(i);
            final NodeList attributes = node.getChildNodes();
            final int l = attributes.getLength();
            for(int j = 0; j < l; j++) {
                final Node att = attributes.item(j);
                if("category".equals(att.getNodeName())) {
                    final NodeList genInfos = att.getChildNodes();
                    final int i1 = genInfos.getLength();
                    for(int k = 0; k < i1; k++) {
                        final Node node1 = genInfos.item(k);
                        if("name".equals(node1.getNodeName())) {
                            final String issueName = node1.getTextContent();
                            issues.add(new Issue(issueName));
                        }
                    }
                }
            }
        }
        return issues;
        //        return Collections.emptyList();
    }

    private static final String GET_CANDIDATES = String.format(
        URI_ACCESS,
        HOST,
        "Officials.getStatewide",
        VS_API_KEY,
        ""
    );

    private static final String GET_CANDIDATES_BY_ZIP_CODE = String.format(
        URI_ACCESS,
        HOST,
        "Officials.getByZip",
        VS_API_KEY,
        "&zip5="
    );

    private static final String GET_BILLS_BY_CANDIDATE_ID = String.format(
        URI_ACCESS,
        HOST,
        "Votes.getByOfficial",
        VS_API_KEY,
        "&year=2008&&candidateId="
    );

    /**
     * Uses the VoteSmart API to get the politicians.
     *
     * @return
     * @throws Exception
     */
    public List<Candidate> getPoliticians() throws Exception {
        final String xmlDoc = downloadContent(GET_CANDIDATES);

        return getPoliticians(xmlDoc);
    }

    /**
     * Uses the VoteSmart API to get the politicians.
     *
     * @param zipCode
     * @return
     * @throws Exception
     */
    public List<Candidate> getPoliticians(ZipCode zipCode) throws Exception {
        final String xmlDoc = downloadContent(GET_CANDIDATES_BY_ZIP_CODE + zipCode.getZip5());

        return getPoliticians(xmlDoc);
    }

    public List<Bill> getBills(Candidate p) throws Exception {
        //        final String xmlDoc = downloadContent(GET_BILLS_BY_CANDIDATE_ID + p.getId());
        final String xmlDoc = FileUtils.readFileToString(new File("/Users/hujol/Projects/followthemoney/sf/resources/bills-votesmart-2008-candid32795.xml"));

        return getBills(xmlDoc);
    }

    private List<Bill> getBills(String xmlDoc) throws ParserConfigurationException, IOException, SAXException {
        final ByteArrayInputStream bis = new ByteArrayInputStream(xmlDoc.getBytes("UTF-8"));
        //        FileInputStream bis = new FileInputStream(new File("/Users/hujol/Projects/followthemoney/sf/resources", "pvs-api-candidates.xml"));
        final Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(bis);
        final NodeList nodes = doc.getChildNodes();
        final List<Bill> bills = new ArrayList<Bill>(8);
        for(int i = 0; i < nodes.getLength(); i++) {
            final Node node = nodes.item(i);
            final NodeList attributes = node.getChildNodes();
            final int l = attributes.getLength();
            for(int j = 0; j < l; j++) {
                final Node att = attributes.item(j);
                String nodeName = att.getNodeName().toLowerCase();
                if("bill".equals(nodeName)) {
                    NodeList candidatesAttributes = att.getChildNodes();
                    String title = null;
                    String stage = null;
                    Bill.Vote vote = Bill.Vote.YES;
                    String issue = null;
                    for(int k = 0; k < candidatesAttributes.getLength(); k++) {
                        Node node1 = candidatesAttributes.item(k);
                        String name = node1.getNodeName();
                        String content = node1.getTextContent();
                        if("title".equalsIgnoreCase(name)) {
                            title = content;
                        }
                        else if("stage".equalsIgnoreCase(name)) {
                            stage = content;
                        }
                        else if("vote".equalsIgnoreCase(name)) {
                            vote = "y".equalsIgnoreCase(content) ? Bill.Vote.YES : Bill.Vote.NO;
                        }
                        else if("categories".equalsIgnoreCase(name)) {
                            NodeList Aaaa = node1.getChildNodes();
                            for(int kk = 0; kk < Aaaa.getLength(); kk++) {
                                Node node11 = Aaaa.item(kk);
                                String name1 = node11.getNodeName();
                                if("category".equalsIgnoreCase(name1)) {
                                    NodeList Aaaaa = node11.getChildNodes();
                                    for(int kkk = 0; kkk < Aaaaa.getLength(); kkk++) {
                                        Node node11k = Aaaaa.item(kkk);
                                        if("name".equals(node11k.getNodeName())) {
                                            issue = node11k.getTextContent();
                                            break;
                                        }
                                    }
                                    break;
                                }
                            }
                        }
                    }
                    bills.add(new Bill(title, stage, vote, issue));
                }
            }
        }
        return bills;
    }

    private List<Candidate> getPoliticians(String xmlDoc) throws SAXException, IOException, ParserConfigurationException {
        final ByteArrayInputStream bis = new ByteArrayInputStream(xmlDoc.getBytes("UTF-8"));
        //        FileInputStream bis = new FileInputStream(new File("/Users/hujol/Projects/followthemoney/sf/resources", "pvs-api-candidates.xml"));
        final Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(bis);
        final NodeList nodes = doc.getChildNodes();
        final List<Candidate> candidates = new ArrayList<Candidate>(8);
        for(int i = 0; i < nodes.getLength(); i++) {
            final Node node = nodes.item(i);
            final NodeList attributes = node.getChildNodes();
            final int l = attributes.getLength();
            for(int j = 0; j < l; j++) {
                final Node att = attributes.item(j);
                String nodeName = att.getNodeName().toLowerCase();
                if("candidate".equals(nodeName)) {
                    NodeList candidatesAttributes = att.getChildNodes();
                    String firstName = null;
                    String lastName = null;
                    int candidateId = -1;
                    for(int k = 0; k < candidatesAttributes.getLength(); k++) {
                        Node node1 = candidatesAttributes.item(k);
                        String name = node1.getNodeName();
                        String content = node1.getTextContent();
                        if("firstName".equalsIgnoreCase(name)) {
                            firstName = content;
                        }
                        else if("lastName".equalsIgnoreCase(name)) {
                            lastName = content;
                        }
                        else if("candidateId".equalsIgnoreCase(name)) {
                            candidateId = Integer.valueOf(content);
                        }
                    }
                    candidates.add(new Candidate(candidateId, lastName, firstName));
                }
            }
        }
        return candidates;
    }

    private static String downloadContent(String uri) throws IOException {
        final StringBuilder sb = new StringBuilder();
        BufferedReader bufIn = null;
        InputStream in = null;
        try {
            final URL url = new URL(uri);
            HttpURLConnection myConn = (HttpURLConnection) url.openConnection();
            myConn.setInstanceFollowRedirects(true);
            myConn.setRequestProperty("User-Agent", "Mozilla/5.0");
            in = myConn.getInputStream();
            BufferedReader bufIn1;
            bufIn1 = new BufferedReader(new InputStreamReader(in));
            String s = bufIn1.readLine();
            while(null != s) {
                sb.append(s);
                sb.append(LINE_SEPARATOR);
                s = bufIn1.readLine();
            }
            bufIn = bufIn1;
        }
        finally {
            if(null != in) {
                in.close();
            }
            if(null != bufIn) {
                bufIn.close();
            }
        }
        return sb.toString();
    }
}
