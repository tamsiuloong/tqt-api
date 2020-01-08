package com.coachtam.tqt.viewmodel.admin;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 〈〉
 *
 * @author coach tam
 * @email 327395128@qq.com
 * @create 2018/12/24
 * @since 1.0.0
 * 〈坚持灵活 灵活坚持〉
 */
//@Data
public class ZtreeVM {
    //{ id:2, pId:0, name:"随意勾选 2", checked:true, open:true},
    private String id;

    private String pId;
    private String name;
    //是否选中
    private Boolean checked;
    //是否展开
    private Boolean open;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    @JsonProperty("pId")
    public String getPId() {
        return pId;
    }

    public void setPId(String pId) {
        this.pId = pId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public Boolean getOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }
}
