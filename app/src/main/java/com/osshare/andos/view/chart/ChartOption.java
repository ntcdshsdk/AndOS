package com.osshare.andos.view.chart;

/**
 * Created by apple on 16/9/19.
 */
public class ChartOption {
    private final int[] colors;

    private ChartOption(PieBuilder builder) {
        this.colors = builder.colors;
    }

    private ChartOption(BarBuilder builder) {
        this.colors = builder.colors;
    }

    private ChartOption(TabBuilder builder) {
        this.colors = builder.colors;
    }

    public static class PieBuilder {
        private int[] colors;

        public PieBuilder setColors(int[] colors) {
            this.colors = colors;
            return this;
        }

        public ChartOption build() {
            return new ChartOption(this);
        }
    }

    public static class BarBuilder {
        private int[] colors;
    }

    public static class TabBuilder {
        private int[] colors;
    }
}
