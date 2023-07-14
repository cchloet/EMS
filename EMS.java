import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class EMS {
    private Map<Integer, Employee> employeeMap;
    private Scanner scanner;

    public EMS() {
        employeeMap = new HashMap<>();
        scanner = new Scanner(System.in);
    }

    public void run() {
        boolean exit = false;
        while (!exit) {
            System.out.println("Employee Management System");
            System.out.println("---------------------------");
            System.out.println("1. Add New Employee");
            System.out.println("2. Update Employee Information");
            System.out.println("3. Remove Employee");
            System.out.println("4. List Employee Information");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 0:
                    exit = true;
                    break;
                case 1:
                    addEmployee();
                    break;
                case 2:
                    updateEmployee();
                    break;
                case 3:
                    removeEmployee();
                    break;
                case 4:
                    listEmployees();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }

            System.out.println();
        }

        scanner.close();
    }

    // Add a new employee
    private void addEmployee() {
        System.out.println("Add New Employee");
    
        String firstName = readString("Enter First Name: ");
        String lastName = readString("Enter Last Name: ");
        String dateOfEmployment = readDate("Enter Date of Employment (mm/dd/yy): ");
        double salary = readDouble("Enter Salary: ");
        String department = readString("Enter Department: ");
    
        int employeeId = generateUniqueEmployeeId();
        Employee employee = new Employee(firstName, lastName, employeeId, dateOfEmployment, salary, department);
        employeeMap.put(employee.getEmployeeId(), employee);
    
        System.out.println("Employee added successfully!");
    }

    // Generate a unique employee ID
    private int generateUniqueEmployeeId() {
        int employeeId = 1; // Start with ID 1
        while (employeeMap.containsKey(employeeId)) {
            employeeId++; // Increment ID until it is unique
        }
        return employeeId;
    }

    // Update employee information
    private void updateEmployee() {
        System.out.println("Update Employee Information");
        int employeeId = readInt("Enter Employee ID: ");

        Employee employee = findEmployeeById(employeeId);
        if (employee == null) {
            System.out.println("Employee not found!");
            return;
        }

        String newFirstName = readString("Enter New First Name: ");
        String newLastName = readString("Enter New Last Name: ");
        String newDateOfEmployment = readDate("Enter New Date of Employment (mm/dd/yy): ");
        double newSalary = readDouble("Enter New Salary: ");
        String newDepartment = readString("Enter New Department: ");

        employee.setFirstName(newFirstName);
        employee.setLastName(newLastName);
        employee.setDateOfEmployment(newDateOfEmployment);
        employee.setSalary(newSalary);
        employee.setDepartment(newDepartment);

        employeeMap.put(employee.getEmployeeId(), employee);

        System.out.println("Employee information updated successfully!");
    }

    // Read a string value from the user
    private String readString(String prompt) {
        System.out.print(prompt);
        String value = scanner.nextLine();
        return capitalizeFirstLetter(value);
    }
    
    // Capitalize the first letter of a string
    private String capitalizeFirstLetter(String input) {
        if (input.isEmpty()) {
            return input;
        }
        String firstLetter = input.substring(0, 1).toUpperCase();
        String restLetters = input.substring(1).toLowerCase();
        return firstLetter + restLetters;
    }

    // Read an integer value from the user
    private int readInt(String prompt) {
        int value;
        while (true) {
            try {
                System.out.print(prompt);
                value = scanner.nextInt();
                scanner.nextLine(); // Consume newline character
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter an integer.");
                scanner.nextLine(); // Consume invalid input
            }
        }
        return value;
    }

    // Read a double value from the user
    private double readDouble(String prompt) {
        double value;
        while (true) {
            try {
                System.out.print(prompt);
                value = scanner.nextDouble();
                if (value < 0) {
                    System.out.println("Invalid input. Please enter a positive value for salary.");
                    continue;
                }
                scanner.nextLine(); // Consume newline character
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine(); // Consume invalid input
            }
        }
        return value;
    }

    // Read a date string from the user
    private String readDate(String prompt) {
        String value;
        while (true) {
            System.out.print(prompt);
            value = scanner.nextLine();
            if (isValidDate(value)) {
                break;
            } else {
                System.out.println("Invalid date format. Please enter the date in the format (mm/dd/yy).");
            }
        }
        return value;
    }

    // Check if a date string is valid
    private boolean isValidDate(String date) {
        try {
            String[] parts = date.split("/");
            if (parts.length != 3) {
                return false;
            }
            int month = Integer.parseInt(parts[0]);
            int day = Integer.parseInt(parts[1]);
            int year = Integer.parseInt(parts[2]);
    
            // Check if month, day, and year are within valid ranges
            if (month < 1 || month > 12) {
                return false;
            }
            if (day < 1 || day > 31) {
                return false;
            }
            if (year < 0 || year > 99) {
                return false;
            }
    
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Remove an employee from the system
    private void removeEmployee() {
        System.out.println("Remove Employee");
        int employeeId = readInt("Enter Employee ID: ");

        Employee employee = findEmployeeById(employeeId);
        if (employee == null) {
            System.out.println("Employee not found!");
            return;
        }

        employeeMap.remove(employee.getEmployeeId());

        System.out.println("Employee removed successfully!");
    }

    // List all employees in the system
    private void listEmployees() {
        System.out.println("List of Employees");
        System.out.println("-----------------");

        if (employeeMap.isEmpty()) {
            System.out.println("No employees found.");
            return;
        }

        for (Employee employee : employeeMap.values()) {
            System.out.println(employee.toString());
        }
    }

    // Find an employee by their ID
    private Employee findEmployeeById(int employeeId) {
        return employeeMap.get(employeeId);
    }

    public static void main(String[] args) {
        EMS system = new EMS();
        system.run();
    }
}

class Employee {
    private String firstName;
    private String lastName;
    private int employeeId;
    private String dateOfEmployment;
    private double salary;
    private String department;

    public Employee(String firstName, String lastName, int employeeId, String dateOfEmployment, double salary, String department) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.employeeId = employeeId;
        this.dateOfEmployment = dateOfEmployment;
        this.salary = salary;
        this.department = department;
    }

    // Getters and setters for the Employee class
    public int getEmployeeId() {
        return employeeId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setDateOfEmployment(String dateOfEmployment) {
        this.dateOfEmployment = dateOfEmployment;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    // Print statement for employees
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Employee ID: ").append(employeeId)
            .append(", Name: ").append(firstName).append(" ").append(lastName)
            .append(", Date of Employment: ").append(dateOfEmployment)
            .append(", Salary: ").append(salary)
            .append(", Department: ").append(department);
        return sb.toString();
    }
}

