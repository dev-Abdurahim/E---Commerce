package org.example.ecommerce.enums;

public enum OrderStatus {
    PENDING,     // buyurtma berildi, lekin hali tasdiqlanmagan
    CONFIRMED,   // admin/tizim tasdiqladi
    SHIPPED,     // jo'natildi
    DELIVERED,   // yetkazib berildi
    CANCELLED    // bekor qilindi
}
