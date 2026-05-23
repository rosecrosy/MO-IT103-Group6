/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package motorphpayrollsystem;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

public class MotorPHPayrollSystem {

    static String EMPLOYEE_CSV = "src/motorphpayrollsystem/MotorPH_Employee Data - Employee Details.csv";
    static String ATTENDANCE_CSV = "src/motorphpayrollsystem/MotorPH_Employee Data - Attendance Record.csv";

    static DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    // ================= MAIN PROGRAM =================
    // Handles program flow: login, role selection, and menu navigation
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Load data from CSV files
        Map<String, String[]> employees = loadEmployees(EMPLOYEE_CSV);
        List<String[]> attendance = loadAttendance(ATTENDANCE_CSV);

        // If no employees found, stop execution
        if (employees.isEmpty()) {
            System.out.println("No employees loaded. Check EMPLOYEE_CSV path/name.");
            return;
        }

        System.out.println("======================================");
        System.out.println("        MotorPH Payroll System        ");
        System.out.println("======================================");

        // User authentication
        String role = login(sc);
        if (role == null) {
            return;
        }

        // Main loop depending on user role
        while (true) {

            // ================= EMPLOYEE VIEW =================
            if (role.equals("EMPLOYEE")) {
                System.out.println("\nIf username is: employee");
                System.out.println("Display options:");
                System.out.println("1. Enter your employee number");
                System.out.println("2. Exit the program");
                System.out.print("Choose: ");
                String choice = sc.nextLine().trim();

                if (choice.equals("1")) {
                    employeeMenu(sc, employees);
                } else if (choice.equals("2")) {
                    System.out.println("Terminate the program.");
                    break;
                } else {
                    System.out.println("Invalid choice.");
                }

            // ================= PAYROLL STAFF VIEW =================    
            } else if (role.equals("PAYROLL")) {
                System.out.println("\nIf username is: payroll_staff");
                System.out.println("Display options:");
                System.out.println("1. Process Payroll");
                System.out.println("2. Exit the program");
                System.out.print("Choose: ");
                String choice = sc.nextLine().trim();

                if (choice.equals("1")) {
                    payrollMenu(sc, employees, attendance);
                } else if (choice.equals("2")) {
                    System.out.println("Terminate the program.");
                    break;
                } else {
                    System.out.println("Invalid choice.");
                }
            }
        }

