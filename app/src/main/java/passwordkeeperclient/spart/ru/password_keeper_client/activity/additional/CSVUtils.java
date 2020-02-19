package passwordkeeperclient.spart.ru.password_keeper_client.activity.additional;

import java.io.IOException;
import java.io.SerializablePermission;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pamela on 07.12.2019.
 */

public class CSVUtils {
    private static final char DEFAULT_SEPARATOR = ';';


        public static void writeLine(Writer w, List<String> values) throws IOException {

//            boolean first = true;

            //default customQuote is empty

            StringBuilder sb = new StringBuilder();
            for (String value : values) {
                sb.append(value).append(DEFAULT_SEPARATOR);

            }
            sb.append("\n");
            w.append(sb.toString());


        }

}