package com.dmrj.dm_resqjeevan.services;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface CloudinaryImageService {
    Map upload(MultipartFile file);
}
