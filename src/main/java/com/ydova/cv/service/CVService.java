package com.ydova.cv.service;

import com.ydova.cv.CVGenerationException;
import com.ydova.ahub.dto.AHubClientDto;
import com.ydova.ahub.repositoty.AHubClientRepository;
import com.ydova.cv.service.util.DefaultCVGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class CVService {
    public final AHubClientRepository cvRepository;

    @Autowired
    public CVService(AHubClientRepository cvRepository) {
        this.cvRepository = cvRepository;
    }

    public File generateCV(AHubClientDto dto) throws CVGenerationException {
        DefaultCVGenerator generator = new DefaultCVGenerator();
        return generator.generate(dto);
    }
}
