# E-Commerce API
 
Mini onlayn do'kon (e-commerce) uchun yozilgan **Spring Boot REST API** loyihasi. Faqat backend qismini o'z ichiga oladi — autentifikatsiya, mahsulot/kategoriya boshqaruvi, savatcha va buyurtma (checkout) funksiyalarini qamrab oladi.
 
## 📋 Mundarija
 
- [Texnologiyalar](#-texnologiyalar)
- [Funksiyalar](#-funksiyalar)
- [Loyiha strukturasi](#-loyiha-strukturasi)
- [Ma'lumotlar bazasi sxemasi](#-malumotlar-bazasi-sxemasi)
- [O'rnatish va ishga tushirish](#-ornatish-va-ishga-tushirish)
- [API endpointlar](#-api-endpointlar)
- [Xatolik formati](#-xatolik-formati)
- [Kelajakdagi rejalar](#-kelajakdagi-rejalar)
## 🛠 Texnologiyalar
 
| Kategoriya | Texnologiya |
|---|---|
| Til | Java 17 |
| Framework | Spring Boot 3.5.6 |
| Web | Spring MVC (REST API) |
| Ma'lumotlar bazasi | PostgreSQL |
| ORM | Spring Data JPA / Hibernate |
| Migratsiya | Liquibase |
| Xavfsizlik | Spring Security + JWT (jjwt) |
| Mapping | MapStruct |
| Boilerplate | Lombok |
| API hujjatlashtirish | springdoc-openapi (Swagger UI) |
| Build tool | Maven |
 
## ✨ Funksiyalar
 
- **Autentifikatsiya** — JWT asosida ro'yxatdan o'tish va login (access + refresh token)
- **Role-based access control** — `USER` va `ADMIN` rollari (`@PreAuthorize` orqali himoyalangan)
- **Mahsulot va kategoriya boshqaruvi** — CRUD amallari (yaratish/o'zgartirish/o'chirish faqat ADMIN uchun, ko'rish hammaga ochiq)
- **Qidiruv va filtrlash** — mahsulotlarni kategoriya va nom bo'yicha qidirish, birgalikda ham ishlaydi
- **Pagination** — barcha ro'yxat endpointlarida sahifalash
- **Savatcha (Cart)** — mahsulot qo'shish, miqdorni o'zgartirish, o'chirish
- **Buyurtma (Order/checkout)** — savatchadan buyurtmaga o'tish, ombordagi miqdorni avtomatik kamaytirish, narxni "muzlatib" saqlash
- **Soft delete** — ma'lumotlar bazadan butunlay o'chirilmaydi, faqat `deleted=true` belgilanadi (`@SQLRestriction` orqali avtomatik yashiriladi)
- **Global xatolik boshqaruvi** — barcha xatoliklar (validatsiya, biznes, autentifikatsiya, kutilmagan) bitta izchil JSON formatda qaytadi
- **Swagger UI** — JWT bilan interaktiv API hujjatlashtirish
## 🏗 Loyiha strukturasi
 
```
org.example.ecommerce
├── entity/          # JPA entity'lar (User, Product, Category, Cart, Order va h.k.)
├── enums/           # UserRole, OrderStatus
├── repository/      # Spring Data JPA repository'lar
├── dto/
│   ├── request/     # Kiruvchi so'rov obyektlari
│   └── response/    # Chiquvchi javob obyektlari
├── mapper/          # MapStruct mapperlar (entity ↔ DTO)
├── service/         # Biznes logika interfeyslari
│   └── impl/        # Implementatsiyalar
├── controller/      # REST controllerlar
├── security/        # JWT, UserDetails, SecurityConfig
├── exception/       # ApiException, ErrorCode, GlobalExceptionHandler
└── config/          # Swagger va boshqa konfiguratsiyalar
```
 
Arxitektura **layered** (qatlamli) yondashuvga asoslangan: Controller → Service → Repository, har bir qatlam faqat o'ziga tegishli mas'uliyatni bajaradi (SOLID, ayniqsa Single Responsibility va Dependency Inversion tamoyillariga rioya qilingan holda).
 
## 🗄 Ma'lumotlar bazasi sxemasi
 
```
users ─┬─< user_roles
       ├─── carts ─< cart_items >─ products ─> categories
       └─< orders ─< order_items >───┘
```
 
- **Cart/CartItem** — foydalanuvchining "hozirgi" holati, narx muzlatilmaydi
- **Order/OrderItem** — checkout tugagandan keyingi "tarixiy" yozuv, `priceAtOrderTime` orqali narx muzlatib saqlanadi
## 🚀 O'rnatish va ishga tushirish
 
### Talablar
- Java 17+
- Maven
- PostgreSQL (mahalliy yoki masofaviy)
### Qadamlar
 
1. Repozitoriyni klonlang:
```bash
   git clone https://github.com/dev-Abdurahim/E---Commerce.git
   cd e-commerce
```
 
2. PostgreSQL'da `ecommerce_db` nomli baza yarating.
3. `src/main/resources/application.yml` faylida DB ulanish ma'lumotlarini va JWT secret'ni sozlang:
```yaml
   spring:
     datasource:
       url: jdbc:postgresql://localhost:5432/ecommerce_db
       username: postgres
       password: your_password
   jwt:
     secret: ${JWT_SECRET:your-super-secure-random-64-char-secret-key-here}
```
 
4. Loyihani ishga tushiring:
```bash
   mvn spring-boot:run
```
   Ishga tushganda, Liquibase barcha jadvallarni avtomatik yaratadi.
 
5. Swagger UI orqali API'ni ko'ring:
```
   http://localhost:8081/swagger-ui.html
```
 
### Admin foydalanuvchi yaratish
 
Ro'yxatdan o'tgan foydalanuvchi standart holatda `USER` rolida bo'ladi. Uni `ADMIN` qilish uchun bazada qo'lda yangilang:
```sql
UPDATE user_roles
SET role = 'ADMIN'
WHERE user_id = (SELECT id FROM users WHERE username = 'your_username');
```
Rol o'zgargandan keyin, yangi token olish uchun qayta login qiling.
 
## 📡 API endpointlar
 
### Autentifikatsiya (`/api/auth`)
| Metod | Endpoint | Tavsif | Ruxsat |
|---|---|---|---|
| POST | `/api/auth/register` | Ro'yxatdan o'tish | Ochiq |
| POST | `/api/auth/login` | Login qilish | Ochiq |
 
### Kategoriyalar (`/api/categories`)
| Metod | Endpoint | Tavsif | Ruxsat |
|---|---|---|---|
| GET | `/api/categories` | Barcha kategoriyalar | Ochiq |
| GET | `/api/categories/{id}` | Bitta kategoriya | Ochiq |
| POST | `/api/categories` | Yangi kategoriya yaratish | ADMIN |
| PUT | `/api/categories/{id}` | Kategoriyani tahrirlash | ADMIN |
| DELETE | `/api/categories/{id}` | Kategoriyani o'chirish | ADMIN |
 
### Mahsulotlar (`/api/products`)
| Metod | Endpoint | Tavsif | Ruxsat |
|---|---|---|---|
| GET | `/api/products?categoryId=&search=&page=&size=` | Mahsulotlar ro'yxati (filtr + pagination) | Ochiq |
| GET | `/api/products/{id}` | Bitta mahsulot | Ochiq |
| POST | `/api/products` | Yangi mahsulot yaratish | ADMIN |
| PUT | `/api/products/{id}` | Mahsulotni tahrirlash | ADMIN |
| DELETE | `/api/products/{id}` | Mahsulotni o'chirish | ADMIN |
 
### Savatcha (`/api/cart`)
| Metod | Endpoint | Tavsif | Ruxsat |
|---|---|---|---|
| GET | `/api/cart` | Hozirgi savatchani ko'rish | Login qilingan |
| POST | `/api/cart/items` | Savatchaga mahsulot qo'shish | Login qilingan |
| PUT | `/api/cart/items/{itemId}` | Miqdorni o'zgartirish | Login qilingan |
| DELETE | `/api/cart/items/{itemId}` | Savatchadan o'chirish | Login qilingan |
 
### Buyurtmalar (`/api/orders`)
| Metod | Endpoint | Tavsif | Ruxsat |
|---|---|---|---|
| POST | `/api/orders` | Checkout (savatchadan buyurtma yaratish) | Login qilingan |
| GET | `/api/orders` | O'zining buyurtmalari (pagination) | Login qilingan |
| GET | `/api/orders/{id}` | Bitta buyurtma tafsiloti | Login qilingan |
 
**Autentifikatsiya:** himoyalangan endpointlar uchun `Authorization: Bearer <access_token>` header'i talab qilinadi.
 
## ⚠️ Xatolik formati
 
Barcha xatoliklar izchil JSON formatda qaytadi:
 
```json
{
  "errorCode": "PRODUCT_NOT_FOUND",
  "message": "Mahsulot topilmadi",
  "status": 404,
  "timestamp": "2026-08-16T12:00:00",
  "path": "/api/products/999"
}
```
## Bu shunchaki 4 oylik tanafusdan keyin ⌛, bilimlarni eslap olish uchun qilingan bir kichik project:
