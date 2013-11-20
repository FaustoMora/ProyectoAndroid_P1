package com.android.buscaminas;

import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Point;
import android.view.Menu;
import android.widget.GridLayout;
import android.widget.TableLayout;

@SuppressLint("NewApi") public class MainActivity extends Activity {
	public static final int rows=15;
	public static final int cols=15;
	Map<Point, Celda> map = new HashMap<Point, Celda>();
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TableLayout g = new TableLayout(this);
        GridLayout g =new GridLayout(this);
        g.setColumnCount(cols);
        g.setRowCount(rows);
        //g.setColumnCollapsed(cols, true);
        //g.setRowCount(rows);
        Celda last =null;
        setContentView(g);
        g.isHapticFeedbackEnabled();
        g.isHorizontalScrollBarEnabled();
        g.setScrollContainer(g.isHorizontalScrollBarEnabled());
        g.setScrollContainer(g.isVerticalScrollBarEnabled());
        g.isSoundEffectsEnabled();
        g.setAlignmentMode(0);
        
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {
              Celda current = new Celda(this,i, j);
              g.addView(current);
              map.put(current.getPoint(), current);
            }
          }
        for (Celda celda : map.values()) {
            Point p = celda.getPoint();
            for (int i = p.x-1; i <= p.x+1; ++i) {
              for (int j = p.y-1; j <= p.y+1; ++j) {
                Celda adj = map.get(new Point(i,j));
                if (adj == null)
                  continue;
                adj.addAdjacent(celda);
                celda.addAdjacent(adj);
              }
            }
          }
        
        
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
