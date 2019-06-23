package com.bigsea.entity;

/**
 * 权限实体类
 * @author sea
 * @date 2019-06-23
 */
public class Privilege {
    /**
     * id
     */
    private String id;

    /**
     * 权限code
     */
    private String code;

    /**
     * 权限名称
     */
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Privilege{" +
                "id='" + id + '\'' +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
