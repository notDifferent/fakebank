DROP DATABASE IF EXISTS fake_bank;
CREATE DATABASE fake_bank;

CREATE TABLE fake_bank.account(
id bigint unsigned NOT NULL AUTO_INCREMENT,
customer_id bigint unsigned NOT NULL,
account_number bigint unsigned unique NOT NULL,
opening_balance int(11) NOT NULL,
current_balance int(11) NOT NULL,
created_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
modified_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
PRIMARY KEY (id)
);


CREATE TABLE fake_bank.transfer(
id bigint unsigned NOT NULL AUTO_INCREMENT,
customer_id bigint unsigned NOT NULL,
debit_account bigint unsigned NOT NULL,
credit_account bigint unsigned NOT NULL,
transfer_amount int(11) NOT NULL,
status enum('SUCCESSFUL','FAILED','PENDING') NOT NULL,
message varchar(100) NULL,
created_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
modified_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
PRIMARY KEY (id)
);