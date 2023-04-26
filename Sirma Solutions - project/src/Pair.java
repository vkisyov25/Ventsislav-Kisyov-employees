public class Pair {
    private int empId1;
    private int empId2;
    private int projectId;
    private int daysWorked;

    public Pair(int empId1, int empId2, int projectId, int daysWorked) {
        this.empId1 = empId1;
        this.empId2 = empId2;
        this.projectId = projectId;
        this.daysWorked = daysWorked;
    }

    public int getEmpId1() {
        return empId1;
    }

    public int getEmpId2() {
        return empId2;
    }

    public int getProjectId() {
        return projectId;
    }

    public int getDaysWorked() {
        return daysWorked;
    }

    @Override
    public String toString() {
        return "The longest working pair is with the following parameters: " + "empId1=" + empId1 + ", empId2=" + empId2 + ", projectId=" + projectId + ", daysWorked=" + daysWorked + '}';
    }
}
