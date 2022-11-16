import java.awt.*;

class JImageDisplay extends javax.swing.JComponent {

    private java.awt.image.BufferedImage image;

    public JImageDisplay(int w, int h) {

        image = new java.awt.image.BufferedImage(w, h, java.awt.image.BufferedImage.TYPE_INT_RGB);
        super.setPreferredSize(new java.awt.Dimension(w, h));
    }

    public void clearImage() {

        var graphics = (Graphics2D) image.getGraphics();
        graphics.setBackground(Color.BLACK);
    }

    public void drawPixel(int x, int y, int rgbColor) {

        image.setRGB(x, y, rgbColor);
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
        g.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);

    }
}