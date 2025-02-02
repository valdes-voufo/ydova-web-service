package com.ydova.ahub.dto;

import com.ydova.ahub.entity.Email;
import lombok.*;

import java.util.List;

@Setter
@Getter
@Builder
@Data
public class EmailGroupDto {

        private Long id;
        private String name;
        List<Email> emails;

}
