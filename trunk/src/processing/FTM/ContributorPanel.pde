class ContributorPanel {

  ControlP5 controlP5;
  Textlabel placeHolder;
  boolean isVisible = false;

  ContributorPanel(ControlP5 controlP5) {
    this.controlP5 = controlP5;
    show();
  }

  void show() {
    if (null == placeHolder) {
      placeHolder = controlP5.addTextlabel(
        "placeHolder2",
        "Contributors coming soon...", 
        xAlign + lWidth + horizSpacing, 
        zcfY + 5
      ); 
    }
    isVisible = true;
  }
  
  void hide() {
    controlP5.remove("placeHolder2");
    placeHolder = null;
    isVisible = false;
  }
}
