MotorPH Payroll System
Project Overview

The MotorPH Payroll System is a Java-based payroll management application designed to automate employee payroll processing for MotorPH. The system reads employee information and attendance records from CSV files and calculates employee salaries based on working hours and hourly rates.

The application improves payroll accuracy and efficiency by automating the computation of gross salary, deductions, and net salary through both a console-based system and a Java Swing graphical user interface (GUI).

Program Details

The MotorPH Payroll System was developed using Java in Apache NetBeans IDE. The system processes employee data and attendance logs stored in CSV files to generate payroll results.

The application loads employee details and attendance records during startup and performs payroll computations based on recorded working hours.

The system supports two user roles:

Employee
Payroll Staff

Employees can view their personal information, while payroll staff can process payroll for one employee or all employees.

The system calculates employee work hours using attendance records containing log-in and log-out times. Company payroll rules such as official work hours, break deductions, and payroll cutoff periods are automatically applied.

Payroll is calculated in two cutoff periods every month:

First Cutoff: 1st – 15th
Second Cutoff: 16th – End of Month

The first cutoff payout excludes deductions, while the second cutoff payout includes mandatory government deductions.

The system automatically computes:

SSS Contribution
PhilHealth Contribution
Pag-IBIG Contribution
Withholding Tax

After deductions are applied, the program displays the employee’s net salary.

GUI Version Update

A graphical user interface (GUI) version of the MotorPH Payroll System was added using Java Swing. The GUI improves user interaction by replacing the console-based interface with interactive windows, buttons, forms, tables, and payroll result displays.

System Features
1. Login System

The system includes a simple authentication feature with two user roles:

Employee
Allows employees to access personal information
Payroll Staff
Allows payroll staff to process payroll data

Users must enter valid login credentials to access the system.

2. Employee Information Viewing

Employees can enter their employee number to view:

Employee Number
Employee Name
Birthday
Position
Supervisor
3. Payroll Processing

Payroll staff can generate payroll reports for:

One Employee
All Employees

The system calculates:

Total Hours Worked
Gross Salary
Government Deductions
Net Salary
4. Attendance-Based Salary Computation

The system calculates employee working hours using:

Log-in Time
Log-out Time
Official Work Schedule

Payroll Rules Applied:

Official work hours: 8:00 AM – 5:00 PM
Log-ins between 8:00–8:10 AM are treated as on-time
Early log-ins are not counted
Overtime hours are not included
A 1-hour break is deducted daily
5. CSV Data Processing

The system reads information from two CSV files:

Employee Details CSV

Contains:

Employee records
Position
Hourly rate
Employee information
Attendance Record CSV

Contains:

Employee attendance logs
Log-in and log-out records
Attendance data for payroll computation
GUI Features

The Java Swing GUI version includes:

Login screen
Employee information interface
Payroll processing menu
Payroll result display window
Scrollable payroll output
Styled buttons and layouts
Dialog messages and validations
Exit buttons and navigation screens
Technologies Used
Java
Java Swing
CSV File Handling
Java Time API (LocalDate, LocalTime, YearMonth)
Java Collections Framework (Map, List)
Apache NetBeans IDE
JFrame
JPanel
JTextArea
JScrollPane
Java AWT
