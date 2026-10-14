
import java.util.*;

public class EmployeeDirectory {

    static class Employee implements Comparable<Employee> {
        int id;
        String name;
        String department;
        double salary;

        Employee(int id, String name, String department, double salary) {
            this.id = id;
            this.name = name;
            this.department = department;
            this.salary = salary;
        }

         public int compareTo(Employee other) {
            return Integer.compare(this.id, other.id);
        }

        public String toString() {
            return id + " | " + name + " | " + department + " | " + salary;
        }

         public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }

            if (!(obj instanceof Employee)) {
                return false;
            }

            Employee other = (Employee) obj;
            return this.id == other.id;
        }

        public int hashCode() {
            return Integer.hashCode(id);
        }
    }

    public static void main(String[] args) {
        Map<Integer, Employee> employees = new HashMap<>();

        employees.put(101, new Employee(101, "Arun", "IT", 75000));
        employees.put(102, new Employee(102, "Bala", "HR", 55000));
        employees.put(103, new Employee(103, "Charan", "IT", 90000));
        employees.put(104, new Employee(104, "Divya", "Finance", 80000));
        employees.put(105, new Employee(105, "Meena", "HR", 65000));
        employees.put(106, new Employee(106, "Ravi", "IT", 95000));
        employees.put(107, new Employee(107, "Priya", "Finance", 85000));

        System.out.println("1. Employee lookup:");
        System.out.println(employees.get(103));

        Map<String, Integer> departmentCount = new HashMap<>();

        for (Employee employee : employees.values()) {
            departmentCount.merge(employee.department, 1, Integer::sum);
        }

        System.out.println("\n2. Department frequency:");
        System.out.println(departmentCount);

        Map<String, Integer> sortedReport = new TreeMap<>(departmentCount);

        System.out.println("\n3. Sorted department report:");
        for (Map.Entry<String, Integer> entry : sortedReport.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

         List<Employee> employeeList = new ArrayList<>(employees.values());
        Collections.sort(employeeList);

        System.out.println("\n4. Employees sorted by ID:");
        for (Employee employee : employeeList) {
            System.out.println(employee);
        }

         employeeList.sort(
                Comparator.comparingDouble((Employee e) -> e.salary)
                        .reversed()
                        .thenComparingInt(e -> e.id)
        );

        System.out.println("\n5. Top 5 salaries:");
        for (int i = 0; i < 5 && i < employeeList.size(); i++) {
            System.out.println(employeeList.get(i));
        }

        System.out.println("\n6. Missing hashCode() bug demonstration:");

        Set<EmployeeWithoutHashCode> buggySet = new HashSet<>();

        buggySet.add(new EmployeeWithoutHashCode(201, "Kumar"));
        buggySet.add(new EmployeeWithoutHashCode(201, "Kumar"));

        System.out.println("Set size without hashCode(): " + buggySet.size());

        Set<Employee> correctSet = new HashSet<>();

        correctSet.add(new Employee(201, "Kumar", "IT", 50000));
        correctSet.add(new Employee(201, "Kumar", "IT", 50000));

        System.out.println("Set size with hashCode(): " + correctSet.size());
    }

     static class EmployeeWithoutHashCode {
        int id;
        String name;

        EmployeeWithoutHashCode(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }

            if (!(obj instanceof EmployeeWithoutHashCode)) {
                return false;
            }

            EmployeeWithoutHashCode other = (EmployeeWithoutHashCode) obj;

            return this.id == other.id && this.name.equals(other.name);
        }
    }
}
