# 🛒 E-Commerce Website (Spring Boot)

## 🌍 Overview

This is a simple e-commerce website built using **Spring Boot**. It provides features for 👤 authentication, 📦 product management, 🛍️ cart functionality, and 🏷️ order & payment processing.

## ⭐ Features

- 👤 User Registration & 🔑 Authentication (Spring Security, JWT)
- 🛍️ Product Management (CRUD operations for admin)
- 🛒 Shopping Cart & 📦 Order Management
- 🌐 RESTful API for integration
- 🗄️ Database support with MySQL / PostgreSQL

## 🏗️ Tech Stack

- **⚙️ Backend:** Spring Boot, Spring Security, Hibernate, JPA
- **🗄️ Database:** MySQL / PostgreSQL
- **🔑 Authentication:** JWT-based authentication

## 🛠️ Installation & Setup

1. **📥 Clone the repository:**
   ```bash
   git clone https://github.com/yourusername/springboot-ecom.git
   cd springboot-ecom
   ```
2. **⚙️ Configure the database:** Update `application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/ecomdb
   spring.datasource.username=root
   spring.datasource.password=yourpassword
   ```
3. **▶️ Run the application:**
   ```bash
   mvn spring-boot:run
   ```

## 🔌 API Endpoints

### **🔑 Authentication Endpoints**

| API Name | Endpoint | Method | Purpose |
|----------|---------|--------|---------|
| 🔐 Sign In | `/signin` | POST | Authenticate a user |
| ✍️ Sign Up | `/signup` | POST | Register a new user |
| 🚪 Sign Out | `/signout` | POST | Sign out the user |
| 🔍 Current Username | `/username` | GET | Retrieve the username of the authenticated user |
| 👤 User Info | `/user` | GET | Retrieve user information |
| 🏪 All Sellers | `/sellers` | GET | Retrieve a paginated list of sellers |

### **📦 Product Endpoints**

| API Name | Endpoint | Method | Purpose |
|----------|---------|--------|---------|
| ➕ Add Product | `/api/admin/categories/{categoryId}/product` | POST | Adds a new product to a category |
| 📋 Get All Products | `/api/public/products` | GET | Retrieves all products |
| 📂 Get Products by Category | `/api/public/categories/{categoryId}/products` | GET | Retrieves products by category |
| 🔍 Get Products by Keyword | `/api/public/products/keyword/{keyword}` | GET | Searches products by keyword |
| 🔄 Update Product | `/api/products/{productId}` | PUT | Updates an existing product |

### **📂 Category Endpoints**

| API Name | Endpoint | Method | Purpose |
|----------|---------|--------|---------|
| ➕ Add Category | `/api/admin/category` | POST | Add a new category |
| 📋 Get Categories | `/api/public/categories` | GET | Retrieve all categories |
| ✏️ Update Category | `/api/admin/category/{id}` | PUT | Update a category |
| ❌ Delete Category | `/api/admin/category/{id}` | DELETE | Delete a category |

### **🛒 Cart Endpoints**

| API Name | Endpoint | Method | Purpose |
|----------|---------|--------|---------|
| ➕ Add to Cart | `/api/cart/add` | POST | Add a product to cart |
| 🛒 View Cart | `/api/cart` | GET | Retrieve user's cart |
| 🔄 Update Cart | `/api/cart/update/{id}` | PUT | Update cart item quantity |
| ❌ Remove Item | `/api/cart/remove/{id}` | DELETE | Remove an item from cart |
| ❌ Clear Cart | `/api/cart/clear` | DELETE | Clear all items from cart |

### **📍 Address Endpoints**

| API Name | Endpoint | Method | Purpose |
|----------|---------|--------|---------|
| ➕ Add Address | `/api/address/add` | POST | Add a new address |
| 📋 Get Addresses | `/api/address` | GET | Retrieve user's addresses |
| ✏️ Update Address | `/api/address/update/{id}` | PUT | Update an address |
| ❌ Delete Address | `/api/address/delete/{id}` | DELETE | Delete an address |

## 🔄 Application Flow

1. **👤 User Registration & 🔑 Authentication:** Users register and authenticate using JWT tokens.
2. **🛍️ Product Browsing:** Users browse available products fetched from the 🗄️ database.
3. **🛒 Shopping Cart:** Users can add products to their cart before proceeding to checkout.
4. **💰 Checkout Process:** An order is created upon checkout.
5. **📦 Order Management:** Admins can view and manage orders, updating their statuses.

## 🏛️ Database Schema & Relationships

![image](https://github.com/user-attachments/assets/f63f9535-e54f-432c-a412-399523d9e8a0)

### **📂 Main Entities:**

- **👤 Users:** Stores user details (🆔, ✉️ email, 🏷️ username, 🔒 password).
- **👑 Roles & User Roles:** Defines user roles (👨‍💼 admin, 🛍️ customer, 🏪 seller).
- **📦 Products:** Stores product details (🆔, 🏷️ name, 💰 price, 🔖 discount, 🖼️ image, 📊 stock, 📁 category, 🏪 seller).
- **📁 Categories:** Stores product categories.
- **🛒 Carts & Cart Items:** Temporary storage for products added to the cart.
- **📦 Orders & Order Items:** Stores finalized purchases and ordered products.
- **💳 Payments:** Tracks payment methods used for orders.
- **📍 Addresses & User Address:** Stores user shipping addresses.

### **🔗 Relationships:**

- A **👤 user** can have multiple **👑 roles** (many-to-many).
- A **👤 user** can have multiple **📍 addresses** (one-to-many).
- A **🛒 cart** belongs to a **👤 user** (one-to-one).
- A **🛒 cart** can have multiple **🛍️ cart items** (one-to-many).
- An **📦 order** belongs to a **👤 user** (one-to-many).
- An **📦 order** contains multiple **📋 order items** (one-to-many).
- A **📦 product** belongs to a **📁 category** (many-to-one).
- A **📦 product** can have multiple **🛍️ cart items** and **📋 order items** (one-to-many).

## 📸 Screenshots

(Include 📸 screenshots of the working application here)

## 🤝 Contributing

Feel free to 🍴 fork the project and submit pull requests!

## ⚖️ License

This project is licensed under the MIT License.
