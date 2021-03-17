package com.prodnees.util;

import java.util.concurrent.ThreadLocalRandom;

public abstract class OtpUtil {

    private OtpUtil() {
    }

    public static String generateRandomOtp(int otpLength) {
        StringBuilder builder = new StringBuilder();
        int loop = otpLength / 3;
        for (int i = 0; i < loop; i++) {
            builder.append(ThreadLocalRandom.current().nextInt(0, 9))
                    .append((char) ThreadLocalRandom.current().nextInt(64, 90))
                    .append((char) ThreadLocalRandom.current().nextInt(97, 122));
        }

        return builder.toString();
    }

}
