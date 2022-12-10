--liquibase formatted sql

--changeset kh:1
INSERT INTO gym (name, address, email, phone, working_hours_on_weekdays, working_hours_on_weekends, inst_link, tg_link,
                 vk_link)
VALUES ('СТАРТ.ПОЛИТЕХ',
        'Политехническая улица, 122А',
        'start@mail.ru',
        '+78452000000',
        '8:00-21:00',
        '8:00-21:00',
        'https://instagram.com/start',
        'https://tg.com/start',
        'https://vk.com/start');

INSERT INTO role (name)
VALUES (UPPER('ADMIN')),
       (UPPER('MANAGER')),
       (UPPER('CUSTOMER'));

SELECT *
FROM role;

INSERT INTO users (firstname, patronymic, lastname, email, password, phone, birth_date)
VALUES ('Иван', 'Иванович', 'Иванов', 'admin@gmail.com', '{noop}6%J#Z%u4GgiTv5BE4', '+70000000001',
        '01.01.1991'),
       ('Анна', 'Олеговна', 'Еськина', 'manager@gmail.com', '{noop}6%J#Z%u4GgiTv5BE4', '+70000000002',
        '01.01.1991'),
       ('Федор', 'Иванович', 'Тютчев', 'customer@gmail.com', '{noop}6%J#Z%u4GgiTv5BE4', '+70000000004',
        '01.01.1991');

SELECT *
FROM users;

INSERT INTO users_role (user_id, role_id)
VALUES ((SELECT id FROM users WHERE email = 'admin@gmail.com'),
        (SELECT id FROM role WHERE UPPER(name) = UPPER('ADMIN'))),
       ((SELECT id FROM users WHERE email = 'admin@gmail.com'),
        (SELECT id FROM role WHERE UPPER(name) = UPPER('CUSTOMER'))),

       ((SELECT id FROM users WHERE email = 'manager@gmail.com'),
        (SELECT id FROM role WHERE UPPER(name) = UPPER('MANAGER'))),
       ((SELECT id FROM users WHERE email = 'manager@gmail.com'),
        (SELECT id FROM role WHERE UPPER(name) = UPPER('CUSTOMER'))),

       ((SELECT id FROM users WHERE email = 'customer@gmail.com'),
        (SELECT id FROM role WHERE UPPER(name) = UPPER('CUSTOMER')));

INSERT INTO coach (gym_id, firstname, patronymic, lastname, birth_date, phone, email, specialization, description,
                   image)
VALUES (1, 'Александр ',
        'Иванович',
        'Белов',
        '01.01.1991',
        '+70000000001',
        'coach1@gmail.com',
        'Фитнес – менеджер',
        'Инструктор групповых программ HOT IRON, CYCLING. Здоровый человек – счастливый человек!',
        NULL),
       (1, 'Анна', 'Иванович', 'Еськина',
        '01.01.1991',
        '+70000000002',
        'coach2@gmail.com',
        'Персональный тренер, инструктор тренажерного зала',
        'Инструктор групповых программ HOT IRON, CYCLING. Здоровый человек – счастливый человек!',
        NULL),
       (1, 'Кирилл', 'Иванович', 'Алексеев ',
        '01.01.1991',
        '+70000000003',
        'coach3@gmail.com',
        'Персональный тренер, инструктор тренажерного зала',
        'Инструктор групповых программ HOT IRON, CYCLING. Здоровый человек – счастливый человек!',
        NULL),
       (1, 'Неля ', 'Иванович', 'Стадник',
        '01.01.1991',
        '+70000000004',
        'coach4@gmail.com',
        'Персональный тренер, инструктор тренажерного зала',
        'Инструктор групповых программ HOT IRON, CYCLING. Здоровый человек – счастливый человек!',
        NULL),
       (1, 'Неля ', 'Иванович', 'Стадник',
        '01.01.1991',
        '+70000000005',
        'coach5@gmail.com',
        'Персональный тренер, инструктор тренажерного зала',
        'Инструктор групповых программ HOT IRON, CYCLING. Здоровый человек – счастливый человек!',
        NULL),
       (1, 'Неля ', 'Иванович', 'Беспалова',
        '01.01.1991',
        '+70000000006',
        'coach6@gmail.com',
        'Персональный тренер, инструктор тренажерного зала',
        'Инструктор групповых программ HOT IRON, CYCLING. Здоровый человек – счастливый человек!',
        NULL),
       (1, 'Ольга', 'Иванович', 'Глаголева ',
        '01.01.1991',
        '+70000000007',
        'coach7@gmail.com',
        'тренер',
        'Инструктор групповых программ HOT IRON, CYCLING. Здоровый человек – счастливый человек!',
        '06caf993-a9b4-4082-a7d0-c1b74cb0b941.jpg');

