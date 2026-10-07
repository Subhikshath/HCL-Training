public class MonthlyUsageAnalyser {

    public static void main(String[] args) {

        final long SLAB1_RATE = 2;
        final long SLAB2_RATE = 3;
        final long SLAB3_RATE = 5;
        final long SLAB4_RATE = 7;

        int[] monthlyUsage = {
                120, 150, 180, 220,
                250, 300, 280, 260,
                210, 190, 160, 130
        };

        long total = 0;

        for (int i = 0; i < monthlyUsage.length; i++) {
            total = total + monthlyUsage[i];
        }

        double average = (double) total / monthlyUsage.length;

        int averageUsage = (int) average;

        int max = monthlyUsage[0];

        for (int i = 1; i < monthlyUsage.length; i++) {
            if (monthlyUsage[i] > max) {
                max = monthlyUsage[i];
            }
        }

        int min = monthlyUsage[0];

        for (int i = 1; i < monthlyUsage.length; i++) {
            if (monthlyUsage[i] < min) {
                min = monthlyUsage[i];
            }
        }

        char grade = average <= 150 ? 'A' :
                     average <= 250 ? 'B' :
                     average <= 350 ? 'C' : 'D';

        System.out.println("Monthly Usage Analyser");
        System.out.println("Total Usage: " + total);
        System.out.println("Average Usage: " + average);
        System.out.println("Cast Average: " + averageUsage);
        System.out.println("Maximum Usage: " + max);
        System.out.println("Minimum Usage: " + min);
        System.out.println("Grade: " + grade);

        int[][] houses = {
                {120, 150, 180, 220, 250, 300, 280, 260, 210, 190, 160, 130},
                {200, 210, 220, 230, 240, 250, 260, 270, 280, 290, 300, 310},
                {100, 120, 140, 160, 180, 200, 220, 240, 260, 280, 300, 320}
        };

        for (int i = 0; i < houses.length; i++) {

            long houseTotal = 0;

            for (int j = 0; j < houses[i].length; j++) {
                houseTotal = houseTotal + houses[i][j];
            }

            double houseAverage = (double) houseTotal / houses[i].length;

            System.out.println("House " + (i + 1) +
                    " Total: " + houseTotal +
                    " Average: " + houseAverage);
        }
    }
}