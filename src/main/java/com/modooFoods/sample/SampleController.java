package com.modooFoods.sample;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Tag(name = "sample", description = "샘플 코드 모음")
@RestController
@RequestMapping("samples")
public class SampleController {
    @Operation(
            summary = "모든 샘플 조회",
            description = "모든 샘플 코드를 조회할 수 있습니다."
    )
    @GetMapping
    public List<Sample> findAllSample() {
        List<Sample> samples = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
             samples.add(new Sample(i, "name " + i));
        }
        return samples;
    }

    @GetMapping("{id}")
    public Sample findSample(
            @Parameter(description = "샘플의 id 값")
            @PathVariable int id
    ) {
        return new Sample(id, "name " + id);
    }

    @PostMapping
    public String createSample(@RequestBody Sample sample) {
        return "createSample";
    }

    @ApiResponses(
            value = {
                    @ApiResponse(
                            description = "샘플 정보를 수정합니다.",
                            responseCode = "200",
                            content = @Content(schema = @Schema(implementation = Sample.class))),
                    @ApiResponse(
                            description = "실패",
                            responseCode = "400",
                            content = @Content
                    )
            }
    )
    @PutMapping
    public String updateSample(@RequestBody Sample sample) {
        return "updateSample";
    }

    @DeleteMapping
    public String deleteSample() {
        return "deleteSample";
    }

}
