if exists(select * from sys.databases where name = 'msjdbc')
BEGIN
    DROP DATABASE msjdbc;
END
GO

CREATE DATABASE msjdbc;
GO
