class PoliticianPanel {
  
  Textfield zipCodeField;
  Textfield nameField;

  ListBox l;
  Textarea lc;
  Textarea lb;
  Textlabel textLabel;
  List politicians = new ArrayList();
  ControlP5 controlP5;
  boolean isVisible = false;

  PoliticianPanel(ControlP5 controlP5) {
    this.controlP5 = controlP5;
    show();
  }
  
  void show() {
    if (null == zipCodeField) {
      zipCodeField = controlP5.addTextfield("zipCodeField", xAlign + lWidth + horizSpacing, zcfY, 120, 20);
      zipCodeField.setAutoClear(false);
      zipCodeField.setLabel("ZIP Code");
    }
    
    if (null == nameField) {
      nameField = controlP5.addTextfield("nameField", xAlign + 2 * lWidth + 2 * horizSpacing, zcfY, 120, 20);
      nameField.setAutoClear(false);
      nameField.setLabel("Name");
    }

    if (null == textLabel) {
      textLabel = controlP5.addTextlabel("textLabel", "0 politicians found", xAlign + lWidth + horizSpacing, zcfY * 2 + vertSpacing + 10);
    }
  
    if (null == l) {
        l = controlP5.addListBox("politicianList", xAlign + lWidth + horizSpacing, lY + vertSpacing, lWidth, lHeight);
        l.setItemHeight(15);
        l.setBarHeight(15);
    }
  
    l.captionLabel().toUpperCase(true);
    l.captionLabel().set("Politicians");
    l.captionLabel().style().marginTop = 3;
    l.valueLabel().style().marginTop = 3; // the +/- sign
  
    l.setColorBackground(color(255,128));
    l.setColorActive(color(0,0,255,128));
  
//    lc = controlP5.addTextarea("contributionList", "No politician selected", xAlign + lWidth + horizSpacing, lY, 300, 250);
//  
//    lc.captionLabel().toUpperCase(true);
//    lc.captionLabel().set("Contributions");
//    lc.captionLabel().style().marginTop = 3;
//    lc.valueLabel().style().marginTop = 3; // the +/- sign
//  
//    lc.setColorBackground(color(255,128));
//    lc.setColorActive(color(0,0,255,128));
  
//    lb = controlP5.addTextarea("billList", "No politician selected", (xAlign + lWidth + horizSpacing) + 300 + horizSpacing, lY + vertSpacing + lHeight, 300, 250);
//  
//    lb.captionLabel().toUpperCase(true);
//    lb.captionLabel().set("Bills");
//    lb.captionLabel().style().marginTop = 3;
//    lb.valueLabel().style().marginTop = 3; // the +/- sign
//  
//    lb.setColorBackground(color(255,128));
//    lb.setColorActive(color(0,0,255,128));
    isVisible = true;
  }
  
  boolean isVisible() {
    return isVisible;
  }
  
  void hide() {
    controlP5.remove("zipCodeField");
    zipCodeField = null;
    controlP5.remove("nameField");
    nameField = null;
    controlP5.remove("textLabel");
    textLabel = null;
    controlP5.remove("politicianList");
    l = null;
    isVisible = false;
  }
  
  Politician getSelectedPolitician(int index) {
    return (Politician) this.politicians.get(index);
  }

  String text;
  
  void setPoliticians(List politicians) {
    if (null == politicians) {
      textLabel.setValue("No politicians available");
    } else {
      println(l);
      for (int i = 0; i < this.politicians.size(); i++){
        Politician politician = (Politician) this.politicians.get(i);
        l.removeItem(politician.getFirstName() + ' ' + politician.getLastName());
      }
      this.politicians = politicians;
      for (int i = 0; i < politicians.size(); i++){
        Politician politician = (Politician) politicians.get(i);
        l.addItem(politician.getFirstName() + ' ' + politician.getLastName(), i);
      }
      textLabel.setValue(politicians.size() + " politicians found");
    }
  }
}

