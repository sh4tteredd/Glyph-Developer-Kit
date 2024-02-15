package com.nothing.ketchum;
import java.util.ArrayList;
import java.util.Arrays;

public class GlyphFrame {
    private ArrayList<Integer> mChannel = new ArrayList<>();

    private int mPeriod = 0;

    private int mCycles = 1;

    private int mInterval = 0;

    private static int DEFAULT_LIGHT = 4000;

    private GlyphFrame(Builder builder) {
        this.mPeriod = builder.period;
        this.mCycles = builder.cycles;
        this.mInterval = builder.interval;
        this.mChannel = builder.channel;
    }

    public int getPeriod() {
        return this.mPeriod;
    }

    public int getCycles() {
        return this.mCycles;
    }

    public int getInterval() {
        return this.mInterval;
    }

    public int[] getChannel() {
        int[] result = new int[this.mChannel.size()];
        for (int i = 0; i < this.mChannel.size(); i++)
            result[i] = (this.mChannel.get(i) != null) ? ((Integer)this.mChannel.get(i)).intValue() : 0;
        return result;
    }

    public static class Builder {
        private int period = 0;

        private int cycles = 1;

        private int interval = 0;

        private ArrayList<Integer> channel;

        private String mDevice;

        public Builder() {
            this.mDevice = Glyph.DEVICE_22111;
            this.channel = new ArrayList<>(Arrays.asList(new Integer[Common.getTargetDeviceGlyphChannelSize(this.mDevice)]));
        }

        public Builder(String targetDevice) {
            if (targetDevice == null)
                targetDevice = Glyph.DEVICE_22111;
            this.mDevice = targetDevice;
            this.channel = new ArrayList<>(Arrays.asList(new Integer[Common.getTargetDeviceGlyphChannelSize(targetDevice)]));
        }

        public Builder buildPeriod(int period) {
            this.period = period;
            return this;
        }

        public Builder buildCycles(int cycles) {
            this.cycles = cycles;
            return this;
        }

        public Builder buildInterval(int interval) {
            this.interval = interval;
            return this;
        }

        public Builder buildChannel(int channel) {
            this.channel.set(channel, Integer.valueOf(GlyphFrame.DEFAULT_LIGHT));
            return this;
        }

        public Builder buildChannel(int channel, int light) {
            this.channel.set(channel, Integer.valueOf(light));
            return this;
        }

        public Builder buildChannelA() {
            if (Common.isTargetDevice20111(this.mDevice))
                this.channel.set(Glyph.Code_20111.A1, Integer.valueOf(GlyphFrame.DEFAULT_LIGHT));
            if (Common.isTargetDevice22111(this.mDevice)) {
                this.channel.set(Glyph.Code_22111.A1, Integer.valueOf(GlyphFrame.DEFAULT_LIGHT));
                this.channel.set(Glyph.Code_22111.A2, Integer.valueOf(GlyphFrame.DEFAULT_LIGHT));
            }
            return this;
        }

        public Builder buildChannelB() {
            if (Common.isTargetDevice20111(this.mDevice))
                this.channel.set(Glyph.Code_20111.B1, Integer.valueOf(GlyphFrame.DEFAULT_LIGHT));
            if (Common.isTargetDevice22111(this.mDevice))
                this.channel.set(Glyph.Code_22111.B1, Integer.valueOf(GlyphFrame.DEFAULT_LIGHT));
            return this;
        }

