package com.nihaov.knowledge.common.enums;

/**
 * Created by nihao on 18/3/26.
 */
public enum ResourceTypeEnum {
    视频(1),
    文档(2);
    private Integer value;

    ResourceTypeEnum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public static ResourceTypeEnum getByValue(Integer value){
        for(ResourceTypeEnum resourceTypeEnum : ResourceTypeEnum.values()){
            if(resourceTypeEnum.getValue().equals(value)){
                return resourceTypeEnum;
            }
        }
        throw new RuntimeException("not found");
    }
}
