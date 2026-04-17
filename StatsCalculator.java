public class StatsCalculator {

    public static void main(String[] args) {

        // 2D Array Initialization
        double[][] scores = {
                { 78.0, 85.0, 80.0, 103.0 }, // Student 1
                { 82.5, 88.0, 74.0, 95.0 }, // Student 2
                { 95.5, 90.0, 96.0, 98.0 }, // Student 3
                { 60.0, 70.0, 65.5, 72.0 }, // Student 4
                { 82.5, 92.0, 92.5, 67.0 } // Student 5
        };

        System.out.println("--- GradeBook Statistics ---\n");

        calculateStudentAverages(scores);
        System.out.println();

        calculateAssignmentAverages(scores);
        System.out.println();

        findHighestScore(scores);
    }

    // Row Traversal
    public static void calculateStudentAverages(double[][] data) {
        System.out.println("Student Averages:");

        for (int i = 0; i < data.length; i++) {
            double sum = 0.0;

            for (int j = 0; j < data[i].length; j++) {
                sum += data[i][j];
            }

            double average = sum / data[i].length;
            System.out.printf("Student %d: %.2f%n", i + 1, average);
        }
    }

    // Column Traversal
    public static void calculateAssignmentAverages(double[][] data) {
        System.out.println("Assignment Averages:");

        for (int j = 0; j < data[0].length; j++) {
            double sum = 0.0;

            for (int i = 0; i < data.length; i++) {
                sum += data[i][j];
            }

            double average = sum / data.length;
            System.out.printf("Assignment %d: %.2f%n", j + 1, average);
        }
    }

    // Search Logic
    public static void findHighestScore(double[][] data) {
        double max = Double.NEGATIVE_INFINITY;
        int bestStudent = 0;
        int bestAssignment = 0;

        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                if (data[i][j] > max) {
                    max = data[i][j];
                    bestStudent = i;
                    bestAssignment = j;
                }
            }
        }

        System.out.printf(
                "Highest Score in Class: %.2f (Student %d, Assignment %d)%n",
                max,
                bestStudent + 1,
                bestAssignment + 1);
    }
}