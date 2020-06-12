package com.example.dressmeapp.Objetos;

public class ComboColorPrenda {

    private int id;
    private ColorPrenda color1;
    private ColorPrenda color2;

    public ComboColorPrenda(int id, ColorPrenda color1, ColorPrenda color2) {
        this.id = id;
        this.color1 = color1;
        this.color2 = color2;
    }

    public int getId() { return id; }
    public ColorPrenda getColor1() { return color1; }
    public ColorPrenda getColor2() { return color2; }

}
