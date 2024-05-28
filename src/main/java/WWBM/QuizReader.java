package WWBM;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class QuizReader {
    public static List<QuizItem> readQuizFromFile(String filePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new File(filePath), new TypeReference<List<QuizItem>>() {
        });
    }

    public static void main(String[] args) {
        try {
            List<QuizItem> quiz = readQuizFromFile("C:\\Users\\cbala\\Documents\\BME\\PROG 3\\HF_XZ6051\\src\\main\\java\\WWBM\\questions.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
