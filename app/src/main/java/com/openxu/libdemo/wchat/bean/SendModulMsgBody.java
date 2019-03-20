package com.openxu.libdemo.wchat.bean;


public class SendModulMsgBody {

    private String touser;
    private String template_id;
    private String page;
    private String form_id;
    private String emphasis_keyword;
    private Data data;
    public SendModulMsgBody(String touser, String template_id, String page, String form_id, String emphasis_keyword, Data data) {
        this.touser = touser;
        this.template_id = template_id;
        this.page = page;
        this.form_id = form_id;
        this.emphasis_keyword = emphasis_keyword;
        this.data = data;
    }

    public static class Data{
        public Keyword keyword1;
        public Keyword keyword2;

        public Data(Keyword keyword1, Keyword keyword2) {
            this.keyword1 = keyword1;
            this.keyword2 = keyword2;
        }

        public Keyword getKeyword1() {
            return keyword1;
        }
        public void setKeyword1(Keyword keyword1) {
            this.keyword1 = keyword1;
        }
        public Keyword getKeyword2() {
            return keyword2;
        }
        public void setKeyword2(Keyword keyword2) {
            this.keyword2 = keyword2;
        }
    }
    public static class Keyword{
        public String value;

        public Keyword(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
        public void setValue(String value) {
            this.value = value;
        }
    }
}
