package com.fortumo.httpserver.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class HttpConnectionWorkerThread extends Thread {

    private final static Logger LOGGER = LoggerFactory.getLogger(HttpConnectionWorkerThread.class);
    private Socket socket;
    private List<Integer> numbersList;
    private StringBuilder stringBuilder;

    public HttpConnectionWorkerThread(Socket socket, List<Integer> numbersList, StringBuilder stringBuilder) {
        this.socket = socket;
        this.numbersList = numbersList;
        this.stringBuilder = stringBuilder;
    }

    @Override
    public void run() {
        InputStream inputStream = null;
        OutputStream outputStream = null;

        try {
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
            int postDataI = 0;

            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = in.readLine()) != null && (line.length() != 0)) {
                LOGGER.info(" HTTP-HEADER: {}",  line);
                if (line.indexOf("Content-Length:") > -1) {
                    postDataI = Integer.parseInt(line.substring(
                            line.indexOf("Content-Length:") + 16
                    ));
                }
            }
            String postData = "";
            if (postDataI > 0) {
                char[] charArray = new char[postDataI];
                in.read(charArray, 0, postDataI);
                postData = new String(charArray);
            }

                if (!postData.equalsIgnoreCase("end")) {
                    if("end".equalsIgnoreCase(stringBuilder.toString())) {
                        numbersList.clear();
                        stringBuilder.setLength(0);
                    }
                    numbersList.add(Integer.parseInt(postData));
                } else if (!"end".equalsIgnoreCase(stringBuilder.toString())) {
                    stringBuilder.append("end");
                }
                while (!"end".equalsIgnoreCase(stringBuilder.toString())) {
                    yield();
                }

            LOGGER.info(" Result generated for each thread {}", numbersList.stream().mapToInt(Integer::intValue).sum());
           String httpResponse = "HTTP/1.1 200 OK\r\n\r\n" + numbersList.stream().mapToInt(Integer::intValue)
                    .sum();
            outputStream.write(httpResponse.getBytes());

            LOGGER.info(" * Connection Processing Finished.");
        } catch (IOException e) {
            LOGGER.error("Problem with communication", e);
        } finally {
            if (inputStream!= null) {
                try {
                    inputStream.close();
                } catch (IOException e) {}
            }
            if (outputStream!=null) {
                try {
                    outputStream.close();
                } catch (IOException e) {}
            }
            if (socket!= null) {
                try {
                    socket.close();
                } catch (IOException e) {}
            }


        }
    }

}
