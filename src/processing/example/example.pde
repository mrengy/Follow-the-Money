import processing.net.*;
import org.ftm.api.*;

Client c = null;
String data=null;
DataAccessObject dao = new DataAccessObject();
try {
  data = dao.getCandidates();
} 
catch (Exception e) {
  e.printStackTrace();
  exit();
}
//println("Connected to client");
XMLElement xml = null;
//if (0 < c.available()) {
//data = c.readString();
//println(data);
xml = new XMLElement(data);
//}

if (null != xml) {
  int numSites = xml.getChildCount();
  for (int i = 0; i < numSites; i++) {
    XMLElement kid = xml.getChild(i);
    int id = kid.getIntAttribute("imsp_candidate_id");
    String name = kid.getStringAttribute("candidate_name"); 
    println(name + " - " + id);    
  }
}


