package com.ydova.cv.service.util;

import com.ydova.cv.CVGenerationException;
import com.ydova.ahub.dto.AHubClientDto;

import java.io.File;

public interface CVGenerationStrategy  {

    File generate(AHubClientDto AHubClientDto) throws CVGenerationException;
}
