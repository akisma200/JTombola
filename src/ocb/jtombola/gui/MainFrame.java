package ocb.jtombola.gui;

import java.awt.EventQueue;
import javax.swing.JFrame;
import java.awt.event.WindowListener;
import java.awt.event.WindowEvent;

public class MainFrame extends JFrame implements WindowListener {

    TombolaPanel pnltombola;

    public MainFrame() {
        this.pnltombola = new TombolaPanel();
        this.add(pnltombola);
        this.setTitle("Thales Tombola 2018");
        this.setResizable(false);
        this.setSize(1024, 750);
        this.setLocationRelativeTo(null);
        this.addWindowListener(this);
    }

    @Override
    public void windowClosing(WindowEvent we) {
        pnltombola.saveWinners();
        System.exit(0);
    }

    @Override
    public void windowDeactivated(WindowEvent we) {
    }

    @Override
    public void windowActivated(WindowEvent we) {
    }

    @Override
    public void windowDeiconified(WindowEvent we) {
    }

    @Override
    public void windowIconified(WindowEvent we) {
    }

    @Override
    public void windowClosed(WindowEvent we) {
    }

    @Override
    public void windowOpened(WindowEvent we) {
    }

    public static void main(String args[]) {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                MainFrame frm = new MainFrame();
                frm.setVisible(true);
            }
        };
        EventQueue.invokeLater(r);
    }
}
