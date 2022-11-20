import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.*;

public class FractalExplorer {

    private static final int SCREEN_SIZE = 300;

    public static void main(String[] args) {

        var fractalExplorer = new FractalExplorer(SCREEN_SIZE);
        fractalExplorer.createAndShowGUI();
        fractalExplorer.drawFractal();
    }

    private static final double CLICK_NEW_SCALE = 0.5d;
    private static final String SCREEN_TITLE = "Fractal explorer";
    private static final String RESET_BUTTON_TEXT = "Reset";
    private static final String SAVE_BUTTON_TEXT = "Save image";
    private static final String CHOOSE_FRACTAL_LABEL_TEXT = "Fractal:";
    private static final String SAVE_ERROR_MESSAGE = "Save image error: ";

    private JFrame frame;
    private JButton resetButton;
    private JButton saveButton;
    private JImageDisplay image;
    private JComboBox chooseFractal;
    private JLabel chooseFractalLabel;
    private JFileChooser fileChooser;

    private int screenSize;
    private FractalGenerator fractalGenerator;
    private Rectangle2D.Double currentFractalRect;

    public FractalExplorer(int ScreenSize) {
        screenSize = ScreenSize;
        currentFractalRect = new Rectangle2D.Double();
        fractalGenerator = new Mandelbrot();
        fractalGenerator.getInitialRange(currentFractalRect);
    }

    public void createAndShowGUI() {

        frame = new JFrame(SCREEN_TITLE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("PNG Images", "png"));
        fileChooser.setAcceptAllFileFilterUsed(false);

        image = new JImageDisplay(screenSize, screenSize);
        image.addMouseListener(new MouseInputListener() {

            @Override
            public void mouseClicked(MouseEvent e) {

                var xCoord = FractalGenerator.getCoord(
                        currentFractalRect.x,
                        currentFractalRect.x + currentFractalRect.width,
                        screenSize, e.getX());
                var yCoord = FractalGenerator.getCoord(
                        currentFractalRect.y,
                        currentFractalRect.y + currentFractalRect.width,
                        screenSize, e.getY());
                fractalGenerator.recenterAndZoomRange(currentFractalRect, xCoord, yCoord, CLICK_NEW_SCALE);

                drawFractal();
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseExited(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseDragged(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseMoved(MouseEvent e) {
                // TODO Auto-generated method stub

            }

        });

        resetButton = new JButton();
        resetButton.setText(RESET_BUTTON_TEXT);
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fractalGenerator.getInitialRange(currentFractalRect);
                drawFractal();
            }
        });

        saveButton = new JButton(SAVE_BUTTON_TEXT);
        saveButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if (JFileChooser.APPROVE_OPTION == fileChooser.showSaveDialog(frame)) {

                    try {
                        ImageIO.write(image.getBufferedImage(), "png", fileChooser.getSelectedFile());

                    } catch (Exception exception) {

                        JOptionPane.showMessageDialog(frame, SAVE_ERROR_MESSAGE + exception.getMessage(), SCREEN_TITLE,
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }

        });

        chooseFractal = new JComboBox();
        chooseFractal.addItem(new Mandelbrot());
        chooseFractal.addItem(new Tricorn());
        chooseFractal.addItem(new BurningShip());
        chooseFractal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fractalGenerator = (FractalGenerator) chooseFractal.getSelectedItem();
                fractalGenerator.getInitialRange(currentFractalRect);
                drawFractal();
            }
        });

        chooseFractalLabel = new JLabel(CHOOSE_FRACTAL_LABEL_TEXT);

        var northPanel = new JPanel();
        northPanel.add(chooseFractalLabel);
        northPanel.add(chooseFractal);

        var centerPanel = new JPanel();
        centerPanel.add(image);

        var southPanel = new JPanel();
        southPanel.add(saveButton);
        southPanel.add(resetButton);

        frame.setLayout(new BorderLayout());
        frame.add(northPanel, BorderLayout.NORTH);
        frame.add(centerPanel, BorderLayout.CENTER);
        frame.add(southPanel, BorderLayout.SOUTH);

        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
    }

    private void drawFractal() {
        for (int x = 0; x < screenSize; x++) {
            for (int y = 0; y < screenSize; y++) {

                var xCoord = FractalGenerator.getCoord(
                        currentFractalRect.x,
                        currentFractalRect.x + currentFractalRect.width,
                        screenSize, x);
                var yCoord = FractalGenerator.getCoord(
                        currentFractalRect.y,
                        currentFractalRect.y + currentFractalRect.width,
                        screenSize, y);
                var iterations = fractalGenerator.numIterations(xCoord, yCoord);

                var color = iterations == -1 ? 0
                        : Color.HSBtoRGB(0.7f + (float) iterations / 200f, 1f, 1f);
                image.drawPixel(x, y, color);
            }
        }

        image.repaint();
    }
}
