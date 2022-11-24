package example.avro;

import org.apache.avro.Schema;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;

import java.io.File;
import java.io.IOException;

/**
 * @Author yingge
 * @Date 2022/11/24 22:45
 */
public class UserNoCodeGenerationTest {
    private static  final String filePath = "D:\\workspace\\JavaForOpen\\AvroExample\\src\\main\\resources\\users1.avro";
    private static final String schemaFilePath = "D:\\workspace\\JavaForOpen\\AvroExample\\src\\main\\resources\\avro\\user.avsc";
    public static void main(String[] args) {
        beanCreate();
        serialize();
        deserialize();
    }
    public static void beanCreate(){
        try {
            Schema schema = new Schema.Parser().parse(new File(schemaFilePath));
            GenericRecord user1 = new GenericData.Record(schema);
            user1.put("name", "Alyssa");
            user1.put("favorite_number", 256);
            // Leave favorite color null
            //如果put schema中不存在的字段,运行时会抛出异常AvroRuntimeException
            GenericRecord user2 = new GenericData.Record(schema);
            user2.put("name", "Ben");
            user2.put("favorite_number", 7);
            user2.put("favorite_color", "red");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static void serialize(){
        try {
            Schema schema = new Schema.Parser().parse(new File(schemaFilePath));
            GenericRecord user1 = new GenericData.Record(schema);
            user1.put("name", "Alyssa");
            user1.put("favorite_number", 256);
            // Leave favorite color null
            //如果put schema中不存在的字段,运行时会抛出异常AvroRuntimeException
            GenericRecord user2 = new GenericData.Record(schema);
            user2.put("name", "Ben");
            user2.put("favorite_number", 7);
            user2.put("favorite_color", "red");
            // Serialize user1 and user2 to disk
            File file = new File(filePath);
            DatumWriter<GenericRecord> datumWriter = new GenericDatumWriter<>(schema);
            DataFileWriter<GenericRecord> dataFileWriter = new DataFileWriter<GenericRecord>(datumWriter);
            dataFileWriter.create(schema, file);
            dataFileWriter.append(user1);
            dataFileWriter.append(user2);
            dataFileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void deserialize() {
        try {
            Schema schema = new Schema.Parser().parse(new File(schemaFilePath));
            // Deserialize users from disk
            DatumReader<GenericRecord> datumReader = new GenericDatumReader<>(schema);
            DataFileReader<GenericRecord> dataFileReader = new DataFileReader<GenericRecord>(new File(filePath), datumReader);
            GenericRecord user = null;
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
