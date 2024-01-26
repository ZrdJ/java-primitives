package com.github.zrdj.java.primitives.numbers;


public interface Calculatable<T extends Calculatable<T>> extends Comparable<T> {
    T self();

    T minus(final T other);

    T plus(final T other);

    T divide(final T other);

    default boolean isNegative() {
        return signum() == -1;
    }

    default boolean isPositive() {
        return signum() == 1;
    }

    int signum();

    default boolean isGreaterThan(final T other) {
        return this.compareTo(other.self()) > 0;
    }

    default boolean isGreaterThanOrEqualTo(final T other) {
        return this.compareTo(other.self()) >= 0;
    }

    default boolean isLessThan(final T other) {
        return this.compareTo(other.self()) < 0;
    }

    default boolean isLessThanOrEqualTo(final T other) {
        return this.compareTo(other.self()) <= 0;
    }

    default boolean isEqualTo(final T other) {
        return this.compareTo(other.self()) == 0;
    }

    default boolean isNotEqualTo(final T other) {
        return this.compareTo(other.self()) != 0;
    }
}
