package me.lozm.messageQueue;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Field {

    private String type;
    private boolean optional;
    private String field;

}
