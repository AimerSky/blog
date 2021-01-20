package com.carroll.blog.search.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EsLearnTestHundred implements Serializable {

    private Long id;

    private String name;

    private String phone;

    private Boolean sex;

    private String address;

    private Date createDate;

}
