package io.management.ua.utility;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import io.management.ua.annotations.Export;
import io.management.ua.exceptions.DefaultException;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Slf4j
public class CodeGenerator {
    @Export
    public static String generateCode() {
        return String.valueOf(new Random().nextInt((int) 10E3, (int) 10E4));
    }

    @Export
    public static String generateCode(String pattern) {
        StringBuilder code = new StringBuilder();

        for (Character symbol : pattern.toCharArray()) {
            switch (symbol) {
                case 'i' -> code.append(new Random().nextInt(0, 10));
                case 's' -> code.append((char) new Random().nextInt('A', (int) 'Z' + 1));
                case '-' -> code.append('-');
            }
        }

        return code.toString();
    }

    @Export
    public static byte[] generateQRCode(String qrCodeContent) {
        int width = 500;
        int height = 500;
        String fileType = "png";

        try {
            Map<EncodeHintType, Object> hintMap = new HashMap<>();
            hintMap.put(EncodeHintType.CHARACTER_SET, "UTF-8");

            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(qrCodeContent, BarcodeFormat.QR_CODE, width, height, hintMap);

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, fileType, byteArrayOutputStream);
            log.debug("QR code generated for {}", qrCodeContent);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException | WriterException e) {
            log.error(e.getMessage(), e);
            throw new DefaultException(e.getMessage());
        }
    }
}
