package ndjson;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.Employee;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.Random;

public class CreateNDJSON {
    static ObjectMapper objectMapper = new ObjectMapper();
    public static void main(String[] args) throws IOException {

        File file=new File("src/main/resources/files/data.ndjson");
        Path path = Paths.get("src/main/resources/files");
        PrintWriter printWriter = new PrintWriter(
                new FileWriter(file, false));

        Employee employee;
        long start=System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            employee = new Employee(i + 1, generateRandomString(7), getInts(), generateRandomString(15));
            //System.out.println(objectMapper.writeValueAsString(employee));
            printWriter.println(objectMapper.writeValueAsString(employee));
            printWriter.flush();
        }
        long end=System.currentTimeMillis();
        System.out.println(((double) (end-start)/1000)+"Seconds");

        //deSerializerNDJSONIle(file);


    }

    private static void deSerializerNDJSONIle(File file) throws IOException {
        BufferedReader bufferedReader= Files.newBufferedReader(file.toPath());
        String s=bufferedReader.readLine();
        int i=0;
        while(s!=null){
            Employee e=objectMapper.readValue(s,Employee.class);
            System.out.println("employee : "+(i+1)+" --> "+e.toString());
            i++;
            s=bufferedReader.readLine();
        }
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
