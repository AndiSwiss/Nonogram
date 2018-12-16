package NonogramStructure;

import java.util.List;
import java.util.Objects;

public class NumberLine {
    private List<Number> numbers;

    /**
     * Constructor
     * @param numbers List<Number>
     */
    public NumberLine(List<Number> numbers) {
        this.numbers = numbers;
    }

    //----------------//
    // Custom Methods //
    //----------------//
   /**
     * Returns a specific number of the list of the numbers of this line.
     * @param i i
     * @return Number
     */
    public Number get(int i) {
        return numbers.get(i);
    }

    public boolean areAllCrossedOut() {
        for (Number n : numbers) {
            if (!n.isCrossedOut()) {
                return false;
            }
        }
        return true;
    }

    public int size() {
        return numbers.size();
    }

    //---------//
    // Getters //
    //---------//
    public List<Number> getNumbers() {
        return numbers;
    }


    // todo: check these equals-methods with some good test-cases!

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NumberLine that = (NumberLine) o;
        return Objects.equals(numbers, that.numbers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numbers);
    }
}
