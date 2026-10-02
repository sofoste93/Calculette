package com.sofoste.calculette;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

/** Minimal Swing interface for the calculator. */
public final class CalculetteApp {
    public static final String VERSION = "2.0.0";

    private CalculetteApp() {}

    public static void main(String[] args) {
        if (args.length > 0 && "--version".equals(args[0])) {
            System.out.println("Calculette " + VERSION);
            return;
        }
        SwingUtilities.invokeLater(() -> new CalculatorFrame().setVisible(true));
    }

    static final class CalculatorFrame extends JFrame {
        private static final Color CASE = new Color(42, 45, 48);
        private static final Color KEY = new Color(66, 70, 73);
        private static final Color OPERATOR = new Color(205, 132, 48);
        private static final Color SCREEN = new Color(194, 207, 176);

        private final CalculatorEngine engine = new CalculatorEngine();
        private final JTextField display = new JTextField("0");
        private final JLabel status = new JLabel("READY");

        CalculatorFrame() {
            super("Ma Calculette · Restored Edition");
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setResizable(false);
            setContentPane(buildInterface());
            pack();
            setLocationRelativeTo(null);
            installKeyboardShortcuts();
        }

        private JPanel buildInterface() {
            JPanel casePanel = new JPanel(new BorderLayout(0, 16));
            casePanel.setBackground(CASE);
            casePanel.setBorder(new EmptyBorder(22, 22, 22, 22));
            casePanel.setPreferredSize(new Dimension(390, 570));

            JPanel header = new JPanel(new BorderLayout());
            header.setOpaque(false);
            JLabel brand = new JLabel("SFS  ·  POCKET CALCULATOR");
            brand.setForeground(new Color(203, 207, 207));
            brand.setFont(new Font(Font.MONOSPACED, Font.BOLD, 13));
            status.setForeground(new Color(143, 194, 151));
            status.setFont(new Font(Font.MONOSPACED, Font.BOLD, 11));
            header.add(brand, BorderLayout.WEST);
            header.add(status, BorderLayout.EAST);

            display.setEditable(false);
            display.setHorizontalAlignment(SwingConstants.RIGHT);
            display.setBackground(SCREEN);
            display.setForeground(new Color(31, 42, 32));
            display.setFont(new Font(Font.MONOSPACED, Font.BOLD, 37));
            display.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(22, 24, 25), 4),
                    new EmptyBorder(16, 12, 16, 12)));
            display.setPreferredSize(new Dimension(0, 84));

            JPanel top = new JPanel(new BorderLayout(0, 12));
            top.setOpaque(false);
            top.add(header, BorderLayout.NORTH);
            top.add(display, BorderLayout.CENTER);
            casePanel.add(top, BorderLayout.NORTH);
            casePanel.add(buildKeys(), BorderLayout.CENTER);
            return casePanel;
        }

        private JPanel buildKeys() {
            JPanel keys = new JPanel(new GridLayout(5, 4, 10, 10));
            keys.setOpaque(false);
            String[] labels = {
                    "C", "±", "MOD", "÷",
                    "7", "8", "9", "×",
                    "4", "5", "6", "−",
                    "1", "2", "3", "+",
                    "0", ".", "=", "EXIT"
            };
            for (String label : labels) keys.add(createButton(label));
            return keys;
        }

        private JButton createButton(String label) {
            JButton button = new JButton(label);
            boolean special = "+−×÷MOD=".contains(label);
            button.setBackground(special ? OPERATOR : KEY);
            button.setForeground(Color.WHITE);
            button.setFont(new Font(Font.MONOSPACED, Font.BOLD, label.length() > 2 ? 15 : 22));
            button.setFocusPainted(false);
            button.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(25, 27, 28), 2),
                    new EmptyBorder(10, 8, 10, 8)));
            button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            button.addActionListener(event -> handle(label));
            return button;
        }

        private void handle(String command) {
            if (command.matches("\\d")) engine.inputDigit(command);
            else switch (command) {
                case "." -> engine.inputDecimal();
                case "+", "−", "×", "÷", "MOD" -> engine.chooseOperator(command);
                case "=" -> engine.evaluate();
                case "±" -> engine.toggleSign();
                case "C" -> engine.clear();
                case "EXIT" -> dispose();
                default -> { }
            }
            refresh();
        }

        private void refresh() {
            display.setText(engine.display());
            status.setText(engine.pendingOperator().isEmpty() ? "READY" : engine.pendingOperator() + "  PENDING");
        }

        /** Maps familiar calculator keys without coupling arithmetic to Swing events. */
        private void installKeyboardShortcuts() {
            JRootPane root = getRootPane();
            for (char digit = '0'; digit <= '9'; digit++) bind(root, String.valueOf(digit), "digit" + digit, String.valueOf(digit));
            bind(root, ".", "decimal", "."); bind(root, "+", "add", "+"); bind(root, "-", "subtract", "−");
            bind(root, "*", "multiply", "×"); bind(root, "/", "divide", "÷"); bind(root, "ENTER", "equals", "=");
            bind(root, "ESCAPE", "clear", "C");
            root.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("BACK_SPACE"), "backspace");
            root.getActionMap().put("backspace", new AbstractAction() { public void actionPerformed(ActionEvent e) { engine.backspace(); refresh(); }});
        }

        private void bind(JRootPane root, String key, String actionName, String command) {
            root.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(key), actionName);
            root.getActionMap().put(actionName, new AbstractAction() { public void actionPerformed(ActionEvent e) { handle(command); }});
        }
    }
}

