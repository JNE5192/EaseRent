package com.example.rent.management.system.easerent.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.rent.management.system.easerent.entity.Property;
import com.example.rent.management.system.easerent.repository.PropertyRepository;

@Service
public class ImageService {

	@Autowired
    private PropertyRepository imageRepository;

    public String saveImage(MultipartFile file) throws IOException {
        Property image = new Property();
        image.setImageName(file.getOriginalFilename());
        image.setContentType(file.getContentType());
        image.setPhoto(file.getBytes());

        imageRepository.save(image);

        return "Image uploaded successfully: " + file.getOriginalFilename();
    }
}
