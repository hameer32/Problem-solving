package ndjson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Employee;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class CreateNDJSONStream {
    static ObjectMapper objectMapper = new ObjectMapper();

    public static void main(String[] args) throws IOException {
        File file = new File("src/main/resources/files/data.ndjson");
        Path path = Paths.get("src/main/resources/files");
        PrintWriter printWriter = new PrintWriter(
                new FileWriter(file, false));

        Employee employee;
        long start = System.currentTimeMillis();
        long end = System.currentTimeMillis();

        start = System.currentTimeMillis();
        PrintWriter printWriter1 = new PrintWriter(
                new FileWriter(new File("src/main/resources/files/dataStream.ndjson"), false));
        IntStream.range(1, 10000000).forEach(id -> {
            Employee employee1 = new Employee(id + 1, generateRandomString(7), getInts(), generateRandomString(15));
            try {
                printWriter1.println(objectMapper.writeValueAsString(employee1));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            printWriter1.flush();
        });
        end = System.currentTimeMillis();
        System.out.println(((double) (end - start) / 1000) + "Seconds using stream");
        start = System.currentTimeMillis();
        PrintWriter printWriter2 = new PrintWriter(
                new FileWriter(new File("src/main/resources/files/dataParallelStream.ndjson"), false));
        IntStream.range(1, 10000000).parallel().forEach(id -> {
            Employee employee1 = new Employee(id + 1, generateRandomString(7), getInts(), generateRandomString(15));
            try {
                printWriter2.println(objectMapper.writeValueAsString(employee1));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            printWriter2.flush();
        });
        end = System.currentTimeMillis();
        System.out.println(((double) (end - start) / 1000) + "Seconds using parallel stream");

        deSerializerNDJSONIle(file);


    }

    private static void deSerializerNDJSONIle(File file) throws IOException {
//        BufferedReader bufferedReader= Files.newBufferedReader(file.toPath());
//        String s=bufferedReader.readLine();
//        int i=0;
//        while(s!=null){
//            Employee e=objectMapper.readValue(s,Employee.class);
//            System.out.println("employee : "+(i.get() +1)+" --> "+e.toString());
//            i.getAndIncrement();
//            s=bufferedReader.readLine();
//        }
        //Concise Appraoch
        AtomicInteger i = new AtomicInteger();
        Files.lines(file.toPath(), StandardCharsets.UTF_8).forEach(line -> {
            Employee e = null;
            try {
                e = objectMapper.readValue(line, Employee.class);
            } catch (JsonProcessingException ex) {
                ex.printStackTrace();
            }
            System.out.println("employee : " + (i.get() + 1) + " --> " + e.toString());
            i.getAndIncrement();
        });
    }

    private static int getInts() {
        return new Random().ints(10, 70).findFirst().getAsInt();
    }

    private static String generateRandomString(int size) {
        String alphabet = "abcdefghijklmnopqrstuvwxyz" + "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            int index = random.nextInt(alphabet.length());
            char randomChar = alphabet.charAt(index);
            sb.append(randomChar);
        }
        return sb.toString();
    }

}