        public Builder buildChannelC() {
            if (Common.isTargetDevice20111(this.mDevice)) {
                this.channel.set(Glyph.Code_20111.C1, Integer.valueOf(GlyphFrame.DEFAULT_LIGHT));
                this.channel.set(Glyph.Code_20111.C2, Integer.valueOf(GlyphFrame.DEFAULT_LIGHT));
                this.channel.set(Glyph.Code_20111.C3, Integer.valueOf(GlyphFrame.DEFAULT_LIGHT));
                this.channel.set(Glyph.Code_20111.C4, Integer.valueOf(GlyphFrame.DEFAULT_LIGHT));
            }
            if (Common.isTargetDevice22111(this.mDevice)) {
                this.channel.set(Glyph.Code_22111.C1_1, Integer.valueOf(GlyphFrame.DEFAULT_LIGHT));
                this.channel.set(Glyph.Code_22111.C1_2, Integer.valueOf(GlyphFrame.DEFAULT_LIGHT));
                this.channel.set(Glyph.Code_22111.C1_3, Integer.valueOf(GlyphFrame.DEFAULT_LIGHT));
                this.channel.set(Glyph.Code_22111.C1_4, Integer.valueOf(GlyphFrame.DEFAULT_LIGHT));
                this.channel.set(Glyph.Code_22111.C1_5, Integer.valueOf(GlyphFrame.DEFAULT_LIGHT));
                this.channel.set(Glyph.Code_22111.C1_6, Integer.valueOf(GlyphFrame.DEFAULT_LIGHT));
                this.channel.set(Glyph.Code_22111.C1_7, Integer.valueOf(GlyphFrame.DEFAULT_LIGHT));
                this.channel.set(Glyph.Code_22111.C1_8, Integer.valueOf(GlyphFrame.DEFAULT_LIGHT));
                this.channel.set(Glyph.Code_22111.C1_9, Integer.valueOf(GlyphFrame.DEFAULT_LIGHT));
                this.channel.set(Glyph.Code_22111.C1_10, Integer.valueOf(GlyphFrame.DEFAULT_LIGHT));
                this.channel.set(Glyph.Code_22111.C1_11, Integer.valueOf(GlyphFrame.DEFAULT_LIGHT));
                this.channel.set(Glyph.Code_22111.C1_12, Integer.valueOf(GlyphFrame.DEFAULT_LIGHT));
                this.channel.set(Glyph.Code_22111.C1_13, Integer.valueOf(GlyphFrame.DEFAULT_LIGHT));
                this.channel.set(Glyph.Code_22111.C1_14, Integer.valueOf(GlyphFrame.DEFAULT_LIGHT));
                this.channel.set(Glyph.Code_22111.C1_15, Integer.valueOf(GlyphFrame.DEFAULT_LIGHT));
                this.channel.set(Glyph.Code_22111.C1_16, Integer.valueOf(GlyphFrame.DEFAULT_LIGHT));
                this.channel.set(Glyph.Code_22111.C2, Integer.valueOf(GlyphFrame.DEFAULT_LIGHT));
                this.channel.set(Glyph.Code_22111.C3, Integer.valueOf(GlyphFrame.DEFAULT_LIGHT));
                this.channel.set(Glyph.Code_22111.C4, Integer.valueOf(GlyphFrame.DEFAULT_LIGHT));
                this.channel.set(Glyph.Code_22111.C5, Integer.valueOf(GlyphFrame.DEFAULT_LIGHT));
                this.channel.set(Glyph.Code_22111.C6, Integer.valueOf(GlyphFrame.DEFAULT_LIGHT));
            }
            return this;
        }

        public Builder buildChannelD() {
            if (Common.isTargetDevice20111(this.mDevice)) {
                this.channel.set(Glyph.Code_20111.D1_1, Integer.valueOf(GlyphFrame.DEFAULT_LIGHT));
                this.channel.set(Glyph.Code_20111.D1_2, Integer.valueOf(GlyphFrame.DEFAULT_LIGHT));
                this.channel.set(Glyph.Code_20111.D1_3, Integer.valueOf(GlyphFrame.DEFAULT_LIGHT));
                this.channel.set(Glyph.Code_20111.D1_4, Integer.valueOf(GlyphFrame.DEFAULT_LIGHT));
                this.channel.set(Glyph.Code_20111.D1_5, Integer.valueOf(GlyphFrame.DEFAULT_LIGHT));
                this.channel.set(Glyph.Code_20111.D1_6, Integer.valueOf(GlyphFrame.DEFAULT_LIGHT));
                this.channel.set(Glyph.Code_20111.D1_7, Integer.valueOf(GlyphFrame.DEFAULT_LIGHT));
                this.channel.set(Glyph.Code_20111.D1_8, Integer.valueOf(GlyphFrame.DEFAULT_LIGHT));
            }
            if (Common.isTargetDevice22111(this.mDevice)) {
                this.channel.set(Glyph.Code_22111.D1_1, Integer.valueOf(GlyphFrame.DEFAULT_LIGHT));
                this.channel.set(Glyph.Code_22111.D1_2, Integer.valueOf(GlyphFrame.DEFAULT_LIGHT));
                this.channel.set(Glyph.Code_22111.D1_3, Integer.valueOf(GlyphFrame.DEFAULT_LIGHT));
                this.channel.set(Glyph.Code_22111.D1_4, Integer.valueOf(GlyphFrame.DEFAULT_LIGHT));
                this.channel.set(Glyph.Code_22111.D1_5, Integer.valueOf(GlyphFrame.DEFAULT_LIGHT));
                this.channel.set(Glyph.Code_22111.D1_6, Integer.valueOf(GlyphFrame.DEFAULT_LIGHT));
                this.channel.set(Glyph.Code_22111.D1_7, Integer.valueOf(GlyphFrame.DEFAULT_LIGHT));
                this.channel.set(Glyph.Code_22111.D1_8, Integer.valueOf(GlyphFrame.DEFAULT_LIGHT));
            }
            return this;
        }

        public Builder buildChannelE() {
            if (Common.isTargetDevice20111(this.mDevice))
                this.channel.set(Glyph.Code_20111.E1, Integer.valueOf(GlyphFrame.DEFAULT_LIGHT));
            if (Common.isTargetDevice22111(this.mDevice))
                this.channel.set(Glyph.Code_22111.E1, Integer.valueOf(GlyphFrame.DEFAULT_LIGHT));
            return this;
        }

        public GlyphFrame build() {
            return new GlyphFrame(this);
        }
    }
}