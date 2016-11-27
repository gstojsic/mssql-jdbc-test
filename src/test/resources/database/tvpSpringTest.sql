
CREATE TYPE dbo.MsDriverSqlType AS TABLE
    (ThisId int, ThatlId int)
GO

CREATE TABLE [dbo].[RealTable] (
  [ThisId]    integer NOT NULL,
  [ThatId]	  integer NOT NULL
)
GO

CREATE PROCEDURE [dbo].[TestMsDriverTVP]
    @InputData      dbo.MsDriverSqlType READONLY
AS
BEGIN
	INSERT INTO dbo.RealTable
	SELECT ThisId, ThatlId FROM @InputData
END
GO