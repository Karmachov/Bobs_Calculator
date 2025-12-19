import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try {
            // FORCE "Metal" Look and Feel (Same on Mac/Windows/Linux)
            //By default, Swing tries to mimic macOS buttons ("Aqua").
            // Aqua buttons have strict rules about minimum size and padding that break custom layouts.
            // CrossPlatformLookAndFeel (also called "Metal") is Java's own style. It obeys every command you give it (colors, margins, sizes) perfectly.
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            java.util.logging.Logger.getLogger(Calculator.class.getName())
                    .log(java.util.logging.Level.SEVERE, "Error setting Look and Feel", e);
        }
        new Calculator();
    }
}
