package com.kodlamaio.inventoryservice.business.dto.requests.update;

import com.kodlamaio.commonpackage.constants.Regex;
import com.kodlamaio.commonpackage.utils.annotations.NotFutureYear;
import com.kodlamaio.inventoryservice.entities.enums.State;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCarRequest {
    @NotNull
    @NotBlank
    private UUID modelId;

    @Min(value = 2000)
    @NotFutureYear
    private int modelYear;

    @NotNull
    @NotBlank
    @Pattern(regexp = Regex.Plate)
    private String plate;

    private State state;

    @Min(value = 1)
    private double dailyPrice;
}
