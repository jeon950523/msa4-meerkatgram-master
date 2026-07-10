package com.msa4meerkatgram.global.responses;

import com.msa4meerkatgram.global.responses.constant.CustomResponseCode;
public record GlobalRes<T>(
    String code,
    String message,
    T data
) {
    public static<T> GlobalRes<T> from(CustomResponseCode customResponseCode, T data){
        return new GlobalRes<>(customResponseCode.getCode(), customResponseCode.name(), data);
    }
    public static GlobalRes<Void> from(CustomResponseCode customResponseCode){
        return from(customResponseCode, null);
    }
    public static<T> GlobalRes<T> success(T data){
        return from(CustomResponseCode.SUCCESS, data);
    }
    public static GlobalRes<Void> success(){
        return from(CustomResponseCode.SUCCESS, null);
    }

    /* 
    [변경 전 코드 및 리팩토링 이유]
    - 이유: 기존 코드는 각 메서드마다 new GlobalRes<>(...) 생성자를 직접 호출하여 중복 코드가 발생했습니다.
            특히 CustomResponseCode.SUCCESS의 getCode(), name() 메서드를 여러 번 개별적으로 직접 호출하여 변경에 취약했습니다.
    - 개선: 주 생성 메서드인 from(CustomResponseCode, T)에 생성을 몰아주고, 
            나머지 메서드는 이 주 메서드로 호출을 위임(Delegation)하도록 처리하여 중복을 제거하고 유지보수성을 극대화했습니다.

    public static<T> GlobalRes<T> from(CustomResponseCode customResponseCode, T data){
        return new GlobalRes<T>(customResponseCode.getCode(), customResponseCode.name(), data);
    }
    public static GlobalRes<Void> from( CustomResponseCode customResponseCode){
        return new GlobalRes<Void>(customResponseCode.getCode(), customResponseCode.name(),null);
    }
    public static<T> GlobalRes<T> from(T data){
        return new GlobalRes<T>(CustomResponseCode.SUCCESS.getCode(), CustomResponseCode.SUCCESS.name(), data);
    }
    public static GlobalRes<Void> from(){
        return new GlobalRes<Void>(CustomResponseCode.SUCCESS.getCode(), CustomResponseCode.SUCCESS.name(),null);
    }
    */
}
