/**
 * Labels - the labels can only be exactly BEFORE a loop. <br>
 * The Label doesn't represent a jump mark, instead it gives a certain loop a name, so it can be referenced in
 * the 'break' and the 'continue' statements!
 */
public class PlaygroundLabels {
    public static void main(String[] args) {
        OuterLoop:
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                if (i == 1 && j == 1) {
                    System.out.println("Now performing continue on the inner loop (without using a label), jumping over j == 1");
                    continue;
                }

                if (i == 2 && j == 2) {
                    System.out.println("Now performing continue on the loop labeled 'OuterLoop', thus jumping directly to i == 3, j == 0");
                    continue OuterLoop;
                }

                if (i == 3 && j == 3) {
                    System.out.println("Now performing a break on the inner loop (without using a label), jumping over the j >= 3.");
                    break;
                }

                if (i == 4 && j == 4) {
                    System.out.println("Now performing a break on the loop labeled 'OuterLoop', thus ignoring any further execution of these loops.");
                    break OuterLoop;
                }

                System.out.println("i: " + i + ", j: " + j);
            }
        }

        System.out.println("The end");


        LabelJumpOutOfIf:
        if (true) {
            for (int i = 0; i < 5; i++) {
                if (i == 2) {
                    System.out.println("jump out of the if!");
                    break LabelJumpOutOfIf;
                }
            }

            System.out.println("Other stuff -> this should never be reached!!!");
        }
        System.out.println("The 2nd end");
    }
}
