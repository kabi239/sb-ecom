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
| 🔄 Update Product Image | `/api/products/{productId}/image` | PUT | Updates an existing product |
| ❌ Delete Product | `/api/products/{productId}` | DELETE | Delete a product |

### **📂 Category Endpoints**

| API Name | Endpoint | Method | Purpose |
|----------|---------|--------|---------|
| ➕ Add Category | `/api/admin/category` | POST | Add a new category |
| 📋 Get Categories | `/api/public/categories` | GET | Retrieve all categories |
| ✏️ Update Category | `/api/admin/category/{categoryId}` | PUT | Update a category |
| ❌ Delete Category | `/api/admin/category/{categoryId}` | DELETE | Delete a category |

### **🛒 Cart Endpoints**

| API Name | Endpoint | Method | Purpose |
|----------|---------|--------|---------|
| ➕ Add to Cart | `api/carts/products/{productId}/quantity/{quantity}` | POST | Add a product to cart |
| 🛒 Get All Carts | `/api/carts` | GET | Retrieves List of all carts |
| 🛒 Get Users Cart | `/api/carts/users/cart` | GET | Retrieves the cart of logged- in user |
| 🔄 Update Product Quantity | `/api/carts/products/{productId}/quantity/{operation}` | PUT | Update cart item quantity |
| ❌ Remove Item | `/api/cart/{cartId}/products/{productId}` | DELETE | Remove an item from cart |

### **📍 Address Endpoints**

| API Name | Endpoint | Method | Purpose |
|----------|---------|--------|---------|
| ➕ Create Address | `/api/addresses/add` | POST | Add a new address |
| 📋 Get All Addresses | `/api/addresses` | GET | Retrieve all addresses |
| 📋 Get Addresses By ID | `/api/addresses/{addressId}` | GET | Retrieve an address by Id |
| 📋 Get Addresses By User | `/api/users/addresses` | GET | Retrieve user's addresses |
| ✏️ Update Address | `/api/addresses/{addressId}` | PUT | Update an address by Id |
| ❌ Delete Address | `/api/addresses/{addressId}` | DELETE | Delete an address by Id |

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
