package com.npay.npay_pattern_server.enumeration;

import lombok.Getter;

@Getter
public enum PayInfoEnum {

    //앞 숫자는 csv 칼럼 인덱스, 뒤 스트링은 매핑되는 문자열
    PAYMENT_TIME(0, "결제시간"),
    PAYMENT_SERIAL_NUMBER(1, "결제번호"),
    STORE_NAME(2, "가맹점명"),
    CUSTOMER_ID(3, "사용자명"),
    CUSTOMER_BIRTH(4, "사용자생년월일"),
    CUSTOMER_SEX(5, "사용자성별"),
    PRODUCT_NAME(6, "상품명"),
    PRODUCT_TYPE(7, "상품타입"),
    STATUS(8, "결제상태"),
    MAIN_PAYMENT_OPTION(9, "주결제수단"),
    MAIN_PAYMENT_OPTION_CODE(10, "주결제수단코드"),
    AMOUNT(11, "결제금액"),
    MAIN_AMOUNT(12, "주결제금액"),
    POINT_AMOUNT(13, "포인트결제금액"),
    CASH_RECEIPT_NUMBER(14, "현금영수증번호"),
    INSTALLATION_MONTH(15, "할부개월"),
    AD_ID(16, "광고ID"),
    SAVE_NAVER_POINT(17, "적립네이버포인트");



    final private int index;
    final private String name;

    PayInfoEnum(int index, String name) {
        this.index = index;
        this.name = name;
    }
}
