package com.example.ourmemory.model;

import java.io.Serializable;

public class RecommandDTO implements Serializable {

    private int recommand_seq;
    private String recommand_id;

    public int getRecommand_seq() {
        return recommand_seq;
    }
    public void setRecommand_seq(int recommand_seq) {
        this.recommand_seq = recommand_seq;
    }
    public String getRecommand_id() {
        return recommand_id;
    }
    public void setRecommand_id(String recommand_id) {
        this.recommand_id = recommand_id;
    }

}
