import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Main {
    private static RenderEngine renderEngine;
    private static PhysicEngine physicEngine;
    private static GameEngine gameEngine;

    private static int frameCount = 0;
    private static long lastTime = System.currentTimeMillis();

    public static void main(String[] args) throws Exception {
        //fenêtre écran titre
        JFrame displayZoneFrame = new JFrame("Zelda de Wish");
        displayZoneFrame.setSize(800, 800);
        displayZoneFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        displayZoneFrame.setLocationRelativeTo(null);

        showTitleScreen(displayZoneFrame);
    }

    //afficher l'écran titre
    private static void showTitleScreen(JFrame displayZoneFrame) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.BLACK);

        // Ajouter un titre
        JLabel titleLabel = new JLabel("Zelda de Wish", JLabel.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 50));
        titleLabel.setForeground(Color.WHITE);
        panel.add(titleLabel, BorderLayout.CENTER);

        // Bouton pour commencer le jeu
        JButton startButton = new JButton("Cliquez pour tryharder");
        startButton.setFont(new Font("Serif", Font.PLAIN, 20));
        startButton.setBackground(Color.DARK_GRAY);
        startButton.setForeground(Color.WHITE);
        panel.add(startButton, BorderLayout.SOUTH);
        displayZoneFrame.getContentPane().add(panel);
        displayZoneFrame.setVisible(true);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lancer le jeu
                displayZoneFrame.getContentPane().removeAll();
                startGame(displayZoneFrame);
            }
        });
    }


    private static void startGame(JFrame displayZoneFrame) {

        DynamicSprite hero = null;
        try {
            hero = new DynamicSprite(200, 300,
                    ImageIO.read(new File("./img/heroTileSheetLowRes.png")), 48, 50);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        renderEngine = new RenderEngine(displayZoneFrame);
        physicEngine = new PhysicEngine();
        gameEngine = new GameEngine(hero);

        Timer renderTimer = new Timer(50, (time) -> renderEngine.update());
        Timer gameTimer = new Timer(50, (time) -> gameEngine.update());
        Timer physicTimer = new Timer(50, (time) -> physicEngine.update());

        renderTimer.start();
        gameTimer.start();
        physicTimer.start();

        displayZoneFrame.getContentPane().add(renderEngine);
        displayZoneFrame.setVisible(true);


        Playground level = new Playground("./data/level1.txt");
        renderEngine.addToRenderList(level.getSpriteList());
        renderEngine.addToRenderList(hero);
        physicEngine.addToMovingSpriteList(hero);
        physicEngine.setEnvironment(level.getSolidSpriteList());


        renderEngine.addKeyListener(gameEngine);
        renderEngine.setFocusable(true);
        renderEngine.requestFocusInWindow();
    }

    //Calculer et afficher le FPS dans le coin supérieur droit
    public static void displayFPS(Graphics g) {
        frameCount++;
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastTime >= 1000) {
            int fps = frameCount;
            frameCount = 0;
            lastTime = currentTime;
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.PLAIN, 18));
            g.drawString("IPS: " + fps, 700, 20);
        }
    }
}


