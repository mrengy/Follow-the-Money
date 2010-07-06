import processing.net.*;
import org.ftm.api.*;
import java.util.*;
import org.ftm.impl.*;

// Client c = null;
DataAccessObject dao = new DataAccessObjectVSRest();
java.util.List<Politician> politicians=null;
try {
  politicians = new ArrayList(dao.getPoliticians());
} 
catch (Exception e) {
  e.printStackTrace();
  exit();
}
/*for (Issue issue : issues) ....{
}
*/

for (int i = 0; i < politicians.size(); i++){
  Politician politician = politicians.get(i);
  println(" Politician - " + politician.getName());    
}

