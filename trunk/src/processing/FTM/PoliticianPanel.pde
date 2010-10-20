class PoliticianPanel {
  
  ListBox l;
  Textarea lc;
  Textarea lb;
  Textfield zipCodeField;
  Textlabel textLabel;
  Textlabel politicianLabel;
  List politicians = new ArrayList();
  ControlP5 controlP5;
  boolean isVisible = false;

  PoliticianPanel(ControlP5 controlP5) {
    this.controlP5 = controlP5;
    show();
  }
  
  void show() {
    if (null == zipCodeField) {
      zipCodeField = controlP5.addTextfield("zipCodeField", xAlign, zcfY, 120, 20);
      zipCodeField.setAutoClear(false);
      zipCodeField.setLabel("ZIP Code");
    }
  
    textLabel = controlP5.addTextlabel("textLabel", "0 politicians found", xAlign + zipCodeField.getWidth() + horizSpacing, zcfY + 5); 
  
    if (null == l) {
        l = controlP5.addListBox("politicianList", xAlign, lY, lWidth, lHeight);
        l.setItemHeight(15);
        l.setBarHeight(15);
    }
  
    l.captionLabel().toUpperCase(true);
    l.captionLabel().set("Politicians");
    l.captionLabel().style().marginTop = 3;
    l.valueLabel().style().marginTop = 3; // the +/- sign
  
    l.setColorBackground(color(255,128));
    l.setColorActive(color(0,0,255,128));
  
    lc = controlP5.addTextarea("contributionList", "No politician selected", xAlign + lWidth + horizSpacing, lY + vertSpacing + textLabel.getHeight(), 300, 250);
  
    lc.captionLabel().toUpperCase(true);
    lc.captionLabel().set("Contributions");
    lc.captionLabel().style().marginTop = 3;
    lc.valueLabel().style().marginTop = 3; // the +/- sign
  
    lc.setColorBackground(color(255,128));
    lc.setColorActive(color(0,0,255,128));
  
    politicianLabel = controlP5.addTextlabel("politicianSelected", "No politician selected", xAlign + lWidth + horizSpacing, lY - 10); 
  
    lb = controlP5.addTextarea("billList", "No politician selected", (xAlign + lWidth + horizSpacing) + 300 + horizSpacing, lY + vertSpacing + textLabel.getHeight(), 300, 250);
  
    lb.captionLabel().toUpperCase(true);
    lb.captionLabel().set("Bills");
    lb.captionLabel().style().marginTop = 3;
    lb.valueLabel().style().marginTop = 3; // the +/- sign
  
    lb.setColorBackground(color(255,128));
    lb.setColorActive(color(0,0,255,128));
    isVisible = true;
  }
  
  boolean isVisible() {
    return isVisible;
  }
  
  void hide() {
//    controlP5.remove("zipCodeField");
//    zipCodeField = null;
    controlP5.remove("textLabel");
//    controlP5.remove("politicianList");
    controlP5.remove("contributionList");
    controlP5.remove("politicianSelected");
    controlP5.remove("billList");
    isVisible = false;
  }
  
  Politician getSelectedPolitician(int index) {
    return (Politician) this.politicians.get(index);
  }
  
  void setPoliticianSelected(Politician p) {
    String text;
    if (null == p) {
      text = "No politician selected";
      if (!politicians.isEmpty()) {
        for (int i = 0; i < politicians.size(); i++){
          Politician politician = (Politician) politicians.get(i);
          l.removeItem(politician.getFirstName() + ' ' + politician.getLastName());
        }
      }
    } else {
      String politicianFullName = p.getFirstName() + ' ' + p.getLastName();
  //    println("Politician selected: " + politicianFullName);
      text = "Politician selected: " + politicianFullName;
    }
    politicianLabel.setValue(text);
  }
  
  void setPoliticians(List politicians) {
    if (null == politicians) {
      textLabel.setValue("No politicians available");
    } else {
      this.politicians = politicians;
      for (int i = 0; i < politicians.size(); i++){
        Politician politician = (Politician) politicians.get(i);
        l.addItem(politician.getFirstName() + ' ' + politician.getLastName(), i);
      }
      textLabel.setValue(politicians.size() + " politicians found");
    }
  }
}
