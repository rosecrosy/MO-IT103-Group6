/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package motorphpayrollsystem;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;

public class MotorPHPayrollGUI {

    static JFrame frame;

    static Map<String, String[]> employees;
    static List<String[]> attendance;

    // ================= COLORS =================

    static final Color BACKGROUND =
            new Color(245, 247, 250);

    static final Color BLUE =
            new Color(41, 128, 185);

    static final Color GREEN =
            new Color(39, 174, 96);

    static final Color RED =
            new Color(192, 57, 43);

    static final Color DARK =
            new Color(44, 62, 80);

    // ================= FONTS =================

    static final Font TITLE_FONT =
            new Font("Segoe UI", Font.BOLD, 28);

    static final Font LABEL_FONT =
            new Font("Segoe UI", Font.BOLD, 16);

    static final Font FIELD_FONT =
            new Font("Segoe UI", Font.PLAIN, 15);

    static final Font BUTTON_FONT =
            new Font("Segoe UI", Font.BOLD, 15);

    static final Font RESULT_FONT =
            new Font("Monospaced", Font.PLAIN, 15);

    public static void main(String[] args) {

        employees = MotorPHPayrollSystem.loadEmployees(
                MotorPHPayrollSystem.EMPLOYEE_CSV);

        attendance = MotorPHPayrollSystem.loadAttendance(
                MotorPHPayrollSystem.ATTENDANCE_CSV);

        createLoginGUI();
    }

    // =================================================
    // LOGIN SCREEN
    // =================================================

