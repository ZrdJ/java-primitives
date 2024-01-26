package com.github.zrdj.java.numbers;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BigDecimal2 extends Number implements Calculatable<BigDecimal2> {
    private static final long serialVersionUID = 6347161055205786009L;

    private final BigDecimal _value;

    public BigDecimal2(final BigDecimal value) {
        _value = value;
    }

    public BigDecimal asBigDecimal() {
        return _value;
    }

    private BigDecimal2 from(BigDecimal value) {
        return new BigDecimal2(value);
    }

    @Override
    public int intValue() {
        return _value.intValue();
    }

    @Override
    public long longValue() {
        return _value.longValue();
    }

    @Override
    public float floatValue() {
        return _value.floatValue();
    }

    @Override
    public double doubleValue() {
        return _value.doubleValue();
    }

    @Override
    public BigDecimal2 self() {
        return this;
    }

    public BigDecimal2 minus(final BigDecimal2 other) {
        return from(asBigDecimal().subtract(other.asBigDecimal()));
    }

    public BigDecimal2 plus(final BigDecimal2 other) {
        return from(asBigDecimal().add(other.asBigDecimal()));
    }

    @Override
    public BigDecimal2 divide(BigDecimal2 other) {
        return divide(other, 8, RoundingMode.HALF_UP);
    }

    @Override
    public int signum() {
        return _value.signum();
    }

    public BigDecimal2 divide(final BigDecimal2 other, int scale, RoundingMode mode) {
        return from(asBigDecimal().divide(other.asBigDecimal(), scale, mode));
    }

    public BigDecimal2 divideHalfUp(final BigDecimal2 other, int scale) {
        return divide(other, scale, RoundingMode.HALF_UP);
    }

    public BigDecimal2 divideHalfDown(final BigDecimal2 other, int scale) {
        return divide(other, scale, RoundingMode.HALF_DOWN);
    }


    @Override
    public int compareTo(BigDecimal2 o) {
        return _value.compareTo(o.asBigDecimal());
    }
}
