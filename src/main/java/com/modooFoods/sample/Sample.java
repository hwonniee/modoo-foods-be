package com.modooFoods.sample;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "샘플")
@Getter @Setter
public class Sample {
    private long id;

    @Schema(description = "이름", nullable = false, example = "장혜원")
    private String name;

    public Sample(long id, String name) {
        this.id = id;
        this.name = name;
    }
}
