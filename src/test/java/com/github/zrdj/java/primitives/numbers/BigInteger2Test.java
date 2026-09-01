package com.github.zrdj.java.primitives.numbers;

import org.junit.Test;

import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;

public class BigInteger2Test {

    // [impl->req~numeric-calculation.division-semantics~1]
    @Test
    public void divisionTruncatesTowardZero() {
        final BigInteger2 a = new BigInteger2(BigInteger.valueOf(7));
        final BigInteger2 b = new BigInteger2(BigInteger.valueOf(2));

        assertThat(a.divide(b).asBigInteger()).isEqualTo(BigInteger.valueOf(3));
    }
}
