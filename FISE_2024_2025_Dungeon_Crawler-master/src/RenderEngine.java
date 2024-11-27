import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class RenderEngine extends JPanel implements Engine {
    private ArrayList<Displayable> renderList;
    private long lastTime = System.currentTimeMillis();
    private int frames = 0;
    private int fps = 0;

    public RenderEngine(JFrame jFrame) {
        renderList = new ArrayList<>();
    }

    public void addToRenderList(Displayable displayable) {
        if (!renderList.contains(displayable)) {
            renderList.add(displayable);
        }
    }

    public void addToRenderList(ArrayList<Displayable> displayable) {
        if (!renderList.contains(displayable)) {
            renderList.addAll(displayable);
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        long currentTime = System.currentTimeMillis();
        frames++;

        if (currentTime - lastTime >= 1000) {
            fps = frames;
            frames = 0;
            lastTime = currentTime;
        }

        for (Displayable renderObject : renderList) {
            renderObject.draw(g);
        }

        // IPS dans le coin supérieur droit
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("IPS: " + fps, getWidth() - 80, 20);
    }

    @Override
    public void update() {
        this.repaint();
    }
}

