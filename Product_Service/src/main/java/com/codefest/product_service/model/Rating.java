package com.codefest.product_service.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Embeddable
@Getter
@Setter
@ToString
public class Rating {

    // Map the "rate" field in the API response to "rating_rate"
    @JsonProperty("rate")
    private double rating_rate;

    // Map the "count" field in the API response to "rating_count"
    @JsonProperty("count")
    private int rating_count;

    public Rating() {}

    public Rating(double rating_rate, int rating_count) {
        this.rating_rate = rating_rate;
        this.rating_count = rating_count;
    }
}
