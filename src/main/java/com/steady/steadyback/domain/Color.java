package com.steady.steadyback.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public enum Color {
    RED("FC3C49"),
    ORANGE("FFA500"),
    YELLOW("FFD600"),
    GREEN("26C983"),
    L_GREEN("9BE23F"),
    BLUE("286CCF"),
    L_BLUE("43BBFF"),
    PINK("FF5CEF"),
    PURPLE("934CFF");

    private String value;

    Color(String value){
        this.value = value;
    }
}
