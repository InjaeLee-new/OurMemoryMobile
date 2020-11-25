package com.example.ourmemory.model;

public class MemoryCommentDTO {
    private int memory_seq;
    private String memory_comment_name;
    private String memory_comment_content;
    private String reg_date;

    public String getReg_date() {
        return reg_date;
    }

    public void setReg_date(String reg_date) {
        this.reg_date = reg_date;
    }

    public int getMemory_seq() {
        return memory_seq;
    }
    public void setMemory_seq(int memory_seq) {
        this.memory_seq = memory_seq;
    }
    public String getMemory_comment_name() {
        return memory_comment_name;
    }
    public void setMemory_comment_name(String memory_comment_name) {
        this.memory_comment_name = memory_comment_name;
    }
    public String getMemory_comment_content() {
        return memory_comment_content;
    }
    public void setMemory_comment_content(String memory_comment_content) {
        this.memory_comment_content = memory_comment_content;
    }


}
