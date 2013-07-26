package timer;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Surface extends JPanel implements ActionListener {

    private Timer timer;
    private MyElipse[] elipses;
    private double maxSize;
    private int height;
    private int width;
    private int bubblesNum;
    private Color colors[] = {
        Color.blue, Color.cyan, Color.green,
        Color.magenta, Color.orange, Color.pink,
        Color.red, Color.yellow, Color.lightGray, Color.white
    };

    public Surface(int height, int width, int bubblesNum) {

        this.bubblesNum = bubblesNum;
        this.height = height;
        this.width = width;
        this.maxSize = width / 10;
        
        initSurface();
        initElipses();
        initTimer();
    }

    private void posRandElipses(int i, double size, int x, int y) {

        MyElipse elipse = elipses[i];

        elipse.setSize(size);
        elipse.setStroke(1.0f);
        /* Generate random position inside surface */
        double newX = Math.random() * (x - (maxSize / 2));
        double newY = Math.random() * (y - (maxSize / 2));
        elipse.getElipse().setFrame(newX, newY, size, size);
    }

    private void doStep(int w, int h) {

        for (int i = 0; i < elipses.length; i++) {

            MyElipse elipse = elipses[i];
            float stroke = elipse.getStroke();
            double size = elipse.getSize();

            elipse.setStroke(stroke += 0.025f);
            elipse.setSize(size += 1);

            if (elipse.getSize() > (double) maxSize) {
                posRandElipses(i, 1, w, h);
            } else {
                elipse.getElipse().setFrame(
                        elipse.getElipse().getX(),
                        elipse.getElipse().getY(),
                        elipse.getSize(),
                        elipse.getSize());
            }
        }
    }

    private void drawElipses(Graphics2D g2d) {

        for (int i = 0; i < elipses.length; i++) {
            g2d.setColor(colors[i % colors.length]);
            g2d.setStroke(new BasicStroke(elipses[i].getStroke()));
            g2d.draw(elipses[i].getElipse());
        }
    }

    private void initElipses() {

        for (int i = 0; i < elipses.length; i++) {
            elipses[i] = new MyElipse();
            elipses[i].setElipse(new Ellipse2D.Float());
            posRandElipses(i, maxSize * Math.random(), width, height);
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        RenderingHints rh =
                new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        rh.put(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);

        g2.setRenderingHints(rh);

        Dimension size = getSize();
        doStep(size.width, size.height);
        drawElipses(g2);
    }

    public void initTimer() {
        timer = new Timer(50, this);
        timer.setInitialDelay(190);
        timer.start();
    }

    private void initSurface() {
        setBackground(Color.BLACK);
        elipses = new MyElipse[bubblesNum];
    }
}
