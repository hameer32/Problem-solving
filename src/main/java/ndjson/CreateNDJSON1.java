package ndjson;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Employee;

import java.io.*;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class CreateNDJSON1 {
    static ObjectMapper objectMapper = new ObjectMapper();
    public static void main(String[] args) throws IOException {
        File file=new File("src/main/resources/files/data1.ndjson");
        Path path = Paths.get("src/main/resources/files");
        PrintWriter printWriter = new PrintWriter(
                new FileWriter(file, false));
        PrintWriter printWriter1 = new PrintWriter(
                file,"UTF-8");

        Employee employee;
//        JsonGenerator g = objectMapper.getFactory().createGenerator(file, JsonEncoding.UTF8);
        JsonGenerator g = objectMapper.getFactory().createGenerator(printWriter1);
        for (int i = 0; i < 10; i++) {
            employee = new Employee(i + 1, generateRandomString(7), getInts(), generateRandomString(15));
            //System.out.println(objectMapper.writeValueAsString(employee));
            if (i!=0)printWriter1.println("");
            objectMapper.writeValue(g,employee);
            printWriter1.flush();
        }

        deSerializerNDJSONIle(file);
        g.close();


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

    private static void deSerializerNDJSONIle1(File file) throws IOException {
        BufferedReader bufferedReader= Files.newBufferedReader(file.toPath());
        String s=bufferedReader.readLine();
        int i=0;
        while(s!=null){
            String[] arr=(s.split(" "));
            for(String s1:arr){
                Employee e=objectMapper.readValue(s1,Employee.class);
                System.out.println("employee : "+(i+1)+" --> "+s1.toString());
                i++;
            }
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
