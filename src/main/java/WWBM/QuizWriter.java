package WWBM;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.exc.StreamReadException;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class QuizWriter {
    public static void writeQuizToFile(List<QuizItem> quizItems, String filePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File(filePath), quizItems);
    }
}
