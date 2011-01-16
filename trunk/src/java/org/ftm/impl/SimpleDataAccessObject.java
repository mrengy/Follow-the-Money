package org.ftm.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.lang.StringUtils;
import org.ftm.api.Bill;
import org.ftm.api.Candidate;
import org.ftm.api.Contribution;
import org.ftm.api.Contributor;
import org.ftm.api.DataAccessObject;
import org.ftm.api.Issue;
import org.ftm.api.ZipCode;
import org.ftm.util.Filter;
import org.ftm.xml.XPathReader;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPathConstants;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
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
 * This implementation is using multiple data source APIs.
 *
 * @author <font size=-1 color="#a3a3a3">johnny.hujol@gmail.com (Johnny Hujol)</font>
 * @since Apr 26, 2010
 */
public final class SimpleDataAccessObject implements DataAccessObject {

    private static final String VS_API_KEY = "1ebd4d39454987ed3d3712cacdfd9e87";
    private static final String HOST = "api.votesmart.org";
    private static final String URI_ACCESS = "http://%s/%s?key=%s%s";

    private static final String LINE_SEPARATOR = System.getProperty("line.separator");

    public Collection<Contribution> getContributions(final String candidateName) throws Exception {
        final Reader reader = new StringReader(downloadContent(
            "http://transparencydata.com/api/1.0/contributions.json?apikey=160f59b8c6ea40cca6ed1c709179d647&contributor_state=md|va" +
                "&recipient_ft=" + candidateName.toLowerCase() + "&amount=>|1000&per_page=100000&cycle=2008"));

        //        final Reader reader = new InputStreamReader(SimpleDataAccessObject.class.getResourceAsStream("/resources/contributions.json"));

        Filter<Contribution> filter;
        if(0 == candidateName.trim().length()) {
            filter = new Filter<Contribution>() {
                public boolean accept(Contribution c) {
                    return true;
                }
            };
        }
        else {
            filter = new Filter<Contribution>() {
                public boolean accept(Contribution c) {
                    return c.getRecipientName().getLastName().toLowerCase().contains(candidateName.toLowerCase());
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
            if(null != contribution && filter.accept(contribution) && !contributions.contains(contribution)) {
                contributions.add(contribution);
            }
        }
        return contributions;
    }

    private static Map<String, String> getCategoryOrderToIndustryName() throws IOException {
        InputStream is = SimpleDataAccessObject.class.getResourceAsStream("/resources/catcodes-20100402.csv");

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

    private static final String GET_CATEGORIES = String.format(
        URI_ACCESS,
        HOST,
        "Votes.getCategories",
        VS_API_KEY,
        "&year=%s"
    );

    public List<Issue> getIssues() throws Exception {
        final String xmlDoc = downloadContent(String.format(
            GET_CATEGORIES,
            2010
        ));

        //        final String xmlDoc = FileUtils.readFileToString(new File("/Users/hujol/Projects/followthemoney/sf/resources/categories.xml"));
        //

        final XPathReader reader = new XPathReader(xmlDoc);
        final NodeList categoryNodes = reader.evaluate("/categories/category", XPathConstants.NODESET);
        final List<Issue> issues = new ArrayList<Issue>(8);
        for(int i = 0; i < categoryNodes.getLength(); i++) {
            Node catNode = categoryNodes.item(i);
            issues.add(new Issue(reader.evaluate("name", catNode)));
        }
        return issues;
    }

    private static final String GET_CANDIDATES = String.format(
        URI_ACCESS,
        HOST,
        "Officials.getStatewide",
        VS_API_KEY,
        ""
    );

    /**
     * Uses the VoteSmart API to get the candidates.
     *
     * @return
     * @throws Exception
     */
    public List<Candidate> getCandidates() throws Exception {
        final String xmlDoc = downloadContent(GET_CANDIDATES);

        return getCandidatesFromXml(xmlDoc);
    }

    private static final String GET_CANDIDATES_BY_ZIP_CODE = String.format(
        URI_ACCESS,
        HOST,
        "Officials.getByZip",
        VS_API_KEY,
        "&zip5="
    );

    /**
     * Uses the VoteSmart API to get the candidates.
     *
     * @param zipCode
     * @return
     * @throws Exception
     */
    public List<Candidate> getCandidates(ZipCode zipCode) throws Exception {
        final String xmlDoc = downloadContent(GET_CANDIDATES_BY_ZIP_CODE + zipCode.getZip5());

        return getCandidatesFromXml(xmlDoc);
    }

    private static final String GET_CANDIDATES_BY_LEVENHSTEIN = String.format(
        URI_ACCESS,
        HOST,
        "Officials.getByLevenshtein",
        VS_API_KEY,
        "&lastName="
    );

    public Collection<Candidate> getCandidates(String s) throws Exception {
        final String xmlDoc = downloadContent(GET_CANDIDATES_BY_LEVENHSTEIN + s);

        return getCandidatesFromXml(xmlDoc);
    }

    private List<Candidate> getCandidatesFromXml(String xmlDoc) throws Exception {
        final XPathReader reader = new XPathReader(xmlDoc);
        final NodeList candidateNodes = reader.evaluate("/candidateList/candidate", XPathConstants.NODESET);
        final List<Candidate> candidates = new ArrayList<Candidate>(8);
        for(int i = 0; i < candidateNodes.getLength(); i++) {
            final Node candidateNode = candidateNodes.item(i);
            candidates.add(new Candidate(
                ((Double) reader.evaluate("candidateId", candidateNode, XPathConstants.NUMBER)).intValue(),
                reader.evaluate("lastName", candidateNode),
                reader.evaluate("firstName", candidateNode)
            ));
        }
        return candidates;
    }

    private static final String GET_BILLS_BY_CANDIDATE_ID = String.format(
        URI_ACCESS,
        HOST,
        "Votes.getByOfficial",
        VS_API_KEY,
        "&year=2008&&candidateId="
    );

    public List<Bill> getBills(Candidate p) throws Exception {
        final String xmlDoc = downloadContent(GET_BILLS_BY_CANDIDATE_ID + p.getId());
        //        final String xmlDoc = FileUtils.readFileToString(new File("/Users/hujol/Projects/followthemoney/sf/resources/bills-votesmart-2008-candid32795.xml"));

        return getBills(xmlDoc);
    }

    private static List<Bill> getBills(String xmlDoc) throws Exception {
        final XPathReader reader = new XPathReader(xmlDoc);
        final NodeList billNodes = reader.evaluate("/bills/bill", XPathConstants.NODESET);

        final List<Bill> bills = new ArrayList<Bill>(billNodes.getLength());
        for(int i = 0; i < billNodes.getLength(); i++) {
            final Node billNode = billNodes.item(i);
            final Bill.BillBuilder builder = new Bill.BillBuilder();

            final String billId = reader.evaluate("billId", billNode);
            setBillInfo(builder, billId);

            final NodeList categories = reader.evaluate("categories/category", billNode, XPathConstants.NODESET);
            for(int j = 0; j < categories.getLength(); j++) {
                final Node categoryNode = categories.item(j);
                final String issueName = reader.evaluate("name", categoryNode);
                builder.addIssue(issueName);
            }

            bills.add(builder.createBill());
        }
        return bills;
    }

    private static void setBillInfo(Bill.BillBuilder builder, String billId) throws Exception {
        final String billInfoXml = downloadContent("http://api.votesmart.org/Votes.getBill?key=1ebd4d39454987ed3d3712cacdfd9e87&billId=" + billId);
        final XPathReader reader = new XPathReader(billInfoXml);

        builder.setBillId(billId);
        builder.setBillNumber(reader.evaluate("/bill/billnumber"));
        builder.setTitle(reader.evaluate("/bill/title"));

        // Compile the expression to get a XPathExpression object.
        final NodeList actions = reader.evaluate("/bill/actions/action", XPathConstants.NODESET);

        for(int i = 0; i < actions.getLength(); i++) {
            final Node actionNode = actions.item(i);
            final String stage = reader.evaluate("stage", actionNode);
            if("introduced".equalsIgnoreCase(stage)) {
                builder.setDateIntroduced(reader.evaluate("statusDate", actionNode));
            }
            else if("Passage".equalsIgnoreCase(stage) || "Amendment Vote".equalsIgnoreCase(stage)) {
                builder.setOutcome(reader.evaluate("outcome", actionNode));
                builder.setOutcomeStatusDate(reader.evaluate("statusDate", actionNode));
            }
        }
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
