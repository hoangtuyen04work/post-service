package com.service.post_service.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostResponse {
    String content;
    String title;
    String id;
    LocalDate creationDate;
    LocalDate modifiedDate;
    LocalDate deleteDate;
    String userId;
}
