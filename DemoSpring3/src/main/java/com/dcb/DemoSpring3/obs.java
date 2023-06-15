package com.dcb.DemoSpring3;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class obs {

    public static void main(String[] args) {
        String dateString = "3/12/2023 11:11:14 AM";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy h:mm:ss a", Locale.US);
        LocalDateTime dateTime = LocalDateTime.parse(dateString, formatter);
        System.out.println(dateTime);
        LocalDateTime dateTimes = LocalDateTime.now();
        System.out.println(dateTimes);
    }
}
