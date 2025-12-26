create database expenseTracker;
use expenseTracker;

CREATE TABLE user (
    id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(50) UNIQUE NOT NULL,
    username VARCHAR(50) NOT NULL,
    name varchar(50) NOT NULL,
    password VARCHAR(255) NOT NULL,
    phone BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    activeYN INT DEFAULT 1
);

CREATE TABLE category (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    FOREIGN KEY (user_id)
        REFERENCES user (id) on delete cascade,
    name VARCHAR(50) NOT NULL,
    description VARCHAR(50),
    icon_url varchar(255),
    transaction_type ENUM('income','expense') NOT NULL default 'expense',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    activeYN INT DEFAULT 1
    -- tiny int one bit hi allow karta hai 
);
CREATE TABLE transaction (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    FOREIGN KEY (user_id)
        REFERENCES user (id),
    amount DECIMAL(10 , 2 ) NOT NULL,
    category_id INT NOT NULL,
    FOREIGN KEY (category_id)
        REFERENCES category (id),
    dateOfTransaction DATE NOT NULL,
    notes VARCHAR(500),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    activeYN INT DEFAULT 1
);

insert into user(email,username,name,password,phone) values('eshikarupani2519@gmail.com','esh_rupani','Eshika Rupani','esha123',7276056156);
insert into category(user_id,name,description) values(1,'food','all food expenses...');
insert into transaction(user_id,amount,category_id,dateOfTransaction) values (1,200,1,'2025-12-26');