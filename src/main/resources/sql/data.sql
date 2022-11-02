INSERT INTO role (name)
VALUES (UPPER('ADMIN')),
       (UPPER('MANAGER')),
       (UPPER('COACH')),
       (UPPER('CUSTOMER'));

SELECT *
FROM role;

INSERT INTO users (firstname, patronymic, lastname, email, password, phone, birth_date)
VALUES ('Иван', 'Иванович', 'Иванов', 'ivanov_admin@gmail.com', '{noop}123456', '+79173157777', '01.01.1991'),
       ('Иван', 'Иванович', 'Иванов', 'ivanov_manager@gmail.com', '{noop}123456', '+79173157776', '01.01.1991'),
       ('Иван', 'Иванович', 'Иванов', 'ivanov_coach@gmail.com', '{noop}123456', '+79173157775', '01.01.1991'),
       ('Иван', 'Иванович', 'Иванов', 'ivanov_customer@gmail.com', '{noop}123456', '+79173157774', '01.01.1991');

SELECT *
FROM users;

INSERT INTO users_role (user_id, role_id)
VALUES ((SELECT id FROM users WHERE email = 'ivanov_admin@gmail.com'),
        (SELECT id FROM role WHERE UPPER(name) = UPPER('ADMIN'))),
       ((SELECT id FROM users WHERE email = 'ivanov_manager@gmail.com'),
        (SELECT id FROM role WHERE UPPER(name) = UPPER('MANAGER'))),
       ((SELECT id FROM users WHERE email = 'ivanov_coach@gmail.com'),
        (SELECT id FROM role WHERE UPPER(name) = UPPER('COACH'))),
       ((SELECT id FROM users WHERE email = 'ivanov_customer@gmail.com'),
        (SELECT id FROM role WHERE UPPER(name) = UPPER('CUSTOMER')));

INSERT INTO coach (firstname, patronymic, lastname, birth_date, phone, email, specialization, description, image)
VALUES ('Иван', 'Иванович', 'Иванов', '01.01.1991', '+79173157777', 'ivanov_admin@gmail.com', 'фитнес – менеджер', 'Инструктор групповых программ HOT IRON, CYCLING. Здоровый человек – счастливый человек!', NULL),
       ('Иван', 'Иванович', 'Иванов', '01.01.1991', '+79173157776', 'ivanov_manager@gmail.com', 'персональный тренер, инструктор тренажерного зала', 'Инструктор групповых программ HOT IRON, CYCLING. Здоровый человек – счастливый человек!', NULL),
       ('Иван', 'Иванович', 'Иванов', '01.01.1991', '+79173157775', 'ivanov_coach@gmail.com', 'инструктор групповых программ', 'Инструктор групповых программ HOT IRON, CYCLING. Здоровый человек – счастливый человек!', NULL),
       ('Иван', 'Иванович', 'Иванов', '01.01.1991', '+79173157774', 'ivanov_customer@gmail.com', 'тренер', 'Инструктор групповых программ HOT IRON, CYCLING. Здоровый человек – счастливый человек!', NULL);

INSERT INTO training_program (name, overview, description, image)
VALUES ('СИЛОВЫЕ ТРЕНИРОВКИ','Групповые тренировки, направленные на развитие силы, выносливости и укрепления всех групп мышц. Мы предлагаем следующие направления силовых тренировок','PumpSuper sculptLower bodyUpper workBums+ABS',NULL),
       ('BODY&MIND ТРЕНИРОВКИ','Универсальная групповая программа тренировок, которая подойдет всем, включая новичков. Мы предлагаем следующие направления body&mind тренировок','ПилатесБалансЙогаЙога-ланчЙога-здоровая спинаЙога в гамакахЙога в гамакахMFR+Stretching',NULL),
       ('ФУНКЦИОНАЛЬНЫЕ ТРЕНИРОВКИ','Групповые занятия, которые помогут развить ловкость, силу, координацию и выносливость. Регулярные занятия дадут вам возможность получить физически развитое и красивое тело. Мы предлагаем следующие направления функциональных тренировок:','Functional training',NULL),
       ('СМЕШАННЫЕ ТРЕНИРОВКИ','Групповые занятия, основанные на комбинированных тренировках - это одновременное сочетание силовых упражнений с аэробными нагрузками. Помогут значительно улучшить физическую форму. Мы предлагаем следующие направления смешанных тренировок','ABS+stretching',NULL),
       ('ТАНЦЕВАЛЬНЫЕ ТРЕНИРОВКИ','Групповые тренировки, которые сочетают в себе аэробные и кардио нагрузки. Эффективно сжигают лишние калории, избавляют от лишнего веса и делают осанку и тело красивее. Мы предлагаем следующие направления танцевальных тренировок','Belly danceBelly PROZumba',NULL),
       ('КАРДИО ТРЕНИРОВКИ','Универсальная групповая программа тренировок для укрепления сердечной мышцы, а также улучшения состояния систем кровообращения и дыхания. Отлично подходят для похудения. Мы предлагаем следующие направления кардио-тренировок','Степ PRO',NULL);

INSERT INTO training (name, start_time, end_time, day_of_week)
VALUES ('Yoga','10:00','11:00','1'),
        ('Tabata','10:00','11:00','2'),
        ('Stretch','10:00','11:00','3'),
        ('Yoga','10:00','11:00','4'),
        ('Body Sculpt','10:00','11:00','5'),
        ('Belly Dance','10:00','11:00','6'),
        ('Pilates','10:00','11:00','7');

INSERT INTO subscription (name, period, price)
VALUES ('1 месяц','1 месяц','3500'),
       ('3 месяца','3 месяца','7700'),
       ('6 месяцев','6 месяцев','13800'),
       ('12 месяц','12 месяц','23300');