        sc.close();
    }

    // ================= LOGIN =================
    static String login(Scanner sc) {
        System.out.println("\nLOGIN");
        System.out.print("Username: ");
        String username = sc.nextLine().trim();

        System.out.print("Password: ");
        String password = sc.nextLine().trim();

        if (!(username.equals("employee") || username.equals("payroll_staff")) || !password.equals("12345")) {
            System.out.println("Incorrect username and/or password.");
            System.out.println("Then terminate the program.");
            return null;
        }

        if (username.equals("employee")) {
            return "EMPLOYEE";
        } else {
            return "PAYROLL";
        }
    }

    // ================= EMPLOYEE MENU =================
    static void employeeMenu(Scanner sc, Map<String, String[]> employees) {
        System.out.print("\nEnter your employee number: ");
        String empNo = sc.nextLine().trim();

        String[] emp = employees.get(empNo);

        if (emp == null) {
            System.out.println("Employee number does not exist.");
            return;
        }

        System.out.println("\nEmployee Number: " + emp[0]);
        System.out.println("Employee Name: " + emp[1] + ", " + emp[2]);
        System.out.println("Birthday: " + emp[3]);
    }

    // ================= PAYROLL MENU =================
    static void payrollMenu(Scanner sc, Map<String, String[]> employees, List<String[]> attendance) {
        while (true) {
            System.out.println("\n1. Process Payroll (Do not include allowances)");
            System.out.println("Display sub-options:");
            System.out.println("1. One employee");
            System.out.println("2. All employees");
            System.out.println("3. Exit the program");
            System.out.print("Choose: ");
            String choice = sc.nextLine().trim();

            if (choice.equals("1")) {
                processOneEmployee(sc, employees, attendance);
            } else if (choice.equals("2")) {
                processAllEmployees(employees, attendance);
            } else if (choice.equals("3")) {
                System.out.println("Terminate the program.");
                break;
            } else {
                System.out.println("Invalid choice.");
            }
        }
    }

    // ================= ONE EMPLOYEE =================
    static void processOneEmployee(Scanner sc, Map<String, String[]> employees, List<String[]> attendance) {

        System.out.print("\nEnter the employee number: ");
        String empNo = sc.nextLine().trim();

        String[] emp = employees.get(empNo);

        if (emp == null) {
            System.out.println("Employee number does not exist.");
            return;
        }

        displayPayrollForEmployee(empNo, emp, attendance);
    }

    // ================= ALL EMPLOYEES =================
    static void processAllEmployees(Map<String, String[]> employees, List<String[]> attendance) {
        for (String empNo : employees.keySet()) {
            String[] emp = employees.get(empNo);
            displayPayrollForEmployee(empNo, emp, attendance);
            System.out.println();
        }
    }

    // ================= PAYROLL CALCULATION =================
    // Computes salary, deductions, and net pay per cutoff
    static void displayPayrollForEmployee(String empNo, String[] emp, List<String[]> attendance) {

        System.out.println("\nEmployee #: " + emp[0]);
        System.out.println("Employee Name: " + emp[1] + ", " + emp[2]);
        System.out.println("Birthday: " + emp[3]);

        double hourlyRate = parseMoney(emp[6]);

        for (int month = 6; month <= 12; month++) {
            int year = 2024;

            LocalDate firstStart = LocalDate.of(year, month, 1);
            LocalDate firstEnd = LocalDate.of(year, month, 15);

            LocalDate secondStart = LocalDate.of(year, month, 16);
            LocalDate secondEnd = YearMonth.of(year, month).atEndOfMonth();

            double hours1 = computeHoursForEmployeeInRange(empNo, attendance, firstStart, firstEnd);
            double hours2 = computeHoursForEmployeeInRange(empNo, attendance, secondStart, secondEnd);

            if (hours1 == 0 && hours2 == 0) {
                continue;
            }

            // Compute payroll data
            double[] payroll = calculatePayrollForCutoff(hours1, hours2, hourlyRate);

            // Print results
            printPayrollForCutoff(month, hours1, hours2, payroll, secondEnd);
        }
    }
    
    static double[] calculatePayrollForCutoff(double hours1, double hours2, double hourlyRate) {

        double gross1 = hours1 * hourlyRate;
        double gross2 = hours2 * hourlyRate;

        double monthlyGross = gross1 + gross2;

        double sss = computeSSS(monthlyGross);
        double philhealth = computePhilHealth(monthlyGross);
        double pagibig = computePagIbig(monthlyGross);

        double taxable = monthlyGross - (sss + philhealth + pagibig);
        if (taxable < 0) {
            taxable = 0;
        }

        double tax = computeWithholdingTax(taxable);

        double totalDeductions = sss + philhealth + pagibig + tax;

        double net1 = gross1;
        double net2 = gross2 - totalDeductions;

        return new double[]{
            gross1, gross2,
            net1, net2,
            sss, philhealth, pagibig, tax,
            totalDeductions
        };
    }
    
    // ================= PAYROLL OUTPUT DISPLAY =================
    // Displays computed payroll details for both cutoffs (1–15 and 16–end of month)   
    static void printPayrollForCutoff(int month, double hours1, double hours2, double[] p, LocalDate secondEnd) {

        double gross1 = p[0];
        double gross2 = p[1];
        double net1 = p[2];
        double net2 = p[3];
        double sss = p[4];
        double philhealth = p[5];
        double pagibig = p[6];
        double tax = p[7];
        double totalDeductions = p[8];

        // FIRST CUTOFF
        System.out.println("\nCutoff Date: " + getMonthName(month) + " 1 to " + getMonthName(month) + " 15");
        System.out.println("Total Hours Worked: " + round2(hours1));
        System.out.println("Gross Salary: " + round2(gross1));
        System.out.println("Net Salary: " + round2(net1));

        // SECOND CUTOFF
        System.out.println("\nCutoff Date: " + getMonthName(month) + " 16 to " + getMonthName(month) + " " + secondEnd.getDayOfMonth() + " (Second payout includes all deductions)");
        System.out.println("Total Hours Worked: " + round2(hours2));
        System.out.println("Gross Salary: " + round2(gross2));

        System.out.println("Each Deduction:");
        System.out.println("SSS: " + round2(sss));
        System.out.println("PhilHealth: " + round2(philhealth));
        System.out.println("Pag-IBIG: " + round2(pagibig));
        System.out.println("Tax: " + round2(tax));

        System.out.println("Total Deductions: " + round2(totalDeductions));
        System.out.println("Net Salary: " + round2(net2));
    }

    // ================= ATTENDANCE CALCULATION =================
    // Computes total worked hours of an employee within a date range
    static double computeHoursForEmployeeInRange(String empNo, List<String[]> attendance, LocalDate start, LocalDate end) {
        double totalMinutes = 0;

        for (String[] row : attendance) {
            String rowEmpNo = row[0];
            LocalDate date = LocalDate.parse(row[1]);
            LocalTime login = parseTimeFlexible(row[2]);
            LocalTime logout = parseTimeFlexible(row[3]);

            // Skip if not the target employee
            if (!rowEmpNo.equals(empNo)) {
                continue;
            }

            // Skip if outside the selected cutoff range
            if (date.isBefore(start) || date.isAfter(end)) {
                continue;
            }

            // Skip invalid time records
            if (login == null || logout == null) {
                continue;
            }

            // Official working schedule: 8:00 AM to 5:00 PM
            LocalTime officialIn = LocalTime.of(8, 0);
            LocalTime officialOut = LocalTime.of(17, 0);

            LocalTime actualIn = login;
            LocalTime actualOut = logout;

            // Apply grace period: arrivals between 8:00–8:10 are treated as 8:00
            if (!actualIn.isBefore(officialIn) && !actualIn.isAfter(LocalTime.of(8, 10))) {
                actualIn = officialIn;
            }

            //// If employee logs in earlier than 8:00, do not count early time
            if (actualIn.isBefore(officialIn)) {
                actualIn = officialIn;
            }

            // If employee logs out later than 5:00 PM, ignore overtime
            if (actualOut.isAfter(officialOut)) {
                actualOut = officialOut;
            }

            // Compute total worked minutes for the day
            long minutes = Duration.between(actualIn, actualOut).toMinutes();

            // Deduct 1 hour (60 minutes) for unpaid lunch break
            minutes = minutes - 60;

            // Prevent negative work time
            if (minutes < 0) {
                minutes = 0;
            }

            totalMinutes += minutes;
        }

        // Convert minutes to hours
        return totalMinutes / 60.0;
    }

    // ================= DEDUCTIONS =================
    // Contains all government-mandated deductions and tax computations
    
    // Computes SSS contribution based on salary bracket
    static double computeSSS(double monthlySalary) {
        
        // Uses salary ranges (brackets) with fixed contribution values
        if (monthlySalary < 3250) {
            return 135.00;
        }
        if (monthlySalary < 3750) {
            return 157.50;
        }
        if (monthlySalary < 4250) {
            return 180.00;
        }
        if (monthlySalary < 4750) {
            return 202.50;
        }
        if (monthlySalary < 5250) {
            return 225.00;
        }
        if (monthlySalary < 5750) {
            return 247.50;
        }
        if (monthlySalary < 6250) {
            return 270.00;
        }
        if (monthlySalary < 6750) {
            return 292.50;
        }
        if (monthlySalary < 7250) {
            return 315.00;
        }
        if (monthlySalary < 7750) {
            return 337.50;
        }
        if (monthlySalary < 8250) {
            return 360.00;
        }
        if (monthlySalary < 8750) {
            return 382.50;
        }
        if (monthlySalary < 9250) {
            return 405.00;
        }
        if (monthlySalary < 9750) {
            return 427.50;
        }
        if (monthlySalary < 10250) {
            return 450.00;
        }
        if (monthlySalary < 10750) {
            return 472.50;
        }
        if (monthlySalary < 11250) {
            return 495.00;
        }
        if (monthlySalary < 11750) {
            return 517.50;
        }
        if (monthlySalary < 12250) {
            return 540.00;
        }
        if (monthlySalary < 12750) {
            return 562.50;
        }
        if (monthlySalary < 13250) {
            return 585.00;
        }
        if (monthlySalary < 13750) {
            return 607.50;
        }
        if (monthlySalary < 14250) {
            return 630.00;
        }
        if (monthlySalary < 14750) {
            return 652.50;
        }
        if (monthlySalary < 15250) {
            return 675.00;
        }
        if (monthlySalary < 15750) {
            return 697.50;
        }
        if (monthlySalary < 16250) {
            return 720.00;
        }
        if (monthlySalary < 16750) {
            return 742.50;
        }
        if (monthlySalary < 17250) {
            return 765.00;
        }
        if (monthlySalary < 17750) {
            return 787.50;
        }
        if (monthlySalary < 18250) {
            return 810.00;
        }
        if (monthlySalary < 18750) {
            return 832.50;
        }
        if (monthlySalary < 19250) {
            return 855.00;
        }
        if (monthlySalary < 19750) {
            return 877.50;
        }
        if (monthlySalary < 20250) {
            return 900.00;
        }
        if (monthlySalary < 20750) {
            return 922.50;
        }
        if (monthlySalary < 21250) {
            return 945.00;
        }
        if (monthlySalary < 21750) {
            return 967.50;
        }
        if (monthlySalary < 22250) {
            return 990.00;
        }
        if (monthlySalary < 22750) {
            return 1012.50;
        }
        if (monthlySalary < 23250) {
            return 1035.00;
        }
        if (monthlySalary < 23750) {
            return 1057.50;
        }
        if (monthlySalary < 24250) {
            return 1080.00;
        }
        if (monthlySalary < 24750) {
            return 1102.50;
        }
        
        // Maximum contribution cap
        return 1125.00;
    }

    // Computes PhilHealth contribution
    static double computePhilHealth(double monthlySalary) {
        
        // 3% of monthly salary
        double premium = monthlySalary * 0.03;

        // Apply minimum and maximum limits
        if (premium < 300) {
            premium = 300;
        }
        
        // Employee pays 50% of total premium
        if (premium > 1800) {
            premium = 1800;
        }

        return premium * 0.50;
    }

    // Computes Pag-IBIG contribution
    static double computePagIbig(double monthlySalary) {
        double rate;

        // Contribution rate depends on salary range
        if (monthlySalary >= 1000 && monthlySalary <= 1500) {
            rate = 0.01;
        } else {
            rate = 0.02;
        }

        // Cap at ₱100 maximum contribution
        double contribution = monthlySalary * rate;

        if (contribution > 100) {
            contribution = 100;
        }

        return contribution;
    }

    // Computes withholding tax using TRAIN law brackets
    static double computeWithholdingTax(double taxableMonthly) {
        
        // Progressive tax system (higher income = higher tax rate)
        if (taxableMonthly <= 20832) {
            return 0;
        } else if (taxableMonthly < 33333) {
            return (taxableMonthly - 20833) * 0.20;
        } else if (taxableMonthly < 66667) {
            return 2500 + (taxableMonthly - 33333) * 0.25;
        } else if (taxableMonthly < 166667) {
            return 10833 + (taxableMonthly - 66667) * 0.30;
        } else if (taxableMonthly < 666667) {
            return 40833.33 + (taxableMonthly - 166667) * 0.32;
        } else {
            return 200833.33 + (taxableMonthly - 666667) * 0.35;
        }
    }

    // Loads employee data into a Map using employee number as key
    static Map<String, String[]> loadEmployees(String path) {
        Map<String, String[]> map = new LinkedHashMap<>();
        List<String> lines;

        // Try reading all lines from CSV file
        try {
            lines = Files.readAllLines(Paths.get(path));
        } catch (IOException e) {
            System.out.println("Cannot read employee CSV: " + path);
            return map;
        }

        // If file has no data (only header or empty)
        if (lines.size() < 2) {
            return map;
        }

        // Extract header row to identify column positions dynamically
        String[] header = splitCSV(lines.get(0));

        // Find indexes of required columns (handles flexible column naming)
        int idxEmpNo = findIndex(header, "Employee #", "Employee#", "Employee Number");
        int idxLast = findIndex(header, "Last Name");
        int idxFirst = findIndex(header, "First Name");
        int idxBirthday = findIndex(header, "Birthday");
        int idxPosition = findIndex(header, "Position");
        int idxSupervisor = findIndex(header, "Immediate Supervisor");
        int idxBasicSalary = findIndex(header, "Basic Salary");
        int idxHourlyRate = findIndex(header, "Hourly Rate");

        // Loop through each row (skip header)
        for (int i = 1; i < lines.size(); i++) {
            String[] row = splitCSV(lines.get(i));

            // Get employee number (primary key)
            String empNo = getSafe(row, idxEmpNo);
            
            // Skip rows without employee number
            if (empNo.isEmpty()) {
                continue;
            }

            // Store only necessary fields in fixed structure
            String[] emp = new String[7];
            emp[0] = empNo;
            emp[1] = getSafe(row, idxLast);
            emp[2] = getSafe(row, idxFirst);
            emp[3] = getSafe(row, idxBirthday);
            emp[4] = getSafe(row, idxPosition);
            emp[5] = getSafe(row, idxSupervisor);
            emp[6] = getSafe(row, idxHourlyRate);

            // Save to map using employee number as key
            map.put(empNo, emp);
        }

        return map;
    }

    // Loads attendance records into a List
    static List<String[]> loadAttendance(String path) {
        List<String[]> list = new ArrayList<>();
        List<String> lines;

        // Try reading attendance CSV file
        try {
            lines = Files.readAllLines(Paths.get(path));
        } catch (IOException e) {
            System.out.println("Cannot read attendance CSV: " + path);
            return list;
        }

        // If no data rows exist
        if (lines.size() < 2) {
            return list;
        }

        // Extract header row
        String[] header = splitCSV(lines.get(0));

        int idxEmpNo = findIndex(header, "Employee #", "Employee#", "Employee Number");
        int idxDate = findIndex(header, "Date");
        int idxIn = findIndex(header, "Log In");
        int idxOut = findIndex(header, "Log Out");

        // Process each row (skip header)
        for (int i = 1; i < lines.size(); i++) {
            String[] row = splitCSV(lines.get(i));

            String empNo = getSafe(row, idxEmpNo);
            String dateStr = getSafe(row, idxDate);
            String inStr = getSafe(row, idxIn);
            String outStr = getSafe(row, idxOut);

            // Skip invalid or incomplete records
            if (empNo.isEmpty() || dateStr.isEmpty()) {
                continue;
            }

            LocalDate date;
            try {
                // Convert string date to LocalDate using formatter
                date = LocalDate.parse(dateStr.trim(), DATE_FMT);
            } catch (Exception e) {
                // Skip invalid date formats
                continue;
            }

            // Store cleaned attendance record
            String[] att = new String[4];
            att[0] = empNo;
            att[1] = date.toString(); // standard ISO format
            att[2] = inStr;
            att[3] = outStr;

            list.add(att);
        }

        return list;
    }

    // ================= HELPERS =================
    // Utility methods for parsing, formatting, and safe data handling
    
    // Finds column index based on possible header names
    static int findIndex(String[] headers, String... options) {
        for (int i = 0; i < headers.length; i++) {
            String h = headers[i].trim().replace("\uFEFF", "");
            
            // Check against all possible header name variations
            for (String opt : options) {
                if (h.equalsIgnoreCase(opt)) {
                    return i;
                }
            }
        }
        
        // Return -1 if column not found
        return -1;
    }

    // Safely retrieves value from array (prevents index errors)
    static String getSafe(String[] arr, int idx) {
        
        // If index is invalid, return empty string instead of crashing
        if (idx < 0 || idx >= arr.length) {
            return "";
        }
        return arr[idx].trim();
    }

    // Splits CSV line while handling quoted values
    static String[] splitCSV(String line) {
        List<String> parts = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean inQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char ch = line.charAt(i);

            // Toggle quote mode
            if (ch == '"') {
                inQuotes = !inQuotes;
                
            // Only split on commas outside quotes
            } else if (ch == ',' && !inQuotes) {
                parts.add(current.toString());
                current.setLength(0);
            } else {
                current.append(ch);
            }
        }

        parts.add(current.toString());
        return parts.toArray(new String[0]);
    }

    // Parses time with multiple formats (flexible input handling)
    static LocalTime parseTimeFlexible(String s) {
        if (s == null) {
            return null;
        }

        s = s.trim();
        if (s.isEmpty()) {
            return null;
        }

        // Supports different time formats (e.g., 8:00, 08:00, 8:00 AM)
        String[] patterns = {
            "H:mm", "HH:mm",
            "h:mm a", "hh:mm a",
            "h:mma", "hh:mma"
        };

        for (String pattern : patterns) {
            try {
                DateTimeFormatter tf = DateTimeFormatter.ofPattern(pattern, Locale.ENGLISH);
                return LocalTime.parse(s.toUpperCase(), tf);
            } catch (Exception e) {
            }
        }

        // Return null if parsing fails
        return null;
    }

    // Converts string to double safely (handles commas and invalid values)
    static double parseMoney(String s) {
        if (s == null) {
            return 0;
        }

        // Remove commas (e.g., "12,000" → "12000")
        s = s.replace(",", "").trim();
        if (s.isEmpty()) {
            return 0; 
        }

        try {
            return Double.parseDouble(s);
        } catch (Exception e) {
            return 0; // Prevent crash on invalid input
        }
    }

    // Returns month name based on number
    static String getMonthName(int month) {
        String[] months = {
            "", "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
        };
        return months[month];
    }

    // Rounds value to 2 decimal places (for currency display)
    static double round2(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}