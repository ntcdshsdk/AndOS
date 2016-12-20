package com.osshare.core.view.chart;

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

        public PieBuilder colors(int[] colors) {
            this.colors = colors;
            return this;
        }

        public ChartOption build() {
            return new ChartOption(this);
        }
    }

    public static class BarBuilder {
        private int[] colors;
        public BarBuilder colors(int[] colors) {
            this.colors = colors;
            return this;
        }

        public ChartOption build() {
            return new ChartOption(this);
        }
    }

    public static class TabBuilder {
        private int[] colors;
        public TabBuilder colors(int[] colors) {
            this.colors = colors;
            return this;
        }

        public ChartOption build() {
            return new ChartOption(this);
        }
    }
}
