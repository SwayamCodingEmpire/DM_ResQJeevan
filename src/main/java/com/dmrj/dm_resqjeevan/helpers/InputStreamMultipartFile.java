package com.dmrj.dm_resqjeevan.helpers;

import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

public class InputStreamMultipartFile implements MultipartFile {
    private final InputStream inputStream;
    private final String name;
    private final String orginalFileName;
    private final String contentType;
    private byte[] content;

    public InputStreamMultipartFile(InputStream inputStream, String name, String orginalFileName, String contentType) {
        this.inputStream = inputStream;
        this.name = name;
        this.orginalFileName = orginalFileName;
        this.contentType = contentType;
        try{
            this.content = StreamUtils.copyToByteArray(inputStream);
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
        finally {
            try {
                inputStream.close();
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getOriginalFilename() {
        return this.orginalFileName;
    }

    @Override
    public String getContentType() {
        return this.contentType;
    }

    @Override
    public boolean isEmpty() {
        return (this.content == null || this.content.length == 0);
    }

    @Override
    public long getSize() {
        return this.content.length;
    }

    @Override
    public byte[] getBytes() throws IOException {
        return this.content;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(this.content);
    }

    @Override
    public void transferTo(File dest) throws IOException, IllegalStateException {
        new FileOutputStream(dest).write(this.content);
    }
}
