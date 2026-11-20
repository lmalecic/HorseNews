use master
go

create database HorseNewsApp
go

use HorseNewsApp
go

-- Structure
create table Creator (
	Id int primary key identity,
	[Name] nvarchar(255) not null
)
go

go
create table Article (
	Id int primary key identity,
	Title nvarchar(255) not null,
	Link nvarchar(max) not null,
	[Description] nvarchar(max) not null,
	DatePublished datetime2 not null,
	[Guid] nvarchar(255) unique not null,
	[Image] varbinary(max),
	CreatorId int foreign key references Creator(Id) not null
)
go

create table Category (
	Id int primary key identity,
	[Name] nvarchar(255) not null
)
go

create table [User](
	Id int primary key identity,
	Username nvarchar(255) not null unique,
	PwdHash nvarchar(255) not null,
    PwdSalt nvarchar(255) not null,
	RoleIndex int not null default 1
)
go

create table ArticleCategory (
	Id int primary key identity,
	ArticleId int foreign key references Article(Id) not null,
	CategoryId int foreign key references Category(Id) not null
)
go

-- Creator procedures
create or alter proc createCreator
	@Name nvarchar(255),
	@NewId int output
as
begin
	set nocount on

	insert into Creator ([Name])
    values (@Name)

    set @NewId = scope_identity()
end
go

create or alter proc getCreator
    @Id int
as
begin
    set nocount on
    select * from Creator where Id = @Id
end
go

create or alter proc getAllCreators
as
begin
    set nocount on
    select * from Creator order by [Name] desc
end
go

create or alter proc getCreatorByName
	@Name nvarchar(255)
as
begin
	set nocount on
	select * from Creator
	where @Name = [Name]
end
go

create or alter proc updateCreator
    @Id int,
    @Name nvarchar(255)
as
begin
    set nocount on
    update Creator
    set [Name] = @Name
    where Id = @Id
end
go

create or alter proc deleteCreator
    @Id int
as
begin
    set nocount on
    delete from Creator where Id = @Id
end
go

-- Article procedures
create or alter proc createArticle
    @Title nvarchar(255),
	@Link nvarchar(max),
    @Description nvarchar(max),
    @DatePublished datetime2,
	@CreatorId int,
	@Guid nvarchar(255),
    @Image varbinary(max),
    @NewId int output
as
begin
    set nocount on

    insert into Article (Title, Link, [Description], DatePublished, CreatorId, [Guid], [Image])
    values (@Title, @Link, @Description, @DatePublished, @CreatorId, @Guid, @Image)

    set @NewId = scope_identity()
end
go

create or alter proc getArticle
    @Id int
as
begin
    set nocount on
    select * from Article where Id = @Id
end
go

create or alter proc getAllArticles
as
begin
    set nocount on
    select * from Article order by DatePublished desc
end
go

create or alter proc getArticleByGuid
	@Guid nvarchar(255)
as
begin
	set nocount on
	select * from Article
	where @Guid = [Guid]
end
go

create or alter proc updateArticle
    @Id int,
    @Title nvarchar(255),
	@Link nvarchar(max),
    @Description nvarchar(max),
    @DatePublished datetime2,
	@CreatorId int,
	@Guid nvarchar(255),
    @Image varbinary(max)
as
begin
    set nocount on
    update Article
    set Title = @Title,
		Link = @Link,
        [Description] = @Description,
        DatePublished = @DatePublished,
		CreatorId = @CreatorId,
		[Guid] = @Guid,
        [Image] = @Image
    where Id = @Id
end
go

create or alter proc deleteArticle
    @Id int
as
begin
    set nocount on

    delete from ArticleCategory
    where ArticleId = @Id

    delete from Article where Id = @Id
end
go

-- Category procedures
create or alter proc createCategory
    @Name nvarchar(255),
    @NewId int output
as
begin
    set nocount on
    insert into Category ([Name]) values (@Name)
    set @NewId = scope_identity()
end
go

create or alter proc getCategory
	@Id int
as
begin
	set nocount on
	select * from Category where Id = @Id
end
go

create or alter proc getAllCategories
as
begin
    set nocount on
    select * from Category order by [Name]
end
go

create or alter proc getCategoryByName
	@Name nvarchar(255)
as
begin
	set nocount on
	select * from Category
	where [Name] = @Name
end
go

create or alter proc updateCategory
    @Id int,
    @Name nvarchar(255)
as
begin
    set nocount on
    update Category set [Name] = @Name where Id = @Id
end
go

create or alter proc deleteCategory
    @Id int
as
begin
    set nocount on
    delete from Category where Id = @Id
end
go

-- User procedures
create or alter proc createUser
    @Username nvarchar(255),
    @PwdHash nvarchar(255),
    @PwdSalt nvarchar(255),
    @RoleIndex int,
    @NewId int output
as
begin
    set nocount on
    insert into [User] (Username, PwdHash, PwdSalt, RoleIndex)
    values (@Username, @PwdHash, @PwdSalt, @RoleIndex)
    set @NewId = scope_identity()
end
go

create or alter proc updateUser
    @UserId int,
    @Username nvarchar(255),
    @PwdHash nvarchar(255),
    @PwdSalt nvarchar(255),
    @RoleIndex int
as
begin
    set nocount on
    update [User]
    set Username = @Username,
        PwdHash = @PwdHash, 
        PwdSalt = @PwdSalt,
        RoleIndex = @RoleIndex
    where Id = @UserId
end
go

create or alter proc getAllUsers
as
begin
    set nocount on
    select Id, Username, PwdHash, PwdSalt, RoleIndex from [User]
end
go

create or alter proc getUserByUsername(
	@Username nvarchar(255)
)
as
begin
	set nocount on
	select Id, Username, PwdHash, PwdSalt, RoleIndex from [User]
	where Username = @Username
end
go

create or alter proc deleteUser(
    @Username nvarchar(255)
)
as
begin
    set nocount on
    delete from [User]
    where Username = @Username
end
go

-- ArticleCategory procedures
create or alter proc addCategoryToArticle
    @ArticleId int,
    @CategoryId int
as
begin
	set nocount on
	if not exists (
		select * from ArticleCategory
		where ArticleId = @ArticleId and CategoryId = @CategoryId)
	begin
		insert into ArticleCategory (ArticleId, CategoryId)
		values (@ArticleId, @CategoryId)
	end
end
go

create or alter proc removeCategoryFromArticle
    @ArticleId int,
    @CategoryId int
as
begin
	set nocount on
    delete from ArticleCategory
    where ArticleId = @ArticleId and CategoryId = @CategoryId
end
go

create or alter proc getArticleCategories
    @ArticleId int
as
begin
	set nocount on
    select ac.*, c.[Name] as CategoryName
    from ArticleCategory ac
    join Category c on ac.CategoryId = c.Id
    where ac.ArticleId = @ArticleId
end
go

-- Procedure to delete all data
create or alter proc deleteAllData
as
begin
	set nocount on
	delete from ArticleCategory
	delete from Article
	delete from Creator
	delete from Category
	delete from [User]
end
go
