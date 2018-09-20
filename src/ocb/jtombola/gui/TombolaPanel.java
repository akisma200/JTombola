package ocb.jtombola.gui;

import java.awt.Color;
import ocb.jtombola.core.TombolaLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.LineBorder;

public class TombolaPanel extends JPanel implements ActionListener, MouseListener {

    private final static Cursor CURSOR = new Cursor(java.awt.Cursor.HAND_CURSOR);
    private final ImageIcon start1;
    private final ImageIcon start2;
    private final ImageIcon stop1;
    private final ImageIcon stop2;
    private final ImageIcon congrats;
    private final ImageIcon background;
    private final JLabel lblCongrats;
    private final JButton btnGo;
    private final TombolaLabel tombolaLabel;
    private BufferedImage img;
    private Graphics g2;
    private int w;
    private int h;
    private boolean isSpinning;

    public TombolaPanel() {
        isSpinning = false;
        background = new ImageIcon("img/background.png");
        start1 = new ImageIcon("img/start1.png");
        start2 = new ImageIcon("img/start2.png");
        stop1 = new ImageIcon("img/stop1.png");
        stop2 = new ImageIcon("img/stop2.png");
        congrats  = new ImageIcon("img/congrats.gif");
        
        btnGo = new JButton();
        btnGo.setIcon(start1);
        btnGo.setRolloverIcon(start2);
        btnGo.setBackground(null);
        btnGo.setFocusPainted(false);
        btnGo.setContentAreaFilled(false);
        btnGo.setCursor(CURSOR);
        btnGo.setBounds(800, 400, 180, 50);
        btnGo.addActionListener(this);
        btnGo.addMouseListener(this);

        tombolaLabel = new TombolaLabel("input.txt");
        tombolaLabel.setFont(new Font("Nimbus Sans L", Font.BOLD, 74));
        tombolaLabel.setForeground(Color.WHITE);
        tombolaLabel.setBounds(50, 210, 930, 170);
        tombolaLabel.setHorizontalTextAlignment(JLabel.CENTER);
        tombolaLabel.setOutlineColor(Color.BLACK);
        tombolaLabel.setBorder(new LineBorder(Color.GRAY));
        tombolaLabel.setMillis(80);
        
        lblCongrats = new JLabel(congrats);
        lblCongrats.setVisible(false);
        lblCongrats.setBorder(new LineBorder(Color.GRAY));
        lblCongrats.setBounds(320, 475, 400, 120);

        this.setLayout(null);
        this.add(tombolaLabel);
        this.add(btnGo);
        this.add(lblCongrats);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnGo) {
            if (isSpinning) {
                isSpinning = false;
                tombolaLabel.stop();
                btnGo.setIcon(start1);
                btnGo.setRolloverIcon(start2);
                lblCongrats.setVisible(true);
            } else {
                isSpinning = true;
                if (tombolaLabel.hasNames()) {
                    tombolaLabel.start();
                    btnGo.setIcon(stop1);
                    btnGo.setRolloverIcon(stop2);
                    lblCongrats.setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(null, "No more names in the input file", "Warning", JOptionPane.WARNING_MESSAGE);
                    btnGo.setEnabled(false);
                }
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        JButton btn = (JButton) e.getSource();
        btn.setCursor(CURSOR);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        JButton btn = (JButton) e.getSource();
        btn.setCursor(java.awt.Cursor.getDefaultCursor());
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        w = this.getWidth() + 1;
        h = this.getHeight() + 1;
        img = (BufferedImage) this.createImage(w, h);
        g2 = img.createGraphics();
        g2.drawImage(background.getImage(), 0, 0, w, h, this);
        g.drawImage(img, -1, -1, w, h, null);
    }

    public void saveWinners() {
        tombolaLabel.saveWinners();
    }
}
