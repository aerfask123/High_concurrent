package com.java.bytecode.type;

import lombok.Data;

@Data
public class ClassFile {

    //魔数
    private U4 magic;

    //副版本号
    private U2 minorVersion;

    //主版本号
    private U2 majorVersion;

    //常量池计数器
    private U2 constantPoolCount;

    //常量池
    private CpInfo[] constantPool;

    //访问标志
    private U2 accessFlags;

    //类索引
    private U2 thisClass;

    //父类索引
    private U2 superClass;

    //接口总数
    private U2 interfacesCount;

    //接口数组
    private U2[] interfaces;

    //字段总数
    private U2 fieldsCount;

    //字段表
    private FieldInfo[] fields;

    //方法总数
    private U2 methodsCount;

    //方法表
    private MethodInfo[] methods;

    //属性总数
    private U2 attributesCount;

    //属性表
    private AttributeInfo[] attributes;
}
