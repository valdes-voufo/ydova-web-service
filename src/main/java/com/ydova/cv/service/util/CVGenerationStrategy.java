package com.ydova.cv.service.util;

import com.ydova.ahub.entity.AHubClient;
import com.ydova.cv.CVGenerationException;

import java.io.File;

public interface CVGenerationStrategy  {

    File generate(AHubClient AHubClientDto) throws CVGenerationException;
}
