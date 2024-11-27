import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.ImagingOpException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class EcranTitre extends JFrame {
    public EcranTitre() {
        // Paramètres de la fenêtre
        setTitle("Donjons & Dragons");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Titre de l'écran
        JLabel titreLabel = new JLabel("Donjons & Dragons", JLabel.CENTER);
        titreLabel.setFont(new Font("Serif", Font.BOLD, 36));

        // Bouton pour commencer le jeu
        JButton boutonCommencer = new JButton("Commencer le jeu");
        boutonCommencer.setFont(new Font("Arial", Font.PLAIN, 20));

        // Ajout d'un écouteur d'événements pour démarrer le jeu
        boutonCommencer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Ferme l'écran titre
                lancerJeu(); // Démarre le jeu
            }
        });

        // Ajout des composants
        setLayout(new BorderLayout());
        add(titreLabel, BorderLayout.CENTER);
        add(boutonCommencer, BorderLayout.SOUTH);
    }

    // Méthode pour démarrer le jeu

    private void lancerJeu() {
        //chargement de l'image
        ImageIcon icon = new ImageIcon("Donjons & Dragons/images/title.png");
        Image img = icon.getImage();
        // Création et démarrage du moteur de jeu
        GameEngine jeu = new GameEngine(new DynamicSprite(150.0, 300, img, 100,100)); // Instancie le moteur avec un sprite ou personnage
        jeu.demarrer(); // Appelle la méthode pour démarrer le jeu dans GameEngine
    }

    // Méthode principale pour lancer l'écran titre
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            EcranTitre ecranTitre = new EcranTitre();
            ecranTitre.setVisible(true);
        });
    }
}
