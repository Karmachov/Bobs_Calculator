import javax.swing.*;
//import java.awt.*;
//JFrame is a window manager

public class Calculator extends  JFrame {
    //we cannot modify a result variable inside a lambda  as variables inside these lambda expressions must be final
    // so we add global variables---> v2 we make these instance variables so that they belong to the object
    private double num1 = 0;
    private String operator = "";
    private JTextField screen;

    //Constructor method with same name as class that runs automatically when we make a new instance in main
    public Calculator() {
        super("Bob's Calculator");
        setSize(300, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        initUI();
        setVisible(true);
    }

    private void initUI() {

        screen = new JTextField(); //white box
        screen.setBounds(30, 50, 225, 50);
        screen.setEditable(false);
        add(screen);

        // instead of doing individually we create a list and then calc the coords and place each of them
        String[] btnLabels = {
                "1", "2", "3", "+",
                "4", "5", "6", "-",
                "7", "8", "9", "*",
                ".", "0", "=", "/",
                "Clear"
        };
        int x = 30;
        int y = 120;

        for (int i = 0; i < btnLabels.length; i++) {
            JButton btn = new JButton(btnLabels[i]);
            btn.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 22));
            btn.setBackground(java.awt.Color.DARK_GRAY);
            btn.setForeground(java.awt.Color.WHITE);

            // --- ADD THESE TWO LINES FOR MAC USERS ---
            btn.setOpaque(true);
            btn.setBorderPainted(false); //remove 3D borders
            btn.setMargin(new java.awt.Insets(0, 0, 0, 0));
            String label = btnLabels[i]; // Create a "final" copy for the lambda to use
            if (btnLabels[i].equals("Clear")) {
                btn.setBounds(30, 360, 230, 50); // Custom spot for Clear
            } else {
                // Standard button positioning
                btn.setBounds(x, y, 50, 50);

                x += 60; //50 px button+gap before next beside
                if ((i + 1) % 4 == 0) {
                    x = 30;
                    y += 60; //50 px  row+gap before next below
                }
            }


                //v2 requires a master Listener for all but

                btn.addActionListener(e -> {
                    if ("0123456789.".contains(label)) {
                        if (label.equals(".") && screen.getText().contains(".")) {
                            return;
                        }
                        screen.setText(screen.getText() + label);
                    } else if (label.equals("Clear")) {
                        screen.setText("");
                    } else if ("+-*/".contains(label)) {
                        String currentText = screen.getText();
                        if (!currentText.isEmpty()) {
                            num1 = Double.parseDouble(currentText);
                            operator = label;
                            screen.setText("");
                        }
                    } else if (label.equals("=")) {
                        String currentText = screen.getText();
                        if (!currentText.isEmpty()) {
                            double num2 = Double.parseDouble(currentText);
                            double result = 0;

                            switch (operator) {
                                case "+":
                                    result = num1 + num2;
                                    break;
                                case "-":
                                    result = num1 - num2;
                                    break;
                                case "*":
                                    result = num1 * num2;
                                    break;
                                case "/":
                                    if (num2 != 0) result = num1 / num2;
                                    else screen.setText("Error");
                                    break;
                            }
                            // Only update screen if not Error
                            if (!screen.getText().equals("Error")) {
                                screen.setText(String.valueOf(result));
                                num1 = result; //  To Chain
                            }
                        }
                    }
                });


            add(btn);
        }


    }

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




        //buttons are dumb so we need to attach a Listener to them
        //ActionListener is an interface to implement actionPerformed method

        //Up until Java 8 we  used Anonymous Inner Class now we use lambdas
//            // The "Boilerplate" way (Anonymous Inner Class)
//            btn1.addActionListener(new java.awt.event.ActionListener() {
//                @Override
//                public void actionPerformed(java.awt.event.ActionEvent e) {
//                    String currentText = screen.getText();
//                    screen.setText(currentText + "1");
//                }
//            });

        // --- BUTTON LISTENER ---

//        btn1.addActionListener(e -> {
//            String currentText = screen.getText();
//            screen.setText(currentText+ "1");
//        });
//
//        btn2.addActionListener(e -> screen.setText(screen.getText() + "2"));




















