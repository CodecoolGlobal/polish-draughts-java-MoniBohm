public class CoordinateGenerator {
    int row;
    int col;
    boolean isCrowned;
    int n;

    public CoordinateGenerator(int row, int col, boolean isCrowned, int n) {
        this.row = row;
        this.col = col;
        this.n = n;
    }

    public int[] doNewCoord() {
        if (row == 0 && col == n - 1) {
            return topRightPosition();
        }
        ;
        if (row == n - 1 && col == n - 1) {
            return bottomRightPosition();
        }
        ;
        if (row == 0 && col == 0) {
            return topLeftPosition();
        }
        ;
        if (row == n - 1 && col == 0) {
            return bottomLeftPosition();
        }
        ;
        if (col == 0) {
            return leftColPosition();
        }
        ;
        if (col == n - 1) {
            return rightColPosition();
        }
        ;
        if (row == 0) {
            return bottomRowPoisiton();
        }
        ;
        if (row == n - 1) {
            return topRowPoisiton();
        }
        ;
        return notSpecialPosition();
    }

    private int[] topRightPosition() {
        if (isCrowned) {
            return new int[]{row - 1, col + 1};
        }
        return new int[]{};
    }

    private int[] bottomRightPosition() {
        return new int[]{row - 1, col - 1};
    }

    private int[] topLeftPosition() {
        if (isCrowned) {
            return new int[]{row + 1, col + 1};
        }
        return new int[]{};
    }

    private int[] bottomLeftPosition() {
        return new int[]{row + 1, col - 1};
    }

    private int[] leftColPosition() {
        if (isCrowned) {
            return new int[]{row - 1, col + 1};
        }
        return new int[]{row + 1, col - 1};
    }

    private int[] rightColPosition() {
        if (isCrowned) {
            return new int[]{row - 1, col + 1};
        }
        return new int[]{row - 1, col - 1};
    }

    private int[] bottomRowPoisiton() {
        return new int[]{row + 1, col + 1, row - 1, col - 1};
    }

    private int[] topRowPoisiton() {
        if (isCrowned) {
            return new int[]{row + 1, col + 1, row - 1, col - 1};
        }
        return new int[]{};
    }

    private int[] notSpecialPosition() {
        if (isCrowned) {
            return new int[]{row - 1, col - 1, row + 1, col - 1, row + 1, col + 1, row - 1, col - 1};
        }
        return new int[]{row + 1, col + 1, row + 1, col - 1};
    }
}
