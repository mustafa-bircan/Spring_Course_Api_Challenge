# Course Rest Api

## Proje Yapısı

### 1. **Controller**
- **CourseController**: API'nin ana denetleyicisidir ve tüm derslerle ilgili CRUD işlemlerini içerir.

### 2. **Model**
- **CourseGpa**: Dersin GPA hesaplamasına temel oluşturan interface.
- **LowCourseGpa**, **MediumCourseGpa**, **HighCourseGpa**: Farklı ders kredileri için GPA hesaplamalarını içerir.


### 3. **Entity**
- **Course**: Dersin adı, kredi ve not bilgilerini içerir.
- **Grade**: Dersin not bilgilerini ve katsayısını içerir.

### 4. **Exceptions**
- **ApiResponseError**: Hata mesajlarını döndüren sınıf.
- **ApiException**: Özel hata sınıfı.
- **GlobalExceptionHandler**: Tüm hataları global olarak yöneten sınıf.

## Kurulum

### Gereksinimler
- **Java 11 veya daha yeni bir versiyon**
- **Maven** (Bağımlılıkları yönetmek için)
- **IntelliJ IDEA veya başka bir IDE**

### Adımlar

1. **Proje Forklama ve Clone Etme**  
   Projeyi GitHub üzerinden fork edip, lokal makinenize clone edin.
   ```bash
   git clone https://github.com/username/repository-name.git
