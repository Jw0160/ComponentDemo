package com.common.common_base;

/**
 * @author : wangb
 * @email :
 * @date : 2017/12/25
 * @desc :
 */

public class DemoResult{
    /**
     * data : {"author":{"des":"欢迎下载使用OkHttpUtils网络框架，如果遇到问题，欢迎反馈！如果觉得好用，不妨star一下，以便让更多的人了解使用！","email":"liaojeason@126.com","address":"北京","name":"jeasonlzy","github":"https://github.com/jeasonlzy0216","qq":"QQ交流群：489873144","fullname":"廖子尧"},"des":"请求服务器返回Json对象","method":"GET","url":"http://server.jeasonlzy.com/OkHttpUtils/jsonObject","ip":"2001:470:c:e72:0:0:0:2:48006"}
     * code : 0
     * msg : 请求成功
     */

    private DataBean data;
    private int code;
    private String msg;

    public DataBean getData(){
        return data;
    }

    public void setData(DataBean data){
        this.data = data;
    }

    public int getCode(){
        return code;
    }

    public void setCode(int code){
        this.code = code;
    }

    public String getMsg(){
        return msg;
    }

    public void setMsg(String msg){
        this.msg = msg;
    }

    public static class DataBean{
        /**
         * author : {"des":"欢迎下载使用OkHttpUtils网络框架，如果遇到问题，欢迎反馈！如果觉得好用，不妨star一下，以便让更多的人了解使用！","email":"liaojeason@126.com","address":"北京","name":"jeasonlzy","github":"https://github.com/jeasonlzy0216","qq":"QQ交流群：489873144","fullname":"廖子尧"}
         * des : 请求服务器返回Json对象
         * method : GET
         * url : http://server.jeasonlzy.com/OkHttpUtils/jsonObject
         * ip : 2001:470:c:e72:0:0:0:2:48006
         */

        private AuthorBean author;
        private String des;
        private String method;
        private String url;
        private String ip;

        public AuthorBean getAuthor(){
            return author;
        }

        public void setAuthor(AuthorBean author){
            this.author = author;
        }

        public String getDes(){
            return des;
        }

        public void setDes(String des){
            this.des = des;
        }

        public String getMethod(){
            return method;
        }

        public void setMethod(String method){
            this.method = method;
        }

        public String getUrl(){
            return url;
        }

        public void setUrl(String url){
            this.url = url;
        }

        public String getIp(){
            return ip;
        }

        public void setIp(String ip){
            this.ip = ip;
        }

        public static class AuthorBean{
            /**
             * des : 欢迎下载使用OkHttpUtils网络框架，如果遇到问题，欢迎反馈！如果觉得好用，不妨star一下，以便让更多的人了解使用！
             * email : liaojeason@126.com
             * address : 北京
             * name : jeasonlzy
             * github : https://github.com/jeasonlzy0216
             * qq : QQ交流群：489873144
             * fullname : 廖子尧
             */

            private String des;
            private String email;
            private String address;
            private String name;
            private String github;
            private String qq;
            private String fullname;

            public String getDes(){
                return des;
            }

            public void setDes(String des){
                this.des = des;
            }

            public String getEmail(){
                return email;
            }

            public void setEmail(String email){
                this.email = email;
            }

            public String getAddress(){
                return address;
            }

            public void setAddress(String address){
                this.address = address;
            }

            public String getName(){
                return name;
            }

            public void setName(String name){
                this.name = name;
            }

            public String getGithub(){
                return github;
            }

            public void setGithub(String github){
                this.github = github;
            }

            public String getQq(){
                return qq;
            }

            public void setQq(String qq){
                this.qq = qq;
            }

            public String getFullname(){
                return fullname;
            }

            public void setFullname(String fullname){
                this.fullname = fullname;
            }
        }
    }
}
