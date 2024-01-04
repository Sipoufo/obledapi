package com.example.obledapi.payloads.responses;

import lombok.*;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class DataResponse implements Serializable {
    private List<?> data;
    private int actualPage;
    private int dataNumber;
    private Pageable pageable;
}
