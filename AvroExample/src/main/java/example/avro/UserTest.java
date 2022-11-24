package example.avro;

import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;

import java.io.File;
import java.io.IOException;

/**
 * @Author yingge
 * @Date 2022/11/24 22:00
 */
public class UserTest {
    private static  final String filePath = "D:\\workspace\\JavaForOpen\\AvroExample\\src\\main\\resources\\users.avro";
    public static void main(String[] args) {
//        serializeWithCodeGeneration();
        deserializeWithCodeGeneration();
    }

    public static void notNeedSetAllFieldIfCanBeNullUsingConstructor() {
        User user = new User();
        user.setName("cbf");
        System.out.println(user.toString());
    }

    public static void notSetAllFieldCauseErrorUsingBuilder() {
        User builderCreate = User.newBuilder().setName("this").setFavoriteColor("this").build();
        System.out.println(builderCreate.toString());
    }

    public static void serializeWithCodeGeneration(){
        User user1 = new User();
        user1.setName("Alyssa");
        user1.setFavoriteNumber(256);
        // Leave favorite color null

        // Alternate constructor
        User user2 = new User("Ben", 7, "red");

        // Construct via builder
        User user3 = User.newBuilder()
                .setName("Charlie")
                .setFavoriteColor("blue")
                .setFavoriteNumber(null)
                .build();
        // Serialize user1, user2 and user3 to disk
        DatumWriter<User> userDatumWriter = new SpecificDatumWriter<>(User.class);
        DataFileWriter<User> dataFileWriter = new DataFileWriter<User>(userDatumWriter);
        try {
            dataFileWriter.create(user1.getSchema(), new File(filePath));
            dataFileWriter.append(user1);
            dataFileWriter.append(user2);
            dataFileWriter.append(user3);
            dataFileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void deserializeWithCodeGeneration(){
        // Deserialize Users from disk
        try {
            DatumReader<User> userDatumReader = new SpecificDatumReader<>(User.class);
            DataFileReader<User> dataFileReader = new DataFileReader<User>(new File(filePath), userDatumReader);
            User user = null;
            while (dataFileReader.hasNext()) {
            // Reuse user object by passing it to next(). This saves us from
            // allocating and garbage collecting many objects for files with
            // many items.
                user = dataFileReader.next(user);
                System.out.println(user.toString());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


}
