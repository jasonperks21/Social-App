package com.cs334.project3.requestbody;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateDispnameRequestBody {
    private Long userid;
    private String dispname;
    private String avatar;
}
