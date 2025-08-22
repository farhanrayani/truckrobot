package com.caterpillar.truckrobot.dto;

import com.caterpillar.truckrobot.model.Turn;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlaceDto {

    @NotNull(message = "X required")
    @Min(value = 0, message = "X should between 0 and 4")
    @Max(value = 4, message = "X should be between 0 and 4")
    private int x;

    @NotNull(message = "Y is required")
    @Min(value = 0, message = "Y should be between 0 and 4")
    @Max(value = 4, message = "Y should be between 0 and 4")
    private int y;

    @NotNull(message = "Facing is required")
    private Turn facing;

}