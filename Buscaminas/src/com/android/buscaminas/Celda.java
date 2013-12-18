package com.android.buscaminas;

public class Celda {
    public int x,y,tamaño;
    public int content=0;
    public boolean descubierto=false;
    boolean banderaCelda=false;
    
    public void fijarxy(int x,int y, int tamañocell) {
        this.x=x;
        this.y=y;
        this.tamaño=tamañocell;
    }
    
    public boolean limites(int valorX,int valorY) {
        if (valorX>=this.x && valorX<=this.x+tamaño 
        		&& valorY>=this.y && valorY<=this.y+tamaño) 
            return true;
        else
            return false;
    }
}