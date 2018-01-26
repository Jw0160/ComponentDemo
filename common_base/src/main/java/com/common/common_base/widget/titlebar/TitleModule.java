package com.common.common_base.widget.titlebar;

public class TitleModule {

    private ViewBean leftImgVB1;
    private ViewBean leftImgVB2;
    private ViewBean leftTxtVB;
    private ViewBean titleVB;
    private ViewBean rightTxtVB;
    private ViewBean rightImgVB1;
    private ViewBean rightImgVB2;

    public TitleModule(ViewBean leftImgVB1, ViewBean leftImgVB2, ViewBean leftTxtVB, ViewBean titleVB, ViewBean rightTxtVB, ViewBean rightImgVB1, ViewBean rightImgVB2) {
        this.leftImgVB1 = leftImgVB1;
        this.leftImgVB2 = leftImgVB2;
        this.leftTxtVB = leftTxtVB;
        this.titleVB = titleVB;
        this.rightTxtVB = rightTxtVB;
        this.rightImgVB1 = rightImgVB1;
        this.rightImgVB2 = rightImgVB2;
    }

    public ViewBean getLeftImgVB1() {
        return leftImgVB1;
    }

    public void setLeftImgVB1(ViewBean leftImgVB1) {
        this.leftImgVB1 = leftImgVB1;
    }

    public ViewBean getLeftImgVB2() {
        return leftImgVB2;
    }

    public void setLeftImgVB2(ViewBean leftImgVB2) {
        this.leftImgVB2 = leftImgVB2;
    }

    public ViewBean getLeftTxtVB() {
        return leftTxtVB;
    }

    public void setLeftTxtVB(ViewBean leftTxtVB) {
        this.leftTxtVB = leftTxtVB;
    }

    public ViewBean getTitleVB() {
        return titleVB;
    }

    public void setTitleVB(ViewBean titleVB) {
        this.titleVB = titleVB;
    }

    public ViewBean getRightTxtVB() {
        return rightTxtVB;
    }

    public void setRightTxtVB(ViewBean rightTxtVB) {
        this.rightTxtVB = rightTxtVB;
    }

    public ViewBean getRightImgVB1() {
        return rightImgVB1;
    }

    public void setRightImgVB1(ViewBean rightImgVB1) {
        this.rightImgVB1 = rightImgVB1;
    }

    public ViewBean getRightImgVB2() {
        return rightImgVB2;
    }

    public void setRightImgVB2(ViewBean rightImgVB2) {
        this.rightImgVB2 = rightImgVB2;
    }
}
