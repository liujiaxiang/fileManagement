package com.greennet.filemanagement.model;

public class Folder {
    private Integer id;

    private Integer dId;

    private Integer uId;

    private String folderName;

    private String folderDes;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getdId() {
        return dId;
    }

    public void setdId(Integer dId) {
        this.dId = dId;
    }

    public Integer getuId() {
        return uId;
    }

    public void setuId(Integer uId) {
        this.uId = uId;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName == null ? null : folderName.trim();
    }

    public String getFolderDes() {
        return folderDes;
    }

    public void setFolderDes(String folderDes) {
        this.folderDes = folderDes == null ? null : folderDes.trim();
    }
    
    public String toString() {
        return "{id:\"" + id + "\", folderName:\"" + folderName+"\", folderDes:\"" + folderDes+"}";
    }
}