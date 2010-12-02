import org.ftm.util.*;
import org.ftm.impl.*;
import org.ftm.api.*;

/**
 * ControlP5 ListBox
 * listBox operates the same way as a scrollList, but
 * is optimized for many items (1000+). using a listBox
 * over a scrollList is recommended
 * by andreas schlegel, 2009
 */

import controlP5.*;
import org.ftm.api.*;
import java.util.*;
import org.ftm.impl.*;

int xAlign = 20;
static int horizSpacing = 30;
static int vertSpacing = 20;
static int lWidth = 120;
static int lHeight = 100;
static int zcfY = 20;
static int lY = 80;

ControlP5 controlP5;

Navigation navigation;
// Politician panel
PoliticianPanel politicianPanel;
ContributorPanel contributorPanel;
IssuePanel issuePanel;
VisualizationPanel visualizationPanel;
    
int cnt = 0;
ArrayList contributions = new ArrayList();
DataAccessObject dao = new SimpleDataAccessObject();

void setup() {
  size(800, 500);
  frameRate(30);
  controlP5 = new ControlP5(this);
  navigation = new Navigation(controlP5);
  
  politicianPanel = new PoliticianPanel(controlP5);
  politicianPanel.show();
  contributorPanel = new ContributorPanel(controlP5);
  contributorPanel.hide();
  issuePanel = new IssuePanel(controlP5);
  issuePanel.hide();
  visualizationPanel = new VisualizationPanel(controlP5);
  visualizationPanel.hide();
}

void controlEvent(ControlEvent theEvent) {
  // WARNING: ListBox is if type ControlGroup.
  // 1 controlEvent will be executed, where the event
  // originates from a ControlGroup. therefore
  // you need to check the Event with
  // if (theEvent.isGroup())
  // to avoid an error message from controlP5.

  // WARNING: API broken theEvent.id() exists according to the javadoc: http://www.sojamo.de/libraries/controlP5/reference/controlP5/ControlEvent.html#id%28%29
  // but this theEvent.id() fails!! 
  
//  println("***** event: " +theEvent.name());

  // WARNING: the following breaks the code when an event is generated by a ListBox i.e. from the politicianList
  // theEvent.label()
  // theEvent.controller()
  // controlP5 just says that there is an invocationTargetException and no documentation explain why the behavior is not acceptable for a ListBox!
  // Javadoc: ControlEvent.html#controller%28%29
  
  if ("zipCodeField".equals(theEvent.name())) {
    String zipCode = theEvent.stringValue();
    println("ZIP code entered: " + zipCode);
    updatePoliticianList(zipCode);
  } 
  else if ("politicianList".equals(theEvent.name())) {
    println(theEvent.group().value());
    int idx = (int)theEvent.group().value();
    Politician p = politicianPanel.getSelectedPolitician(idx);
    navigation.setPoliticianSelected(p);
//    updateContributionList(p);
//    updateBillList(p);
//    if (politicianPanel.isVisible()) 
//      politicianPanel.hide();
//    else
//      politicianPanel.show();
      
    println("candidate selected " + p);
  }
  else if (1 == theEvent.controller().id()) {
    politicianPanel.show();
    contributorPanel.hide();
    issuePanel.hide();
    visualizationPanel.hide();
  } 
  else if (2 == theEvent.controller().id()) {
    politicianPanel.hide();
    contributorPanel.show();
    issuePanel.hide();
    visualizationPanel.hide();
  } 
  else if (3 == theEvent.controller().id()) {
    politicianPanel.hide();
    contributorPanel.hide();
    issuePanel.show();
    visualizationPanel.hide();
  } 
  else if (4 == theEvent.controller().id()) {
    politicianPanel.hide();
    contributorPanel.hide();
    issuePanel.hide();
    visualizationPanel.show();
  } 
   
//  println(theEvent);
}

//void updateBillList(Politician candidate) {
//  if (!candidate.hasId()) {
//    billLabel.setValue("Cannot get candidate ID!");
//    return;
//  }
//
//  billLabel.setValue("Politician ID: " + candidate.getId());
//  
//  ArrayList bills;
//  try {
//    bills = new ArrayList(dao.getBills(candidate));
//  } catch (Throwable e) {
//    e.printStackTrace();
//    billLabel.setValue("Error occured: " + e.getMessage());
//    billLabel.setColorBackground(23);
//    return;
//  }
//  
//  StringBuilder sb = new StringBuilder();
//  for (int i = 0; i < bills.size(); i++){
//    Bill bill = (Bill) bills.get(i);
//    sb.append(bill.getTitle() + ' ' + bill.getStage() + ' ' + bill.getVote().name() + "\n");
//  }
//  lb.setText(sb.toString());
//}

//void updateContributionList(Politician candidate) {
//  if (!contributions.isEmpty()) {
//    lc.setText("No candidate selected");
//    contributions.clear();
//  }
//  println("candidate selected: " + candidate);
//  try {
//    contributions.addAll(dao.getContributions(candidate.getLastName()));
//  }
//  catch (Throwable e) {
//    e.printStackTrace();
//    textLabel.setValue("Error occured: " + e.getMessage());
//    textLabel.setColorBackground(23);
//    return;
//  }
//  println("candidate selected: " + candidate);
//
//  StringBuilder sb = new StringBuilder();
//  for (int i = 0; i < contributions.size(); i++){
//    Contribution contribution = (Contribution) contributions.get(i);
//    sb.append(contribution.getContributorName().getIndustryCategory() + ' ' + contribution.getAmountUSDollars() + "\n");
//  }
//  lc.setText(sb.toString());
//}

void updatePoliticianList(String zipCode) {
  politicianPanel.show();
  List candidates = new ArrayList();
  Politician p = null;
  navigation.setPoliticianSelected(p);
  try {
    candidates.addAll(dao.getPoliticians(new ZipCode(zipCode)));
  }
  catch (Exception e) {
    e.printStackTrace();
    politicianPanel.setPoliticians(candidates);
    return;
  }
  politicianPanel.setPoliticians(candidates);
}

void draw() {
  background(128);
}
