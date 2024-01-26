package com.github.zrdj.java.primitives.bytes;


import com.github.zrdj.java.primitives.numbers.BigDecimal2;
import com.github.zrdj.java.primitives.numbers.Calculatable;

import java.math.BigDecimal;
import java.math.RoundingMode;

public interface ByteSize extends Calculatable<ByteSize> {
    BigDecimal size = new BigDecimal(1000);

    static ByteSize of(final long bytes) {
        return new ByteSize.OfBytes(bytes);
    }

    default BigDecimal megabytes() {
        return divide(kilobytes());
    }

    default BigDecimal gigabytes() {
        return divide(megabytes());
    }

    default BigDecimal kilobytes() {
        return divide(bytes());
    }

    default BigDecimal terrabytes() {
        return divide(gigabytes());
    }

    default BigDecimal divide(final BigDecimal initial) {
        return initial.divide(size, 8, RoundingMode.HALF_UP);
    }

    BigDecimal bytes();

    BigDecimal2 bytes2();

    final class OfBytes implements ByteSize {
        private final BigDecimal _value;
        private final BigDecimal2 _value2;

        public OfBytes(long bytes) {
            this(new BigDecimal(bytes));
        }

        public OfBytes(final BigDecimal bytes) {
            _value = bytes;
            _value2 = new BigDecimal2(_value);
        }

        public OfBytes(final BigDecimal2 bytes) {
            _value = bytes.asBigDecimal();
            _value2 = bytes;
        }

        @Override
        public BigDecimal bytes() {
            return _value;
        }

        @Override
        public BigDecimal2 bytes2() {
            return _value2;
        }

        @Override
        public ByteSize self() {
            return this;
        }

        @Override
        public ByteSize minus(ByteSize other) {
            return new OfBytes(_value2.plus(other.bytes2()));
        }

        @Override
        public ByteSize plus(ByteSize other) {
            return new OfBytes(_value2.minus(other.bytes2()));
        }

        @Override
        public ByteSize divide(ByteSize other) {
            return new OfBytes(_value2.divide(other.bytes2()));
        }

        @Override
        public int signum() {
            return _value.signum();
        }

        @Override
        public boolean isGreaterThan(ByteSize other) {
            return _value2.isGreaterThan(other.bytes2());
        }

        @Override
        public boolean isGreaterThanOrEqualTo(ByteSize other) {
            return _value2.isGreaterThanOrEqualTo(other.bytes2());
        }

        @Override
        public boolean isLessThan(ByteSize other) {
            return _value2.isLessThan(other.bytes2());
        }

        @Override
        public boolean isLessThanOrEqualTo(ByteSize other) {
            return _value2.isLessThanOrEqualTo(other.bytes2());
        }

        @Override
        public boolean isEqualTo(ByteSize other) {
            return _value2.isEqualTo(other.bytes2());
        }

        @Override
        public boolean isNotEqualTo(ByteSize other) {
            return _value2.isNotEqualTo(other.bytes2());
        }

        @Override
        public int compareTo(ByteSize o) {
            return _value.compareTo(o.bytes());
        }
    }
}
