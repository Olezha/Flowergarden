package com.flowergarden.model.property;

import lombok.Data;

@Data
public class FreshnessInteger implements Freshness<Integer> {

    private Integer freshness;

    @Override
    public int compareTo(Freshness o) {
        if (!(o instanceof FreshnessInteger))
            throw new UnsupportedOperationException();

        FreshnessInteger fio = (FreshnessInteger) o;
        if (freshness > fio.getFreshness()) return 1;
        if (freshness < fio.getFreshness()) return -1;
        return 0;
    }
}
