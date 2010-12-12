import org.gicentre.utils.stat.*;        // For chart classes.


// --------------------- Sketch-wide variables ----------------------

BarChart barChart; 
Vote vote1;
Vote vote2;
Vote vote3;
Vote vote4;
Vote vote5;
Vote vote6;

//variables for spacing
  //top of the votes graphic
  float votesY = 500;
  
  //vertical axis of votes graphic
  float votesX = 602;
  
  //line height for votes graphic
  float lineHeight = 18;


// ------------------------ Initialization --------------------------

// Initialises the data and bar chart.
void setup()
{
  size(1000,700);
  smooth();
  
  PFont font = createFont("Helvetica",11);
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
  
  //set parameters for vote 1
  vote1 = new Vote("HR 1207","Treatment of Human Embryos","yes",10,1);
  
  //set parameters for vote 2
  vote2 = new Vote("HR 1211","Medicare Payment Adjustment","no",42,2);
  
  //set parameters for vote 3
  vote3 = new Vote("HR 1264","Expressing Support for the designation of March as National Essential Tremor Awareness Month","non-vote",102,3);
  
  //set parameters for vote 4
  vote4 = new Vote("HR 1290","Supporting the Observance of American Diabetes Month","no",232,4);
  
  //set parameters for vote 5
  vote5 = new Vote("HR 1311","Pysician Payment and Therapy Relief Act of 2010","non-vote",332,5);
  
  //set parameters for vote 6
  vote6 = new Vote("HR 1342","Supporting the Goals and Ideals of a National Mesothelioma Awareness Day","yes",350,6);
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
  textSize(12);
  
  //draw label for votes graphic
  textSize(22);
  text("Votes by date", votesX, (votesY - (lineHeight/2)));
  textSize(12);
  
  //draw votes
  vote1.display();
  vote2.display();
  vote3.display();
  vote4.display();
  vote5.display();
  vote6.display();
}

// ------------------ Class definition --------------------
// Vote
class Vote{
  String billCode;
  String billName;
  String result; //yes, no, or non-vote
  float date; //determines x position of ellipse. CURRENTLY INPUT AS AN X-POSITION - NEEDS TO CONVERT A DATE TO X-POSITION
  float lineNum; //determines line on which to place each vote
  
  //constructor with arguments
  Vote(String tempBillCode, String tempBillName, String tempResult, float tempDate, float tempLineNum){
   billCode = tempBillCode;
   billName = tempBillName;
   result = tempResult;
   date = tempDate; 
   lineNum = tempLineNum;
   
  }
  
  void display(){
   //sets y Position for each item
   float yPos = votesY + (lineHeight*lineNum);
   
   //sets x Position for each item
   float xPos = votesX + date; 
   
   //outputs text of bill
     
     //sets text to right
     textAlign(RIGHT, CENTER);
     
     //resets fill color to gray
     fill(120);
     
     //writes text
     text(billCode + ", " + billName, votesX, yPos);
     
     //rests text to left
     textAlign(LEFT);
   
   //begins to draw ellipse - other commands follow in if statement below  
   ellipseMode(CENTER);
   stroke(120);
   
   
   // if vote is yes, draw green circle
   if(result.toLowerCase().equals("yes")){
     fill(0,255,0);
     ellipse(xPos,yPos,12,12);
   }
   
   // if vote is no, draw red circle
   else if (result.toLowerCase().equals("no")){
     fill(255,0,0);
     ellipse(xPos,yPos,12,12);
   }
   
   // if vote is any other value (e.g. a non-vote), draw gray circle
   else {
     fill(150);
     ellipse(xPos,yPos,12,12);
   }
   
  }
}
