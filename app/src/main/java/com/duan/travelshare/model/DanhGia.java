package com.duan.travelshare.model;

import java.io.Serializable;

public class DanhGia implements Serializable {
    private String IdComment,Star,Comment;

    public DanhGia() {
    }

    public DanhGia(String idComment, String star, String comment) {
        IdComment = idComment;
        Star = star;
        Comment = comment;
    }

    public String getIdComment() {
        return IdComment;
    }

    public void setIdComment(String idComment) {
        IdComment = idComment;
    }

    public String getStar() {
        return Star;
    }

    public void setStar(String star) {
        Star = star;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }
}
