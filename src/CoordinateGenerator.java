public class CoordinateGenerator {
    int row;
    int col;
    boolean isCrowned;
    int n;

    public CoordinateGenerator (int row, int col, boolean isCrowned, int n) {
        this.row = row;
        this.col = col;
        this.isCrowned = isCrowned;
        this.n = n;
    }

    public int[] doNewCoord(){
        if(row ==0 && col == n -1){return topRightPosition();};
        if(row ==n-1 && col == n-1){return bottomRightPosition();};
        if(row ==0 && col == 0){return topLeftPosition();};
        if(row ==n-1 && col == 0){return bottomLeftPosition();};
        if(col == 0){return leftColPosition();};
        if(col == n-1){return rightColPosition();};
        if(row == 0){ return bottomRowPoisiton();};
        if(row == n-1){ return topRowPoisiton();};
        return  notSpecialPosition();
    }

    private int[] topRightPosition() {
        -1+1
        return new int[]{0,0};
    }

    private int[] bottomRightPosition() {
        +1+1
        return new int[]{0,0};
    }

    private int[] topLeftPosition() {
        +1+1
        return new int[]{0,0};
    }

    private int[] bottomLeftPosition() {
        +1+1
        return new int[]{0,0};
    }

    private int[] leftColPosition() {
        +1-1
                -1 +1
        return new int[]{0,0};
    }

    private int[] rightColPosition() {
        -1-1
                -1, +1
        return new int[]{0,0};
    }

    private int[] bottomRowPoisiton() {
        -1-1
                -1, +1
        return new int[]{0,0};
    }

    private int[] topRowPoisiton() {
//        -1-1
//         -1, +1(isCrowned)
        return new int[]{0,0};
    }

    private int[] notSpecialPosition() {
//        -1-1
//        +1 -1
//        -1+1(isCrowned)
//        +1+1(isCrowned)
        return new int[]{0,0};
    }

}
