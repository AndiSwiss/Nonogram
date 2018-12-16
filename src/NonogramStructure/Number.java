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

    public boolean isCrossedOut() {
        return crossedOut;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Number number = (Number) o;
        return n == number.n;
    }

    @Override
    public int hashCode() {
        return Objects.hash(n);
    }

    @Override
    public String toString() {
        return "" + n;
    }
}
