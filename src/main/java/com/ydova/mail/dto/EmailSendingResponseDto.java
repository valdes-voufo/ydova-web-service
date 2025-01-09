package com.ydova.mail.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class EmailSendingResponseDto {
    private  String recipient;
    private boolean sent ;
}
