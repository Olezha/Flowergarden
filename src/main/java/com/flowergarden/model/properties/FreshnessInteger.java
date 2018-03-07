package com.flowergarden.model.properties;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class FreshnessInteger implements Freshness<Integer> {

    @XmlElement
    private Integer freshness;

    public FreshnessInteger() {
    }

    public FreshnessInteger(Integer freshness) {
        this.freshness = freshness;
    }

    @Override
    public Integer getFreshness() {
        return freshness;
    }

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
