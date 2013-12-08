package com.android.buscaminas;

public class Celda {
    public int x,y,ancho;
    public int contenido=0; //si tiene bomba o carga
    public boolean destapado=false;
    boolean banderaCelda=false;
    
    
    public void fijarxy(int x,int y, int ancho) {
        this.x=x;
        this.y=y;
        this.ancho=ancho;
    }
    
    public boolean dentro(int xx,int yy) {
        if (xx>=this.x && xx<=this.x+ancho && yy>=this.y && yy<=this.y+ancho) 
            return true;
        else
            return false;
    }
}