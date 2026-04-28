package br.com.haroldo.sgm.exceptions.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErroPadrao {
    private int code;
    private String message;
}
