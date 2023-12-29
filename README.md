# Messenger
Тестовое задание на стажировку от РЭЛЭКС

Задание: https://docs.google.com/document/d/1JJ0xWr8maPbsthWCR5oN8934VELzpoXmU-SobxY-8G0/edit

## Наличие необязательных задач
 * хэширование паролей; :white_check_mark:
 * подтверждение почты через ссылку  в письме, отправленном на указанную почту. :white_check_mark:
 * поддержка Spring Security; :white_check_mark:
 * механизмы защиты от обхода разлогина. :white_check_mark:
 * при изменении email есть подтверждение изменения ссылкой на указанный новый email. :white_check_mark:
 * реализован перевод профиля в статус “Не активен” с дальнейшей возможностью восстановить профиль в течение некоторого времени. :white_check_mark:
 * API, позволяющий просматривать друзей, а также добавлять в друзья другого пользователя; :white_check_mark:
 * возможность ограничивать получение сообщений только своим кругом друзей; :white_check_mark:
 * возможность просматривать друзей другого пользователя :white_check_mark:
 * возможность скрывать свой список друзей. :white_check_mark:
 * использование базы данных (PostgreSQL) для хранения данных; :white_check_mark:
 * [документирование запросов через Swagger](http://localhost:8080/swagger-ui/index.html#); :white_check_mark:
 * написание тестов. :white_check_mark:
 * обмен и просмотр сообщений реализован с помощью веб-сокетов. :white_check_mark:
___
## Для работы
##### В `application.properties` и `application-test.properties` изменить параметры:

Указать свои данные для почты
```properies
spring.mail.host=smtp.mail.ru
spring.mail.username=relex-test-back@mail.ru
spring.mail.password=pTKV40Tt3dgzbCfrd6Nr
spring.mail.port=465
```
