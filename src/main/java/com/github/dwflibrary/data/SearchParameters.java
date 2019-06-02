package com.github.dwflibrary.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchParameters {

    private String key;
    private String value;
    private Operator operator;

    private boolean startsWith;
    private boolean endsWith;
    private boolean containing;

    private boolean filterByEnum;
    private String enumClass;
}
