package com.cs334.project3.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
public class BaseDTO<D>{
    @Setter
    protected String status;
    @Setter
    protected D data;

    public BaseDTO(D data){
        this.data = data;
    }

    public void ok(){
        status = "ok";
    }

    public void error(){
        status = "error";
    }
}
