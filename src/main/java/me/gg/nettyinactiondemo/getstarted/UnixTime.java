package me.gg.nettyinactiondemo.getstarted;

import java.util.Date;

/**
 * Created by danny on 2019/1/25.
 */
public class UnixTime {
    private String label;
    private final long value;

    public UnixTime() {
        this(System.currentTimeMillis() / 1000L + 2208988800L,"");
    }

    public UnixTime(long value, String label) {
        this.value = value;
        this.label = label;
    }

    public long value() {
        return value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "标签:" + label + ";时间:" + new Date((value() - 2208988800L) * 1000L).toString();
    }

}
