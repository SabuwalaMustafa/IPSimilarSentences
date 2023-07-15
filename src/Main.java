import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String inputLine;
        String[] splittedInput;
        ISimilarSentencesDS similarSentencesDS = ISimilarSentenceDSFactory.getISimilarSentencesDS();
        while (true) {
            inputLine = bufferedReader.readLine().trim();
            if (inputLine.toLowerCase().equals("exit")) {
                break;
            }
            splittedInput = inputLine.split(" ");
            boolean isValidInput = validateInput(inputLine);
            if (!isValidInput) {
                System.out.println("Not a valid input. Please try again...");
                continue;
            }
            if (splittedInput[0].equals("1")) {
                similarSentencesDS.addSynonymPair(splittedInput[1], splittedInput[2]);
            } else if (splittedInput[0].equals("2")) {
                similarSentencesDS.removeSynonymPair(splittedInput[1], splittedInput[2]);
            } else {
                StringBuilder sb = new StringBuilder();
                Arrays.stream(splittedInput).skip(1).forEach(s -> sb.append(s).append(" "));
                String inputSentence = sb.toString().trim();
                similarSentencesDS.getSentences(inputSentence).forEach(System.out::println);
            }
        }
    }

    private static boolean validateInput(String inputLine) {
        if (inputLine == null) return false;
        String[] splitted = inputLine.split(" ");
        if (splitted[0].equals("1") || splitted[0].equals("2")) {
            return splitted.length == 3;
        } else if (splitted[0].equals("3")) {
            return splitted.length > 1;
        } else {
            return false;
        }
    }


}
