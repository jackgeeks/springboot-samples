package com.wpp.springbootwebflux.pojo;

import lombok.*;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import java.lang.annotation.Documented;
import java.time.LocalDateTime;

/**
 * @Author: jackgeeks
 * @ProjectName: springboot-webflux
 * @Package: com.wpp.springbootwebflux.pojo
 * @ClassName: user
 * @Description: @todo
 * @CreateDate: 2020/5/3 22:19
 * @Version: 1.0
 */
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document( "user")
public  class User {
    @Id
    private String id;
    @NotBlank
    private String name;
    @Range(min = 0,max = 100)
    private Integer age;
    @CreatedDate
    private LocalDateTime createdDate;
}