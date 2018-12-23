package NonogramStructure;

import java.util.Objects;

public class Number {
    private int n;
    private boolean crossedOut;

    public Number(int n) {
        this.n = n;
        crossedOut = false;
    }


    public void crossOut() {
        crossedOut = true;
    }

    public void unCross() {
        crossedOut = false;
    }

    public int getN() {
        return n;
    }

    public String getNAsString() {
        return String.valueOf(n);
    }

    public boolean isCrossedOut() {
        return crossedOut;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Number number = (Number) o;
        return n == number.n &&
                crossedOut == number.crossedOut;
    }

    @Override
    public int hashCode() {
        return Objects.hash(n, crossedOut);
    }


    // todo: write a smart toString-method!
    @Override
    public String toString() {
        return "Number{" +
                "n=" + n +
                ", crossedOut=" + crossedOut +
                '}';
    }
}
