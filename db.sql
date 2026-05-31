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
SELECT 
    *
FROM
    user;
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
SELECT 
    id, activeYN, name, description, icon_url, transaction_type
FROM
    category
WHERE
    user_id = 6 AND activeYN = 1;
insert into category(user_id,name,description,icon_url,transaction_type) values(6,'shopping',null,null,null);
SELECT 
    *
FROM
    category
WHERE
    user_id = 6 AND activeYN = 1;
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
    CURRENT_TIMESTAMP() - expiryTime AS diff
FROM
    resetPasswordTokens
WHERE
    email = 'eshikarupani2510@gmail.com'
        AND token = 'test'
        AND usedYN = 0
        AND id = 14;

SELECT 
    (CURRENT_TIMESTAMP() - expiryTime) / 60 AS diff
FROM
    resetPasswordTokens
WHERE
    token = 'test' AND usedYN = 0;
truncate table resetPasswordTokens;

UPDATE resetPasswordTokens 
SET 
    password = 'test'
WHERE
    email = 'eshikarupani100@gmail.com';
SELECT 
    *
FROM
    resetPasswordTokens;

SELECT 
    token AS diff
FROM
    resetPasswordTokens
WHERE
    email = 'eshikarupani100@gmail.com'
        AND usedYN = 0
        AND CURRENT_TIMESTAMP() - expiryTime <= 0;
SELECT 
    *
FROM
    category;
DELETE FROM category 
WHERE
    id = 16;
SELECT 
    *
FROM
    transaction;
DELETE FROM transaction 
WHERE
    id = 25;
SELECT 
    *
FROM
    category
WHERE
    user_id = 6 AND name = 'food';

SELECT 
    t.user_id,
    t.amount,
    t.dateOfTransaction,
    t.notes,
    c.name,
    c.transaction_type
FROM
    transaction AS t
        INNER JOIN
    category AS c ON t.category_id = c.id
WHERE
    t.user_id = 1;
SELECT 
    t.id,
    t.user_id,
    t.amount,
    t.dateOfTransaction,
    t.notes,
    c.name,
    c.transaction_type,
    t.category_id,
    t.activeYN
FROM
    transaction t
        INNER JOIN
    category c ON t.category_id = c.id
WHERE
    t.user_id = 6;
    
-- query to fetch categorywise income and expenses
SELECT 
    c.name, SUM(t.amount) AS amount_spent
FROM
    category c
        LEFT JOIN
    transaction t ON c.id = t.category_id
WHERE
    c.transaction_type = 'expense' and c.user_id=1 and c.activeYN=1 and t.activeYN=1
GROUP BY c.name;
select * from user;
select id,user_id,amount,category_id,dateOfTransaction,notes,activeYN from transaction where id=26 and user_id=6 and activeYN=1;
SELECT 
     MONTH(t.dateOfTransaction) as months,SUM(t.amount) AS amount_spent
FROM
    category c
        LEFT JOIN
    transaction t ON c.id = t.category_id
WHERE
    c.transaction_type = 'expense' and c.user_id=6 and c.activeYN=1 and t.activeYN=1
GROUP BY months;

select id,user_id,amount,category_id,dateOfTransaction,notes,activeYN from transaction where id=26 and user_id=6 and activeYN=1;
SELECT 
     date_format(t.dateOfTransaction,'%b %Y') as months,SUM(CASE WHEN c.transaction_type='expense' THEN t.amount ELSE 0 END) AS amount_spent,
     SUM(CASE WHEN c.transaction_type='income' THEN t.amount ELSE 0 END) AS amount_gained
FROM
    category c
        LEFT JOIN
    transaction t ON c.id = t.category_id
WHERE
     c.user_id=6 and c.activeYN=1 and t.activeYN=1 and t.dateOfTransaction >=DATE_SUB(NOW(), INTERVAL 1 YEAR) and 
     t.dateOfTransaction<=now()
GROUP BY months order by MONTH(months),YEAR(months);

select *
from category;

insert into transaction(user_id,amount,category_id,dateOfTransaction) values (6,600,3,'2026-04-13');
