package com.fortumo.test;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

/**
 * Note: The server has to be up and running
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestApplication {

    @Test
    public void execute() throws IOException, InterruptedException {
        String[] commands = new String[] {"1", "2", "5", "end", "1", "12", "9", "2", "22", "end", "11", "33",
        "19", "33", "5", "1", "5", "6", "7", "end"};

        for (int i = 0; i < commands.length; i++) {
            String command = "curl -d " + commands[i] + " http://localhost:8081/";
            ProcessBuilder processBuilder = new ProcessBuilder(command.split(" "));
            processBuilder.start();

           // Threads doesn't guarantees the order of execution. The sleep method can be removed and tested.
            // The sum shown in console need not be calculated as per the order of commands array defined.
            sleep(5000);
        }
    }
}
