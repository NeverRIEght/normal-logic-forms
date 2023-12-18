import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int[][] tableOfTruth = convertFromCNFToTable(sc);
        printTableOfTruth(tableOfTruth);
        System.out.println("DNF: " + convertFromTableToDNF(tableOfTruth));

        sc.close();
    }

    public static int[][] convertFromCNFToTable(Scanner sc) {
        String input = sc.nextLine();
        input = input.replaceAll(" ","");

        String[] arrayOfAdds = new String[32];

        if(input.contains(")(")) {
            arrayOfAdds = input.split("\\)\\(");
        }


        int[][] tableOfTruthRef = new int[32][6];
        int currentBinary = 0b00000;

        for(int i = 0; i < tableOfTruthRef.length; i++) {
            String refString = "";
            if(Integer.toBinaryString(currentBinary).length() < 5) {
                for(int k = 0; k < 5 - Integer.toBinaryString(currentBinary).length(); k++) {
                    refString += "0";
                }
            }
            refString += Integer.toBinaryString(currentBinary);
            tableOfTruthRef[i][0] = Integer.parseInt(String.valueOf(refString.charAt(0)));
            tableOfTruthRef[i][1] = Integer.parseInt(String.valueOf(refString.charAt(1)));
            tableOfTruthRef[i][2] = Integer.parseInt(String.valueOf(refString.charAt(2)));
            tableOfTruthRef[i][3] = Integer.parseInt(String.valueOf(refString.charAt(3)));
            tableOfTruthRef[i][4] = Integer.parseInt(String.valueOf(refString.charAt(4)));
            tableOfTruthRef[i][5] = 1;
            currentBinary++;
        }

        int[][] tableOfTruth = new int[32][6];


        for(int i = 0; i < arrayOfAdds.length; i++) {
            if(arrayOfAdds[i].contains("~a")) {
                tableOfTruth[i][0] = 1;
            } else {
                tableOfTruth[i][0] = 0;
            }

            if(arrayOfAdds[i].contains("~b")) {
                tableOfTruth[i][1] = 1;
            } else {
                tableOfTruth[i][1] = 0;
            }

            if(arrayOfAdds[i].contains("~c")) {
                tableOfTruth[i][2] = 1;
            } else {
                tableOfTruth[i][2] = 0;
            }

            if(arrayOfAdds[i].contains("~d")) {
                tableOfTruth[i][3] = 1;
            } else {
                tableOfTruth[i][3] = 0;
            }

            if(arrayOfAdds[i].contains("~e")) {
                tableOfTruth[i][4] = 1;
            } else {
                tableOfTruth[i][4] = 0;
            }

            for(int j = 0; j < tableOfTruthRef.length; j++) {
                if(tableOfTruthRef[j][0] == tableOfTruth[i][0] &&
                        tableOfTruthRef[j][1] == tableOfTruth[i][1] &&
                        tableOfTruthRef[j][2] == tableOfTruth[i][2] &&
                        tableOfTruthRef[j][3] == tableOfTruth[i][3] &&
                        tableOfTruthRef[j][4] == tableOfTruth[i][4]) {
                    tableOfTruthRef[j][5] = 0;
                }
            }
        }

        return tableOfTruthRef;
    }
    public static void printTableOfTruth(int[][] tableOfTruth) {
        System.out.println("Table of Truth:");
        System.out.println("a\tb\tc\td\te\tf");
        for(int i = 0; i < tableOfTruth.length; i++) {
            for(int j = 0; j < tableOfTruth[i].length; j++) {
                System.out.print(tableOfTruth[i][j] + "\t");
            }
            System.out.println();
        }
    }
    public static String convertFromTableToDNF(Scanner sc) {
        // Устанавливаем в качестве разделителя символ новой строки
        sc.useDelimiter("\n");

        // Считываем многострочный текст
        System.out.println("Введите многострочный текст (для завершения введите Ctrl+D в конце):");

        int lineCounter = 0;
        String outputDNF = "";

        while (lineCounter < 32) {
            String line = sc.next();
            lineCounter++;
            if(!convertTableStringToDNF(line).equals("")) {
                outputDNF += convertTableStringToDNF(line) + "+";
            }
        }

        return outputDNF.substring(0, outputDNF.length() - 1);
    }

    public static String convertFromTableToDNF(int[][] tableOfTruth) {


        int lineCounter = 0;
        String outputDNF = "";

        while (lineCounter < 32) {
            String line = tableOfTruth[lineCounter][0] + "\t" +
                    tableOfTruth[lineCounter][1] + "\t" +
                    tableOfTruth[lineCounter][2] + "\t" +
                    tableOfTruth[lineCounter][3] + "\t" +
                    tableOfTruth[lineCounter][4] + "\t" +
                    tableOfTruth[lineCounter][5];
            lineCounter++;
            if(!convertTableStringToDNF(line).equals("")) {
                outputDNF += convertTableStringToDNF(line) + "+";
            }
        }

        return outputDNF.substring(0, outputDNF.length() - 1);
    }

    public static String convertTableStringToDNF (String input) {
        String[] arr = input.split("\t");

        if(arr[5].equals("0")) {
            return "";
        }

        String output = "";

        if(arr[0].equals("1")) {
            output += "a";
        } else {
            output += "~a";
        }

        if(arr[1].equals("1")) {
            output += "b";
        } else {
            output += "~b";
        }

        if(arr[2].equals("1")) {
            output += "c";
        } else {
            output += "~c";
        }

        if(arr[3].equals("1")) {
            output += "d";
        } else {
            output += "~d";
        }

        if(arr[4].equals("1")) {
            output += "e";
        } else {
            output += "~e";
        }

        return output;
    }
}
