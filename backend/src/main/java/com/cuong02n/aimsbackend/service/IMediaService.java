package com.cuong02n.aimsbackend.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

public interface IMediaService {
    String saveMedia(MultipartFile file);
}
