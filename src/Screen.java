import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Screen {
    private GraphicsDevice vc;
    public Screen(){
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        vc = env.getDefaultScreenDevice();
    }
    public DisplayMode[] getcurrentmode(){
        return vc.getDisplayModes();
    }
    public DisplayMode setcompatibledisplaymode(DisplayMode[] mode){
        DisplayMode [] goodmode = vc.getDisplayModes();
        for (int x = 0; x<goodmode.length; x++){
            for (int y =0; y<mode.length; y++){
                if (displaymodecompatible(goodmode[y],mode[x])){
                    return mode[x];
                }
            }
        }
        return null;
    }
    public boolean displaymodecompatible(DisplayMode m1, DisplayMode m2){
        if (m1.getWidth()!= m2.getWidth()||m1.getHeight()!= m2.getHeight()){
            return false;
        }
        if (m1.getBitDepth() != DisplayMode.BIT_DEPTH_MULTI&&m2.getBitDepth()!= DisplayMode.BIT_DEPTH_MULTI&&m1.getBitDepth()!=m2.getBitDepth()){
            return false;
        }
        if (m1.getRefreshRate()!= DisplayMode.REFRESH_RATE_UNKNOWN&&m2.getRefreshRate()!= DisplayMode.REFRESH_RATE_UNKNOWN&&m1.getRefreshRate()!=m2.getRefreshRate()){
            return false;
        }
        return true;
    }
    public void setfullscreen(DisplayMode dm,JFrame frame){
        // = new JFrame();
        //frame.setIgnoreRepaint(true);
        frame.setUndecorated(true);
        frame.setResizable(false);
        vc.setFullScreenWindow(frame);

        if (dm != null && vc.isDisplayChangeSupported()){
            try {
                vc.setDisplayMode(dm);
            } catch (Exception e) {

            }
        }
        //frame.createBufferStrategy(2);
    }
    public void restorescreen(){
        Window w = vc.getFullScreenWindow();
        if (w != null){
            w.dispose();
        }
        vc.setFullScreenWindow(null);
    }
    public void update(){
        Window w = vc.getFullScreenWindow();
        if (w != null){
            BufferStrategy b = w.getBufferStrategy();
            if (!b.contentsLost()) {
                b.show();
            }
        }
    }
    public Graphics2D getgraphics(){
        Window w = vc.getFullScreenWindow();
        if (w!=null){
            BufferStrategy b = w.getBufferStrategy();
            return (Graphics2D)b.getDrawGraphics();
        }
        return null;
    }
    public BufferedImage createcompartibleimage(int w, int h, int t){
        Window window = vc.getFullScreenWindow();
        if (window != null){
            GraphicsConfiguration gc = window.getGraphicsConfiguration();
            return gc.createCompatibleImage(w,h,t);
        }
        return null;
    }

}
