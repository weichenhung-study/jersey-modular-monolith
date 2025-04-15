package com.ntou.tool;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.time.Duration;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

@Log4j2
public class ExecutionTimer {

    private static final Map<String, Instant[]> stages = new LinkedHashMap<>();

    @AllArgsConstructor
    public enum ExecutionModule {
        APPLICATION          ("APPLICATION" , "後端程式")
        , DATABASE           ("DATABASE" , "資料庫")
        , DATA_INTERFACE     ("DATA_INTERFACE", "資料庫介面")
        , CONSUL_APPLICATION ("CONSUL_APPLICATION" , "經過CONSUL進行AA MODE分流的後端程式")
        , GATEWAY_APPLICATION ("GATEWAY_APPLICATION" , "經過GATE WAY的應用程式")
        ;
        private final String value;
        private final String msg;

        @JsonValue
        public String getValue() {return value;}
        public String getMsg() {return msg;}

        public static ExecutionModule find(String val) {
            return Arrays.stream(values())
                    .filter(predicate
                            -> predicate.value.equals(val)
                            || predicate.msg.equals(val))
                    .findFirst()
                    .orElse(null);
        }
    }

//    public static void main(String[] args) {
//        String sid = ExecutionTimer.class.getSimpleName();
//        // 記錄每一階段開始與結束時間
//        startStage(ExecutionModule.APPLICATION.value);
//        simulateWork(500);
//        endStage(ExecutionModule.APPLICATION.value);
//
//        startStage(ExecutionModule.DATA_INTERFACE.value);
//        simulateWork(700);
//        endStage(ExecutionModule.DATA_INTERFACE.value);
//
//        startStage(ExecutionModule.DATABASE.value);
//        simulateWork(300);
//        endStage(ExecutionModule.DATABASE.value);
//
//        exportTimings(sid + "_" + DateTool.getYYYYmmDDhhMMss() + ".txt");
//    }

    public static void startStage(String name) {
        stages.put(name, new Instant[]{Instant.now(), null});
    }

    public static void endStage(String name) {
        if (stages.containsKey(name)) {
            stages.get(name)[1] = Instant.now();
        }
    }

    private static void simulateWork(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            log.error("InterruptedException: " ,e);
        }
    }

    public static void exportTimings(String fileName) {
        log.info("START exportTimings");
        long totalMillis = 0;

        final String projectLocation = "/test-result/program-execution-time/"; //"D:\\Project\\api-test-scripts\\program-execution-time\\";
        createProjectFolderIfNotExists(projectLocation);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(projectLocation + fileName))) {
            writer.write("Execution Time Report\n");
            writer.write("======================\n");

            for (Map.Entry<String, Instant[]> entry : stages.entrySet()) {
                String stageName = entry.getKey();
                Instant start = entry.getValue()[0];
                Instant end = entry.getValue()[1];

                if (start != null && end != null) {
                    long duration = Duration.between(start, end).toMillis();
                    totalMillis += duration;
                    writer.write(String.format("%s: %d ms\n", stageName, duration));
                } else {
                    writer.write(String.format("%s: Incomplete timing data\n", stageName));
                }
            }

            writer.write("----------------------\n");
            writer.write(String.format("Total Time: %d ms\n", totalMillis));
        } catch (IOException e) {
            log.error("Error writing file: " ,e);
        }
    }

    private static void createProjectFolderIfNotExists(String projectLocation) {
        File folder = new File(projectLocation);
        if (!folder.exists()) {
            boolean created = folder.mkdirs();
            if (created)
                log.info("資料夾建立成功：{} created", projectLocation);
            else
                log.error("資料夾建立失敗：{}", projectLocation);
        } else {
            log.info("資料夾已存在：{} created", projectLocation);

        }
    }
}

