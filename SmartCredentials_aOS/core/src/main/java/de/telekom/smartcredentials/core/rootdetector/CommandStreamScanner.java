/*
 * Copyright (c) 2019 Telekom Deutschland AG
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.telekom.smartcredentials.core.rootdetector;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class CommandStreamScanner {

    private static final String REGEX_NEW_LINE = "\n";

    public static String[] getCommand(String command) {
        InputStream inputstream;
        try {
            inputstream = Runtime.getRuntime().exec(command).getInputStream();
        } catch (IOException e) {
            return null;
        }
        String propertyValue = "";
        if (inputstream != null) {
            try {
                Scanner scanner = new Scanner(inputstream, Charset.defaultCharset().name()).useDelimiter("\\A");
                propertyValue = scanner.next();
            } catch (NoSuchElementException ignored) {
                return null;
            }
        }

        return propertyValue.split(REGEX_NEW_LINE);
    }

}
