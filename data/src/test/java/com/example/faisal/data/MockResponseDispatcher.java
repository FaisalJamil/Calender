/*
 * Copyright (c) 2017. Faisal Jamil
 */

package com.example.faisal.data;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.RecordedRequest;

public class MockResponseDispatcher {

    private static MockResponse mockResponse = new MockResponse()
            .addHeader("Content-Type", "text/xml")
            .addHeader("Pragma", "no-cache")
            .addHeader("Cache-Control", "no-store")
            .addHeader("Expires", "0")
            .setResponseCode(200);

    public static boolean RETURN_500 = false;

    public static final Dispatcher DISPATCHER = new Dispatcher() {

        @Override
        public MockResponse dispatch(RecordedRequest request) throws InterruptedException {
            if (RETURN_500) {
                return new MockResponse().setResponseCode(500);
            } else if (request.getPath().matches("/some/path")) {
                //These files can be added copying the response from server
                //return mockResponse.setBody(getFile("getList.json"));
            } else if (request.getPath().matches("/add/path")) {
                //These files can be added copying the response from server
                return mockResponse.setBody(getFile("addBirthDay.json"));
            }
            return new MockResponse().setResponseCode(404);
        }
    };

    private MockResponseDispatcher() {
        // no instance by default
    }

    private static String getFile(String fileName) {
        StringBuilder result = new StringBuilder("");
        //Get file from resources folder
        ClassLoader classLoader = MockResponseDispatcher.class.getClassLoader();
        File file = new File(classLoader.getResource(fileName).getFile().replace("%20", " "));
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                result.append(line).append("\n");
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    public static void reset() {
        RETURN_500 = false;
    }
}
