package com.example.test_amocrm.utils;

public enum CodeType {
    CODE_110("110"),
    CODE_111("111"),
    CODE_112("112"),
    CODE_113("113"),
    CODE_101("101"),
    CODE_400("400"),
    CODE_401("401"),
    CODE_403("403"),
    CODE_404("404"),
    CODE_500("500"),
    CODE_502("502"),
    CODE_503("503");

    private String code;

    CodeType(String code){
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static CodeType getCodeType(String code){
         for (CodeType codeType : values()){
             if (codeType.getCode().equals(code)){
                 return codeType;
             }
         }
         throw new IllegalArgumentException();
    }
}
