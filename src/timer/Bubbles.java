package timer;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Bubbles extends JFrame {

    public Bubbles() {
        initUI();
    }

    private void initUI() {

        setTitle("Bubbles");

        int w = 450;
        int h = 450;
        int bubblesNum = 40;

        add(new Surface(w, h, bubblesNum));
        setSize(w, h);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Bubbles b = new Bubbles();
                b.setVisible(true);
            }
        });
    }
}
