class IssuePanel {
  
    Textlabel billLabel;

  IssuePanel(ControlP5 controlP5) {
      billLabel = controlP5.addTextlabel(
      "billLabel",
      "No politician ID available", xAlign + FTM.lWidth + horizSpacing + 300 + horizSpacing, lY - 10); 
  }
}
