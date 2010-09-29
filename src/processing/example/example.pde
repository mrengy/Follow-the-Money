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
import processing.net.*;
import org.ftm.api.*;
import java.util.*;
import org.ftm.impl.*;

int xAlign = 20;
int horizSpacing = 30;

ControlP5 controlP5;
ListBox l;
Textfield zipCodeField;
Textlabel textLabel;
Textlabel politicianLabel;

int cnt = 0;
ArrayList politicians = new ArrayList();
DataAccessObject dao = new SimpleDataAccessObject();

void setup() {
  size(600, 400);
  frameRate(30);
  controlP5 = new ControlP5(this);
  int zcfY = 20;
  zipCodeField = controlP5.addTextfield("zipCodeField", xAlign, zcfY, 120, 20);
  zipCodeField.setAutoClear(false);
  zipCodeField.setLabel("ZIP Code");
  
  textLabel = controlP5.addTextlabel("textLabel", "0 politicians found", xAlign + zipCodeField.getWidth() + horizSpacing, zcfY + 5); 

  int lWidth = 120;
  int lY = 80;
  l = controlP5.addListBox("politicianList", xAlign, lY, lWidth, 100);
  l.setItemHeight(15);
  l.setBarHeight(15);

  l.captionLabel().toUpperCase(true);
  l.captionLabel().set("Politicians");
  l.captionLabel().style().marginTop = 3;
  l.valueLabel().style().marginTop = 3; // the +/- sign
  //l.setBackgroundColor(color(100,0,0));

  // Client c = null;
//  try {
//    politicians.addAll(dao.getPoliticians(new ZipCode("90007")));
//  }
//  catch (Exception e) {
//    e.printStackTrace();
//    exit();
//  }
  /*for (Issue issue : issues) ....{
   }
   */

//  for (int i = 0; i < politicians.size(); i++){
//    Politician politician = (Politician) politicians.get(i);
//    l.addItem(politician.getFirstName() + ' ' + politician.getLastName(), i);
//  }
  l.setColorBackground(color(255,128));
  l.setColorActive(color(0,0,255,128));

  politicianLabel = controlP5.addTextlabel("politicianSelected", "No politician selected", xAlign + lWidth + horizSpacing, lY - 10); 
}

void keyPressed() {
  if(key=='1') {
    // set the height of a listBox should alwyays be a multiple of itemHeight
    l.setHeight(210);
  }
  else if(key=='2') {
    // set the height of a listBox should alwyays be a multiple of itemHeight
    l.setHeight(120);
  }
  else if(key=='i') {
    // set the height of a listBoxItem, should alwyays be a fraction of the listBox
    l.setItemHeight(30);
  }
  else if(key=='u') {
    // set the height of a listBoxItem, should alwyays be a fraction of the listBox
    l.setItemHeight(10);
    l.setBackgroundColor(color(100,0,0));
  }
  else if(key=='a') {
    int n = (int)(random(100000));
    l.addItem("item "+n, n);
  }
  else if(key=='d') {
    l.removeItem("item "+cnt);
    cnt++;
  }
}

void controlEvent(ControlEvent theEvent) {
  // ListBox is if type ControlGroup.
  // 1 controlEvent will be executed, where the event
  // originates from a ControlGroup. therefore
  // you need to check the Event with
  // if (theEvent.isGroup())
  // to avoid an error message from controlP5.

  if ("politicianList".equals(theEvent.name())) {
    // an event from a group e.g. scrollList
    int idx = (int)theEvent.group().value();
    Politician p = (Politician) politicians.get(idx);
    String politicianFullName = p.getFirstName() + ' ' + p.getLastName();
//    println("Politician selected: " + politicianFullName);
    politicianLabel.setValue("Politician selected: " + politicianFullName);
  } else if ("zipCodeField".equals(theEvent.name())) {
    String zipCode = zipCodeField.getText();
    println("ZIP code entered: " + zipCode);
    updatePoliticianList(zipCode);
  }
//  println(theEvent);
}

void updatePoliticianList(String zipCode) {
  if (!politicians.isEmpty()) {
    for (int i = 0; i < politicians.size(); i++){
      Politician politician = (Politician) politicians.get(i);
      l.removeItem(politician.getFirstName() + ' ' + politician.getLastName());
    }
    politicians.clear();
  }
  politicianLabel.setValue("No politician selected");
  try {
    politicians.addAll(dao.getPoliticians(new ZipCode(zipCode)));
  }
  catch (Exception e) {
    e.printStackTrace();
    textLabel.setValue("Error occured: " + e.getMessage());
    textLabel.setColorBackground(23);
    return;
  }
  
  for (int i = 0; i < politicians.size(); i++){
    Politician politician = (Politician) politicians.get(i);
    l.addItem(politician.getFirstName() + ' ' + politician.getLastName(), i);
  }
  textLabel.setValue(politicians.size() + " politicians found");
}


void draw() {
  background(128);
  // scroll the scroll List according to the mouseX position
  // when holding down SPACE.
  if(keyPressed && key==' ') {
    //l.scroll(mouseX/((float)width)); // scroll taks values between 0 and 1
  }
  if(keyPressed && key==' ') {
    l.setWidth(mouseX);
  }
}
