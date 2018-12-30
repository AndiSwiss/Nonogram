class Playground2 {
    public static void main(String[] args) {
        int[] numbers = {1, 43, 256, 9_999, 10_000, 333_333, 1_000_001};

        // amount of digits, with the log10 and int-casting, and + 1:
        for (int n : numbers) {
            System.out.printf("(int) Math.log10(%s) + 1: %s\n", n, (int) Math.log10(n) + 1);
        }

        // or less mathematical, by looking at the length of the string:
        for (int n : numbers) {
            System.out.printf("String.valueOf(n).length(): %s\n", String.valueOf(n).length());
        }
    }
}
