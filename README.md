## Установка

1. **Клонируйте репозиторий:**
   ```bash
   git clone https://github.com/RobotBot5/avito-music-player
   ```

2. **Откройте проект в Android Studio** и дождитесь синхронизации Gradle.

3. **Запустите приложение** на устройстве или эмуляторе.

## Проблемы, с которыми столкнулся

1. Сервер возвращал код 200, даже когда была ошибка. В таком случае вместо ожидамого ответа сервер возвращал { error={...} }, что сделало парсинг менее комфортным
2. Сервер при использовании пагинации не всегда возвращал ожидаемое кол-во элементов. Как выяснилось, связано это было с заблокированными в стране треками.
3. Не успел реализовать тесты, исправить баги и сделать рефакторинг, прошу понять и простить :) Минута в минуту успел сделать рабочую версию