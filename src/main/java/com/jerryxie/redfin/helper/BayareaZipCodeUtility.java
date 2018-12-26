package com.jerryxie.redfin.helper;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class BayareaZipCodeUtility {
    private Logger logger = Logger.getLogger(BayareaZipCodeUtility.class);
    private final String fileName = "src/main/resources/datasheets/zip_code.csv";

    public List<String> getZipcodes() {
        List<String> zipcodes = new ArrayList<>();
        try (BufferedReader buf = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)))) {
            //skip header line
            buf.readLine();
            String line = buf.readLine();
            while(line != null) {
                String[] tokens = line.split(",");
                zipcodes.add(tokens[1]);
                line = buf.readLine();
            }
        } catch (FileNotFoundException e) {
            logger.error(e);
        } catch (IOException e) {
            logger.error(e);
        }
        return zipcodes;
    }
}
