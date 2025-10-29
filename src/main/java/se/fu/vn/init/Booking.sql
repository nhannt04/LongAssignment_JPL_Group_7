create database Booking;
use Booking;

create table Users (
                       user_id int identity(1,1) primary key,
                       username nvarchar(50) not null unique,
                       password nvarchar(100) not null,
                       fullname nvarchar(100),
                       role nvarchar(20) default 'CUSTOMER'
);
go

-- thêm admin mặc định
insert into Users (username, password, fullname, role)
values
    ('admin', '123456', 'System Administrator', 'ADMIN'),
    ('Minh', '123456', 'Minh Smily', 'CUSTOMER');
go

-- bảng services
create table Services (
                          service_id int identity(1,1) primary key,
                          name nvarchar(100) not null,
                          description nvarchar(max),
                          duration_minutes int not null,
                          price decimal(10,2) not null,
                          created_at datetime default getdate()
);
go

-- dữ liệu mẫu
insert into Services (name, description, duration_minutes, price)
values
    (N'Haircut', N'Basic men haircut', 30, 100000),
    (N'Shaving', N'Beard shaving and grooming', 20, 80000),
    (N'Massage', N'Shoulder & head massage', 45, 150000);
go
select * from Appointments

SELECT appointment_id, customer_id, customer_name, customer_email, service_id, appointment_time, status, created_at FROM Appointments where customer_id = 3 ORDER BY appointment_time
-- bảng appointments
create table Appointments (
                              appointment_id int identity(1,1) primary key,
                              customer_id INT NOT NULL,
                              customer_name nvarchar(100) not null,
                              customer_email nvarchar(100) not null,
                              service_id int not null,
                              appointment_time datetime not null,
                              status nvarchar(20) default N'PENDING',
                              created_at datetime default getdate(),
                              foreign key (service_id) references Services(service_id),
                              foreign key (customer_id) references Users(user_id)

);
go