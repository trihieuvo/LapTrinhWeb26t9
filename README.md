## Các công nghệ chính

*   **Backend:** Spring Boot 3, Spring for GraphQL, Spring Data JPA
*   **Database:** SQL Server
*   **Frontend:** Thymeleaf, AJAX 
*   **Công cụ phát triển:** Eclipse Spring Tool Suite (STS), Apache Maven, Apache Tomcat 10.1
*   ## 1. Thiết lập Database (SQL Server)

Dự án sử dụng SQL Server. Vui lòng chạy script SQL dưới đây để tạo database `web26t9` và các bảng cần thiết cùng với dữ liệu mẫu.

```sql
IF EXISTS (SELECT name FROM sys.databases WHERE name = N'web26t9')
BEGIN
    ALTER DATABASE web26t9 SET SINGLE_USER WITH ROLLBACK IMMEDIATE;
    DROP DATABASE web26t9;
END
GO

CREATE DATABASE web26t9 COLLATE Vietnamese_CI_AS;
GO

USE web26t9;
GO

CREATE TABLE Category (
    id INT PRIMARY KEY IDENTITY(1,1),
    name NVARCHAR(255) NOT NULL,
    images NVARCHAR(MAX)
);
GO

CREATE TABLE [User] (
    id INT PRIMARY KEY IDENTITY(1,1),
    fullname NVARCHAR(255) NOT NULL,
    email NVARCHAR(255) NOT NULL UNIQUE,
    password NVARCHAR(255) NOT NULL,
    phone NVARCHAR(20)
);
GO

CREATE TABLE Product (
    id INT PRIMARY KEY IDENTITY(1,1),
    title NVARCHAR(255) NOT NULL,
    quantity INT NOT NULL DEFAULT 0,
    [desc] NVARCHAR(MAX),
    price DECIMAL(10, 2) NOT NULL,
    userid INT,
    FOREIGN KEY (userid) REFERENCES [User](id)
);
GO

CREATE TABLE UserCategory (
    userid INT,
    categoryid INT,
    PRIMARY KEY (userid, categoryid),
    FOREIGN KEY (userid) REFERENCES [User](id),
    FOREIGN KEY (categoryid) REFERENCES Category(id)
);
GO

INSERT INTO Category (name, images) VALUES
(N'Điện thoại', N'images/categories/dienthoai.jpg'),
(N'Laptop', N'images/categories/laptop.jpg'),
(N'Phụ kiện', N'images/categories/phukien.jpg'),
(N'Thiết bị gia dụng', N'images/categories/giadung.jpg');
GO

INSERT INTO [User] (fullname, email, password, phone) VALUES
(N'Nguyễn Văn A', N'vana@example.com', N'pass123', N'0901234567'),
(N'Trần Thị B', N'thib@example.com', N'pass123', N'0907654321'),
(N'Lê Văn C', N'vanc@example.com', N'pass123', N'0912345678');
GO

INSERT INTO Product (title, quantity, [desc], price, userid) VALUES
(N'iPhone 15 Pro Max', 100, N'Điện thoại cao cấp của Apple', 1200.00, 1),
(N'Samsung Galaxy S24 Ultra', 150, N'Điện thoại Android hàng đầu', 1100.00, 1),
(N'MacBook Pro M3', 50, N'Laptop mạnh mẽ cho công việc và sáng tạo', 2000.00, 2),
(N'Dell XPS 15', 75, N'Laptop thiết kế đẹp, hiệu năng cao', 1800.00, 2),
(N'Tai nghe Bluetooth Sony WH-1000XM5', 200, N'Tai nghe chống ồn tốt nhất', 350.00, 1),
(N'Chuột Logitech MX Master 3S', 300, N'Chuột không dây cao cấp', 100.00, 3),
(N'Bàn phím cơ Anne Pro 2', 120, N'Bàn phím cơ nhỏ gọn, tùy chỉnh cao', 120.00, 3);
GO

INSERT INTO UserCategory (userid, categoryid) VALUES
(1, 1), (1, 3), (2, 2), (2, 4), (3, 3);
GO
```

## 2. Cấu hình ứng dụng

Cập nhật file `src/main/resources/application.properties` với thông tin kết nối SQL Server cụ thể.

## 3. Kiểm tra API và Giao diện người dùng

Bạn có thể tương tác với ứng dụng qua các đường dẫn sau:

*   **Giao diện GraphiQL (kiểm thử GraphQL API):**
    [http://localhost:8080/graphiql](http://localhost:8080/graphiql)
    Sử dụng giao diện này để thực hiện các Query và Mutation GraphQL đã định nghĩa.

*   **Giao diện người dùng (Frontend CRUD):**
    [http://localhost:8080/](http://localhost:8080/)
    Đây là trang HTML với AJAX tích hợp để thực hiện các thao tác CRUD lên Product, User, Category.

