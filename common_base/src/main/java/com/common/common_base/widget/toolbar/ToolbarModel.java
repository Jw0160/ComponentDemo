package com.common.common_base.widget.toolbar;

/**
 * @author : JoyWong0160
 * @email :
 * @date : 2018/2/6
 * @desc :
 */

public class ToolbarModel{

    private ToolbarBean mTvTitleName;
    private ToolbarBean mLogo;
    private ToolbarBean mTitle;
    private ToolbarBean mSubtitle;
    private ToolbarBean mNavigationIcon;
    private ToolbarBean mMenu;

    public ToolbarModel(ToolbarBean tvTitleName, ToolbarBean logo, ToolbarBean title, ToolbarBean subtitle, ToolbarBean navigationIcon, ToolbarBean menu){
        mTvTitleName = tvTitleName;
        mLogo = logo;
        mTitle = title;
        mSubtitle = subtitle;
        mNavigationIcon = navigationIcon;
        mMenu = menu;
    }

    public ToolbarBean getTvTitleName(){
        return mTvTitleName;
    }

    public void setTvTitleName(ToolbarBean tvTitleName){
        mTvTitleName = tvTitleName;
    }

    public ToolbarBean getLogo(){
        return mLogo;
    }

    public void setLogo(ToolbarBean logo){
        mLogo = logo;
    }

    public ToolbarBean getTitle(){
        return mTitle;
    }

    public void setTitle(ToolbarBean title){
        mTitle = title;
    }

    public ToolbarBean getSubtitle(){
        return mSubtitle;
    }

    public void setSubtitle(ToolbarBean subtitle){
        mSubtitle = subtitle;
    }

    public ToolbarBean getNavigationIcon(){
        return mNavigationIcon;
    }

    public void setNavigationIcon(ToolbarBean navigationIcon){
        mNavigationIcon = navigationIcon;
    }

    public ToolbarBean getMenu(){
        return mMenu;
    }

    public void setMenu(ToolbarBean menu){
        mMenu = menu;
    }

    public static class Builder{
        private ToolbarBean mTvTitleName;
        private ToolbarBean mLogo;
        private ToolbarBean mTitle;
        private ToolbarBean mSubtitle;
        private ToolbarBean mNavigationIcon;
        private ToolbarBean mMenu;

        public ToolbarBean getTvTitleName(){
            return mTvTitleName;
        }

        public Builder setTvTitleName(ToolbarBean tvTitleName){
            mTvTitleName = tvTitleName;
            return this;
        }

        public ToolbarBean getLogo(){
            return mLogo;
        }

        public Builder setLogo(ToolbarBean logo){
            mLogo = logo;
            return this;
        }

        public ToolbarBean getTitle(){
            return mTitle;
        }

        public Builder setTitle(ToolbarBean title){
            mTitle = title;
            return this;
        }

        public ToolbarBean getSubtitle(){
            return mSubtitle;
        }

        public Builder setSubtitle(ToolbarBean subtitle){
            mSubtitle = subtitle;
            return this;
        }

        public ToolbarBean getNavigationIcon(){
            return mNavigationIcon;
        }

        public Builder setNavigationIcon(ToolbarBean navigationIcon){
            mNavigationIcon = navigationIcon;
            return this;
        }

        public ToolbarBean getMenu(){
            return mMenu;
        }

        public Builder setMenu(ToolbarBean menu){
            mMenu = menu;
            return this;
        }

        public ToolbarModel create(){
            return new ToolbarModel(mTvTitleName, mLogo, mTitle, mSubtitle, mNavigationIcon, mMenu);
        }
    }
}

