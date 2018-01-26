package com.common.common_base.widget.titlebar;

public class TitleModuleBuilder {
    public static TitleModuleBuilder builder = new TitleModuleBuilder();
    private ViewBean leftImgVB1 = null;
    private ViewBean leftImgVB2 = null;
    private ViewBean leftTxtVB = null;
    private ViewBean titleVB = null;
    private ViewBean rightTxtVB = null;
    private ViewBean rightImgVB1 = null;
    private ViewBean rightImgVB2 = null;

    public TitleModuleBuilder() {
        leftImgVB1 = null;
        leftImgVB2 = null;
        leftTxtVB = null;
        titleVB = null;
        rightTxtVB = null;
        rightImgVB1 = null;
        rightImgVB2 = null;
    }

    public TitleModuleBuilder setLeftImgVB1(ViewBean leftImgVB1) {
        this.leftImgVB1 = leftImgVB1;
        return this;
    }

    public TitleModuleBuilder setLeftImgVB2(ViewBean leftImgVB2) {
        this.leftImgVB2 = leftImgVB2;
        return this;
    }

    public TitleModuleBuilder setLeftTxtVB(ViewBean leftTxtVB) {
        this.leftTxtVB = leftTxtVB;
        return this;
    }

    public TitleModuleBuilder setTitleVB(ViewBean titleVB) {
        this.titleVB = titleVB;
        return this;
    }

    public TitleModuleBuilder setRightTxtVB(ViewBean rightTxtVB) {
        this.rightTxtVB = rightTxtVB;
        return this;
    }

    public TitleModuleBuilder setRightImgVB1(ViewBean rightImgVB1) {
        this.rightImgVB1 = rightImgVB1;
        return this;
    }

    public TitleModuleBuilder setRightImgVB2(ViewBean rightImgVB2) {
        this.rightImgVB2 = rightImgVB2;
        return this;
    }

    public TitleModule createTitleModule() {
        return new TitleModule(leftImgVB1, leftImgVB2, leftTxtVB, titleVB, rightTxtVB, rightImgVB1, rightImgVB2);
    }
}