drop table if exists Processes;
drop table if exists Analytics;

create table Processes(

ProcessID integer,
name varchar(30),
author varchar(30),
url varchar(50),
dateCreated date,
description varchar(100),
filesizeKB integer,

primary key (ProcessID)
);

select * from processes;


create table Analytics(
	
    AnalyticID integer,
	DeviceID varchar(50),
	ProcessID integer,
	TaskID integer,
	taskStart date,
	taskEnd date,
	ProcessRating integer,
	Comment varchar(300),
	
	primary key (AnalyticID)
	);
	
select * from Analytics;

create table Analytics(AnalyticID integer,DeviceID varchar(50), ProcessID integer, TaskID integer, taskStart date, taskEnd date, ProcessRating integer, Comment varchar(300), primary key (AnalyticID));