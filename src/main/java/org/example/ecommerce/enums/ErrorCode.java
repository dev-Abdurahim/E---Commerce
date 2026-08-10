package org.example.ecommerce.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "Foydalanuvchi topilmadi"),
    USERNAME_ALREADY_EXISTS(HttpStatus.CONFLICT, "Bu username band"),
    INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, "Username yoki parol noto'g'ri"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED,"Token yaroqsiz yoki mavjud emas"),
    CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "Kategoriya topilmadi"),
    CATEGORY_ALREADY_EXISTS(HttpStatus.CONFLICT, "Bu nomdagi kategoriya allaqachon mavjud"),

    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "Mahsulot topilmadi"),
    INSUFFICIENT_STOCK(HttpStatus.BAD_REQUEST, "Omborda yetarli mahsulot yo'q"),
    EMPTY_CART(HttpStatus.BAD_REQUEST, "Savatcha bo'sh, buyurtma berib bo'lmaydi"),

    CART_ITEM_NOT_FOUND(HttpStatus.NOT_FOUND, "Savatchada bunday mahsulot topilmadi"),

    ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "Buyurtma topilmadi"),

    ACCESS_DENIED(HttpStatus.FORBIDDEN, "Sizda bu amalni bajarish huquqi yo'q"),
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "Kiritilgan ma'lumotlar noto'g'ri"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Kutilmagan xatolik yuz berdi");

    private final HttpStatus status;
    private final String defaultMessage;


    ErrorCode(HttpStatus status, String defaultMessage) {
        this.status = status;
        this.defaultMessage = defaultMessage;
    }

}
