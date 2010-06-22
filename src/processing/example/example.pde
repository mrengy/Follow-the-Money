import processing.net.*;
import org.ftm.api.*;
import java.util.*;
import org.ftm.impl.*;

Client c = null;
java.util.List<Issue> issues=null;
DataAccessObject dao = new DataAccessObjectTextFile();
try {
  issues = new ArrayList(dao.getIssues());
} 
catch (Exception e) {
  e.printStackTrace();
  exit();
}
/*for (Issue issue : issues) ....{
}
*/

for (int i = 0; i < issues.size(); i++){
  Issue issue = issues.get(i);
  println(" - " + issue.getDescription());    
}

