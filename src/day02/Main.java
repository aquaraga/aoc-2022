package day02;

import util.FileUtil;

import java.util.List;




enum RPS {
    ROCK(1), PAPER(2), SCISSOR(3);

    private int score;

    RPS(int score) {
        this.score = score;
    }

    int compete(RPS other) {
        if (other.equals(this)) {
            return 0;
        }
        if (this.equals(ROCK)) {
            return other.equals(SCISSOR) ? 1: -1;
        }
        if (this.equals(PAPER)) {
            return other.equals(ROCK) ? 1: -1;
        }
        return other.equals(PAPER) ? 1: -1;
    }

    public int getScore() {
        return score;
    }

    static RPS fromABC(String x) {
        return x.equals("A") ? ROCK: x.equals("B") ? PAPER: SCISSOR;
    }

    static RPS fromXYZ(String x) {
        return x.equals("X") ? ROCK: x.equals("Y") ? PAPER: SCISSOR;
    }

    static RPS fromNewXYZ(String x, RPS opponent) {
        return x.equals("X") ? loseTo(opponent): x.equals("Y") ? opponent: winOver(opponent);
    }

    static RPS winOver(RPS x) {
        return x.equals(ROCK) ? PAPER: x.equals(PAPER) ? SCISSOR: ROCK;
    }

    static RPS loseTo(RPS x) {
        return x.equals(ROCK) ? SCISSOR: x.equals(PAPER) ? ROCK: PAPER;
    }
}


public class Main {
    public static void main(String[] args) throws Exception {
        List<String> roundStrings = FileUtil.readStrings("src/day02/test.txt");
        int totalScore = 0;
        for (String round :
                roundStrings) {
            RPS opponent = RPS.fromABC(round.split(" ")[0]);
            RPS you = RPS.fromXYZ(round.split(" ")[1]);
            totalScore += you.getScore();
            totalScore += ((you.compete(opponent) + 1) * 3);
        }
        System.out.println(totalScore);

        int newTotalScore = 0;
        for (String round :
                roundStrings) {
            RPS opponent = RPS.fromABC(round.split(" ")[0]);
            RPS you = RPS.fromNewXYZ(round.split(" ")[1], opponent);
            newTotalScore += you.getScore();
            newTotalScore += ((you.compete(opponent) + 1) * 3);
        }
        System.out.println(newTotalScore);
    }
}