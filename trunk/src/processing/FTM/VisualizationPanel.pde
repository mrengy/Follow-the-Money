class VisualizationPanel {
  
  ControlP5 controlP5;
  Textlabel placeHolder;
  boolean isVisible = false;

  VisualizationPanel(ControlP5 controlP5) {
    this.controlP5 = controlP5;
    show();
  }

  void show() {
    if (null == placeHolder) {
      placeHolder = controlP5.addTextlabel(
        "placeHolder",
        "Visualization coming soon...", 
        xAlign + lWidth + horizSpacing, 
        zcfY + 5
      ); 
    }
    isVisible = true;
  }
  
  void hide() {
    controlP5.remove("placeHolder");
    placeHolder = null;
    isVisible = false;
  }
}
