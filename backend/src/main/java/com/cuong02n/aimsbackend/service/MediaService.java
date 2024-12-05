package com.cuong02n.aimsbackend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class MediaService {
    @Value("${aims.review.saved-folder}")
    private String savedReviewMedia;

    /**
     *
     * @param file: media file: video or image
     * @return relative path to file
     */
    public String saveMedia(MultipartFile file){
        return "/%s".formatted(file.getOriginalFilename());
    }
}
