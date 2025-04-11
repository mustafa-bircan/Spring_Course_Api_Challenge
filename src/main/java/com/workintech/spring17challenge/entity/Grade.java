package com.workintech.spring17challenge.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Grade {
    private int coefficient;
    private String note;

    public Grade(String note) {
        this.note = note;
        this.coefficient = switch (note.toUpperCase()) {
            case "AA" -> 4;
            case "BA" -> 3;
            case "BB" -> 3;
            case "CB" -> 2;
            case "CC" -> 2;
            case "DC" -> 1;
            case "DD" -> 1;
            case "FD" -> 0;
            case "FF" -> 0;
            default -> throw new IllegalArgumentException("Geçersiz not: " + note);
        };
    }
}