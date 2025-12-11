# 🚗 **Online Toll Payment System – TollApp**

TollApp is a mobile-based solution designed to simplify and speed up toll collection by enabling users to **pay tolls online** before reaching the toll plaza. This minimizes waiting time, reduces congestion, and encourages digital payments. The system consists of two Android applications:

* **TollApp (User App)** – for drivers to view tolls, calculate fees, and make payments.
* **Admin TollApp** – for toll operators to authenticate and scan QR-based receipts.

---

## 📌 **Features**

### 🌟 User Features (TollApp)

* Simple and user-friendly Android interface
* User registration with email verification
* Login authentication via Firebase
* Select source & destination to view all tolls on the route
* Real-time toll fee calculation based on selected tolls
* Multiple payment options (Debit/Credit cards, Wallets)
* Automatic QR code receipt generation
* Digital, paperless toll transactions

### 🛠 Admin Features (Admin TollApp)

* Toll operator registration and login
* QR code scanner for validating payments
* Instant display of user trip and payment details

---

## 🧩 **System Architecture**

The system includes:

* **TollApp (User)** → Route selection → Choose tolls → Make payment → QR Code generated
* **Admin TollApp (Toll Operator)** → Scan QR → Validate → Allow vehicle passage

Data flow and communication are handled using **Firebase Authentication** and **Firebase Database**.

---

## 📱 **Technology Stack**

### 🔧 **Frontend**

* Android (Java / XML UI)

### ☁ **Backend**

* Firebase Authentication
* Firebase Realtime Database

### 🔍 **Additional Technologies**

* Navigation & Map Services
* QR Code Generation
* QR Code Scanner

---

## 🖥 **System Requirements**

### 📱 User App

* Android smartphone
* Stable internet connection (min 256 Kbps recommended 1 Mbps)

### 🛃 Admin App

* Android device
* Internet connectivity
* Firebase-registered toll credentials

---

## 🏗 **Modules Overview**

### **1. User Registration**

* Enter name, email, mobile number, password
* Email verification required

### **2. Login**

* Firebase authentication
* Valid users can access system functionalities

### **3. Route Selection**

* Select single or round trip
* Enter source and destination
* View tolls on map
* Choose tolls to pay
* System calculates total payable amount

### **4. Payment**

* Pay via cards or wallets
* Secure digital processing

### **5. QR Code Generation**

* Generated upon successful payment
* Contains route, tolls, vehicle type, trip type, and user details

### **6. Admin QR Scanner**

* Scans QR to validate toll payment
* Displays complete transaction details

---

## 🚧 **Limitations**

* Requires continuous internet
* Available only for Maharashtra tolls
* Toll rates are static (not dynamically updated)
* Offline payment not supported
* Only registered users can access the app

---

## 📈 **Future Enhancements**

* Support for all-India toll integration
* Dynamic toll rate updates
* Integration with FASTag systems
* Vehicle GPS auto-detection
* Analytics dashboard for toll authorities

---

## 🏁 **Conclusion**

TollApp provides a fast, reliable, and digital way of paying tolls, reducing congestion and saving time for drivers. It enhances the toll experience and brings transparency and convenience to road travel across Maharashtra.

---

## 📚 **References**

* Android Developer Documentation
* Firebase Documentation
* StackOverflow
* TutorialsPoint
* Android Black Book – Pradeep Kothari

