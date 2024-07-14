package com.dmrj.dm_resqjeevan.services.serviceImpl;

import com.cloudinary.Cloudinary;
import com.dmrj.dm_resqjeevan.services.CloudinaryImageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
@Service
public class CloudinaryImageServiceImpl implements CloudinaryImageService {
    private final Cloudinary cloudinary;

    public CloudinaryImageServiceImpl(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    @Override
    public Map upload(MultipartFile file) {
        try {
            Map data = this.cloudinary.uploader().upload(file.getBytes(),Map.of());
            return data;
        }
        catch (IOException exception){
            throw new RuntimeException("Image Uploading Fail" + exception);
        }
    }

    public InputStream getImageFile(String public_id) throws IOException {
        String url = cloudinary.url().generate(public_id);
        return downloadImage(url);
    }

    private InputStream downloadImage(String imageUrl) throws IOException {
        URL url = new URL(imageUrl);
        HttpsURLConnection connection = (HttpsURLConnection)url.openConnection();
        connection.setRequestMethod("GET");
        connection.setDoInput(true);
        connection.connect();
        return connection.getInputStream();
    }
}
