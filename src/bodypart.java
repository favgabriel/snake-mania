import java.awt.*;

public class bodypart {
    private int xCoor,yCoor,width,height;
    public bodypart(int xCoor,int  yCoor,int tilesize){
        this.xCoor = xCoor;
        this.yCoor = yCoor;
        width = tilesize;
        height = tilesize;
    }
    public void tick(){}
    public void draw (Graphics g){
        g.setColor(Color.black);
        g.fill3DRect(xCoor*width,yCoor*height,width,height,true);
    }

    public int getxCoor() {
        return xCoor;
    }

    public void setxCoor(int xCoor) {
        this.xCoor = xCoor;
    }

    public int getyCoor() {
        return yCoor;
    }

    public void setyCoor(int yCoor) {
        this.yCoor = yCoor;
    }
}
