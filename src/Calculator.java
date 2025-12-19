import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

//JFrame is a window manager

public class Calculator extends  JFrame {
    //we cannot modify a result variable inside a lambda  as variables inside these lambda expressions must be final
    // so we add global variables---> v2 we make these instance variables so that they belong to the object
    private BigDecimal num1=BigDecimal.ZERO;
    private String operator = "";
    private JTextField screen;
    private JPanel buttonPanel;
    private JButton btnClear;
    private boolean isDarkMode;
    private boolean isNewNumber = true; //  Helps us know when to wipe the screen


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

        //Toggle
        JMenuBar menuBar = new JMenuBar();
        JMenu viewMenu=new JMenu("View");
        JMenuItem toggleItem=new JMenuItem("Toggle Dark/Light Mode");
        //when clicked run theme switcher function
        toggleItem.addActionListener(e->toggleTheme());
        viewMenu.add(toggleItem);
        menuBar.add(viewMenu);
        setJMenuBar(menuBar);


        //Screen
        screen = new JTextField(); //white box
        screen.setEditable(false);
        screen.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 24));
        screen.setHorizontalAlignment(SwingConstants.RIGHT); // Align numbers to the right like a real calc
        // We use "setPreferredSize" because the layout manager calculates the actual size
        screen.setPreferredSize(new java.awt.Dimension(300, 60));
        add(screen, BorderLayout.NORTH);

        buttonPanel = new JPanel(new GridLayout(4, 4, 10, 10));

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
                    if(isNewNumber){
                        screen.setText("");
                        isNewNumber=false;
                    }
                    if (label.equals(".") && screen.getText().contains(".")) {
                        return;
                    }
                    screen.setText(screen.getText() + label);
                } else if ("+-*/".contains(label)) {
                    String currentText = screen.getText();
                    if (!currentText.isEmpty()) {
                        num1 = new BigDecimal(screen.getText());
                        operator = label;
                        isNewNumber=true;
                    }
                } else if (label.equals("=")) {
                    String currentText = screen.getText();
                    if (!currentText.isEmpty()) {
                        BigDecimal num2 = new BigDecimal(screen.getText());
                        BigDecimal result = BigDecimal.ZERO;

                        // BigDecimal is a Java class that calculates math exactly the way humans
                        // do—digit by digit—rather than approximating it in binary.

                        switch (operator) {
                            case "+":
                                result = num1.add(num2);
                                break;
                            case "-":
                                result = num1.subtract(num2);
                                break;
                            case "*":
                                result = num1.multiply(num2);
                                break;
                            case "/":
                                if (!num2.equals(BigDecimal.ZERO)) result = num1.divide(num2, 10,RoundingMode.HALF_UP).stripTrailingZeros(); // we need rounding mode with 10 dp
                                else screen.setText("Error");
                                break;
                        }
                        // Only update screen if not Error
                        if (!screen.getText().equals("Error")) {
                            screen.setText(String.valueOf(result));
                            num1 = result;//  To Chain
                            isNewNumber = true;
                        }
                    }
                }
            });


            buttonPanel.add(btn);
        }
        add(buttonPanel, BorderLayout.CENTER);
        // 4. THE CLEAR BUTTON (South)
        btnClear = new JButton("Clear");
        btnClear.setFont(new Font("Arial", Font.BOLD, 22));
        btnClear.setBackground(Color.RED.darker());
        btnClear.setForeground(Color.WHITE);
        btnClear.setOpaque(true);
        btnClear.setBorderPainted(false);
        btnClear.setPreferredSize(new Dimension(300, 50));
        btnClear.addActionListener(e -> {
            screen.setText("");
            num1 = BigDecimal.ZERO;
            operator = "";
            isNewNumber = true;
        });


        add(btnClear, BorderLayout.SOUTH);
        applyThemeColors();
        pack();
    }

    private  void toggleTheme(){
        isDarkMode = !isDarkMode; // Flip the switch
        applyThemeColors();
    }

    private  void applyThemeColors(){
        Color bgColor = isDarkMode ? new Color(50, 50, 50) : Color.WHITE;
        Color btnColor = isDarkMode ? Color.DARK_GRAY : Color.LIGHT_GRAY;
        Color txtColor = isDarkMode ? Color.WHITE : Color.BLACK;

        // 1. Update Window & Panel Backgrounds
        getContentPane().setBackground(bgColor);
        buttonPanel.setBackground(bgColor);

        // 2. Update Screen
        screen.setBackground(isDarkMode ? new Color(30, 30, 30) : new Color(230, 230, 230));
        screen.setForeground(txtColor);

        // 3. Update Grid Buttons
        for (Component comp : buttonPanel.getComponents()) {
            if (comp instanceof JButton btn) {
                btn.setBackground(btnColor);
                btn.setForeground(txtColor);
            }
        }

        // 4. Update Clear Button (improve text readability)
        btnClear.setForeground(Color.WHITE);
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




















