package com.greennet.filemanagement.model;

public class Department {
    private Integer id;

    private String departmentName;

    private String departmentDes;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName == null ? null : departmentName.trim();
    }

    public String getDepartmentDes() {
        return departmentDes;
    }

    public void setDepartmentDes(String departmentDes) {
        this.departmentDes = departmentDes == null ? null : departmentDes.trim();
    }
}