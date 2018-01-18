public class MineSweep {

    private static int[] data = { 0, 0, 0 };

    private MineSweep() {

    }

    public static void main(String[] args) throws InterruptedException {
        Setting s = new Setting();
        while (data[0] == 0) {
            Thread.sleep(200);
            data = s.data;
        }
        s.dispose();
        MineSweepView view = new MineSweepView(data[0], data[1], data[2]);
        MineSweepModelAndController mna = new MineSweepModelAndController(view,
                data[0], data[1], data[2]);

        view.registerObserver(mna);
    }

    public void setData(int[] d) {
        data[0] = d[0];
        data[1] = d[1];
        data[2] = d[2];
    }

}
