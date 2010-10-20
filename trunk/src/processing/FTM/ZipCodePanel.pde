class ZipCodePanel {
  
  Textfield zipCodeField;

  ZipCodePanel(ControlP5 controlP5) {
    if (null == zipCodeField) {
      zipCodeField = controlP5.addTextfield("zipCodeField", xAlign, zcfY, 120, 20);
      zipCodeField.setAutoClear(false);
      zipCodeField.setLabel("ZIP Code");
    }
  }
}
