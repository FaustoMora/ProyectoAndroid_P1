package com.android.buscaminas;

import java.util.Vector;

import android.app.LauncherActivity.IconResizer;
import android.content.Context;
import android.graphics.Point;
import android.provider.MediaStore.Images;
import android.text.style.IconMarginSpan;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ToggleButton;

public class Celda extends ToggleButton{
	   private boolean bomba;
	   private int x;
	   private int y;
	   private Vector<Celda> adjacents;
	   private static IconResizer icon;
	   private int i = 0;
	   
	   
	  
	
	   public Celda(Context context,int x,int y) {
		super(context);
		// TODO Auto-generated constructor stub
		this.x = x;
	    this.y = y;
	    adjacents = new Vector<Celda>();
	    bomba = Math.random() < 0.15;
	    
	    
	    
	  
		
	}
	
	   private void destapa() {
		    int acum = 0;
		    for (Celda c : adjacents) {
		      if (c.bomba)
		        ++acum;
		    }
		    if (acum == 0) {
		      for (Celda c : adjacents) {
		        if (!c.isSelected()) {
		          c.setSelected(true);
		          c.destapa();
		        }
		      }
		    }
		    if (acum > 0) {
		      setText("" + acum);
		    }
		  }

		  public void addAdjacent(Celda celda) {
		    if (adjacents.contains(celda))
		      return;

		    if (celda != this)
		      adjacents.add(celda);
		  }

		  public Point getPoint() {
		    return new Point(x, y);
		  }
}
