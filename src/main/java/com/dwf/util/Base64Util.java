package com.dwf.util;

import org.apache.commons.io.FileUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Base64Util {

    public static File toFile(String base64, String path) throws Exception{
        File file = new File(path);
        byte[] decodedBytes = Base64.getDecoder().decode(normalizeBase64(base64).getBytes(StandardCharsets.UTF_8));
        FileUtils.writeByteArrayToFile(file, decodedBytes);
        return file;
    }

    public static String fromFile(String path) throws IOException {
        byte[] fileContent = FileUtils.readFileToByteArray(new File(path));
        return Base64.getEncoder().encodeToString(fileContent);
    }

    public static String extractExtension(String base64){
        String mimeType = extractMimeType(base64);
        if (!StringUtils.isEmpty(mimeType)){
            return mimeType.split("/")[1];
        }
        return "jpg";
    }

    private static String extractMimeType(final String encoded) {
        final Pattern mime = Pattern.compile("^data:([a-zA-Z0-9]+/[a-zA-Z0-9]+).*,.*");
        final Matcher matcher = mime.matcher(encoded);
        if (!matcher.find())
            return "";
        return matcher.group(1).toLowerCase();
    }

    public static String normalizeBase64(final String base64){
        if (base64.contains(",")){
            return base64.split(",")[1];
        }
        return base64;
    }

    public static String createWithHeader(String type, String extension, String base64){
        return "data:"+type+"/"+extension+";base64,"+base64;
    }

}