    static void createLoginGUI() {

        frame = new JFrame("MotorPH Payroll System");

        frame.setSize(600, 500);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setLocationRelativeTo(null);

        frame.getContentPane().setBackground(BACKGROUND);

        frame.setLayout(new BorderLayout());

        // ===== TITLE =====

        JLabel title =
                new JLabel("MotorPH Payroll System");

        title.setFont(TITLE_FONT);

        title.setForeground(DARK);

        title.setHorizontalAlignment(
                SwingConstants.CENTER);

        title.setBorder(new EmptyBorder(
                40,
                0,
                20,
                0));

        frame.add(title, BorderLayout.NORTH);

        // ===== CENTER PANEL =====

        JPanel centerPanel = new JPanel();

        centerPanel.setBackground(BACKGROUND);

        centerPanel.setLayout(new BoxLayout(
                centerPanel,
                BoxLayout.Y_AXIS));

        centerPanel.setBorder(new EmptyBorder(
                20,
                170,
                50,
                170));

        JLabel userLabel = new JLabel("Username");

        userLabel.setFont(LABEL_FONT);

        userLabel.setForeground(DARK);

        JTextField usernameField =
                new JTextField();

        usernameField.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        40));

        usernameField.setFont(FIELD_FONT);

        JLabel passLabel = new JLabel("Password");

        passLabel.setFont(LABEL_FONT);

        passLabel.setForeground(DARK);

        JPasswordField passwordField =
                new JPasswordField();

        passwordField.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        40));

        passwordField.setFont(FIELD_FONT);

        JButton loginButton =
                new JButton("Login");

        styleButton(loginButton, BLUE);

        // ===== ADD COMPONENTS =====

        centerPanel.add(userLabel);

        centerPanel.add(Box.createRigidArea(
                new Dimension(0, 10)));

        centerPanel.add(usernameField);

        centerPanel.add(Box.createRigidArea(
                new Dimension(0, 25)));

        centerPanel.add(passLabel);

        centerPanel.add(Box.createRigidArea(
                new Dimension(0, 10)));

        centerPanel.add(passwordField);

        centerPanel.add(Box.createRigidArea(
                new Dimension(0, 35)));

        centerPanel.add(loginButton);

        frame.add(centerPanel, BorderLayout.CENTER);

        // ===== LOGIN ACTION =====

        loginButton.addActionListener(e -> {

            String username =
                    usernameField.getText().trim();

            String password =
                    new String(
                            passwordField.getPassword());

            if (username.isEmpty()
                    || password.isEmpty()) {

                JOptionPane.showMessageDialog(
                        frame,
                        "Please complete all fields."
                );

                return;
            }

            if ((username.equals("employee")
                    || username.equals("payroll_staff"))
                    && password.equals("12345")) {

                frame.dispose();

                if (username.equals("employee")) {

                    createEmployeeInputGUI();

                } else {

                    createPayrollMenuGUI();
                }

            } else {

                JOptionPane.showMessageDialog(
                        frame,
                        "Invalid username or password."
                );
            }
        });

        frame.setVisible(true);
    }

    // =================================================
    // EMPLOYEE INPUT SCREEN
    // =================================================

    static void createEmployeeInputGUI() {

        frame = new JFrame("Employee Information");

        frame.setSize(550, 400);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setLocationRelativeTo(null);

        frame.getContentPane().setBackground(BACKGROUND);

        frame.setLayout(new BorderLayout());

        JLabel title =
                new JLabel("Enter Employee Number");

        title.setFont(TITLE_FONT);

        title.setForeground(DARK);

        title.setHorizontalAlignment(
                SwingConstants.CENTER);

        title.setBorder(new EmptyBorder(
                40,
                0,
                20,
                0));

        frame.add(title, BorderLayout.NORTH);

        JPanel panel = new JPanel();

        panel.setBackground(BACKGROUND);

        panel.setLayout(new BoxLayout(
                panel,
                BoxLayout.Y_AXIS));

        panel.setBorder(new EmptyBorder(
                30,
                120,
                50,
                120));

        JTextField empField =
                new JTextField();

        empField.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        45));

        empField.setFont(FIELD_FONT);

        JButton enterButton =
                new JButton("Enter");

        styleButton(enterButton, BLUE);

        panel.add(empField);

        panel.add(Box.createRigidArea(
                new Dimension(0, 30)));

        panel.add(enterButton);

        frame.add(panel, BorderLayout.CENTER);

        enterButton.addActionListener(e -> {

            String empNo =
                    empField.getText().trim();

            if (empNo.isEmpty()) {

                JOptionPane.showMessageDialog(
                        frame,
                        "Employee number is required."
                );

                return;
            }

            String[] emp =
                    employees.get(empNo);

            if (emp == null) {

                JOptionPane.showMessageDialog(
                        frame,
                        "Employee not found."
                );

                return;
            }

            frame.dispose();

            createEmployeeResultGUI(emp);
        });

        frame.setVisible(true);
    }

    // =================================================
    // EMPLOYEE RESULT SCREEN
    // =================================================

    static void createEmployeeResultGUI(
            String[] emp) {

        frame = new JFrame("Employee Information");

        frame.setSize(700, 450);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setLocationRelativeTo(null);

        frame.getContentPane().setBackground(BACKGROUND);

        frame.setLayout(new BorderLayout());

        JLabel title =
                new JLabel("Employee Information");

        title.setFont(TITLE_FONT);

        title.setForeground(DARK);

        title.setHorizontalAlignment(
                SwingConstants.CENTER);

        title.setBorder(new EmptyBorder(
                30,
                0,
                20,
                0));

        frame.add(title, BorderLayout.NORTH);

        // ===== TABLE PANEL =====

        JPanel tablePanel = new JPanel(
                new GridLayout(5, 2));

        tablePanel.setBackground(Color.WHITE);

        tablePanel.setBorder(BorderFactory
                .createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(220,220,220)),
                        new EmptyBorder(
                                20,
                                20,
                                20,
                                20)));

        addTableRow(
                tablePanel,
                "Employee Number",
                emp[0]);

        addTableRow(
                tablePanel,
                "Employee Name",
                emp[1] + ", " + emp[2]);

        addTableRow(
                tablePanel,
                "Birthday",
                emp[3]);

        addTableRow(
                tablePanel,
                "Position",
                emp[4]);

        addTableRow(
                tablePanel,
                "Supervisor",
                emp[5]);

        JPanel wrapper = new JPanel(
                new BorderLayout());

        wrapper.setBackground(BACKGROUND);

        wrapper.setBorder(new EmptyBorder(
                20,
                80,
                20,
                80));

        wrapper.add(tablePanel);

        frame.add(wrapper, BorderLayout.CENTER);

        // ===== EXIT BUTTON =====

        JPanel bottomPanel = new JPanel();

        bottomPanel.setBackground(BACKGROUND);

        JButton exitButton =
                new JButton("Exit");

        styleButton(exitButton, RED);

        bottomPanel.add(exitButton);

        frame.add(bottomPanel, BorderLayout.SOUTH);

        exitButton.addActionListener(
                e -> System.exit(0));

        frame.setVisible(true);
    }

    // =================================================
    // PAYROLL MENU SCREEN
    // =================================================

    static void createPayrollMenuGUI() {

        frame = new JFrame("Payroll Menu");

        frame.setSize(500, 400);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setLocationRelativeTo(null);

        frame.getContentPane().setBackground(BACKGROUND);

        frame.setLayout(new BorderLayout());

        JLabel title =
                new JLabel("Payroll Processing");

        title.setFont(TITLE_FONT);

        title.setForeground(DARK);

        title.setHorizontalAlignment(
                SwingConstants.CENTER);

        title.setBorder(new EmptyBorder(
                40,
                0,
                20,
                0));

        frame.add(title, BorderLayout.NORTH);

        JPanel panel = new JPanel();

        panel.setBackground(BACKGROUND);

        panel.setLayout(new BoxLayout(
                panel,
                BoxLayout.Y_AXIS));

        panel.setBorder(new EmptyBorder(
                20,
                130,
                50,
                130));

        JButton oneEmployeeButton =
                new JButton("One Employee");

        JButton allEmployeeButton =
                new JButton("All Employees");

        JButton exitButton =
                new JButton("Exit");

        styleButton(oneEmployeeButton, BLUE);

        styleButton(allEmployeeButton, GREEN);

        styleButton(exitButton, RED);

        panel.add(oneEmployeeButton);

        panel.add(Box.createRigidArea(
                new Dimension(0, 20)));

        panel.add(allEmployeeButton);

        panel.add(Box.createRigidArea(
                new Dimension(0, 20)));

        panel.add(exitButton);

        frame.add(panel, BorderLayout.CENTER);

        oneEmployeeButton.addActionListener(e -> {

            frame.dispose();

            createPayrollInputGUI();
        });

        allEmployeeButton.addActionListener(e -> {

            frame.dispose();

            createAllPayrollResultGUI();
        });

        exitButton.addActionListener(
                e -> System.exit(0));

        frame.setVisible(true);
    }

    // =================================================
    // PAYROLL INPUT SCREEN
    // =================================================

    static void createPayrollInputGUI() {

        frame = new JFrame("Payroll Processing");

        frame.setSize(550, 400);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setLocationRelativeTo(null);

        frame.getContentPane().setBackground(BACKGROUND);

        frame.setLayout(new BorderLayout());

        JLabel title =
                new JLabel("Enter Employee Number");

        title.setFont(TITLE_FONT);

        title.setForeground(DARK);

        title.setHorizontalAlignment(
                SwingConstants.CENTER);

        title.setBorder(new EmptyBorder(
                40,
                0,
                20,
                0));

        frame.add(title, BorderLayout.NORTH);

        JPanel panel = new JPanel();

        panel.setBackground(BACKGROUND);

        panel.setLayout(new BoxLayout(
                panel,
                BoxLayout.Y_AXIS));

        panel.setBorder(new EmptyBorder(
                30,
                120,
                50,
                120));

        JTextField empField =
                new JTextField();

        empField.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        45));

        empField.setFont(FIELD_FONT);

        JButton enterButton =
                new JButton("Enter");

        styleButton(enterButton, BLUE);

        panel.add(empField);

        panel.add(Box.createRigidArea(
                new Dimension(0, 30)));

        panel.add(enterButton);

        frame.add(panel, BorderLayout.CENTER);

        enterButton.addActionListener(e -> {

            String empNo =
                    empField.getText().trim();

            if (empNo.isEmpty()) {

                JOptionPane.showMessageDialog(
                        frame,
                        "Employee number is required."
                );

                return;
            }

            String[] emp =
                    employees.get(empNo);

            if (emp == null) {

                JOptionPane.showMessageDialog(
                        frame,
                        "Employee not found."
                );

                return;
            }

            frame.dispose();

            createPayrollResultGUI(empNo, emp);
        });

        frame.setVisible(true);
    }

    // =================================================
    // ONE EMPLOYEE PAYROLL RESULT
    // =================================================

    static void createPayrollResultGUI(
            String empNo,
            String[] emp) {

        createResultWindow();

        displayPayroll(empNo, emp);
    }

    // =================================================
    // ALL PAYROLL RESULT
    // =================================================

    static void createAllPayrollResultGUI() {

        createResultWindow();

        for (String empNo : employees.keySet()) {

            String[] emp =
                    employees.get(empNo);

            displayPayroll(empNo, emp);

            outputArea.append(
                    "\n\n");
        }
    }

    static JTextArea outputArea;

    static void createResultWindow() {

        frame = new JFrame("Payroll Results");

        frame.setSize(900, 700);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setLocationRelativeTo(null);

        frame.getContentPane().setBackground(BACKGROUND);

        frame.setLayout(new BorderLayout());

        JLabel title =
                new JLabel("Payroll Output");

        title.setFont(TITLE_FONT);

        title.setForeground(DARK);

        title.setHorizontalAlignment(
                SwingConstants.CENTER);

        title.setBorder(new EmptyBorder(
                20,
                0,
                10,
                0));

        frame.add(title, BorderLayout.NORTH);

        outputArea = new JTextArea();

        outputArea.setEditable(false);

        outputArea.setFont(RESULT_FONT);

        outputArea.setMargin(
                new Insets(20,20,20,20));

        JScrollPane scrollPane =
                new JScrollPane(outputArea);

        scrollPane.setBorder(new EmptyBorder(
                20,
                30,
                20,
                30));

        frame.add(scrollPane, BorderLayout.CENTER);

        JButton exitButton =
                new JButton("Exit");

        styleButton(exitButton, RED);

        JPanel bottom =
                new JPanel();

        bottom.setBackground(BACKGROUND);

        bottom.add(exitButton);

        frame.add(bottom, BorderLayout.SOUTH);

        exitButton.addActionListener(
                e -> System.exit(0));

        frame.setVisible(true);
    }

    // =================================================
    // DISPLAY PAYROLL
    // =================================================

    static void displayPayroll(
            String empNo,
            String[] emp) {

        outputArea.append(
                "====================================================\n");

        outputArea.append(
                "Employee Number : "
                        + emp[0] + "\n");

        outputArea.append(
                "Employee Name   : "
                        + emp[1]
                        + ", "
                        + emp[2]
                        + "\n\n");

        double hourlyRate =
                MotorPHPayrollSystem.parseMoney(emp[6]);

        for (int month = 6; month <= 12; month++) {

            int year = 2024;

            LocalDate firstStart =
                    LocalDate.of(year, month, 1);

            LocalDate firstEnd =
                    LocalDate.of(year, month, 15);

            LocalDate secondStart =
                    LocalDate.of(year, month, 16);

            LocalDate secondEnd =
                    YearMonth.of(year, month)
                            .atEndOfMonth();

            double hours1 =
                    MotorPHPayrollSystem
                            .computeHoursForEmployeeInRange(
                                    empNo,
                                    attendance,
                                    firstStart,
                                    firstEnd);

            double hours2 =
                    MotorPHPayrollSystem
                            .computeHoursForEmployeeInRange(
                                    empNo,
                                    attendance,
                                    secondStart,
                                    secondEnd);

            if (hours1 == 0 && hours2 == 0) {
                continue;
            }

            double[] payroll =
                    MotorPHPayrollSystem
                            .calculatePayrollForCutoff(
                                    hours1,
                                    hours2,
                                    hourlyRate);

            printPayroll(
                    month,
                    hours1,
                    hours2,
                    payroll,
                    secondEnd);
        }
    }

    // =================================================
    // PRINT PAYROLL
    // =================================================

    static void printPayroll(
            int month,
            double hours1,
            double hours2,
            double[] p,
            LocalDate secondEnd) {

        outputArea.append(
                "--------------------------------------------\n");

        outputArea.append(
                "Cutoff: "
                        + MotorPHPayrollSystem
                        .getMonthName(month)
                        + " 1-15\n");

        outputArea.append(
                "Hours Worked : "
                        + MotorPHPayrollSystem
                        .round2(hours1)
                        + "\n");

        outputArea.append(
                "Gross Salary : "
                        + MotorPHPayrollSystem
                        .round2(p[0])
                        + "\n");

        outputArea.append(
                "Net Salary   : "
                        + MotorPHPayrollSystem
                        .round2(p[2])
                        + "\n\n");

        outputArea.append(
                "Cutoff: "
                        + MotorPHPayrollSystem
                        .getMonthName(month)
                        + " 16-"
                        + secondEnd.getDayOfMonth()
                        + "\n");

        outputArea.append(
                "Hours Worked : "
                        + MotorPHPayrollSystem
                        .round2(hours2)
                        + "\n");

        outputArea.append(
                "Gross Salary : "
                        + MotorPHPayrollSystem
                        .round2(p[1])
                        + "\n");

        outputArea.append(
                "SSS          : "
                        + MotorPHPayrollSystem
                        .round2(p[4])
                        + "\n");

        outputArea.append(
                "PhilHealth   : "
                        + MotorPHPayrollSystem
                        .round2(p[5])
                        + "\n");

        outputArea.append(
                "Pag-IBIG     : "
                        + MotorPHPayrollSystem
                        .round2(p[6])
                        + "\n");

        outputArea.append(
                "Tax          : "
                        + MotorPHPayrollSystem
                        .round2(p[7])
                        + "\n");

        outputArea.append(
                "Total Deduct : "
                        + MotorPHPayrollSystem
                        .round2(p[8])
                        + "\n");

        outputArea.append(
                "Net Salary   : "
                        + MotorPHPayrollSystem
                        .round2(p[3])
                        + "\n\n");
    }

    // =================================================
    // TABLE ROW
    // =================================================

    static void addTableRow(
            JPanel panel,
            String label,
            String value) {

        JLabel left =
                new JLabel(label);

        left.setFont(LABEL_FONT);

        left.setForeground(DARK);

        JLabel right =
                new JLabel(value);

        right.setFont(FIELD_FONT);

        panel.add(left);

        panel.add(right);
    }

    // =================================================
    // BUTTON STYLE
    // =================================================

    static void styleButton(
            JButton button,
            Color color) {

        button.setBackground(color);

        button.setForeground(Color.WHITE);

        button.setFont(BUTTON_FONT);

        button.setFocusPainted(false);

        button.setBorderPainted(false);

        button.setCursor(
                new Cursor(Cursor.HAND_CURSOR));

        button.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        45));
    }
}