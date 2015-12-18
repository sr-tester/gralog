/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gralog.rendering;

/**
 *
 * @author viktor
 */
public class GralogColor {
    
    public short r;
    public short g;
    public short b;
    
    public GralogColor(short red, short green, short blue)
    {
        this.r = red;
        this.g = green;
        this.b = blue;
    }
    
    public GralogColor(int rgb)
    {
        this((short)((rgb >> 16) & 0xFF),
             (short)((rgb >> 8) & 0xFF),
             (short)(rgb & 0xFF));
    }
    
    public boolean equals(GralogColor c)
    {
        return this.r==c.r
            && this.g == c.g
            && this.b == c.b;
    }
    
    
    static final GralogColor black = new GralogColor(0x000000);
    static final GralogColor red   = new GralogColor(0xFF0000);
    static final GralogColor green = new GralogColor(0x00FF00);
    static final GralogColor blue  = new GralogColor(0x0000FF);
    static final GralogColor white = new GralogColor(0xFFFFFF);
}
