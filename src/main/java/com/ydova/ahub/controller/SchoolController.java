package com.ydova.ahub.controller;


import com.ydova.ahub.entity.School;
import com.ydova.ahub.service.SchoolService;
import jakarta.validation.Valid;
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
    public School add(@RequestBody @Valid School school) {
       return schoolService.create(school);
    }

    @GetMapping("/school")
    public List<School> readAll() {
        return schoolService.readAll();
    }

    @PostMapping("/school-many")
    public String addSchoolList(@RequestBody @Valid  List<School> schoolDtoList) {
        schoolDtoList.forEach(schoolService::create);
        return "School Successfully created";
    }

    @DeleteMapping("/school/{id}")
    public String remove(@PathVariable("id") Long id) {
         schoolService.remove(id);
        return "School Successfully created";
    }
}
