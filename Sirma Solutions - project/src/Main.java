import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static <Pair> void main(String[] args) {
        String csvFile = "D:\\JavaPrograms\\Advanced\\Sirma Solutions - project\\src\\file.csv";

        List<Employee> employeeList = new ArrayList<>();
        Map<Integer,List<Employee>> grupByProjectMap = new HashMap<>();

        try(BufferedReader in = new BufferedReader(new FileReader(csvFile))) {
           String line = in.readLine();

           //Read information from file
           while (line != null){
               String[] arr = line.split(", ");

               int empId = Integer.parseInt(arr[0]);
               int projId = Integer.parseInt(arr[1]);
               LocalDate dateFrom = LocalDate.parse((arr[2]));
               LocalDate dateTo;
               if (arr[3].equals("NULL")) {
                   dateTo = LocalDate.now();
               }else {
                   dateTo = LocalDate.parse(arr[3]);
               }
               //create object of class Employee
               Employee employee = new Employee(empId,projId,dateFrom,dateTo);
               //add the object in the list
               employeeList.add(employee);

               line = in.readLine();
           }

           //Group employees in map by projectId like a key
            List<Employee> employeeList1 = new ArrayList<>();
            for(int i=0; i<employeeList.size(); i++){
                int key = employeeList.get(i).getProjectID();
                if (grupByProjectMap.get(key) != null) {
                    for (int j = 0; j < grupByProjectMap.get(key).size(); j++) {
                        employeeList1.add(grupByProjectMap.get(key).get(j));
                    }
                }
                employeeList1.add(employeeList.get(i));
                grupByProjectMap.put(key,employeeList1);
                employeeList1 = new ArrayList<>();

            }

            //print the longest working pair
            System.out.println(findLongestWorkingPair(grupByProjectMap).toString());



        }catch (IOException e){
            e.printStackTrace();
        }
    }


    private static Pair findLongestWorkingPair(Map<Integer, List<Employee>> employeesByProject) {
        Pair longestWorkingPair = null;
        int longestWorkingDays = 0;
        for (List<Employee> employees : employeesByProject.values()) {
            for (int i = 0; i < employees.size(); i++) {
                for (int j = i + 1; j < employees.size(); j++) {
                    int daysWorked = calculateDaysWorked(employees.get(i), employees.get(j));
                    if (daysWorked > longestWorkingDays) {
                        longestWorkingDays = daysWorked;
                        longestWorkingPair = new Pair(employees.get(i).getEmpID(), employees.get(j).getEmpID(), employees.get(i).getProjectID(), daysWorked);
                    }
                }
            }
        }
        return longestWorkingPair;
    }
    private static int calculateDaysWorked(Employee emp1, Employee emp2) {
        LocalDate dateFrom1 = emp1.getDateFrom();
        LocalDate dateTo1 = emp1.getDateTo();
        LocalDate dateFrom2 = emp2.getDateFrom();
        LocalDate dateTo2 = emp2.getDateTo();

        //If dateFrom1 is after dateFrom2, it assigns dateFrom1 to the startDate variable.
        //Otherwise, it assigns dateFrom2 to startDate.
        LocalDate startDate = dateFrom1.isAfter(dateFrom2) ? dateFrom1 : dateFrom2;

        //If dateTo1 is before dateTo2, it assigns dateTo1 to the endDate variable.
        //Otherwise, it assigns dateTo2 to endDate.
        LocalDate endDate = dateTo1.isBefore(dateTo2) ? dateTo1 : dateTo2;


        if (startDate.isAfter(endDate)) {
            return 0;
        }


        //ChronoUnit.DAYS.between(startDate, endDate) -> calculates the number of days between the start and end dates
        return (int) ChronoUnit.DAYS.between(startDate, endDate);
    }
}
