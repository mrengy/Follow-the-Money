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
int horizSpacing = 30;
int vertSpacing = 20;

ControlP5 controlP5;

// Politician panel
PoliticianPanel politicianPanel;



int cnt = 0;
ArrayList contributions = new ArrayList();
DataAccessObject dao = new SimpleDataAccessObject();

void setup() {
  size(800, 500);
  frameRate(30);
  controlP5 = new ControlP5(this);
  
  politicianPanel = new PoliticianPanel(controlP5);
}

void controlEvent(ControlEvent theEvent) {
  // ListBox is if type ControlGroup.
  // 1 controlEvent will be executed, where the event
  // originates from a ControlGroup. therefore
  // you need to check the Event with
  // if (theEvent.isGroup())
  // to avoid an error message from controlP5.

  if ("politicianList".equals(theEvent.name())) {
//    // an event from a group e.g. scrollList
//    int idx = (int)theEvent.group().value();
//    Politician p = (Politician) politicians.get(idx);
//    politicianPanel.setPoliticianSelected(p);
//    updateContributionList(p);
//    updateBillList(p);
  } else if ("zipCodeField".equals(theEvent.name())) {
    String zipCode = theEvent.stringValue();
    println("ZIP code entered: " + zipCode);
    updatePoliticianList(zipCode);
  }
//  println(theEvent);
}

//void updateBillList(Politician politician) {
//  if (!politician.hasId()) {
//    billLabel.setValue("Cannot get politician ID!");
//    return;
//  }
//
//  billLabel.setValue("Politician ID: " + politician.getId());
//  
//  ArrayList bills;
//  try {
//    bills = new ArrayList(dao.getBills(politician));
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

//void updateContributionList(Politician politician) {
//  if (!contributions.isEmpty()) {
//    lc.setText("No politician selected");
//    contributions.clear();
//  }
//  println("politician selected: " + politician);
//  try {
//    contributions.addAll(dao.getContributions(politician.getLastName()));
//  }
//  catch (Throwable e) {
//    e.printStackTrace();
//    textLabel.setValue("Error occured: " + e.getMessage());
//    textLabel.setColorBackground(23);
//    return;
//  }
//  println("politician selected: " + politician);
//
//  StringBuilder sb = new StringBuilder();
//  for (int i = 0; i < contributions.size(); i++){
//    Contribution contribution = (Contribution) contributions.get(i);
//    sb.append(contribution.getContributorName().getIndustryCategory() + ' ' + contribution.getAmountUSDollars() + "\n");
//  }
//  lc.setText(sb.toString());
//}

void updatePoliticianList(String zipCode) {
  ArrayList politicians = new ArrayList();
  politicianPanel.setPoliticianSelected(null);
  try {
    politicians.addAll(dao.getPoliticians(new ZipCode(zipCode)));
  }
  catch (Exception e) {
    e.printStackTrace();
    politicianPanel.setPoliticians((List) null);
    return;
  }
  
  politicianPanel.setPoliticians(politicians);
}

void draw() {
  background(128);
}

