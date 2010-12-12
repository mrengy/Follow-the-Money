import org.gicentre.utils.stat.*;        // For chart classes.

// Sketch to demonstrate the use of the BarChart class to draw simple bar charts.

// --------------------- Sketch-wide variables ----------------------

BarChart barChart;

// ------------------------ Initialisation --------------------------

// Initialises the data and bar chart.
void setup()
{
  size(500,400);
  smooth();
  
  PFont font = createFont("Helvetica",11);
  textFont(font,10);

  barChart = new BarChart(this);
  barChart.setData(new float[] {246268768,280196760,4000000,200000000});
  barChart.setBarLabels(new String[] {"2007","2008","2009","2010"});
  barChart.setBarColour(color(300,600,0,200));
  barChart.setBarGap(2); 
  barChart.setValueFormat("$###,###");
  barChart.showValueAxis(true); 
  barChart.showCategoryAxis(true); 
}

// ------------------ Processing draw --------------------

// Draws the graph in the sketch.
void draw()
{
  background(255);
  
  barChart.draw(10,10,width-30,height-20);
  fill(120);
  textSize(22);
  text("Contributions", 200,30);
  float textHeight = textAscent();
  textSize(12);
}
