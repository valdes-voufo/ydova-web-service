package com.ydova.ahub.controller;


import com.ydova.ahub.entity.School;
import com.ydova.ahub.service.SchoolService;
import com.ydova.cv.YdovaException;
import jakarta.validation.Valid;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class SchoolController {

  private  final   SchoolService schoolService;
 @Autowired
    public SchoolController(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    @PostMapping("/school")
    public School add(@RequestBody @Valid School school) throws YdovaException {
       return schoolService.create(school);
    }

    @GetMapping("/school")
    public List<School> readAll() {
        return schoolService.readAll();
    }



    @PostMapping("/school-many")
    public String addSchoolList(@RequestBody @Valid  List<School> schoolDtoList)  throws YdovaException {


        //todo implemnt
        return "School Successfully created";
    }

    @DeleteMapping("/school/{id}")
    public String remove(@PathVariable("id") Long id) {
         schoolService.remove(id);
        return "School Successfully created";
    }
}
