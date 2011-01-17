import org.gicentre.utils.stat.*;        // For chart classes.


// --------------------- Sketch-wide variables ----------------------

BarChart barChart; 
Bill bill1;
Bill bill2;
Bill bill3;
Bill bill4;
Bill bill5;
Bill bill6;
Vote vote1;
Vote vote2;
Vote vote3;
Vote vote4;
Vote vote5;
Vote vote6;
Vote vote2_1;
Vote vote3_1;
Vote vote3_2;
Vote vote4_1;
Vote vote5_1;

//variables for spacing
  //top of the votes graphic
  float votesY = 500;
  
  //vertical axis of votes graphic
  float votesX = 602;
  
  //line height for votes graphic
  float lineHeight = 18;
  
  //default text size
  float defaultTextSize = 12;


// ------------------------ Initialization --------------------------

// Initialises the data and bar chart.
void setup()
{
  size(1000,700);
  smooth();
  
  PFont font = createFont("Helvetica",11);
  //createFont method may be contributing to performance issues. may want to use loadFont() instead.
  textFont(font,10);

  //set parameters for bar chart for contributions
  barChart = new BarChart(this);
  barChart.setData(new float[] {246268768,280196760,4000000,200000000});
  barChart.setBarLabels(new String[] {"2007","2008","2009","2010"});
  barChart.setBarColour(color(300,600,0,200));
  barChart.setBarGap(2); 
  barChart.setValueFormat("$###,###");
  barChart.showValueAxis(true); 
  barChart.showCategoryAxis(true); 
  
  //set parameters for bills
  bill1 = new Bill("HR 1207","Treatment of Human Embryos","yes",10,30,1);
  bill2 = new Bill("HR 1211","Medicare Payment Adjustment","no",20,42,2);
  bill3 = new Bill("HR 1264","Expressing Support for the designation of March as National Essential Tremor Awareness Month","non-vote",40,102,3);
  bill4 = new Bill("HR 1290","Supporting the Observance of American Diabetes Month","no",200,232,4);
  bill5 = new Bill("HR 1311","Pysician Payment and Therapy Relief Act of 2010","non-vote",280,332,5);
  bill6 = new Bill("HR 1342","Supporting the Goals and Ideals of a National Mesothelioma Awareness Day","yes",100,350,6);
  
  //set parameters for votes
  vote1 = new Vote("no",20,1);
  vote2 = new Vote("non-vote",30,2);
  vote3 = new Vote("yes",62,3);
  vote4 = new Vote("no",232,4);
  vote5 = new Vote("non-vote",302,5);
  vote6 = new Vote("yes",350,6);
  
  vote2_1 = new Vote("yes",42,2);
  vote3_1 = new Vote("no",40,3);
  vote3_2 = new Vote("yes",102,3);
  vote4_1 = new Vote("non-vote",200,4);
  vote5_1 = new Vote("yes",332,5);
}

// ------------------ Processing draw --------------------

// Draws the graph in the sketch.
void draw()
{
  background(255);
  
  //draw bar chart for contributions
  barChart.draw(535,50,470,380);
  fill(120);
  textSize(22);
  text("Contributions Received per Year", 602,20);
  float textHeight = textAscent();
  textSize(defaultTextSize);
  
  //draw label for votes graphic
  textSize(22);
  text("Votes by date", votesX, (votesY - (lineHeight/2)));
  textSize(defaultTextSize);
  
  //draw bills
  bill1.display();
  bill2.display();
  bill3.display();
  bill4.display();
  bill5.display();
  bill6.display();
  
  //draw votes
  vote1.display();
  vote2.display();
  vote3.display();
  vote4.display();
  vote5.display();
  vote6.display();
  
  vote2_1.display();
  vote3_1.display();
  vote3_2.display();
  vote4_1.display();
  vote5_1.display();
}

// ------------------ Class definition --------------------
// Bill
class Bill{
  String billCode;
  String billName;
  String shortBillName; //shortened bill name
  String result; //yes, no, or non-resolved
  float startDate; //determines x position of start. CURRENTLY INPUT AS AN X-POSITION - NEEDS TO CONVERT A DATE TO X-POSITION
  float endDate; //determines x position of end. CURRENTLY INPUT AS AN X-POSITION - NEEDS TO CONVERT A DATE TO X-POSITION
  float lineNum; //determines line on which to place each bill
  
  //constructor with arguments
  Bill(String tempBillCode, String tempBillName, String tempResult, float tempStartDate, float tempEndDate, float tempLineNum){
   billCode = tempBillCode;
   billName = tempBillName;
   shortBillName = billName.substring(0,26);
   result = tempResult;
   startDate = tempStartDate;
   endDate = tempEndDate;
   lineNum = tempLineNum;   
  }
  
  void display(){
   //sets y Position for each item
   float yPos = votesY + (lineHeight*lineNum);
   
   //sets width for each item
   float billWidth = endDate - startDate;
   
   //sets x Position for each item
   float xPos = votesX + startDate + (billWidth / 2);
   
   //outputs text of bill
     
     //sets text to right
     textAlign(RIGHT, CENTER);
     
     //resets fill color to gray
     fill(120);
     
     //writes text
     if (billName.length() > 26){ //takes shortened version if longer than 27 characters
       text(billCode + ", " + shortBillName + "...", votesX, yPos);
     }
     else{
       text(billCode + ", " + billName, votesX, yPos);
     }
     
     //rests text to left
     textAlign(LEFT);
   
   //begins to draw ellipse - other commands follow in if statement below  
   rectMode(CENTER);
   noStroke();
   
   
   // if vote is yes, draw green circle
   if(result.toLowerCase().equals("yes")){
     fill(0,255,0);
   }
   
   // if vote is no, draw red circle
   else if (result.toLowerCase().equals("no")){
     fill(255,0,0);
   }
   
   // if vote is any other value (e.g. a non-vote), draw gray circle
   else {
     fill(150);
   }
   
   //runs for every case
   rect(xPos,yPos,billWidth,defaultTextSize);
   
   //resets rectangle mode
   rectMode(CORNER);
   
   //resets stroke
   stroke(0);
  }
}

// Vote
class Vote{
  String result; //yes, no, or non-vote
  float date; //determines x position of ellipse. CURRENTLY INPUT AS AN X-POSITION - NEEDS TO CONVERT A DATE TO X-POSITION
  float lineNum; //determines line on which to place each vote
  
  //constructor with arguments
  Vote(String tempResult, float tempDate, float tempLineNum){
   result = tempResult;
   date = tempDate; 
   lineNum = tempLineNum;
   
  }
  
  void display(){
   //sets y Position for each item
   float yPos = votesY + (lineHeight*lineNum);
   
   //sets x Position for each item
   float xPos = votesX + date; 
   
   //begins to draw ellipse - other commands follow in if statement below  
   ellipseMode(CENTER);
   stroke(255);
   strokeWeight(1.5);
   
   
   // if vote is yes, draw green circle
   if(result.toLowerCase().equals("yes")){
     fill(0,255,0);
   }
   
   // if vote is no, draw red circle
   else if (result.toLowerCase().equals("no")){
     fill(255,0,0);
   }
   
   // if vote is any other value (e.g. a non-vote), draw gray circle
   else {
     fill(150);
   }
   
   //runs for all cases
   ellipse(xPos,yPos,7,7);
   
   //resets stroke
   stroke(120);
   strokeWeight(1);
  }
}