INSERT INTO training_program (gym_id, name, description, overview, image)
VALUES (1, 'СИЛОВЫЕ ТРЕНИРОВКИ',
        'Групповые тренировки, направленные на развитие силы, выносливости и укрепления всех групп мышц. Мы предлагаем следующие направления силовых тренировок',
        'PumpSuper sculptLower bodyUpper workBums+ABS',
        NULL),
       (1, 'BODY&MIND ТРЕНИРОВКИ',
        'Универсальная групповая программа тренировок, которая подойдет всем, включая новичков. Мы предлагаем следующие направления body&mind тренировок',
        'ПилатесБалансЙогаЙога-ланчЙога-здоровая спинаЙога в гамакахЙога в гамакахMFR+Stretching',
        NULL),
       (1, 'ФУНКЦИОНАЛЬНЫЕ ТРЕНИРОВКИ',
        'Групповые занятия, которые помогут развить ловкость, силу, координацию и выносливость. Регулярные занятия дадут вам возможность получить физически развитое и красивое тело. Мы предлагаем следующие направления функциональных тренировок:',
        'Functional training',
        NULL),
       (1, 'СМЕШАННЫЕ ТРЕНИРОВКИ',
        'Групповые занятия, основанные на комбинированных тренировках - это одновременное сочетание силовых упражнений с аэробными нагрузками. Помогут значительно улучшить физическую форму. Мы предлагаем следующие направления смешанных тренировок',
        'ABS+stretching',
        NULL),
       (1, 'ТАНЦЕВАЛЬНЫЕ ТРЕНИРОВКИ',
        'Групповые тренировки, которые сочетают в себе аэробные и кардио нагрузки. Эффективно сжигают лишние калории, избавляют от лишнего веса и делают осанку и тело красивее. Мы предлагаем следующие направления танцевальных тренировок',
        'Belly danceBelly PROZumba',
        NULL),
       (1, 'КАРДИО ТРЕНИРОВКИ',
        'Универсальная групповая программа тренировок для укрепления сердечной мышцы, а также улучшения состояния систем кровообращения и дыхания. Отлично подходят для похудения. Мы предлагаем следующие направления кардио-тренировок',
        'Степ PRO',
        NULL);

INSERT INTO sub_training_program (name, training_program_id, overview, description, image)
VALUES ('Pump', 1, 'overview1', 'description1', NULL),
       ('Super sculpt', 1, 'overview1', 'description1', NULL),
       ('Lower body', 1, 'overview1', 'description1', NULL),
       ('Йога', 2, 'overview2', 'description2', NULL),
       ('Йога-ланч', 2, 'overview2', 'description2', NULL),
       ('Пилатес', 2, 'overview2', 'description2', NULL),
       ('MFR+Stretching', 2, 'overview2', 'description2', NULL),
       ('Functional training', 3, 'overview3', 'description3', NULL),
       ('Степ PRO', 4, 'overview4', 'description4', NULL),
       ('ABS+stretching', 5, 'overview5', 'description5', NULL),
       ('Belly dance', 6, 'overview6', 'description6', NULL),
       ('Belly PRO', 6, 'overview6', 'description6', NULL),
       ('Zumba', 6, 'overview6', 'description6', NULL);

INSERT INTO room (gym_id,name)
VALUES ('1','Большой зал'),
       ('1','Малый зал');

INSERT INTO training (gym_id, coach_id,room_id,sub_training_program_id, name, start_time, end_time, day_of_week,total_seats,taken_seats)
VALUES (1, 1,'1','1', 'Yoga', '09:00:00', '10:00:00', '1','25','0'),
       (1, 1,'1','1', 'Tabata', '10:00:00', '11:10:00', '2','25','0'),
       (1, 1,'1','1', 'Stretch', '11:00:00', '12:10:00', '3','25','0'),
       (1, 1,'2','1', 'Yoga', '12:00:00', '13:10:00', '4','25','0'),
       (1, 1,'2','1', 'Body Sculpt', '13:00:00', '14:10:00', '5','25','0'),
       (1, 1,'2','1', 'Belly Dance', '14:00:00', '15:10:00', '6','25','0'),
       (1, 1,'2','1', 'Pilates', '15:00:00', '16:10:00', '0','25','0');

INSERT INTO subscription (name, gym_id, period, price)
VALUES ('1 месяц', 1, '1 месяц', '3500'),
       ('3 месяца', 1, '3 месяца', '7700'),
       ('6 месяцев', 1, '6 месяцев', '13800'),
       ('12 месяц', 1, '12 месяц', '23300');


INSERT INTO free_pass (gym_id,date,start_time,end_time, email, firstname, lastname, phone, is_done)
VALUES (1,'2022-12-13','19:00:00','20:00:00', 'test@gmail.com', 'Иван', 'Иванов', '+79990009999', FALSE),
       (1,'2022-12-13','19:00:00','20:00:00', 'test1@gmail.com', 'Алексей', 'Иванов', '+79991009999', FALSE),
       (1,'2022-12-13','19:00:00','20:00:00', 'test21@gmail.com', 'Алексей', 'Иванов', '+79992009999', FALSE),
       (1,'2022-12-13','19:00:00','20:00:00', 'test31@gmail.com', 'Алексей', 'Иванов', '+79993009999', FALSE),
       (1,'2022-12-13','19:00:00','20:00:00', 'test41@gmail.com', 'Алексей', 'Иванов', '+79994009999', FALSE),
       (1,'2022-12-13','19:00:00','20:00:00', 'test51@gmail.com', 'Алексей', 'Иванов', '+79995009999', FALSE),
       (1,'2022-12-13','19:00:00','20:00:00', 'test61@gmail.com', 'Алексей', 'Иванов', '+79996009999', FALSE),
       (1,'2022-12-13','19:00:00','20:00:00', 'test71@gmail.com', 'Алексей', 'Иванов', '+79997009999', FALSE),
       (1,'2022-12-13','19:00:00','20:00:00', 'test81@gmail.com', 'Алексей', 'Иванов', '+79998009999', FALSE),
       (1,'2022-12-13','19:00:00','20:00:00', 'test2@gmail.com', 'Игнат', 'Иванов', '+79990109999', FALSE);

