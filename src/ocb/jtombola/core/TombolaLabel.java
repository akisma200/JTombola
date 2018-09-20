package ocb.jtombola.core;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import sas.swing.MultiLineLabel;
import sas.swing.plaf.MultiLineLabelUI;
import sas.swing.plaf.MultiLineShadowUI;

public class TombolaLabel extends MultiLineLabel implements Runnable {
    private final NamesLoader inputList;
    private final List<String> lstNames;
    private final List<String> lstWinners;
    private final Random random;
    private int millis = 100;
    private volatile Thread tombolaThread;
    private int n;
    
    private Color outlineColor;
    private boolean isPaintingOutline = false;
    private boolean forceTransparent = false;
    
    public TombolaLabel(String fileName) {
        inputList = new NamesLoader( fileName );
        lstNames = inputList.getList();
        if ( lstNames.isEmpty() ) {
            JOptionPane.showMessageDialog(null, "No names in the input file", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
        lstWinners = new ArrayList<>();
        random = new Random();
    }
    
    public void setMillis(int millis) {
        this.millis = millis;
    }

    public void start() {
        this.setUI(MultiLineLabelUI.labelUI);
        tombolaThread = new Thread(this);
        tombolaThread.start();
    }

    @Override
    public void run() {
        Thread thisThread = Thread.currentThread();
        while (tombolaThread == thisThread) {
            n = random.nextInt(lstNames.size());
            this.setText(lstNames.get(n));
            //System.out.println(n);
            try {
                Thread.sleep(millis);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void stop() {
        tombolaThread = null;
        this.setUI(MultiLineShadowUI.labelUI);
        lstWinners.add(lstNames.get(n));
        lstNames.remove(n);
    }
    
    public boolean hasNames() {
        return !lstNames.isEmpty();
    }

    public void saveWinners() {
        inputList.saveWinners(lstWinners);
    }
    
       
    public Color getOutlineColor() {
        return outlineColor;
    }

    public void setOutlineColor(Color outlineColor) {
        this.outlineColor = outlineColor;
        this.invalidate();
    }

    @Override
    public Color getForeground() {
        if ( isPaintingOutline ) {
            return outlineColor;
        } else {
            return super.getForeground();
        }
    }

    @Override
    public boolean isOpaque() {
        if ( forceTransparent ) {
            return false;
        } else {
            return super.isOpaque();
        }
    }
    
    @Override
    public void paint(Graphics g) {
        String text = getText();
        if ( text == null || text.length() == 0 ) {
            super.paint(g);
            return;
        }
        if ( isOpaque() )
            super.paint(g);
        forceTransparent = true;
        isPaintingOutline = true;
        g.translate(-1, -1); super.paint(g);
        g.translate( 1,  0); super.paint(g);
        g.translate( 1,  0); super.paint(g);
        g.translate( 0,  1); super.paint(g);
        g.translate( 0,  1); super.paint(g);
        g.translate(-1,  0); super.paint(g);
        g.translate(-1,  0); super.paint(g);
        g.translate( 0, -1); super.paint(g);
        g.translate( 1,  0);
        isPaintingOutline = false;
        super.paint(g);
        forceTransparent = false;
    }   
}