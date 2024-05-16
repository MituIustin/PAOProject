import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AuditService {
    private static final String AUDIT_FILE_PATH = "audit.csv";

    public static void logAction(String action) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(AUDIT_FILE_PATH, true))) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String timestamp = dateFormat.format(new Date());
            writer.println(action + "," + timestamp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
