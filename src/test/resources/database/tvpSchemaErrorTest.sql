CREATE SCHEMA tableValued
GO

CREATE TYPE [tableValued].[TheSqlDataType] AS TABLE
    (ThisId int, ThatId smallint)
GO

CREATE TABLE [tableValued].[RealTable] (
    [ThisId]     integer NOT NULL,
    [ThatId]     smallint NOT NULL
)
GO

CREATE PROCEDURE [tableValued].[UpdateMsDriverTest]
    @InputData     tableValued.TheSqlDataType READONLY
AS
BEGIN
    INSERT INTO tableValued.RealTable
    SELECT ThisId, ThatId FROM @InputData
END
GO
