package org.ftm.ui;

import processing.core.PApplet;

/**
 * This class is the Processing Applet allowing the visualization.
 *
 * @author <font size=-1 color="#a3a3a3">Johnny Hujol</font>
 * @since Oct 26, 2010
 */
final class VisualizationPanel extends PApplet {
    float px, py, px2, py2;
    float angle, angle2;
    float radius = 100;
    float frequency = 2;
    float frequency2 = 2;
    float x, x2;

    public void setup() {
        // original setup code here ...
//        size(400, 400);

        // prevent thread from starving everything else
        noLoop();
    }

    public void draw() {
        background(127);
        noStroke();
        fill(255);
        ellipse(width / 8, 75, radius, radius);
        // rotates rectangle around circle
        px = width / 8 + cos(radians(angle)) * (radius / 2);
        py = 75 + sin(radians(angle)) * (radius / 2);
        rectMode(CENTER);
        fill(0);
        //draw rectangle
        rect(px, py, 5, 5);
        stroke(100);
        line(width / 8, 75, px, py);
        stroke(200);

        // keep reinitializing to 0, to avoid
        // flashing during redrawing
        angle2 = 0;

        // draw static curve - y = sin(x)
        for(int i = 0; i < width; i++) {
            px2 = width / 8 + cos(radians(angle2)) * (radius / 2);
            py2 = 75 + sin(radians(angle2)) * (radius / 2);
            point(width / 8 + radius / 2 + i, py2);
            angle2 -= frequency2;
        }

        // send small ellipse along sine curve
        // to illustrate relationship of circle to wave
        noStroke();
        ellipse(width / 8 + radius / 2 + x, py, 5, 5);
        angle -= frequency;
        x += 1;

        // when little ellipse reaches end of window
        // reinitialize some variables
        if(x >= width - 60) {
            x = 0;
            angle = 0;
        }

        // draw dynamic line connecting circular
        // path with wave
        stroke(50);
        line(px, py, width / 8 + radius / 2 + x, py);

        // output some calculations
        text("y = sin x", 35, 185);
        text("px = " + px, 105, 185);
        text("py = " + py, 215, 185);
    }

    public void mousePressed() {
        // do something based on mouse movement

        // update the screen (run draw once)
        redraw();
    }
}
