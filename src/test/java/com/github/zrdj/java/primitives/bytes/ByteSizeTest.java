package com.github.zrdj.java.primitives.bytes;

import org.junit.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class ByteSizeTest {

    // [impl->req~byte-size-conversion.decimal-units~1]
    @Test
    public void kilobytesDividesByOneThousand() {
        final ByteSize size = ByteSize.of(1000);

        assertThat(size.kilobytes()).isEqualByComparingTo(BigDecimal.ONE);
    }

    // [impl->req~byte-size-conversion.decimal-units~1]
    @Test
    public void megabytesIsTwoDivisionsByOneThousand() {
        final ByteSize size = ByteSize.of(1_000_000);

        assertThat(size.megabytes()).isEqualByComparingTo(BigDecimal.ONE);
    }

    // [impl->req~byte-size-conversion.calculatable~1]
    @Test
    public void addingTwoSizes() {
        final ByteSize result = ByteSize.of(500).plus(ByteSize.of(500));

        assertThat(result.bytes()).isEqualByComparingTo(new BigDecimal(1000));
    }

    // [impl->req~byte-size-conversion.calculatable~1]
    @Test
    public void comparingTwoSizes() {
        assertThat(ByteSize.of(2000).isGreaterThan(ByteSize.of(1000))).isTrue();
    }
}
