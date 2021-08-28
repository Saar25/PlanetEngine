package org.saar.utils.file;

import java.io.InputStream;
import java.util.Scanner;

public final class TextFileLoader {

    private TextFileLoader() {

    }

    public static String loadResource(String fileName) throws Exception {
        String result;
        try (InputStream in = TextFileLoader.class.getResourceAsStream(fileName);
             Scanner scanner = new Scanner(in, "UTF-8")) {
            result = scanner.useDelimiter("\\A").next();
        }
        return result;
    }
}