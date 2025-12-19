import javax.swing.*;
import java.awt.*;

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
        setLayout(new java.awt.BorderLayout(10, 10));
        screen = new JTextField(); //white box
        screen.setEditable(false);
        screen.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 24));
        screen.setHorizontalAlignment(SwingConstants.RIGHT); // Align numbers to the right like a real calc
        // We use "setPreferredSize" because the layout manager calculates the actual size
        screen.setPreferredSize(new java.awt.Dimension(300, 60));
        add(screen, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(4, 4, 10, 10));

        // instead of doing individually we create a list and then calc the coords and place each of them
        String[] btnLabels = {
                "1", "2", "3", "+",
                "4", "5", "6", "-",
                "7", "8", "9", "*",
                ".", "0", "=", "/",

        };


//        btn.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 22));
//        btn.setBackground(java.awt.Color.DARK_GRAY);
//        btn.setForeground(java.awt.Color.WHITE);
//
//        // --- ADD THESE TWO LINES FOR MAC USERS ---
//        btn.setOpaque(true);
//        btn.setBorderPainted(false); //remove 3D borders
//        btn.setMargin(new java.awt.Insets(0, 0, 0, 0));
//        String label = btnLabels[i]; // Create a "final" copy for the lambda to use

//        replace the manual coords decision with layout


        for (String label : btnLabels) {
            JButton btn = new JButton(label);
            btn.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 22));
            btn.setBackground(java.awt.Color.DARK_GRAY);
            btn.setForeground(java.awt.Color.WHITE);
            btn.setOpaque(true);
            btn.setBorderPainted(false);

            //v2 requires a master Listener for all

            btn.addActionListener(e -> {
                if ("0123456789.".contains(label)) {
                    if (label.equals(".") && screen.getText().contains(".")) {
                        return;
                    }
                    screen.setText(screen.getText() + label);
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


            buttonPanel.add(btn);
        }
        add(buttonPanel, BorderLayout.CENTER);
        // 4. THE CLEAR BUTTON (South)
        JButton btnClear = new JButton("Clear");
        btnClear.setFont(new Font("Arial", Font.BOLD, 22));
        btnClear.setBackground(Color.RED.darker());
        btnClear.setForeground(Color.WHITE);
        btnClear.setOpaque(true);
        btnClear.setBorderPainted(false);
        btnClear.setPreferredSize(new Dimension(300, 50));
        btnClear.addActionListener(e -> {
            screen.setText("");
            num1 = 0;
            operator = "";
        });

        add(btnClear, BorderLayout.SOUTH);
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




















