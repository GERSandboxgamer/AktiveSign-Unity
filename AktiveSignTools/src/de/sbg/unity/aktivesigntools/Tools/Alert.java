package de.sbg.unity.aktivesigntools.Tools;

public class Alert extends Tool {

    public String Text;

    public Alert(long l, ToolTyp tt) {
        super(l, tt);
    }

    public void setText(String text) {
        this.Text = text;
    }

    public String getText() {
        return Text;
    }

}
