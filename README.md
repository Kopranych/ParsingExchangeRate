# ParsingExchangeRate
Приложение получает с сайта https://www.audit-it.ru/currency/ курс валют доллары и евро каждые 10 минут и выводит его пользователю, при этом сохраняет полученный курс валют в базе данных.

Для получения и разбора веб страницы используется библиотека JSOUP

Данные сохраняются в Postgresql DB

Для работы с базой данных используется фреймворк Hibernate