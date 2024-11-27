import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameEngine implements Engine, KeyListener {
    DynamicSprite hero;

    public GameEngine(DynamicSprite hero) {
        this.hero = hero;
    }

    public void demarrer() {
        // Logique de démarrage du jeu
        System.out.println("Le jeu commence !");
        // Peut-être afficher une fenêtre de jeu, ou lancer une boucle principale
        // Par exemple :
        // JFrame fenetreJeu = new JFrame();
        // fenetreJeu.add(this); // Si GameEngine est un JPanel ou autre composant visuel
        // fenetreJeu.setVisible(true);
    }

    @Override
    public void update() {
        // Logique de mise à jour du jeu (appelée dans une boucle de jeu)
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Traitement des touches tapées
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                hero.setDirection(Direction.NORTH);
                break;
            case KeyEvent.VK_DOWN:
                hero.setDirection(Direction.SOUTH);
                break;
            case KeyEvent.VK_LEFT:
                hero.setDirection(Direction.WEST);
                break;
            case KeyEvent.VK_RIGHT:
                hero.setDirection(Direction.EAST);
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Code à exécuter lorsque les touches sont relâchées
    }
}

