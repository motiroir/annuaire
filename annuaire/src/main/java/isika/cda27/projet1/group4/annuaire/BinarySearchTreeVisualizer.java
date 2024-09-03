package isika.cda27.projet1.group4.annuaire;

import javafx.application.Application;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import isika.cda27.projet1.group4.annuaire.back.BinarySearchTree;
import isika.cda27.projet1.group4.annuaire.back.Node;

public class BinarySearchTreeVisualizer extends Application {

    private static final int NODE_RADIUS = 40;  // Taille du cercle
    private static final int VERTICAL_SPACING = 100;
    private static final int DOUBLON_SPACING = 100;  // Espace horizontal pour les doublons
    private static final int RIGHT_MARGIN = 50;      // Marge droite pour l'élément le plus à droite

    private BinarySearchTree tree;

    @Override
    public void start(Stage primaryStage) {
        Pane pane = new Pane();
        tree = new BinarySearchTree();
        tree.affichage();

        Scene scene = new Scene(pane, 1000, 600);
        primaryStage.setTitle("Binary Search Tree Visualizer");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Écoutez les changements de taille de la scène
        scene.widthProperty().addListener((observable, oldValue, newValue) -> {
            pane.getChildren().clear();
            drawTree(tree.getRoot(), pane, scene.getWidth() / 2, 50, (scene.getWidth() - RIGHT_MARGIN) / 4, VERTICAL_SPACING, 0);
        });

        scene.heightProperty().addListener((observable, oldValue, newValue) -> {
            pane.getChildren().clear();
            drawTree(tree.getRoot(), pane, scene.getWidth() / 2, 50, (scene.getWidth() - RIGHT_MARGIN) / 4, VERTICAL_SPACING, 0);
        });

        // Initial draw
        drawTree(tree.getRoot(), pane, scene.getWidth() / 2, 50, (scene.getWidth() - RIGHT_MARGIN) / 4, VERTICAL_SPACING, 0);
    }

    public void drawTree(Node node, Pane pane, double x, double y, double xOffset, double yOffset, int depth) {
        if (node == null || node.getKey() == null) {
            return;
        }

        // Calcul du nouvel xOffset pour les enfants
        double newXOffset = xOffset / Math.pow(2, depth);

        // Dessiner les liens avant les cercles pour éviter les chevauchements
        if (node.getLeftChild() != -1) {
            Node leftNode = node.nodeReader(tree.raf, node.getLeftChild() * Node.NODE_SIZE_OCTET);
            double childX = x - newXOffset;
            double childY = y + yOffset;
            pane.getChildren().add(new Line(x, y, childX, childY));
            drawTree(leftNode, pane, childX, childY, xOffset, yOffset, depth + 1);
        }

        if (node.getRightChild() != -1) {
            Node rightNode = node.nodeReader(tree.raf, node.getRightChild() * Node.NODE_SIZE_OCTET);
            double childX = x + newXOffset;
            double childY = y + yOffset;
            pane.getChildren().add(new Line(x, y, childX, childY));
            drawTree(rightNode, pane, childX, childY, xOffset, yOffset, depth + 1);
        }

        // Cercle pour le nœud principal
        double circleRadius = NODE_RADIUS;
        Circle circle = new Circle(x, y, circleRadius);
        circle.setFill(Color.LIGHTBLUE); // Couleur des nœuds principaux
        circle.setStroke(Color.BLACK);

        // Texte centré à l'intérieur du cercle
        Text text = new Text(node.getKey().getName());
        text.setFont(Font.font("Arial", 14));

        // Centrage du texte dans le cercle
        Bounds textBounds = text.getBoundsInLocal();
        double textWidth = textBounds.getWidth();
        double textHeight = textBounds.getHeight();
        text.setX(x - textWidth / 2);
        text.setY(y + textHeight / 4);

        // Ajout du cercle et du texte au pane
        pane.getChildren().addAll(circle, text);

        // Gestion des doublons
        if (node.getDoublon() != -1) {
            Node doublonNode = node.nodeReader(tree.raf, node.getDoublon() * Node.NODE_SIZE_OCTET);

            double doublonX = x + DOUBLON_SPACING; // Positionné à droite du nœud parent
            double doublonY = y; // Même niveau que le nœud parent

            // Cercle pour le doublon
            Circle doublonCircle = new Circle(doublonX, doublonY, circleRadius);
            doublonCircle.setFill(Color.LIGHTPINK); // Couleur différente pour les doublons
            doublonCircle.setStroke(Color.BLACK);

            // Texte centré à l'intérieur du cercle de doublon
            Text doublonText = new Text(doublonNode.getKey().getName());
            doublonText.setFont(Font.font("Arial", 14));

            // Centrage du texte dans le cercle de doublon
            Bounds doublonTextBounds = doublonText.getBoundsInLocal();
            double doublonTextWidth = doublonTextBounds.getWidth();
            double doublonTextHeight = doublonTextBounds.getHeight();
            doublonText.setX(doublonX - doublonTextWidth / 2);
            doublonText.setY(doublonY + doublonTextHeight / 4);

            // Ajouter le cercle et le texte du doublon au pane
            pane.getChildren().addAll(doublonCircle, doublonText);

            // Dessiner le lien entre le nœud principal et le doublon
            pane.getChildren().add(new Line(x, y, doublonX, doublonY));

            // Appel récursif pour les doublons
            drawTree(doublonNode, pane, doublonX, doublonY, xOffset / 1.5, yOffset, depth + 1);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}