create database expenseTracker;
use expenseTracker;

CREATE TABLE user (
    id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(50) UNIQUE NOT NULL,
    username VARCHAR(50) NOT NULL,
    name VARCHAR(50) NOT NULL,
    password VARCHAR(255) NOT NULL,
    phone BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    activeYN INT DEFAULT 1
);
select * from user;
CREATE TABLE category (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    FOREIGN KEY (user_id)
        REFERENCES user (id)
        ON DELETE CASCADE,
    name VARCHAR(50) NOT NULL,
    description VARCHAR(50),
    icon_url VARCHAR(255),
    transaction_type ENUM('income', 'expense') DEFAULT 'expense',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    activeYN INT DEFAULT 1
);
alter table category modify column transaction_type ENUM('income', 'expense') DEFAULT 'expense';
alter table category modify column activeYN INT DEFAULT 1;
select id,activeYN,name,description,icon_url,transaction_type from category where user_id=6 and activeYN=1;
insert into category(user_id,name,description,icon_url,transaction_type) values(6,'shopping',null,null,null);
select * from category where user_id=6 and activeYN=1;
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

SELECT 
    *
FROM
    user;

CREATE TABLE resetPasswordTokens (
    id INT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(50),
    token VARCHAR(255),
    expiryTime TIMESTAMP,
    usedYN INT DEFAULT 0
);
-- insert into resetPasswordTokens(email,expiryTime) values('eshikarupani2519@gmail.com',ADDTIME(NOW(), '00:15:00'));
SELECT 
    *
FROM
    resetPasswordTokens;
insert into resetPasswordTokens(email,token,expiryTime) values('eshikarupani2519@gmail.com','test',ADDTIME(NOW(), '00:15:00'));

SELECT 
    CURRENT_TIMESTAMP(),
    expiryTime,
    CURRENT_TIMESTAMP() - expiryTime as diff
FROM
    resetPasswordTokens
WHERE
    email = 'eshikarupani2510@gmail.com'
        AND token = 'test'
        AND usedYN = 0 AND id=14;

SELECT 
    (CURRENT_TIMESTAMP() - expiryTime) / 60 AS diff
FROM
    resetPasswordTokens
WHERE
   
         token = 'test'
        AND usedYN = 0;
truncate table resetPasswordTokens;

update resetPasswordTokens set password='test' where email='eshikarupani100@gmail.com';
select * from resetPasswordTokens;

select token as diff from resetPasswordTokens where email='eshikarupani100@gmail.com' and usedYN=0 and current_timestamp()-expiryTime<=0;
select * from category;
