package vn.nms.hotkey;

public class HotKeyWordModel {
    private String keyword;
    private int color;

    HotKeyWordModel(String keyword, int color) {
        this.keyword = keyword;
        this.color = color;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
