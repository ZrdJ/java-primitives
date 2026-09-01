package com.github.zrdj.java.primitives.numbers;

import org.junit.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class BigDecimal2Test {

    // [impl->req~numeric-calculation.comparison-uses-compareto~1]
    @Test
    public void isEqualToIgnoresScale() {
        final BigDecimal2 a = new BigDecimal2(new BigDecimal("1.0"));
        final BigDecimal2 b = new BigDecimal2(new BigDecimal("1.00"));

        assertThat(a.isEqualTo(b)).isTrue();
        assertThat(new BigDecimal("1.0").equals(new BigDecimal("1.00"))).isFalse();
    }

    // [impl->req~numeric-calculation.comparison-uses-compareto~1]
    @Test
    public void isGreaterThan() {
        final BigDecimal2 a = new BigDecimal2(new BigDecimal("2"));
        final BigDecimal2 b = new BigDecimal2(new BigDecimal("1"));

        assertThat(a.isGreaterThan(b)).isTrue();
    }

    // [impl->req~numeric-calculation.division-semantics~1]
    @Test
    public void defaultDivisionRoundsHalfUpAtEightDecimalPlaces() {
        final BigDecimal2 a = new BigDecimal2(new BigDecimal(1));
        final BigDecimal2 b = new BigDecimal2(new BigDecimal(3));

        final BigDecimal result = a.divide(b).asBigDecimal();

        assertThat(result).isEqualByComparingTo(new BigDecimal("0.33333333"));
        assertThat(result.scale()).isEqualTo(8);
    }
}
