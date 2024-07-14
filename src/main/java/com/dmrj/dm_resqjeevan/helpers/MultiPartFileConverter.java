package com.dmrj.dm_resqjeevan.helpers;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;

public class MultiPartFileConverter {
    public MultipartFile convert(InputStream inputStream, String public_id) throws IOException {
        if(inputStream != null) {
            return new InputStreamMultipartFile(inputStream,"file",public_id+".jpg","image/jpeg");
        }
        throw new IOException("Could not Convert to multipart file");
    }
}
