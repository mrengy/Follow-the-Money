class IssuePanel {
  
  ControlP5 controlP5;
  Textlabel placeHolder;
  boolean isVisible = false;

  IssuePanel(ControlP5 controlP5) {
    this.controlP5 = controlP5;
    show();
  }

  void show() {
    if (null == placeHolder) {
      placeHolder = controlP5.addTextlabel(
        "placeHolder3",
        "Issues coming soon...", 
        xAlign + lWidth + horizSpacing, 
        zcfY + 5
      ); 
    }
    isVisible = true;
  }
  
  void hide() {
    controlP5.remove("placeHolder3");
    placeHolder = null;
    isVisible = false;
  }
}
