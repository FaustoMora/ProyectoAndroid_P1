package com.android.buscaminas;

public class Celda {
    public int x,y,tama�o;
    public int content=0;
    public boolean descubierto=false;
    boolean banderaCelda=false;
    
    public void fijarxy(int x,int y, int tama�ocell) {
        this.x=x;
        this.y=y;
        this.tama�o=tama�ocell;
    }
    
    public boolean limites(int valorX,int valorY) {
        if (valorX>=this.x && valorX<=this.x+tama�o 
        		&& valorY>=this.y && valorY<=this.y+tama�o) 
            return true;
        else
            return false;
    }
}