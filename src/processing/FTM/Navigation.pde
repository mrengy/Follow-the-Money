class Navigation {

  Button politicianLabel;
  Button contributorButton;
  Button issueButton;
  Button visualizeButton;

  ControlP5 controlP5;
  boolean isVisible = false;

  Navigation(ControlP5 controlP5) {
    this.controlP5 = controlP5;
    show();
  }
  
  void show() {
    
    if (null == politicianLabel) {
      politicianLabel = controlP5.addButton("No candidate selected");
      politicianLabel.setId(1); 
      politicianLabel.setWidth(120); 
      politicianLabel.setPosition(xAlign, zcfY); 
    }
    
    if (null == contributorButton) {
      contributorButton = controlP5.addButton("No contributor selected");
      contributorButton.setId(2); 
      contributorButton.setWidth(120); 
      contributorButton.setPosition(xAlign, zcfY * 2 + vertSpacing); 
    }
    if (null == issueButton) {
      issueButton = controlP5.addButton("No issue selected");
      issueButton.setId(3); 
      issueButton.setWidth(120); 
      issueButton.setPosition(xAlign, zcfY * 3 + 2 * vertSpacing); 
    }
    if (null == visualizeButton) {
      visualizeButton = controlP5.addButton("Visualize");
      visualizeButton.setId(4); 
      visualizeButton.setWidth(120); 
      visualizeButton.setPosition(xAlign, zcfY * 4 + 3 * vertSpacing); 
    }
  }   
 
  void setPoliticianSelected(Politician p) {
    String text;
    if (null == p) {
      text = "No candidate selected";
    } else {
    String politicianFullName = p.getFirstName() + ' ' + p.getLastName();
      //    println("Politician selected: " + politicianFullName);
      text = politicianFullName;
    }
    politicianLabel.setLabel(text);
  }
}

