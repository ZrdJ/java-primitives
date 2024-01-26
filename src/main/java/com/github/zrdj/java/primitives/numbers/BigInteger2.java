package com.github.zrdj.java.primitives.numbers;

import java.math.BigInteger;

public class BigInteger2 extends Number implements Calculatable<BigInteger2> {
    private static final long serialVersionUID = 5312627255569583250L;
    private final BigInteger _value;

    public BigInteger2(final BigInteger value) {
        _value = value;
    }

    public BigInteger asBigInteger() {
        return _value;
    }

    private BigInteger2 from(BigInteger value) {
        return new BigInteger2(value);
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
    public BigInteger2 self() {
        return this;
    }

    public BigInteger2 minus(final BigInteger2 other) {
        return from(asBigInteger().subtract(other.asBigInteger()));
    }

    public BigInteger2 plus(final BigInteger2 other) {
        return from(asBigInteger().add(other.asBigInteger()));
    }

    public BigInteger2 divide(final BigInteger2 other) {
        return from(asBigInteger().divide(other.asBigInteger()));
    }

    @Override
    public int signum() {
        return _value.signum();
    }

    @Override
    public int compareTo(BigInteger2 o) {
        return _value.compareTo(o.asBigInteger());
    }
}
